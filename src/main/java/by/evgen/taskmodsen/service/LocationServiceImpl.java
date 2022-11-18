package by.evgen.taskmodsen.service;

import by.evgen.taskmodsen.entity.Location;
import by.evgen.taskmodsen.exception.IncorrectDataException;
import by.evgen.taskmodsen.exception.LocationDuplicateException;
import by.evgen.taskmodsen.exception.LocationNotFoundException;
import by.evgen.taskmodsen.repository.LocationRepository;
import by.evgen.taskmodsen.validator.ModelValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("locationServiceImpl")
public class LocationServiceImpl implements LocationService {

    public static final String LOCATION_NOT_FOUND_MESSAGE = "Location with id - %s is not found!";
    private static final String LOCATION_IS_EXIST_IN_DB_MESSAGE = "This location already exist in Db!";
    private static final String DELETE_NON_EXISTENT_LOCATION_MESSAGE = "You try to delete non-existent location!";
    private static final String UPDATE_NON_EXISTENT_LOCATION_MESSAGE = "You try to update non-existent location!";
    private static final String FIELD_NOT_BE_NULL_MESSAGE = "Fields must not be null!";

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location findById(Long id) {
        Location location = locationRepository.findById(id);
        if (location == null) {
            throw new LocationNotFoundException(String.format(LOCATION_NOT_FOUND_MESSAGE, id));
        }
        return location;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public void save(Location model) {
        try {
            if (ModelValidatorImpl.getModelValidator().isSomeOfFieldsNull(model)) {
                throw new IncorrectDataException(FIELD_NOT_BE_NULL_MESSAGE);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        List<Location> locations = locationRepository.findAll();
        if (ModelValidatorImpl.getModelValidator().isModelDuplicateInDb(model, locations)) {
            throw new LocationDuplicateException(LOCATION_IS_EXIST_IN_DB_MESSAGE);
        } else {
            locationRepository.save(model);
        }
    }

    @Override
    public void update(Location model) {
        Location locationNeedUpdate = locationRepository.findById(model.getId());
        if (locationNeedUpdate != null) {
            locationRepository.update(model);
        } else {
            throw new LocationNotFoundException(UPDATE_NON_EXISTENT_LOCATION_MESSAGE);
        }
    }

    @Override
    public void deleteById(Long id) {
        Location locationNeedRemove = locationRepository.findById(id);
        if (locationNeedRemove != null) {
            locationRepository.deleteById(id);
        } else {
            throw new LocationNotFoundException(DELETE_NON_EXISTENT_LOCATION_MESSAGE);
        }
        locationRepository.deleteById(id);
    }
}
