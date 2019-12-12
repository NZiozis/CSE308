package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import mm_districting.Precinct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NeighborSetSerializer extends StdSerializer<Set<Precinct>> {
    public NeighborSetSerializer() {
        this(null);
    }

    public NeighborSetSerializer(Class<Set<Precinct>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Precinct> precincts, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        List<String> ids = new ArrayList<>();
        for (Precinct precinct : precincts) {
            ids.add(precinct.getGeoId());
        }
        jsonGenerator.writeObject(ids);
    }
}
