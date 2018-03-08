package com.decipher.usermanage.security;

import com.decipher.usermanage.entities.Account;
import com.decipher.usermanage.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by decipher16 on 3/3/17.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account=accountService.findUserByEmailId(username);
        if (account != null) {
            return buildUserForAuthentication(account,buildUserGrantedAuthority(account));
        }
        else {
            throw new UsernameNotFoundException("User doesn't exit");
        }
    }

    public static User buildUserForAuthentication(Account account,List<GrantedAuthority> authorities) {
        return new User(account.getEmail(),account.getPassword(),account.getStatus(),true,true,true,authorities);

    }

    public List<GrantedAuthority> buildUserGrantedAuthority(Account roles) {
        Set<GrantedAuthority> authorities=new HashSet();
        authorities.add(roles.getRole());
        return new ArrayList(authorities);
    }
}
