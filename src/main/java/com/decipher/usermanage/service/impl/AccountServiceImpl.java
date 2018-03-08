package com.decipher.usermanage.service.impl;

import com.decipher.usermanage.dao.AccountDao;
import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by decipher16 on 3/3/17.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    @Override
    public Integer saveAccount(Account account) {
        return accountDao.saveAccount(account);
    }

    @Override
    public Boolean saveOrUpdateAccount(Account account) {
        return accountDao.saveOrUpdateAccount(account);
    }

    @Override
    public Boolean updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

    @Override
    public Account getUserByAccountId(Integer accountId) {
        return accountDao.getUserByAccountId(accountId);
    }


    @Override
    public Boolean removeAccountByEmail(String email) {
        return accountDao.removeAccountByEmail(email);
    }

    @Override
    public Account findByUserName(String userName) {
        return accountDao.findByUserName(userName);
    }

    @Override
    public Account findUserByEmailId(String email) {

        return accountDao.findUserByEmailId(email);
    }

    @Override
    public Boolean checkUserAvailability(String email) {
        return accountDao.checkUserAvailability(email);
    }

    @Override
    public Boolean removeAccount(int id) {
        return accountDao.removeAccount(id);
    }

    @Override
    public Boolean removeAccountByName(String userName) {
        return accountDao.removeAccountByName(userName);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountDao.getAllAccount();
    }

    @Override
    public List<Account> getAllAccountWithoutAdmin(Integer firstResult,Integer lastResult,String orderColumn,String orderDir) {
        return accountDao.getAllAccountWithoutAdmin(firstResult,lastResult,orderColumn,orderDir);
    }

    @Override
    public Account getCurrentUser() {
        User principal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof User) {
                principal = (User) authentication.getPrincipal();
            }
            if (principal != null) {
                return accountDao.findUserByEmailId(principal.getUsername());
            }
        }
        return null;
    }
}
