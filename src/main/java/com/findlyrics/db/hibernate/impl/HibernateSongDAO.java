package com.findlyrics.db.hibernate.impl;

import com.findlyrics.db.dao.ISongDAO;
import com.findlyrics.db.model.Song;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Padonag on 21.10.2014.
 */
public class HibernateSongDAO extends HibernateDAO implements ISongDAO {
    private static final Logger log = Logger.getLogger(HibernateSongDAO.class);

    public HibernateSongDAO() {
        super();
    }

    @Override
    public List<Song> getSongs(String lyrics) {
        List<Song> output = new ArrayList<Song>();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Song.class, "songs");
            criteria.add(Restrictions.like("lyrics", lyrics, MatchMode.ANYWHERE));
            output = (List<Song>)criteria.list();
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception " + ex.getMessage(), ex);
        } finally {
            session.close();
        }
        return output;
    }

    @Override
    public boolean addSong(Song song) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List alreadyInDb = session.createCriteria(Song.class).add(Restrictions.eq("title", song.getTitle())).list();
            if (alreadyInDb.size() < 1) {
                session.save(song);
                System.out.println("added song " + song.toString());
                transaction.commit();
            }
            return true;
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception " + ex.getMessage(), ex);
            return false;
        } finally {
            session.close();
        }
    }


}


