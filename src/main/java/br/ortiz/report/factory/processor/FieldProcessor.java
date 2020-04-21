package br.ortiz.report.factory.processor;

import br.ortiz.report.factory.annotations.Field;
import br.ortiz.to.Person;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FieldProcessor {

    private List<FieldMetadata> getFieldMetadata(Class type) {
        final List<java.lang.reflect.Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(type, Field.class);
        return fieldsListWithAnnotation.stream().map((java.lang.reflect.Field f) -> {
            final FieldMetadata fieldMetadata = new FieldMetadata();
            fieldMetadata.setFieldType(f.getType().getTypeName());
            fieldMetadata.setFieldName(f.getName());
            fieldMetadata.setUuid(UUID.randomUUID().toString());
            return fieldMetadata;
        }).collect(Collectors.toList());
    }


    public String generateJrxml() throws Exception {
        VelocityEngine velocityEngine = new VelocityEngine();

        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        velocityEngine.init();

        Template t = velocityEngine.getTemplate("init_model.vm");
        VelocityContext context = new VelocityContext();
        context.put("fields", getFieldMetadata(Person.class));

        StringWriter writer = new StringWriter();
        t.merge( context, writer );

        return writer.toString();
    }

}
