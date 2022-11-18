package by.evgen.taskmodsen.service;

import by.evgen.taskmodsen.entity.Organizer;
import by.evgen.taskmodsen.exception.IncorrectDataException;
import by.evgen.taskmodsen.exception.OrganizerDuplicateException;
import by.evgen.taskmodsen.exception.OrganizerNotFoundException;
import by.evgen.taskmodsen.repository.OrganizerRepository;
import by.evgen.taskmodsen.validator.ModelValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("organizerServiceImpl")
public class OrganizerServiceImpl implements OrganizerService {

    private static final String ORGANIZER_NOT_FOUND_MESSAGE = "Organizer with id - %s not found!";
    private static final String ORGANIZER_IS_EXIST_IN_DB_MESSAGE = "This organizer already exist in Db!";
    private static final String DELETE_NON_EXISTENT_ORGANIZER_MESSAGE = "You try to delete non-existent organizer!";
    private static final String UPDATE_NON_EXISTENT_ORGANIZER_MESSAGE = "You try to update non-existent organizer!";
    private static final String FIELD_NOT_BE_NULL_MESSAGE = "Fields must not be null!";

    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Organizer findById(Long id) {
        Organizer organizer = organizerRepository.findById(id);
        if (organizer == null) {
            throw new OrganizerNotFoundException(String.format(ORGANIZER_NOT_FOUND_MESSAGE, id));
        }
        return organizer;
    }

    @Override
    public List<Organizer> findAll() {
        return organizerRepository.findAll();
    }

    @Override
    public void save(Organizer model) {
        try {
            if (ModelValidatorImpl.getModelValidator().isSomeOfFieldsNull(model)) {
                throw new IncorrectDataException(FIELD_NOT_BE_NULL_MESSAGE);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        List<Organizer> organizers = organizerRepository.findAll();
        if (ModelValidatorImpl.getModelValidator().isModelDuplicateInDb(model, organizers)) {
            throw new OrganizerDuplicateException(ORGANIZER_IS_EXIST_IN_DB_MESSAGE);
        } else {
            model.setEvents(new ArrayList<>());
            organizerRepository.save(model);
        }
    }

    @Override
    public void update(Organizer model) {
        Organizer organizerNeedUpdate = organizerRepository.findById(model.getId());
        if (organizerNeedUpdate != null) {
            organizerRepository.update(model);
        } else {
            throw new OrganizerNotFoundException(UPDATE_NON_EXISTENT_ORGANIZER_MESSAGE);
        }
    }

    @Override
    public void deleteById(Long id) {
        Organizer organizerNeedRemove = organizerRepository.findById(id);
        if (organizerNeedRemove != null) {
            organizerRepository.deleteById(id);
        } else {
            throw new OrganizerNotFoundException(DELETE_NON_EXISTENT_ORGANIZER_MESSAGE);
        }
    }
}
