package info.coelho.etl.service.extraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ActivityParserXML implements ActivityParser {

    private final ActivityXmlParser parser;

    @Autowired
    public ActivityParserXML(ActivityXmlParser parser) {
        this.parser = parser;
    }

    @Override
    public Map<Object, Object> parse(String xmlData) throws IOException {
        return parser.parse(xmlData);
    }

    @Override
    public boolean canParse(String contentType) {
        return contentType.contains(MediaType.APPLICATION_XML_VALUE);
    }

}
