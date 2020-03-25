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

    private final UserManagementProperties properties;
    private final Random pwdLengthRandom;
    private final Random strengthRandom;
    private final Random charactersLengthRandom;

    public PasswordUtils(UserManagementProperties properties) {
        this.properties = properties;
        if(nonNull(properties.getPassword()) && nonNull(properties.getPassword().getLength())) {
            this.pwdLengthRandom = new Random(properties.getPassword().getLength());
        } else {
            this.pwdLengthRandom = new Random(DEFAULT_PASSWORD_LENGTH);
        }
        if(nonNull(properties.getPassword()) && nonNull(properties.getPassword().getStrength())) {
            this.strengthRandom = new Random(properties.getPassword().getStrength().ordinal());
        } else {
            this.strengthRandom = new Random(DEFAULT_PASSWORD_STRENGTH.ordinal());
        }
        this.charactersLengthRandom = new Random(ALLOWED_CHARACTERS_LENGTH);
    }

    public String generatePassword() {
        Integer pwdLength = properties.getPassword().getLength();
        StringBuilder pwd = new StringBuilder(pwdLength);
        for(int i = 0; i < pwdLength; i++) {
            // TODO generation
            pwd.append(ALLOWED_CHARACTERS[pwdLengthRandom.nextInt()]);
        }
        return pwd.toString();
    }

    public enum PasswordStrength {
        LOWER_CASE,
        UPPER_CASE,
        DIGITS,
        SPECIAL
    }
}
