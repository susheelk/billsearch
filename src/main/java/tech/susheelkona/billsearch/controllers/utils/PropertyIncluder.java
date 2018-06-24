package tech.susheelkona.billsearch.controllers.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import tech.susheelkona.billsearch.model.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Susheel Kona
 */
public class PropertyIncluder {

    private static final List<String> alwaysInclude = new ArrayList<String>(Arrays.asList("id"));

    private List<String> includedProperties;
    private PaginatedResponse resource;
    private ObjectMapper mapper;

    public PropertyIncluder(String[] includedProperties, PaginatedResponse resource) {
        this.includedProperties = Arrays.asList(includedProperties);
        this.resource = resource;
        mapper = new ObjectMapper();
    }

    public List<String> getIncludedProperties() {
        return includedProperties;
    }

    public void setIncludedProperties(List<String> includedProperties) {
        this.includedProperties = includedProperties;
    }

    public PaginatedResponse getResource() {
        return resource;
    }

    public String serialize() throws JsonProcessingException {
        PropertyFilter propertyFilter = new SimpleBeanPropertyFilter(){
            @Override
            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
                if (include(writer)) {
                    String fieldName = writer.getName().toString();
                    if ((includedProperties.contains(fieldName)) || (includedProperties.contains("all")) || (alwaysInclude.contains(fieldName))) {
                        writer.serializeAsField(pojo, jgen, provider);
                        return;
                    }
                } else if (!jgen.canOmitFields()) { // since 2.3
                    writer.serializeAsOmittedField(pojo, jgen, provider);
                }
            }

        };

        FilterProvider filters = new SimpleFilterProvider().addFilter("includer", propertyFilter);
        return mapper.writer(filters).writeValueAsString(resource);
    }

    public void setResource(PaginatedResponse resource) {
        this.resource = resource;
    }
}
