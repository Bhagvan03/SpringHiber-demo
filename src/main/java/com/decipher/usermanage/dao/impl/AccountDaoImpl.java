package com.decipher.usermanage.dao.impl;

import com.decipher.usermanage.HibernateResourceUtil;
import com.decipher.usermanage.dao.AccountDao;
import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.util.PasswordEncoder;
import com.decipher.usermanage.util.UserLogger;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by decipher16 on 3/3/17.
 */
@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * saving account data in database
     * @param account
     */
    @Override
    @Transactional
    public Integer saveAccount(Account account) {
        Session session = null;
        Transaction transaction = null;
        int id= 0;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            account.setPassword(PasswordEncoder.passwordEncoder(account.getPassword()));
            session.save(account);
            id = account.getId();
            HibernateResourceUtil.commit(transaction);
        } catch (Exception e) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("user don't save : "+e);
        } finally {
            HibernateResourceUtil.close(session);
        }
        return id;
    }

    /**
     * saving account data in database whenever isnot available otherwise update account...
     * @param account
     */

    @Override
    @Transactional
    public Boolean saveOrUpdateAccount(Account account) {
        Session session = null;
        Transaction transaction = null;
        Boolean status = false;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            account.setPassword(PasswordEncoder.passwordEncoder(account.getPassword()));
            session.saveOrUpdate(account);
            status=true;
            HibernateResourceUtil.commit(transaction);
        } catch (Exception e) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("user don't update : "+e);
        } finally {
            HibernateResourceUtil.close(session);
        }
        return status;
    }

    @Override
    public Boolean updateAccount(Account account) {
        Session session = null;
        Transaction transaction = null;
        Boolean status = false;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(account);
            status=true;
            HibernateResourceUtil.commit(transaction);
        } catch (Exception e) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("user don't update : "+e);
        } finally {
            HibernateResourceUtil.close(session);
        }
        return status;
    }

    /***
     * finding account according to perticular email id
     * @param email
     * @return
     */
    @Override
    public Account findUserByEmailId(String email) {
        Session session=sessionFactory.openSession();
        Transaction transaction=null;
        Account account=null;
        transaction=session.getTransaction();
        try {
            transaction.begin();
            Query query=session.createQuery("from Account where upper(trim(email) ) = upper(trim(:email) ) ");
            Object object = query.setParameter("email", email).uniqueResult();
            if (object != null) {
                account = (Account) object;
            }
            HibernateResourceUtil.commit(transaction);
        }
        catch (Exception ex) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("Invalid user email supplied"+ex);
        }
        finally {
            HibernateResourceUtil.close(session);
        }
        return account;
    }

   @Override
    public Account getUserByAccountId(Integer id) {
        Account account=null;
        Session session=null;
        Transaction transaction=null;
        try
        {
            session=sessionFactory.openSession();
            transaction=session.getTransaction();
            transaction.begin();
            Object object = session.createQuery("from Account where id = :id").setParameter("id", id).uniqueResult();
            if (object != null) {
                account = (Account) object;
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
        return account;
    }

    @Override
    public Boolean removeAccountByEmail(String email) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        Boolean status=false;
        try {
            session.delete(findUserByEmailId(email));
            HibernateResourceUtil.commit(transaction);
            status = true;
        }
        catch (Exception ex) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("Account doesn't exit"+ex);
        }
        finally {
            HibernateResourceUtil.close(session);
        }
        return status;
    }

    @Override
    @Transactional
    public Boolean checkUserAvailability(String email) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        Boolean status=false;
      try {
            Query query=session.createQuery("from Account where upper(trim(email) ) = upper(trim(:email) ) ");
            Object object = query.setParameter("email", email).uniqueResult();
            if (object != null) {
                status = true;
            }
          HibernateResourceUtil.commit(transaction);
        }
        catch (Exception ex) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("Account doesn't exit"+ex);
        }
        finally {
            session.close();
        }
        return status;
    }

    @Override
    public Boolean removeAccount(int id) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        Boolean result=false;
        try {
            session.delete(session.get(Account.class,id));
            HibernateResourceUtil.commit(transaction);
            result=true;
        }
        catch (Exception ex) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("User doesn't exit"+ex);
        }
        finally {
            HibernateResourceUtil.close(session);
        }
        return result;
    }

    @Override
    public Boolean removeAccountByName(String userName){
        Session session=null;
        Transaction transaction=null;
        Boolean status=false;
        try {
            session=sessionFactory.openSession();
            transaction=session.getTransaction();
            transaction.begin();
            session.delete(session.get(Account.class,userName));
//            session.delete(findByUserName(userName));
            HibernateResourceUtil.commit(transaction);
            status=true;
        }
        catch (Exception e) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("User doesn't available"+e);
        }
        finally {
            HibernateResourceUtil.close(session);
        }
        return status;
    }

    @Override
    public Account findByUserName(String userName) {

        Account account=null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query=session.createQuery("from Account where upper(trim(userName) ) = upper(trim(:userName) ) ");
            query.setParameter("userName", userName);
            Object object = query.uniqueResult();
            if(object != null){
                account= (Account) object;
            }
            session.flush();
            HibernateResourceUtil.commit(transaction);

        } catch (Exception e){
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("User doesn't exit"+e);
        } finally {
            HibernateResourceUtil.close(session);
        }
        return account;
    }

    @Override
    public List<Account> getAllAccount() {
        Session session=null;
        Transaction transaction=null;
        List<Account> accountList=null;
        try {
            session=sessionFactory.openSession();
             transaction=session.beginTransaction();
            Object object=session.createQuery("from Account").list();
            if (object != null) {
                accountList= (List<Account>) object;
            }
            HibernateResourceUtil.commit(transaction);
        }
        catch (Exception ex) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("Empty Account : "+ex);

        }
        finally {
            HibernateResourceUtil.close(session);
        }
        return accountList;
    }

    @Override
    public List<Account> getAllAccountWithoutAdmin(Integer firstResult,Integer lastResult,String orderColumn,String orderDirection) {
        List<Account> list= null;
        Session session=null;
        Transaction transaction=null;
        try {
            session=sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria=session.createCriteria(Account.class);
//            criteria.add(Restrictions.ne("email","b.s.jadoun91@gmail.com"));//email is the propertyname
            criteria.add(Restrictions.ne("email","admin"));//email is the propertyname
            criteria.setFirstResult(firstResult);
            criteria.setMaxResults(lastResult);
            if (orderColumn != null && orderDirection != null){
                if(orderDirection.equalsIgnoreCase(String.valueOf("asc"))) {
                    criteria.addOrder(Order.asc(orderColumn));
                }
                else if(orderDirection.equalsIgnoreCase(String.valueOf("desc"))) {

                    criteria.addOrder(Order.desc(orderColumn));
                }

            }
            list = criteria.list();
            HibernateResourceUtil.commit(transaction);
        } catch (Exception e) {
            HibernateResourceUtil.rollback(transaction);
            throw new UsernameNotFoundException("Empty Account : "+e);
        } finally {
            HibernateResourceUtil.close(session);
        }
        return list;
    }
}
