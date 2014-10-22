package com.findlyrics.db.hibernate.impl;

import com.findlyrics.db.model.Song;
import com.findlyrics.db.hibernate.IHibernateSongDAO;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Padonag on 21.10.2014.
 */
public class HibernateSongDAO implements IHibernateSongDAO {
    private static final Logger log = Logger.getLogger(HibernateSongDAO.class);
    private static final String getSongsFromDBQuery = "SELECT * FROM songs WHERE songs.lyrics LIKE :searchKey";
    private static SessionFactory sessionFactory;

    public HibernateSongDAO() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    @Override
    public List<Song> getSong(String lyrics) {
        List<Song> output = new ArrayList<Song>();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            StringBuilder builder = new StringBuilder();
            builder.append(getSongsFromDBQuery);
            Query query = session.createSQLQuery(builder.toString()).setParameter("searchKey", "%" + lyrics + "%");
            List results = query.list();

            Iterator iterator = results.iterator();
            while (iterator.hasNext()) {
                Object[] obj = (Object[]) iterator.next();
                Song tempSong = new Song();
                tempSong.setSongId(Long.parseLong(String.valueOf(obj[0])));
                tempSong.setArtistId(Long.parseLong(String.valueOf(obj[1])));
                tempSong.setTitle(String.valueOf(obj[2]));
                tempSong.setLyrics(String.valueOf(obj[3]));
                output.add(tempSong);
            }

            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) transaction.rollback();
            log.debug("Transaction rolled back! Throwing exception ", ex);
        } finally {
            session.close();
        }
        return output;
    }

    @Override
    public boolean addSong(Song song) {
        return false;
    }

}


