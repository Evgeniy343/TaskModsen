package by.evgen.taskmodsen.validator;

import by.evgen.taskmodsen.entity.Model;

import java.util.List;

public interface ModelValidator {
    boolean isModelDuplicateInDb(Model currentModel, List<? extends Model> models);

    boolean isSomeOfFieldsNull(Object model) throws IllegalAccessException;
}
