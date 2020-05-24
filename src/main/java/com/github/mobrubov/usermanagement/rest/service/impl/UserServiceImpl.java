package com.github.mobrubov.usermanagement.rest.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.transaction.Transactional;

import com.github.mobrubov.usermanagement.common.exception.ErrorCode;
import com.github.mobrubov.usermanagement.common.exception.UserManagementException;
import com.github.mobrubov.usermanagement.common.exception.UserManagementProperties;
import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.rest.mapper.UserRoMapper;
import com.github.mobrubov.usermanagement.rest.ro.UserRo;
import com.github.mobrubov.usermanagement.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserManagementProperties properties;
    private final UserManager userManager;
    private final UserRoMapper userRoMapper;

    @Override
    public UserRo create(UserRo user) {
        // TODO allow only for admins
        if(userManager.exists(user.getLogin())) {
            throw new UserManagementException("Such login exists", ErrorCode.BAD_REQUEST);
        }
        if(properties.getRoles().stream().noneMatch(role -> StringUtils.equals(role.getName(), user.getRole()))) {
            throw new UserManagementException("Incorrect role", ErrorCode.BAD_REQUEST);
        }
        UserRo createdUser = userRoMapper.mapFull(userManager.create(userRoMapper.mapCreate(user)));
        if(BooleanUtils.isFalse(createdUser.getTemporalPassword())) {
            createdUser.setTemporalPassword(null);
            createdUser.setPassword(null);
        }
        return createdUser;
    }

    @Override
    public List<UserRo> getAll() {
        // TODO map short for non admins
        return userRoMapper.mapFull(userManager.getAll());
    }

    @Override
    public UserRo getOne(String guid) {
        // TODO map short for non admins
        UUID realGuid = parseGuid(guid);
        validateExistence(realGuid);
        return userRoMapper.mapFull(userManager.getOne(realGuid));
    }

    @Override
    @Transactional
    public void update(String guid, UserRo user) {
        if(
            Objects.nonNull(user.getRole())
                && properties.getRoles().stream().noneMatch(role -> StringUtils.equals(role.getName(), user.getRole()))
        ) {
            throw new UserManagementException("Invalid role", ErrorCode.BAD_REQUEST);
        }
        UUID realGuid = parseGuid(guid);
        validateExistence(realGuid);
        User userToUpdate = userRoMapper.mapUpdate(user);
        userManager.update(realGuid, userToUpdate);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        // TODO allow only for admins
        UUID realGuid = parseGuid(guid);
        validateExistence(realGuid);
        userManager.delete(realGuid);
    }

    private UUID parseGuid(String guid) {
        try {
            return UUID.fromString(guid);
        } catch(IllegalArgumentException e) {
            throw new UserManagementException("Incorrect guid", ErrorCode.BAD_REQUEST);
        }
    }

    private void validateExistence(UUID uuid) {
        if(!userManager.exists(uuid)) {
            throw new UserManagementException("User not found with such guid", ErrorCode.NOT_FOUND);
        }
    }
}
