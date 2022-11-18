package by.evgen.taskmodsen.controller;

import by.evgen.taskmodsen.entity.Organizer;
import by.evgen.taskmodsen.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizerController")
public class OrganizerController {
    private static final String ORGANIZER_SAVED_MESSAGE = "Organizer has been saved";
    private static final String ORGANIZER_DELETED_MESSAGE = "Organizer with id - %s has been removed";
    private static final String ORGANIZER_UPDATED_MESSAGE = "Organizer with id - %s has been updated";

    private final OrganizerService organizerService;

    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @RequestMapping(value = "/organizer/{id}", method = RequestMethod.GET)
    public ResponseEntity<Organizer> getOrganizer(@PathVariable Long id) {
        Organizer organizer = organizerService.findById(id);
        return new ResponseEntity<>(organizer, HttpStatus.OK);
    }

    @RequestMapping(value = "/organizers", method = RequestMethod.GET)
    public ResponseEntity<List<Organizer>> getAllOrganizers() {
        List<Organizer> organizers = organizerService.findAll();
        return new ResponseEntity<>(organizers, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> saveOrganizer(@RequestBody Organizer organizer) {
        organizerService.save(organizer);
        return new ResponseEntity<>(ORGANIZER_SAVED_MESSAGE, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateOrganizer(@PathVariable Long id, @RequestBody Organizer organizer) {
        organizer.setId(id);
        organizerService.update(organizer);
        return new ResponseEntity<>(String.format(ORGANIZER_UPDATED_MESSAGE, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteOrganizer(@PathVariable Long id) {
        organizerService.deleteById(id);
        return new ResponseEntity<>(String.format(ORGANIZER_DELETED_MESSAGE, id), HttpStatus.OK);
    }
}
