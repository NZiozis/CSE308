package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import mm_districting.DemographicContext;
import org.codehaus.jackson.map.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.logging.Logger;

public class DemographicContextConverter implements AttributeConverter<DemographicContext,String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DemographicContext demographicContext) {
        String demographicContextJson = null;

        try {
            demographicContextJson = objectMapper.writeValueAsString(demographicContext);
        }
        catch (final IOException e) {
            System.out.println(e);
        }

        return demographicContextJson;
    }

    @Override
    public DemographicContext convertToEntityAttribute(String demographicContextJson) {
        DemographicContext context = null;
        try {
            context = objectMapper.readValue(demographicContextJson, DemographicContext.class);
        } catch (final IOException e) {
            System.out.println(e);
        }


        return context;
    }
}
