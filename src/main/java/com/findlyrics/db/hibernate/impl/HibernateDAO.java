package com.findlyrics.db.hibernate.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by Padonag on 23.10.2014.
 */
public abstract class HibernateDAO {
    protected static final Logger log = Logger.getLogger(HibernateArtistDAO.class);
    protected static SessionFactory sessionFactory;

    public HibernateDAO() {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                    applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (HibernateException e) {
            log.debug(e.getMessage(), e);
        }
    }
}
