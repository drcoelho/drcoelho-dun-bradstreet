package info.coelho.etl.service;

import info.coelho.etl.service.extraction.ActivityParser;
import info.coelho.etl.service.publishing.ActivityPublisher;
import info.coelho.etl.service.transformation.ActivityDataTransformer;

import java.io.IOException;
import java.util.Map;

public class ActivityProcessor {

    private final ActivityParser parser;
    private final ActivityDataTransformer dataTransformer;
    private final ActivityPublisher publisher;

    public ActivityProcessor(ActivityParser parser, ActivityDataTransformer dataTransformer, ActivityPublisher publisher) {
        this.parser = parser;
        this.dataTransformer = dataTransformer;
        this.publisher = publisher;
    }

    public void process(String data) throws IOException {
        Map<Object, Object> fields = parser.parse(data);
        Map<Object, Object> transformedData = dataTransformer.transform(fields);
        publisher.publish(transformedData);
    }

}
