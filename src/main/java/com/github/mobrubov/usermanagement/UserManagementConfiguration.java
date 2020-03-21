package com.github.mobrubov.usermanagement;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ComponentScan(basePackageClasses = UserManagementConfiguration.class)
@Configuration
public class UserManagementConfiguration {
}
