package info.coelho.etl.service.transformation;

public class DefaultField implements FieldTransformer {

    private final Object fieldName;

    public DefaultField(Object fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldName() {
        return fieldName;
    }

    public Object getFieldValue(Object rawFieldValue) {
        return rawFieldValue;
    }
}
