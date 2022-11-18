package by.evgen.taskmodsen.factory;

import by.evgen.taskmodsen.entity.Event;
import by.evgen.taskmodsen.entity.Location;
import by.evgen.taskmodsen.entity.Organizer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Event.class);
                configuration.addAnnotatedClass(Location.class);
                configuration.addAnnotatedClass(Organizer.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                log.error("Exception!" + e);
            }
        }
        return sessionFactory;
    }
}
