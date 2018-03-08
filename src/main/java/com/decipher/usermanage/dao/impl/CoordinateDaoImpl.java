package com.decipher.usermanage.dao.impl;

import com.decipher.usermanage.HibernateResourceUtil;
import com.decipher.usermanage.dao.CoordinateDao;
import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.entities.CoordinateDetails;
import com.decipher.usermanage.util.UserLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by decipher19 on 15/4/17.
 */
@Repository
public class CoordinateDaoImpl implements CoordinateDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    @Transactional
    public Boolean saveOrUpdatePosition(CoordinateDetails coordinateDetails) {
        Session session = null;
        Transaction transaction = null;
        Boolean status= false;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(coordinateDetails);
            status=true;
            HibernateResourceUtil.commit(transaction);
        } catch (Exception e) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("user don't save : "+e);
        } finally {
            HibernateResourceUtil.close(session);
        }
        return status;
    }

    @Override
    public CoordinateDetails getCoordinateDetailsById(Integer coordinateDetailsId) {

        CoordinateDetails coordinateDetails=null;
        Session session=null;
        Transaction transaction=null;
        try
        {
            session=sessionFactory.openSession();
            transaction=session.getTransaction();
            transaction.begin();
            Object object = session.createQuery("from CoordinateDetails where coordinateDetailsId = :coordinateDetailsId").setParameter("coordinateDetailsId", coordinateDetailsId).uniqueResult();
            if (object != null) {
                coordinateDetails = (CoordinateDetails) object;
            }
            HibernateResourceUtil.commit(transaction);
        }
        catch(Exception e)
        {
            HibernateResourceUtil.rollback(transaction);
            UserLogger.error(e);
        }
        finally
        {
            HibernateResourceUtil.close(session);
        }
        return coordinateDetails;
    }
}
