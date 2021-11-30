package info.coelho.etl.service.transformation;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityDataTransformer {

    private static final ImmutableMap<String, FieldTransformer> FIELD_TRANSFORMERS = ImmutableMap.<String, FieldTransformer>builder()
            .put("userName", new DefaultField("user"))
            .put("websiteName", new DefaultField("website"))
            .put("activityTypeCode", new ActivityDescriptionField())
            .put("loggedInTime", new DateField("signedInTime"))
            .build();

    public Map<Object, Object> transform(Map<Object, Object> fields) {

        Map<Object, Object> transformedData = new HashMap<>();

        for (Map.Entry<Object, Object> entry : fields.entrySet()) {

            Object rawFieldName = entry.getKey();
            Object rawFieldValue = entry.getValue();

            if (FIELD_TRANSFORMERS.containsKey(rawFieldName)) {
                FieldTransformer fieldTransformer = FIELD_TRANSFORMERS.get(rawFieldName);
                Object transformedFieldName = fieldTransformer.getFieldName();
                Object transformedFieldValue = fieldTransformer.getFieldValue(rawFieldValue);
                transformedData.put(transformedFieldName, transformedFieldValue);
            }

        }

        return transformedData;

    }

}
