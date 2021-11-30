package info.coelho.etl.service;

import info.coelho.etl.service.extraction.ActivityParser;
import info.coelho.etl.service.extraction.ActivityParserFactory;
import info.coelho.etl.service.publishing.ActivityPublisher;
import info.coelho.etl.service.transformation.ActivityDataTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/activities")
public class ActivityResource {

    private static final Logger logger = LoggerFactory.getLogger(ActivityResource.class);

    private final ActivityParserFactory factory;
    private final ActivityDataTransformer transformer;
    private final ActivityPublisher publisher;

    @Autowired
    public ActivityResource(ActivityParserFactory factory, ActivityDataTransformer transformer, ActivityPublisher publisher) {
        this.factory = factory;
        this.transformer = transformer;
        this.publisher = publisher;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void post(@RequestBody String data, @RequestHeader("Content-Type") String contentType) throws IOException {

        ActivityParser parser = factory.getInstance(contentType);
        ActivityProcessor processor = new ActivityProcessor(parser, transformer, publisher);
        processor.process(data);
    }

}
