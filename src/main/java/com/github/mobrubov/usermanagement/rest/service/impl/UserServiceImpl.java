package com.github.mobrubov.usermanagement.rest.service.impl;

import java.util.List;
import javax.transaction.Transactional;

import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.rest.mapper.UserRoMapper;
import com.github.mobrubov.usermanagement.rest.ro.UserRo;
import com.github.mobrubov.usermanagement.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserManager userManager;
    private final UserRoMapper userRoMapper;

    public UserRo create(UserRo user) {
        // TODO check for existing with same login
        User userToCreate = userRoMapper.map(user);
        User createdUser = userManager.create(userToCreate);
        return userRoMapper.map(createdUser);
    }

    @Transactional
    public List<UserRo> getAll() {
        return userRoMapper.map(userManager.getAll());
    }

    @Transactional
    public UserRo getOne(String guid) {
        return userRoMapper.map(userManager.getOne(guid));
    }

    @Transactional
    public void update(String guid, UserRo user) {
        User userToUpdate = userRoMapper.map(user);
        userManager.update(guid, userToUpdate);
    }

    @Transactional
    public void delete(String guid) {
        userManager.delete(guid);
    }
}
