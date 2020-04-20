package com.github.mobrubov.usermanagement.logic.manager.impl;

import java.util.List;
import java.util.UUID;

import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.logic.repository.UserRepository;
import com.github.mobrubov.usermanagement.logic.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserManagerImpl implements UserManager {
    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    @Override
    public User create(User user) {
        user.setGuid(UUID.randomUUID());
        boolean hasNoPassword = false;
        String originalPassword;
        if(Strings.isBlank(user.getPassword())) {
            hasNoPassword = true;
            originalPassword = passwordUtils.generatePassword();
            user.setTemporal(Boolean.TRUE);
        } else {
            originalPassword = user.getPassword();
        }
        user.setPassword(passwordUtils.encodePassword(originalPassword));
        if(Strings.isBlank(user.getNickName())) {
            user.setNickName(user.getLogin());
        }
        User savedUser = userRepository.save(user);
        if(hasNoPassword) {
            savedUser.setPassword(originalPassword);
        }
        return savedUser;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getOne(String guid) {
        return null;
    }

    @Override
    public void update(String guid, User user) {

    }

    @Override
    public void delete(String guid) {

    }
}
