package by.evgen.taskmodsen.repository;

import by.evgen.taskmodsen.entity.Event;
import by.evgen.taskmodsen.factory.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private static final String GET_EVENTS_QUERY = "from Event";
    private static final String GET_EVENT_BY_ID_QUERY = "from Event as ev where ev.id = :id";

    @Override
    public Event findById(Long id) {
        Transaction transaction = null;
        Event event = new Event();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            event = session.get(Event.class, id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
        return event;
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            events = session.createQuery(GET_EVENTS_QUERY, Event.class).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return events;
    }

    @Override
    public void save(Event model) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(model);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void update(Event model) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(model);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }

    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(GET_EVENT_BY_ID_QUERY);
            query.setParameter("id", id);
            Event event = (Event) query.list().get(0);
            session.delete(event);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
    }
}
