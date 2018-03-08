package com.decipher.usermanage.security.requestdenied;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by decipher19 on 19/4/17.
 */

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
        if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
//            showMessage("BAD_CREDENTIAL");
        } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
//            showMessage("USER_DISABLED");
        }
    }
}