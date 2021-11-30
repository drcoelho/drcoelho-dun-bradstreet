package info.coelho.etl.service;

import info.coelho.etl.service.publishing.ActivityOutputFileWriter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Scanner;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivitiesResourceApiTest {

    public static final String XML_EXAMPLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<activity>\n" +
            "  <userName>Williamson</userName>\n" +
            "  <websiteName>xyz.com</websiteName>\n" +
            "  <activityTypeCode>001</activityTypeCode>\n" +
            "  <loggedInTime>2020-01-13</loggedInTime>\n" +
            "  <number_of_views>10</number_of_views>\n" +
            "</activity>";

    public static final String JSON_EXAMPLE = "{" +
            "    \"activity\" : {\n" +
            "        \"userName\" : \"Sam\",\n" +
            "        \"websiteName\" : \"abc.com\",\n" +
            "        \"activityTypeDescription\" : \"Viewed\",\n" +
            "        \"signedInTime\" : \"01/13/2020\"\n" +
            "    }\n" +
            "}";

    private static final String INVALID_XML_EXAMPLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<activity>\n" +
            "  <userName>Williamson</userName>\n" +
            "  <undefinedField>Value</undefinedField>\n" +
            "  <websiteName>xyz.com</websiteName>\n" +
            "  <activityTypeCode>001</activityTypeCode>\n" +
            "  <loggedInTime>2020-01-13</loggedInTime>\n" +
            "  <number_of_views>10</number_of_views>\n" +
            "</activity>";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void beforeEach() {
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    void should_accept_xml_as_request_content_type() {

        given()
                .log().all()
                .contentType(ContentType.XML)
                .body(XML_EXAMPLE)
                .post("/activities")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    void should_accept_json_as_request_content_type() {

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(JSON_EXAMPLE)
                .post("/activities")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    void should_not_accept_invalid_fields() {
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body(INVALID_XML_EXAMPLE)
                .post("/activities")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void should_not_accept_invalid_contentType() {
        given()
                .log().all()
                .contentType(ContentType.TEXT)
                .body(INVALID_XML_EXAMPLE)
                .post("/activities")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    void given_a_XML_request_should_persist_json_at_output_file() throws FileNotFoundException {

        String randomName = "David + " + UUID.randomUUID();
        String modifiedXml = XML_EXAMPLE.replaceFirst("Williamson", randomName);

        given()
                .log().all()
                .contentType(ContentType.XML)
                .body(modifiedXml)
                .post("/activities")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);

        Awaitility.await()
                .pollDelay(Duration.ofSeconds(1))
                .pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(20))
                .untilAsserted(() -> outputFileContains(randomName));
    }

    @Test
    void given_a_JSON_request_should_persist_json_at_output_file() throws FileNotFoundException {

        String randomName = "David - json + " + UUID.randomUUID();
        String modifiedJson = JSON_EXAMPLE.replaceFirst("Sam", randomName);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(modifiedJson)
                .post("/activities")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);

        Awaitility.await()
                .pollDelay(Duration.ofSeconds(1))
                .pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(20))
                .untilAsserted(() -> outputFileContains(randomName));
    }

    private void outputFileContains(String value) throws FileNotFoundException {

        boolean found = false;

        File outputFile = new File(ActivityOutputFileWriter.OUTPUT_FILENAME);

        if (!outputFile.exists()) {
            Assertions.fail("Output file does not exists");
        }

        Scanner scanner = new Scanner(outputFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(value)) {
                found = true;
            }
        }

        if (!found) {
            Assertions.fail("Content not found at file output.json. Content: " + value);
        }

    }



}
