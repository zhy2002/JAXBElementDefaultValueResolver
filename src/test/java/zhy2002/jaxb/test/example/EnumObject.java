package zhy2002.jaxb.test.example;


import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum EnumObject {

    VAL1,
    VAL2,
    VAL3;

    public String value() {
        return name();
    }

    public static EnumObject fromValue(String v) {
        return valueOf(v);
    }

}