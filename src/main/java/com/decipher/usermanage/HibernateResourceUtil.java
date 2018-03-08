package com.decipher.usermanage;

import com.decipher.usermanage.util.UserLogger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by decipher19 on 8/4/17.
 */
public class HibernateResourceUtil {

    public static void close(Session session) {
        if (session != null) {
            try {
//                session.flush();
                session.close();
            } catch (HibernateException ignored) {
                UserLogger.error("Couldn't close Session"+ignored);
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            UserLogger.error("Couldn't rollback Transaction"+ignored);
        }
    }

    public static void commit(Transaction tx) {
        try {
            if (tx != null) {
                tx.commit();
            }
        } catch (HibernateException ignored) {
            UserLogger.error("Couldn't commit Transaction"+ignored);
        }
    }
}
