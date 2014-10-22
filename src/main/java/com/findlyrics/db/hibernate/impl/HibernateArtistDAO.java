package com.findlyrics.db.hibernate.impl;

import com.findlyrics.db.model.Artist;
import com.findlyrics.db.hibernate.IHibernateArtistDAO;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;


/**
 * Created by Padonag on 21.10.2014.
 */
public class HibernateArtistDAO implements IHibernateArtistDAO {
    private static final Logger log = Logger.getLogger(HibernateArtistDAO.class);
    private static final String getArtistFromDBQuery = "SELECT * FROM artists WHERE artists.id = :artistId";
    private static SessionFactory sessionFactory;

    public HibernateArtistDAO() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    @Override
    public Artist getArtist(Long id) {
        Artist result = new Artist();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            StringBuilder builder = new StringBuilder();
            builder.append(getArtistFromDBQuery);
            Query query = session.createSQLQuery(builder.toString()).setParameter("artistId", id.toString());
            List results = query.list();

            Iterator iterator = results.iterator();
            while (iterator.hasNext()) {
                Object[] obj = (Object[]) iterator.next();
                result.setId(Long.parseLong(String.valueOf(obj[0])));
                result.setName(String.valueOf(obj[1]));
            }

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception ", ex);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Long addArtist(Artist artist) {


        return null;
    }
}
