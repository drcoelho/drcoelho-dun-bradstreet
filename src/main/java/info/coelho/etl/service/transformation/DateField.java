package info.coelho.etl.service.transformation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateField implements FieldTransformer {

    private final String fieldName;

    public DateField(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public Object getFieldName() {
        return fieldName;
    }

    @Override
    public Object getFieldValue(Object rawFieldValue) {
        Objects.requireNonNull(rawFieldValue);

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(rawFieldValue.toString(), inputFormatter);

        LocalDateTime localDateTime = localDate.atStartOfDay();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(outputFormatter);
    }
}
