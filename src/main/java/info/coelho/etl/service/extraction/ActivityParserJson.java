package info.coelho.etl.service.extraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.coelho.etl.service.ActivityProcessingException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityParserJson implements ActivityParser {

    @Override
    public Map<Object, Object> parse(String data) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        ActivityJsonEntity activity = mapper.readValue(data, ActivityJsonEntity.class);

        Map<Object, Object> fields = new HashMap<>();
        fields.put("userName", activity.getUserName());
        fields.put("websiteName", activity.getWebsiteName());
        fields.put("activityTypeCode", activity.getActivityTypeDescription());
        fields.put("loggedInTime", activity.getSignedInTimeFormatted());

        return fields;
    }

    @Override
    public boolean canParse(String contentType) {
        return contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }

}
