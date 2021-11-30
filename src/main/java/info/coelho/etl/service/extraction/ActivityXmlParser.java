package info.coelho.etl.service.extraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import info.coelho.etl.service.ActivityProcessingException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

@Service
public class ActivityXmlParser {

    private static final Logger logger = LoggerFactory.getLogger(ActivityXmlParser.class);

    public static final String SCHEMA_FILE = "activities.xsd";

    public Map<Object, Object> parse(String xmlData) throws IOException {

        validate(xmlData);
        
        ObjectMapper mapper = new XmlMapper();
        return mapper.readValue(xmlData, Map.class);

    }

    private void validate(String xmlFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(getResource(SCHEMA_FILE)));

            InputStream is = IOUtils.toInputStream(xmlFile, Charset.defaultCharset());

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(is));
        } catch (SAXException | IOException e) {
            throw new ActivityProcessingException(e);
        }
    }

    private String getResource(String filename) throws FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(filename);
        Objects.requireNonNull(resource);
        return resource.getFile();
    }

}
