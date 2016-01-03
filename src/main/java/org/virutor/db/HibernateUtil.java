package org.virutor.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    static SessionFactory sessionFactory = buildSessionFactory();

    static SessionFactory buildSessionFactory() {

        try {

            Configuration configuration = new Configuration().configure(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public interface Transactional<T> {
        T doAction(Session session);
    }

    public static <T> T doInTransaction(Transactional<T> action) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        T result;
        try {
            result = action.doAction(session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.clear();
        }
        return result;
    }

}