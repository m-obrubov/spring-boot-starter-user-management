package com.github.mobrubov.usermanagement.logic.manager.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.github.mobrubov.usermanagement.common.exception.ErrorCode;
import com.github.mobrubov.usermanagement.common.exception.UserManagementException;
import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.logic.repository.UserRepository;
import com.github.mobrubov.usermanagement.logic.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@RequiredArgsConstructor
@Service
public class UserManagerImpl implements UserManager {
    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;

    @Override
    public User create(User user) {
        user.setGuid(UUID.randomUUID());
        boolean hasNoPassword = isBlank(user.getPassword());
        String originalPassword;
        if(hasNoPassword) {
            originalPassword = passwordUtils.generatePassword();
            user.setTemporalPassword(Boolean.TRUE);
        } else {
            originalPassword = user.getPassword();
        }
        user.setPassword(passwordUtils.encodePassword(originalPassword));
        if(isBlank(user.getNickName())) {
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
        return userRepository.findAll();
    }

    @Override
    public User getOne(UUID guid) {
        return userRepository.findById(guid)
            .orElseThrow(() -> new UserManagementException("No such user", ErrorCode.NOT_FOUND));
    }

    @Override
    public User getOneByLogin(String login) {
        if(Objects.isNull(login)) {
            throw new UserManagementException("No such user", ErrorCode.NOT_FOUND);
        }
        return userRepository.findByLogin(login)
            .orElseThrow(() -> new UserManagementException("No such user", ErrorCode.NOT_FOUND));
    }

    @Override
    public void update(UUID guid, User user) {
        User oldUser = getOne(guid);
//       TODO check deleted only for plain users
//        if(oldUser.getDeleted()) { }
        if(isNotBlank(user.getPassword())) {
            oldUser.setPassword(passwordUtils.encodePassword(user.getPassword()));
        }
        if(isNotBlank(user.getNickName())) {
            oldUser.setNickName(user.getNickName());
        }
        if(isNotBlank(user.getEmail())) {
            oldUser.setEmail(user.getEmail());
        }
        if(isNotBlank(user.getRole())) {
            oldUser.setRole(user.getRole());
        }
        if(Objects.nonNull(user.getLocked())) {
            oldUser.setLocked(user.getLocked());
        }
        userRepository.save(oldUser);
    }

    @Override
    public void delete(UUID guid) {
        User userToDelete = getOne(guid);
        userToDelete.setDeleted(Boolean.TRUE);
        userRepository.save(userToDelete);
    }

    @Override
    public boolean exists(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public boolean exists(String login, boolean deleted) {
        return userRepository.existsByLoginAndDeleted(login, deleted);
    }

    @Override
    public boolean exists(UUID guid) {
        return userRepository.existsById(guid);
    }
}
