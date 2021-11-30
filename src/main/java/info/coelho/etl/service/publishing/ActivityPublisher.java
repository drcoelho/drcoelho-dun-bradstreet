package info.coelho.etl.service.publishing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import info.coelho.etl.service.ActivityProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ActivityPublisher {

    private final ActivityOutputFileWriter outputFileWriter;

    @Autowired
    public ActivityPublisher(ActivityOutputFileWriter outputFileWriter) {
        this.outputFileWriter = outputFileWriter;
    }

    public void publish(Map<Object, Object> fields) throws IOException {
        ObjectMapper mapper = new JsonMapper();
        String json = mapper.writeValueAsString(fields);
        outputFileWriter.write(json);
    }

}
