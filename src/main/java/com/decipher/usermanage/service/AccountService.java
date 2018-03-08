package com.decipher.usermanage.service;

import com.decipher.usermanage.entities.Account;

import java.util.List;

/**
 * Created by decipher16 on 3/3/17.
 */
public interface AccountService {
    Integer saveAccount(Account account);
    Boolean saveOrUpdateAccount(Account account);
    Boolean updateAccount(Account account);
    Account getUserByAccountId(Integer accountId);
    Boolean removeAccountByEmail(String email);
    Account findByUserName(String userName);
    Account findUserByEmailId(String email);
    Boolean checkUserAvailability(String email);
    Boolean removeAccount(int id);
    Boolean removeAccountByName(String userName);
    List<Account> getAllAccount();
    List<Account> getAllAccountWithoutAdmin(Integer firstResult, Integer lastResult, String orderColumn, String orderDir);
    Account getCurrentUser();
}
