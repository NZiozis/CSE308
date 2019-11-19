package com.majmin;

import mm_districting.DemographicContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.DemographicContextConverter;

import static junit.framework.TestCase.assertEquals;

public class ConvertersTests {
    ObjectMapper                objectMapper                = new ObjectMapper();
    DemographicContextConverter demographicContextConverter = new DemographicContextConverter();

    @Test
    void convertingDemographicContext() {
        DemographicContext demographicContext = new DemographicContext(100);
        String temp = demographicContextConverter.convertToDatabaseColumn(demographicContext);
        DemographicContext newContext = demographicContextConverter.convertToEntityAttribute(temp);
        assertEquals(demographicContext.getTotalPopulation(), newContext.getTotalPopulation());
        assertEquals(demographicContext.getRaceOfBloc(), newContext.getRaceOfBloc());
    }

}
