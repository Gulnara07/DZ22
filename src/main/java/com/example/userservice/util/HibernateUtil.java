package com.example.userservice.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    static {
        try {
            logger.info("Инициализация Hibernate SessionFactory...");
            sessionFactory = new Configuration()
                    .configure("com/example/userservice/resources/hibernate.cfg.xml")
                    .buildSessionFactory();
            logger.info("Hibernate SessionFactory успешно инициализирован");
        } catch (Throwable ex) {
            logger.error("Ошибка инициализации SessionFactory", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        logger.info("Завершение работы Hibernate SessionFactory...");
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
