package com.github.mobrubov.usermanagement.common.exception;

import com.github.mobrubov.usermanagement.logic.util.PasswordUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Максим
 * Created on 24.03.2020
 */
@Data
@Component
@ConfigurationProperties(prefix = "management.user")
public class UserManagementProperties {
    private Password password;

    @Data
    public static class Password {
        private Integer length;
        private PasswordUtils.PasswordStrength strength;
    }
}
