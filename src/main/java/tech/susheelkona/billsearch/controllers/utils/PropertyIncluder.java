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

import java.util.Arrays;

/**
 * @author Susheel Kona
 */
public class PropertyIncluder {
    private String[] includedProperties;
    private PaginatedResponse resource;
    private ObjectMapper mapper;

    public PropertyIncluder(String[] includedProperties, PaginatedResponse resource) {
        this.includedProperties = includedProperties;
        this.resource = resource;
        mapper = new ObjectMapper();
    }

    public String[] getIncludedProperties() {
        return includedProperties;
    }

    public void setIncludedProperties(String[] includedProperties) {
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
//                    if (Arrays.asList(includedProperties).contains(writer.getName().toString())) {
                    if(false) {
                        writer.serializeAsField(pojo, jgen, provider);
                        return;
                    }
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
