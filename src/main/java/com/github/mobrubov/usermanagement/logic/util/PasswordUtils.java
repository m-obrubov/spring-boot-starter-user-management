package com.github.mobrubov.usermanagement.logic.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.github.mobrubov.usermanagement.common.exception.UserManagementProperties;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

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

    private static final int ENCODE_ITERATIONS = 1000;
    private static final String ENCODE_DELIMITER = ":";

    private final Integer pwdLength;
    private final PasswordStrength pwdStrength;
    private final Random random = new Random();

    public PasswordUtils(UserManagementProperties properties) {
        if(isNull(properties.getPassword()) || isNull(properties.getPassword().getLength())) {
            throw new IllegalArgumentException("Password length is not set. Set property management.user.password.length");
        }
        if(isNull(properties.getPassword().getStrength())) {
            throw new IllegalArgumentException("Password strength is not set. Set property management.user.password.strength");
        }
        this.pwdLength = properties.getPassword().getLength();
        this.pwdStrength = properties.getPassword().getStrength();
    }

    public String generatePassword() {
        StringBuilder pwd = new StringBuilder(this.pwdLength);
        for(int i = 0; i < this.pwdLength; i++) {
            pwd.append(ALLOWED_CHARACTERS[random.nextInt(pwdStrength.getLevel())]
                .charAt(random.nextInt(ALLOWED_CHARACTERS_LENGTH)));
        }
        return pwd.toString();
    }

    public String encodePassword(String raw) {
        try {
            return encodeWithPBKDF2WithHmacSHA1(raw);
        } catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return raw;
        }
    }

    private String encodeWithPBKDF2WithHmacSHA1(String source) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] chars = source.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, ENCODE_ITERATIONS, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(salt) + ENCODE_DELIMITER + toHex(hash);
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public boolean validatePassword(String original, String encoded) {
        try {
            return validateWithPBKDF2WithHmacSHA1(original, encoded);
        } catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateWithPBKDF2WithHmacSHA1(String original, String encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = encoded.split(ENCODE_DELIMITER);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(original.toCharArray(), salt, ENCODE_ITERATIONS, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
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
