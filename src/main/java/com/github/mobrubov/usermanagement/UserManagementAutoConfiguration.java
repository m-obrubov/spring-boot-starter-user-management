package com.github.mobrubov.usermanagement;

import java.util.Optional;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.mobrubov.usermanagement.common.exception.UserManagementProperties;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.logic.util.PasswordUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@Configuration
@EntityScan(basePackageClasses = UserManagementAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = UserManagementAutoConfiguration.class)
@ComponentScan(basePackageClasses = UserManagementAutoConfiguration.class)
public class UserManagementAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AuditorAware<String> auditorProvider(UserManager userManager) {
        return () -> Optional.of(userManager.getCurrentUserName());
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    @ConditionalOnProperty(value = "management.user", matchIfMissing = true)
    public UserManagementProperties properties() {
        UserManagementProperties properties = new UserManagementProperties();
        properties.setPassword(new UserManagementProperties.Password());
        properties.getPassword().setLength(6);
        properties.getPassword().setStrength(PasswordUtils.PasswordStrength.DIGITS);
        return properties;
    }
}
