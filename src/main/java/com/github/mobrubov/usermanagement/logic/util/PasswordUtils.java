package com.github.mobrubov.usermanagement.logic.util;

import java.util.Random;

import com.github.mobrubov.usermanagement.common.exception.UserManagementProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

/**
 * @author Максим
 * Created on 24.03.2020
 */
@Component
public class PasswordUtils {
    private static final String[] ALLOWED_PASSWORD_CHARACTERS = {
        "abcdefghijklmnopqrstuvwxyz",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        "12345678901234567890123456",
        "!@#$%^&*(){}[]_-+=,./\\<>?~"
    };
    private static final int ALLOWED_PASSWORD_CHARACTERS_LENGTH = ALLOWED_PASSWORD_CHARACTERS[0].length();

    private final PasswordEncoder passwordEncoder;
    private final Integer pwdLength;
    private final PasswordStrength pwdStrength;
    private final Random random = new Random();

    @Autowired
    public PasswordUtils(PasswordEncoder passwordEncoder, UserManagementProperties properties) {
        if(isNull(properties.getPassword()) || isNull(properties.getPassword().getLength())) {
            throw new IllegalArgumentException("Password length is not set. Set property management.user.password.length");
        }
        if(isNull(properties.getPassword().getStrength())) {
            throw new IllegalArgumentException("Password strength is not set. Set property management.user.password.strength");
        }
        this.pwdLength = properties.getPassword().getLength();
        this.pwdStrength = properties.getPassword().getStrength();
        this.passwordEncoder = passwordEncoder;
    }

    public String generatePassword() {
        StringBuilder pwd = new StringBuilder(this.pwdLength);
        for(int i = 0; i < this.pwdLength; i++) {
            pwd.append(ALLOWED_PASSWORD_CHARACTERS[random.nextInt(pwdStrength.getLevel())]
                .charAt(random.nextInt(ALLOWED_PASSWORD_CHARACTERS_LENGTH)));
        }
        return pwd.toString();
    }

    public String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }

    public boolean validatePassword(String original, String encoded) {
        return passwordEncoder.matches(original, encoded);
    }

    public enum PasswordStrength {
        LOWER_CASE(1),
        UPPER_CASE(2),
        DIGITS(3),
        SPECIAL(4);

        private final int level;

        PasswordStrength(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
