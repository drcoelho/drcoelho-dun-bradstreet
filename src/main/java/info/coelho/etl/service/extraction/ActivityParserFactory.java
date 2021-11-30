package info.coelho.etl.service.extraction;

import info.coelho.etl.service.ActivityProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityParserFactory {

    private final List<ActivityParser> parsers;

    @Autowired
    public ActivityParserFactory(List<ActivityParser> parsers) {
        this.parsers = parsers;
    }

    public ActivityParser getInstance(String contentType) {

        for (ActivityParser parser : parsers) {
            if (parser.canParse(contentType)) {
                return parser;
            }
        }

        throw new ActivityProcessingException("Invalid contentType: " + contentType);
    }

}
