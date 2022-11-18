package by.evgen.taskmodsen.controller;

import by.evgen.taskmodsen.entity.Event;
import by.evgen.taskmodsen.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/eventController")
public class EventController {

    private static final String EVENT_SAVED_MESSAGE = "Event has been saved";
    private static final String EVENT_DELETED_MESSAGE = "Event with id - %s has been removed";
    private static final String EVENT_UPDATED_MESSAGE = "Event with id - %s has been updated";

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Event event = eventService.findById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAll();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> saveEvent(@RequestBody String eventJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Event event = mapper.readValue(eventJson, Event.class);
            eventService.save(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(EVENT_SAVED_MESSAGE, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody String eventJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Event event = mapper.readValue(eventJson, Event.class);
            event.setId(id);
            eventService.update(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(String.format(EVENT_UPDATED_MESSAGE, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return new ResponseEntity<>(String.format(EVENT_DELETED_MESSAGE, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/sortBySubject", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> sortEventBySubject() {
        List<Event> events = eventService.sortBySubject();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/sortByOrganizerSurname", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> sortEventByOrganizerSurname() {
        List<Event> events = eventService.sortBySubject();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/sortByTime", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> sortEventByTime() {
        List<Event> events = eventService.sortBySubject();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/filterBySubjectLetter/{letter}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> filterEventBySubjectLetter(@PathVariable String letter) {
        List<Event> events = eventService.filterBySubjectLetter(letter);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/filterByAfterTime/{time}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> filterEventByAfterTime(@PathVariable Timestamp time) {
        List<Event> events = eventService.filterByAfterTimestamp(time);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
