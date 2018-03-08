package com.decipher.usermanage.viewcontroller;

import com.decipher.usermanage.util.UserLogger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by decipher19 on 7/4/17.
 */
@Controller
public class ErrorController {
    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            UserLogger.info("user : " + userDetail.getUsername());
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;
    }
}
