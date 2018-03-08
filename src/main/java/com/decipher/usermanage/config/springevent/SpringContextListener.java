package com.decipher.usermanage.config.springevent;
import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.enums.Role;
import com.decipher.usermanage.service.AccountService;
import com.decipher.usermanage.util.UserLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.stereotype.Component;

/**
 * Created by decipher19 on 28/3/17.
 */

@Component
public class SpringContextListener implements ApplicationEventPublisherAware {

    @Autowired
    private AccountService accountService;

    /****
     * create admin user application whenever deployed using spring Context Listener event
     * @param applicationEventPublisher
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        UserLogger.info("Admin User is created!");
        String emailId="admin";
        Boolean checkStatus = accountService.checkUserAvailability(emailId);
        if (!checkStatus) {
            Account account = new Account();
            account.setUserName("Bhagvan Singh");
            account.setEmail(emailId);
            account.setRole(Role.ROLE_ADMIN);
            account.setStatus(true);
            account.setPassword("admin");
            account.setAddress("jaipur,Rajasthan");
            UserLogger.info("account " + account.getEmail());
            accountService.saveAccount(account);
        }
    }
}
