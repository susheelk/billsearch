package tech.susheelkona.billsearch.controllers.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Susheel Kona
 */
public class PropertyIncluder {

    private static final List<String> alwaysInclude = new ArrayList<>(Arrays.asList("id", "url"));

    private List<String> includedProperties;
    private List<String> excludedProperties;
    private PaginatedResponse resource;
    private ObjectMapper objectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    public PropertyIncluder(String[] includedProperties,PaginatedResponse resource) {
        this.includedProperties = Arrays.asList(includedProperties);
        this.excludedProperties = Collections.emptyList();
        this.resource = resource;
        objectMapper = new ObjectMapper();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(format);
    }

    public PropertyIncluder(String[] includedProperties, String[] excludedProperties, PaginatedResponse resource) {
        this(includedProperties, resource);
        this.excludedProperties = Arrays.asList(excludedProperties);
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
                    if (((includedProperties.contains(fieldName)) || (includedProperties.contains("all")) || (alwaysInclude.contains(fieldName)))
                            && !excludedProperties.contains(fieldName)) {
                        writer.serializeAsField(pojo, jgen, provider);
                        return;
                    }
                } else if (!jgen.canOmitFields()) {
                    writer.serializeAsOmittedField(pojo, jgen, provider);
                }
            }

        };

        FilterProvider filters = new SimpleFilterProvider().addFilter("includer", propertyFilter);
        return objectMapper.writer(filters).writeValueAsString(resource);
    }

    public void setResource(PaginatedResponse resource) {
        this.resource = resource;
    }
}
