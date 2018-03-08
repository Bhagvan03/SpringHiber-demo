package com.decipher.usermanage.dao;

import com.decipher.usermanage.entities.Account;

import java.util.List;

/**
 * Created by decipher16 on 3/3/17.
 */
public interface AccountDao {
    Integer saveAccount(Account account);
    Boolean saveOrUpdateAccount(Account account);
    Boolean updateAccount(Account account);
    Account findByUserName(String userName);
    Account findUserByEmailId(String email);
    Account getUserByAccountId(Integer accountId);
    Boolean checkUserAvailability(String email);
    Boolean removeAccount(int id);
    Boolean removeAccountByName(String userName);
    Boolean removeAccountByEmail(String email);
    List<Account> getAllAccount();
    List<Account> getAllAccountWithoutAdmin(Integer firstResult, Integer lastResult, String orderColumn, String orderDir);

}
