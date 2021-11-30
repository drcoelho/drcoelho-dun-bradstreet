package info.coelho.etl.service.publishing;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ActivityOutputFileWriter {

    private static final Logger logger = LoggerFactory.getLogger(ActivityOutputFileWriter.class);

    public static String OUTPUT_FILENAME = "output.json";

    @Async
    public void write(String json) throws IOException {
        logger.info("Writing content to output file. Content: {}", json);
        File file = new File(OUTPUT_FILENAME);
        FileUtils.writeStringToFile(file, json + "\n", StandardCharsets.UTF_8, true);
    }


}
