# JAXBElementDefaultValueResolver
When JAXB classes are generated, the element default values are not mapped to the corresponding class fields.
There is a default value plugin which can set default values for the basic types such as Boolean, Integer, String and Enums: 
https://java.net/projects/jaxb2-commons/pages/Default-Value

However if you need to map other types of element values, e.g. BigDecimal, then the above plugin-in will not work.

Therefore I created this Spring bean which can be used to set element default values for JAXB objects at run time by inspecting the JAXB annotations on the class.
The implementation of type convertion is delegated to Spring.

Usage:

<pre>
@Autowired
private JAXBElementDefaultValueResolver resolver;
......
TestJaxbObject testJaxbObject = new TestJaxbObject();
resolver.resolve(object);
</pre>

Details can be found in the unit tests.
