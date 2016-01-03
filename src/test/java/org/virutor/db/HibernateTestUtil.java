package org.virutor.db;

public class HibernateTestUtil {

    public static void shutdown() {
        HibernateUtil.sessionFactory.close();
    }

    public static void create() {
        HibernateUtil.sessionFactory = HibernateUtil.buildSessionFactory();
    }

}
