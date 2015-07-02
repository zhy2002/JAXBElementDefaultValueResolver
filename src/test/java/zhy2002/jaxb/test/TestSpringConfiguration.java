package zhy2002.jaxb.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import zhy2002.jaxb.JAXBElementDefaultValueResolver;
import zhy2002.jaxb.impl.ClassNameDefaultValueResolver;

@Configuration
public class TestSpringConfiguration {

    @Bean
    public JAXBElementDefaultValueResolver getJAXBElementDefaultValueResolver(){
        ClassNameDefaultValueResolver resolver = new ClassNameDefaultValueResolver(getDefaultConversionService());
        resolver.setInspectedPackage("zhy2002.jaxb.test.example");
        return resolver;
    }

    @Bean
    public ConversionService getDefaultConversionService(){
        return new DefaultConversionService();
    }

}