package info.coelho.etl.service.transformation;

import com.google.common.collect.ImmutableMap;

import java.util.Objects;

public class ActivityDescriptionField extends DefaultField {

    private static final ImmutableMap<String, Object> TYPE_DESCRIPTION = ImmutableMap.of(
            "001", "Viewed",
            "002", "Purchased"
    );

    public ActivityDescriptionField() {
        super("activityTypeDescription");
    }

    @Override
    public Object getFieldValue(Object rawFieldValue) {
        Objects.requireNonNull(rawFieldValue);

        String key = rawFieldValue.toString();
        return TYPE_DESCRIPTION.getOrDefault(key, rawFieldValue);
    }

}
