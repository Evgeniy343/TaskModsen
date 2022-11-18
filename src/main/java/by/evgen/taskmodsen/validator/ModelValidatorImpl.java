package by.evgen.taskmodsen.validator;

import by.evgen.taskmodsen.entity.Model;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
public class ModelValidatorImpl implements ModelValidator {

    private static ModelValidatorImpl modelValidator;

    private ModelValidatorImpl() {

    }

    public static ModelValidatorImpl getModelValidator() {
        if (modelValidator == null) {
            modelValidator = new ModelValidatorImpl();
        }
        return modelValidator;
    }

    @Override
    public boolean isModelDuplicateInDb(Model currentModel, List<? extends Model> models) {
        for (Model model : models) {
            if (currentModel.equals(model)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSomeOfFieldsNull(Object model) throws IllegalAccessException {
        Field[] fields = model.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals("id")) {
                log.info("id will be generate!");
            } else {
                f.setAccessible(true);
                Object obj = f.get(model);
                if (obj == null) {
                    log.info(f.getName() + " is null");
                    return true;
                } else {
                    if (f.get(model) instanceof Model) {
                        if (isSomeOfFieldsNull(f.get(model))) {
                            return true;
                        } else {
                            log.info("Fields of inner class " + f.getName() + " is not null");
                        }
                    }
                    log.info(f.getName() + " is not null");
                }
            }
        }
        return false;
    }
}
