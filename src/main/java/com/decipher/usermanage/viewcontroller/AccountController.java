package com.decipher.usermanage.viewcontroller;

import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.enums.Role;
import com.decipher.usermanage.service.AccountService;
import com.decipher.usermanage.util.PasswordEncoder;
import com.decipher.usermanage.util.UserLogger;
import com.decipher.usermanage.util.cryptography.CriptographyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by decipher19 on 7/4/17.
 */
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
    public static final String MESSAGE = "message";

    private Boolean status=false;
    @RequestMapping(value = "/")
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.addObject(MESSAGE, "welcomePage...");
        model.setViewName("welcome");
        return model;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = "/registration")
    public ModelAndView createUserPage() {
        ModelAndView model = new ModelAndView();
        model.addObject(MESSAGE, "Registration Form...");
        model.addObject("roleList", Role.values());
        model.setViewName("registration");
        return model;
    }


    @RequestMapping(value = "/success")
    public ModelAndView successPage() {
        ModelAndView model = new ModelAndView();
        Account account = accountService.getCurrentUser();
        UserLogger.info("current account : " + account.getUserName() + ",current user Role :" + account.getRole());
        model.addObject(MESSAGE, "from successPage...");
        model.addObject("emailId", account.getEmail());
        UserLogger.info("emailId : - " + account.getEmail());
        if (status) {
            model.setViewName("change-password");
            status = false;
        } else if (account.getRole() == Role.ROLE_ADMIN) {
            List accountList = accountService.getAllAccount();
            model.addObject(MESSAGE, "from getAllUsersPage...");
            model.addObject("userList", accountList);
            model.addObject(MESSAGE, "Admin Page...");
            model.addObject("roleList", Role.values());
            model.setViewName("admin");
            UserLogger.info("successfully login Admin Page...");
        } else if (account.getRole() == Role.ROLE_USER) {
            model.setViewName("user");
        } else if (account.getRole() == Role.ROLE_DBA) {
            model.setViewName("dba");
        } else {
            model.setViewName("success");
        }
        return model;
    }

    @RequestMapping(value = "/conformUser", method = RequestMethod.GET)
    public ModelAndView conformUser(@RequestParam String emailId) {
        String email = CriptographyUtil.decrypt(emailId);
        UserLogger.info("email : " + emailId + ">>>" + email);
        Account account = accountService.findUserByEmailId(email);
        UserLogger.info("email : " + account.getEmail());
        account.setStatus(true);
        status = accountService.updateAccount(account);
        ModelAndView model = new ModelAndView();
        model.addObject(MESSAGE, "Your Email is verified you can login with your credentials...");
        UserLogger.info("inside conformUser id : " + account.getId());
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
    public ModelAndView changeUserPassword(
            @RequestParam(value = "currentPassword", required = true) String currentPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword,
            @RequestParam(value = "emailId", required = true) String emailId
    ) {
        UserLogger.info("inside changeUserPassword " + currentPassword + " newPassword : " + newPassword);
        ModelAndView model = new ModelAndView();
//        String encodedCurrentPassword = PasswordEncoder.passwordEncoder(currentPassword);
        UserLogger.info("emailId : " + emailId);
        Account user = accountService.findUserByEmailId(emailId);
        if (user != null) {
            String presentPassword = user.getPassword();
            UserLogger.info("presentPassord : " + presentPassword);
            user.setPassword(PasswordEncoder.passwordEncoder(newPassword));
            Boolean checkStatus = accountService.updateAccount(user);
            if (checkStatus) {
                if (user.getRole() == Role.ROLE_ADMIN) {
                    model.setViewName("admin");
                } else if (user.getRole() == Role.ROLE_USER) {
                    model.setViewName("user");
                } else if (user.getRole() == Role.ROLE_DBA) {
                    model.setViewName("dba");
                } else {
                    model.setViewName("success");
                }
                model.addObject(MESSAGE, "successfully changed Your Password...");
                UserLogger.info("successfully changed Your Password...");
            } else {
                model.addObject(MESSAGE, "failed changed Your Password...");
            }

        } else {
            UserLogger.info("password not matched ");
        }
        return model;
    }

    @RequestMapping(value = "/admin**")
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.addObject(MESSAGE, "from adminPage...");
        model.setViewName("admin");
        return model;
    }
    @RequestMapping(value = "/dragger")
    public ModelAndView draggerPage() {
        ModelAndView model = new ModelAndView();
        model.addObject(MESSAGE, "from draggerPage...");
        model.setViewName("draggerPage");
        return model;
    }
}
