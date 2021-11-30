package info.coelho.etl.service.extraction;

import java.io.IOException;
import java.util.Map;

public interface ActivityParser {

    Map<Object, Object> parse(String data) throws IOException;

    boolean canParse(String contentType);
}
