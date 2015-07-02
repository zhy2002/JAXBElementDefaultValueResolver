package zhy2002.jaxb;

/**
 * JAXB does not set element default values on the generated DTO fields.
 * Instead it generates an annotation that documents the default value of the generated DTO field.
 * The implementation of this class should grab these default values from the annotation and set the default value on DTO fields.
 */
public interface JAXBElementDefaultValueResolver {

    /**
     * Set field default values on the given JAXB object.
     * @param jaxbObject an jaxb object.
     */
    void resolve(Object jaxbObject);
}
