package com.findlyrics.db.hibernate.impl;

import com.findlyrics.db.dao.IArtistDAO;
import com.findlyrics.db.model.Artist;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.math.BigInteger;


/**
 * Created by Padonag on 21.10.2014.
 */
public class HibernateArtistDAO extends HibernateDAO implements IArtistDAO {
    private static final Logger log = Logger.getLogger(HibernateArtistDAO.class);
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
            result = (Artist) session.get(Artist.class, id);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception " + ex.getMessage(), ex);
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
            log.debug("Transaction rolled back! Throwing exception " + ex.getMessage(), ex);
        } finally {
            session.close();
        }
        return lastId;
    }

    public Long isArtistExistInDB(String artistName) {
        Long artistId = null;
        Artist artist;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Artist.class, "artists");
            criteria.add(Restrictions.eq("name", artistName));
            if (criteria.list().size() > 0) {
                artist = (Artist) criteria.list().get(0);
                artistId = artist.getId();
            }
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception " + ex.getMessage(), ex);
        } finally {
            session.close();
        }
        return artistId;
    }
}
