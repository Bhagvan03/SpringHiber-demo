package com.decipher.usermanage.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by decipher16 on 3/3/17.
 */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN(0),
    ROLE_USER(1),
    ROLE_DBA(2);

    private final int roleValue;

    Role(int role){
        this.roleValue=role;
    }

    public int getBit(){
        return  roleValue;

    }

    public String getRole() {

        return toString();
    }
    @Override
    public String getAuthority() {
        return toString();
    }

    public String getRoleString() {
        return toString().replace("ROLE_","").toUpperCase();
    }

    /****
     *
     * @param roleValue
     * @return
     */
    public static Role getRoleByValue(int roleValue) {

        if(ROLE_ADMIN.ordinal()==roleValue)
        {
            return ROLE_ADMIN;
        }
        else if(ROLE_DBA.ordinal()==roleValue)
        {
            return ROLE_DBA;
        }
        else if(ROLE_USER.ordinal()==roleValue)
        {
            return ROLE_USER;
        }
        return null;
    }


}
