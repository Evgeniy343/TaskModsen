package by.evgen.taskmodsen.deserializer;

import by.evgen.taskmodsen.entity.Event;
import by.evgen.taskmodsen.entity.Location;
import by.evgen.taskmodsen.entity.Organizer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class EventDeserializer extends StdDeserializer<Event> {

    private static final String NAME_OF_DESCRIPTION_FIELD = "description";
    private static final String NAME_OF_SUBJECT_FIELD = "subject";
    private static final String NAME_OF_DATE_FIELD = "date";
    private static final String NAME_OF_LOCATION_OBJECT = "location";
    private static final String NAME_OF_CITY_FIELD = "city";
    private static final String NAME_OF_STREET_FIELD = "street";
    private static final String NAME_OF_HOUSE_NUMBER_FIELD = "houseNumber";
    private static final String NAME_OF_ORGANIZER_OBJECT = "organizer";
    private static final String NAME_OF_NAME_FIELD = "name";
    private static final String NAME_OF_SURNAME_FIELD = "surname";
    private static final String NAME_OF_PATRONYMIC_FIELD = "patronymic";

    public EventDeserializer() {
        this(null);
    }

    public EventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Event deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode eventNode = jsonParser.getCodec().readTree(jsonParser);

        Location location = getLocationFromJson(eventNode);
        Organizer organizer = getOrganizerFromJson(eventNode);

        Event event = Event.builder()
                .description(eventNode.get(NAME_OF_DESCRIPTION_FIELD).textValue())
                .location(location)
                .subject(eventNode.get(NAME_OF_SUBJECT_FIELD).textValue())
                .date(Timestamp.valueOf(eventNode.get(NAME_OF_DATE_FIELD).textValue()))
                .organizer(organizer)
                .build();

        return event;
    }

    private Location getLocationFromJson(JsonNode eventNode) {
        JsonNode locationNode = eventNode.get(NAME_OF_LOCATION_OBJECT);
        Location location = Location.builder()
                .city(locationNode.get(NAME_OF_CITY_FIELD).textValue())
                .street(locationNode.get(NAME_OF_STREET_FIELD).textValue())
                .houseNumber(Integer.valueOf(locationNode.get(NAME_OF_HOUSE_NUMBER_FIELD).textValue()))
                .build();
        return location;
    }

    private Organizer getOrganizerFromJson(JsonNode eventNode) {
        JsonNode organizerNode = eventNode.get(NAME_OF_ORGANIZER_OBJECT);

        Organizer organizer = Organizer.builder()
                .name(organizerNode.get(NAME_OF_NAME_FIELD).textValue())
                .surname(organizerNode.get(NAME_OF_SURNAME_FIELD).textValue())
                .patronymic(organizerNode.get(NAME_OF_PATRONYMIC_FIELD).textValue())
                .events(new ArrayList<>())
                .build();
        return organizer;
    }

}
