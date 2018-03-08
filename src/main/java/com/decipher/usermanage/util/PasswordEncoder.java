package com.decipher.usermanage.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**password encriptor class
 * Created by decipher16 on 3/3/17.
 */

public class PasswordEncoder extends BCryptPasswordEncoder {
    private static PasswordEncoder ownRef = new PasswordEncoder();

    public static String passwordEncoder(String planeText) {
        return ownRef.encode(planeText);
    }

    public static String generateOutOfLengthForDefaultString() {
        String password = RandomStringUtils.randomAlphanumeric(8);
        return passwordEncoder(password);
    }
}
