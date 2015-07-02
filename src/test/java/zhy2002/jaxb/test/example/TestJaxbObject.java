package zhy2002.jaxb.test.example;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestJaxbObject", propOrder = {
        "integerProperty",
        "stringProperty",
        "bigDecimalProperty",
        "booleanProperty"
})
public class TestJaxbObject {

    public static final String DEFAULT_INTEGER = "65";
    public static final String DEFAULT_BIG_DECIMAL = "59.89";
    public static final String DEFAULT_SPTRING = "Default Value";
    public static final String DEFAULT_BOOLEAN = "true";
    public static final String DEFAULT_ENUM = "VAL2";

    @XmlElement(defaultValue = DEFAULT_INTEGER)
    protected Integer integerProperty;

    @XmlElement(defaultValue = DEFAULT_SPTRING)
    protected String stringProperty;

    @XmlElement(defaultValue = DEFAULT_BIG_DECIMAL)
    protected BigDecimal bigDecimalProperty;

    @XmlElement(defaultValue = DEFAULT_BOOLEAN)
    protected Boolean booleanProperty;

    @XmlElement(defaultValue = DEFAULT_ENUM)
    protected EnumObject enumProperty;

    protected List<TestJaxbObject> listProperty;

    protected TestJaxbObject nestedObjectProperty;

    public Integer getIntegerProperty() {
        return integerProperty;
    }

    public void setIntegerProperty(Integer integerProperty) {
        this.integerProperty = integerProperty;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public BigDecimal getBigDecimalProperty() {
        return bigDecimalProperty;
    }

    public void setBigDecimalProperty(BigDecimal bigDecimalProperty) {
        this.bigDecimalProperty = bigDecimalProperty;
    }

    public EnumObject getEnumProperty() {
        return enumProperty;
    }

    public void setEnumProperty(EnumObject enumProperty) {
        this.enumProperty = enumProperty;
    }

    public Boolean isBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(Boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }

    public List<TestJaxbObject> getListProperty() {
        return listProperty;
    }

    public void setListProperty(List<TestJaxbObject> listProperty) {
        this.listProperty = listProperty;
    }

    public TestJaxbObject getNestedObjectProperty() {
        return nestedObjectProperty;
    }

    public void setNestedObjectProperty(TestJaxbObject nestedObjectProperty) {
        this.nestedObjectProperty = nestedObjectProperty;
    }
}
