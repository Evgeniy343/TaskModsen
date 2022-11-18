package by.evgen.taskmodsen.controller;

import by.evgen.taskmodsen.entity.Location;
import by.evgen.taskmodsen.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locationController")
public class LocationController {

    private static final String LOCATION_SAVED_MESSAGE = "Location has been saved";
    private static final String LOCATION_DELETED_MESSAGE = "Location with id - %s has been removed";
    private static final String LOCATION_UPDATED_MESSAGE = "Location with id - %s has been updated";

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/location/{id}", method = RequestMethod.GET)
    public ResponseEntity<Location> getLocation(@PathVariable Long id) {
        Location location = locationService.findById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> saveLocation(@RequestBody Location location) {
        locationService.save(location);
        return new ResponseEntity<>(LOCATION_SAVED_MESSAGE, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        location.setId(id);
        locationService.update(location);
        return new ResponseEntity<>(String.format(LOCATION_UPDATED_MESSAGE, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        locationService.deleteById(id);
        return new ResponseEntity<>(String.format(LOCATION_DELETED_MESSAGE, id), HttpStatus.OK);
    }
}
