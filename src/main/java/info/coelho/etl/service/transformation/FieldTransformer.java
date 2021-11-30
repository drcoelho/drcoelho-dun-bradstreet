package info.coelho.etl.service.transformation;

public interface FieldTransformer {

    Object getFieldName();
    Object getFieldValue(Object rawFieldValue);

}
