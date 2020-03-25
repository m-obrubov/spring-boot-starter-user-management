package com.github.mobrubov.usermanagement.logic.util;

import java.util.Random;

import com.github.mobrubov.usermanagement.common.exception.UserManagementProperties;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

/**
 * @author Максим
 * Created on 24.03.2020
 */
@Component
public class PasswordUtils {
    private static final Integer DEFAULT_PASSWORD_LENGTH = 6;
    private static final PasswordStrength DEFAULT_PASSWORD_STRENGTH = PasswordStrength.DIGITS;
    private static final String[] ALLOWED_CHARACTERS = {
        "abcdefghijklmnopqrstuvwxyz",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        "12345678901234567890123456",
        "!@#$%^&*(){}[]_-+=,./\\<>?~"
    };
    private static final int ALLOWED_CHARACTERS_LENGTH = 26;

    private final Integer pwdLength;
    private final PasswordStrength pwdStrength;
    private final Random random = new Random();

    public PasswordUtils(UserManagementProperties properties) {
        if(nonNull(properties.getPassword()) && nonNull(properties.getPassword().getLength())) {
            this.pwdLength = properties.getPassword().getLength();
        } else {
            this.pwdLength = DEFAULT_PASSWORD_LENGTH;
        }
        if(nonNull(properties.getPassword()) && nonNull(properties.getPassword().getStrength())) {
            this.pwdStrength = properties.getPassword().getStrength();
        } else {
            this.pwdStrength = DEFAULT_PASSWORD_STRENGTH;
        }
    }

    public String generatePassword() {
        StringBuilder pwd = new StringBuilder(this.pwdLength);
        for(int i = 0; i < this.pwdLength; i++) {
            pwd.append(ALLOWED_CHARACTERS[random.nextInt(pwdStrength.ordinal())]
                .charAt(random.nextInt(ALLOWED_CHARACTERS_LENGTH)));
        }
        return pwd.toString();
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
