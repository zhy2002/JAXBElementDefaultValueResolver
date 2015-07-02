package zhy2002.jaxb.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhy2002.jaxb.JAXBElementDefaultValueResolver;
import zhy2002.jaxb.test.TestSpringConfiguration;
import zhy2002.jaxb.test.example.EnumObject;
import zhy2002.jaxb.test.example.TestJaxbObject;
import zhy2002.jaxb.test.example.TestJaxbObject2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;


/**
 * Unit tests for BaseJAXBElementValidator.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringConfiguration.class})
public class ClassNameDefaultValueResolverTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassNameDefaultValueResolverTest.class);

    @Autowired
    private JAXBElementDefaultValueResolver resolver;

    private void resolve(Object object) {
        long time = System.currentTimeMillis();
        resolver.resolve(object);
        logger.info("Time taken (ms):" + (System.currentTimeMillis() - time));
    }

    @Test
    public void shouldSetDefaultsWhenFieldIsNull() {

        TestJaxbObject testJaxbObject = new TestJaxbObject();

        resolve(testJaxbObject);

        checkAllDefaults(testJaxbObject);
    }

    @Test
    public void shouldSetDefaultsForNestedObject() {
        TestJaxbObject parentObject = new TestJaxbObject();
        TestJaxbObject testJaxbObject = new TestJaxbObject();
        parentObject.setNestedObjectProperty(testJaxbObject);

        resolve(parentObject);

        checkAllDefaults(testJaxbObject);
    }

    @Test
    public void shouldSetDefaultsForListChildObjects() {
        TestJaxbObject parentObject = new TestJaxbObject();
        TestJaxbObject testJaxbObject1 = new TestJaxbObject();
        TestJaxbObject testJaxbObject2 = new TestJaxbObject();
        parentObject.setListProperty(new ArrayList<TestJaxbObject>());
        parentObject.getListProperty().add(testJaxbObject1);
        parentObject.getListProperty().add(testJaxbObject2);

        resolve(parentObject);

        checkAllDefaults(testJaxbObject1);
        checkAllDefaults(testJaxbObject2);
    }

    private TestJaxbObject createTestJaxbObject() {
        TestJaxbObject object = new TestJaxbObject();
        object.setBigDecimalProperty(new BigDecimal("99"));
        object.setBooleanProperty(Boolean.FALSE);
        object.setStringProperty("Random");
        object.setEnumProperty(EnumObject.VAL3);
        object.setIntegerProperty(1999);
        return object;
    }

    @Test
    public void shouldNotOverrideExplicitValue() {
        TestJaxbObject referenceObject = createTestJaxbObject();
        TestJaxbObject parentObject = createTestJaxbObject();
        TestJaxbObject testJaxbObject1 = createTestJaxbObject();
        TestJaxbObject testJaxbObject2 = createTestJaxbObject();
        TestJaxbObject testJaxbObject3 = createTestJaxbObject();
        parentObject.setNestedObjectProperty(testJaxbObject1);
        parentObject.setListProperty(new ArrayList<TestJaxbObject>());
        parentObject.getListProperty().add(testJaxbObject2);
        parentObject.getListProperty().add(testJaxbObject3);

        resolve(parentObject);

        compareSimpleProperties(referenceObject, parentObject);
        compareSimpleProperties(referenceObject, testJaxbObject1);
        compareSimpleProperties(referenceObject, testJaxbObject2);
        compareSimpleProperties(referenceObject, testJaxbObject3);
    }

    @Test
    public void shouldNotFallInDeadLoop() {
        TestJaxbObject testJaxbObject = new TestJaxbObject();
        testJaxbObject.setNestedObjectProperty(testJaxbObject);
        testJaxbObject.setListProperty(new ArrayList<TestJaxbObject>());
        TestJaxbObject testJaxbObject2 = new TestJaxbObject();
        testJaxbObject.getListProperty().add(testJaxbObject2);
        testJaxbObject2.setNestedObjectProperty(testJaxbObject);
        TestJaxbObject referenceObject = new TestJaxbObject();
        resolver.resolve(referenceObject);

        resolve(testJaxbObject);

        compareSimpleProperties(referenceObject, testJaxbObject);
        compareSimpleProperties(referenceObject, testJaxbObject2);

    }

    private void compareSimpleProperties(TestJaxbObject referenceObject, TestJaxbObject testJaxbObject) {
        assertEquals(referenceObject.getBigDecimalProperty(), testJaxbObject.getBigDecimalProperty());
        assertEquals(referenceObject.getStringProperty(), testJaxbObject.getStringProperty());
        assertEquals(referenceObject.isBooleanProperty(), testJaxbObject.isBooleanProperty());
        assertEquals(referenceObject.getIntegerProperty(), testJaxbObject.getIntegerProperty());
        assertEquals(referenceObject.getEnumProperty(), testJaxbObject.getEnumProperty());
    }

    private void checkAllDefaults(TestJaxbObject testJaxbObject) {
        assertEquals(new Boolean(TestJaxbObject.DEFAULT_BOOLEAN), testJaxbObject.isBooleanProperty());
        assertEquals(new BigDecimal(TestJaxbObject.DEFAULT_BIG_DECIMAL), testJaxbObject.getBigDecimalProperty());
        assertEquals(Integer.valueOf(TestJaxbObject.DEFAULT_INTEGER), testJaxbObject.getIntegerProperty());
        assertEquals(TestJaxbObject.DEFAULT_SPTRING, testJaxbObject.getStringProperty());
        assertEquals(EnumObject.fromValue(TestJaxbObject.DEFAULT_ENUM), testJaxbObject.getEnumProperty());
        assertEquals(null, testJaxbObject.getListProperty());
        assertEquals(null, testJaxbObject.getNestedObjectProperty());
    }

    @Test
    public void excludedClassesTest(){
        //before test
        ClassNameDefaultValueResolver defaultValueResolver = (ClassNameDefaultValueResolver) resolver;
        defaultValueResolver.setExcludedClasses(new HashSet<String>());
        defaultValueResolver.getExcludedClasses().add(TestJaxbObject.class.getName());

        excludeNestedObjectTest();

        //after test
        defaultValueResolver.setExcludedClasses(null);
    }

    @Test
    public void inspectedClassesTest(){
        //before test
        ClassNameDefaultValueResolver defaultValueResolver = (ClassNameDefaultValueResolver) resolver;
        final String packageName = defaultValueResolver.getInspectedPackage();
        defaultValueResolver.setInspectedPackage(null);
        defaultValueResolver.setInspectedClasses(new HashSet<String>());
        defaultValueResolver.getInspectedClasses().add(TestJaxbObject2.class.getName());

        excludeNestedObjectTest();

        //after test
        defaultValueResolver.setInspectedPackage(packageName);
        defaultValueResolver.setInspectedClasses(null);
    }

    private void excludeNestedObjectTest() {
        //excluded
        TestJaxbObject2 testJaxbObject2 = new TestJaxbObject2();
        TestJaxbObject testJaxbObject = new TestJaxbObject();
        testJaxbObject2.setNestedObjectProperty(testJaxbObject);
        TestJaxbObject2 referenceObject = new TestJaxbObject2();
        resolver.resolve(referenceObject);

        resolve(testJaxbObject2);

        assertNull(testJaxbObject.getEnumProperty());
        assertNull(testJaxbObject.getStringProperty());
        assertNull(testJaxbObject.getBigDecimalProperty());
        assertNull(testJaxbObject.getIntegerProperty());
        assertNull(testJaxbObject.isBooleanProperty());
        compareSimpleProperties(referenceObject, testJaxbObject2);
    }

}
