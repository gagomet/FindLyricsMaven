package com.findlyrics.db.hibernate.impl;

import com.findlyrics.db.hibernate.IHibernateArtistDAO;
import com.findlyrics.db.model.Artist;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Padonag on 21.10.2014.
 */
public class HibernateArtistDAO extends HibernateDAO implements IHibernateArtistDAO {
    private static final Logger log = Logger.getLogger(HibernateArtistDAO.class);
    private static final String getArtistFromDBQuery = "SELECT * FROM artists WHERE artists.id = :artistId";
    private static final String checkArtistNameInDB = "SELECT * FROM artists WHERE artists.artist_name = :artistName";
    private static final String lastIdQuery = "SELECT LAST_INSERT_ID()";

    public HibernateArtistDAO() {
        super();
    }

    @Override
    public Artist getArtist(Long id) {
        Artist result = new Artist();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(getArtistFromDBQuery).setParameter("artistId", id.toString());
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
        Long lastId = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(artist);
            lastId = ((BigInteger) session.createSQLQuery(lastIdQuery).uniqueResult()).longValue();
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception ", ex);
        } finally {
            session.close();
        }
        return lastId;
    }

    public Long isArtistExistInDB(String artistName) {
        Long artistId = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(checkArtistNameInDB).setParameter("artistName", artistName);
            List results = query.list();
            if (results.iterator().hasNext()) {
                Object[] obj = (Object[]) results.iterator().next();
                artistId = Long.parseLong(String.valueOf(obj[0]));
            }
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception ", ex);
        } finally {
            session.close();
        }
        return artistId;
    }
}
