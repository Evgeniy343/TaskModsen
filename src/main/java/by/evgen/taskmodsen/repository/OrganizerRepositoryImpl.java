package by.evgen.taskmodsen.repository;

import by.evgen.taskmodsen.entity.Organizer;
import by.evgen.taskmodsen.factory.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizerRepositoryImpl implements OrganizerRepository {

    private static final String GET_ORGANIZER_BY_ID_QUERY = "from Organizer as loc where loc.id = :id";
    private static final String GET_ORGANIZERS_QUERY = "from Organizer";

    @Override
    public Organizer findById(Long id) {
        Transaction transaction = null;
        Organizer organizer = new Organizer();
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            organizer = session.get(Organizer.class, id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
        return organizer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Organizer> findAll() {
        List<Organizer> organizers = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            organizers = session.createQuery(GET_ORGANIZERS_QUERY).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return organizers;
    }

    @Override
    public void save(Organizer model) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(model);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void update(Organizer model) {
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
            Query query = session.createQuery(GET_ORGANIZER_BY_ID_QUERY);
            query.setParameter("id", id);
            Organizer organizer = (Organizer) query.list().get(0);
            session.delete(organizer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
    }
}
