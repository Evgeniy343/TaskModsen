package by.evgen.taskmodsen.service;

import by.evgen.taskmodsen.entity.Event;

import java.sql.Timestamp;
import java.util.List;

public interface EventService extends CommonService<Event> {
    List<Event> sortBySubject();

    List<Event> sortByOrganizerSurname();

    List<Event> sortByTime();

    List<Event> filterBySubjectLetter(String letter);

    List<Event> filterByAfterTimestamp(Timestamp time);
}
