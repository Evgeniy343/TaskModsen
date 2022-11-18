package by.evgen.taskmodsen.service;

import by.evgen.taskmodsen.entity.Event;
import by.evgen.taskmodsen.entity.Location;
import by.evgen.taskmodsen.entity.Organizer;
import by.evgen.taskmodsen.exception.EventDuplicateException;
import by.evgen.taskmodsen.exception.EventNotFoundException;
import by.evgen.taskmodsen.exception.IncorrectDataException;
import by.evgen.taskmodsen.repository.EventRepository;
import by.evgen.taskmodsen.repository.OrganizerRepository;
import by.evgen.taskmodsen.validator.ModelValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service("eventServiceImpl")
public class EventServiceImpl implements EventService {

    private static final String EVENT_NOT_FOUND_MESSAGE = "Event with id - %s is not found!";
    private static final String THE_ORGANIZER_DID_NOT_MATCH_MESSAGE = "The organizer didn't match the current";
    private static final String THE_LOCATION_DID_NOT_MATCH_MESSAGE = "The location didn't match the current";
    private static final String EVENT_IS_EXIST_IN_DB_MESSAGE = "This event already exist in Db!";
    private static final String DELETE_NON_EXISTENT_EVENT_MESSAGE = "You try to delete non-existent event!";
    private static final String UPDATE_NON_EXISTENT_EVENT_MESSAGE = "You try to update non-existent event!";
    private static final String FIELD_NOT_BE_NULL_MESSAGE = "Fields must not be null!";

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final LocationService locationService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, OrganizerRepository organizerRepository
            , LocationService locationService) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.locationService = locationService;
    }

    @Override
    public Event findById(Long id) {
        Event event = eventRepository.findById(id);
        if (event == null) {
            throw new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, id));
        }
        return event;
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public void save(Event model) {
        try {
            if (ModelValidatorImpl.getModelValidator().isSomeOfFieldsNull(model)) {
                throw new IncorrectDataException(FIELD_NOT_BE_NULL_MESSAGE);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Organizer currentOrganizer = model.getOrganizer();
        Location currentLocation = model.getLocation();
        setIdForUpdateIfOrganizerExistInDb(currentOrganizer);
        setIdFoUpdateIfLocationExistInDb(currentLocation);
        List<Event> events = eventRepository.findAll();
        if (ModelValidatorImpl.getModelValidator().isModelDuplicateInDb(model, events)) {
            throw new EventDuplicateException(EVENT_IS_EXIST_IN_DB_MESSAGE);
        } else {
            eventRepository.save(model);
        }
    }

    @Override
    public void update(Event model) {
        Event eventNeedUpdate = eventRepository.findById(model.getId());
        if (eventNeedUpdate != null) {
            Location currentLocation = model.getLocation();
            Organizer currentOrganizer = model.getOrganizer();
            setIdForUpdateIfOrganizerExistInDb(currentOrganizer);
            setIdFoUpdateIfLocationExistInDb(currentLocation);
            eventRepository.update(model);
        } else {
            throw new EventNotFoundException(UPDATE_NON_EXISTENT_EVENT_MESSAGE);
        }
    }

    @Override
    public void deleteById(Long id) {
        Event eventNeedRemove = eventRepository.findById(id);
        if (eventNeedRemove != null) {
            eventRepository.deleteById(id);
        } else {
            throw new EventNotFoundException(DELETE_NON_EXISTENT_EVENT_MESSAGE);
        }
    }

    private void setIdForUpdateIfOrganizerExistInDb(Organizer currentOrganizer) {
        List<Organizer> organizers = organizerRepository.findAll();
        for (Organizer organizer : organizers) {
            if (currentOrganizer.equals(organizer)) {
                currentOrganizer.setId(organizer.getId());
            } else {
                log.info(THE_ORGANIZER_DID_NOT_MATCH_MESSAGE);
            }
        }
    }

    private void setIdFoUpdateIfLocationExistInDb(Location currentLocation) {
        List<Location> locations = locationService.findAll();
        for (Location location : locations) {
            if (currentLocation.equals(location)) {
                currentLocation.setId(location.getId());
            } else {
                log.info(THE_LOCATION_DID_NOT_MATCH_MESSAGE);
            }
        }
    }

    @Override
    public List<Event> sortBySubject() {
        List<Event> events = eventRepository.findAll();
        events.sort((Comparator.comparing(Event::getSubject)));
        return events;
    }

    @Override
    public List<Event> sortByOrganizerSurname() {
        List<Event> events = eventRepository.findAll();
        events.sort((Comparator.comparing(o -> o.getOrganizer().getSurname())));
        return events;
    }

    @Override
    public List<Event> sortByTime() {
        List<Event> events = eventRepository.findAll();
        events.sort((Comparator.comparing(Event::getDate)));
        return events;
    }

    @Override
    public List<Event> filterBySubjectLetter(String letter) {
        List<Event> events = eventRepository.findAll();
        List<Event> filteredEvents = events.stream()
                .filter(event -> event.getSubject().startsWith(letter))
                .collect(Collectors.toList());
        return filteredEvents;
    }

    @Override
    public List<Event> filterByAfterTimestamp(Timestamp time) {
        List<Event> events = eventRepository.findAll();
        List<Event> filteredEvents = events.stream()
                .filter(event -> event.getDate().after(time))
                .collect(Collectors.toList());
        return filteredEvents;
    }
}
