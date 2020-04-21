package com.github.mobrubov.usermanagement.common.exception;

import java.util.List;

import com.github.mobrubov.usermanagement.logic.util.PasswordUtils;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Максим
 * Created on 24.03.2020
 */
@Data
@Component
@ConditionalOnMissingBean(UserManagementProperties.class)
@ConfigurationProperties(prefix = "management.user")
public class UserManagementProperties {
    private Password password;
    private List<Role> roles;

    @Data
    public static class Password {
        private Integer length;
        private PasswordUtils.PasswordStrength strength;
    }

    @Data
    public static class Role {
        private String name;
        private Boolean isAdmin;
    }
}
