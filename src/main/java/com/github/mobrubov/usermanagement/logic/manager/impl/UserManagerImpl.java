package com.github.mobrubov.usermanagement.logic.manager.impl;

import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.rest.ro.UserRo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagerImpl implements UserManager {
    @Override
    public UserRo create(UserRo user) {
        return null;
    }

    @Override
    public List<UserRo> getAll() {
        return null;
    }

    @Override
    public UserRo getOne(String guid) {
        return null;
    }

    @Override
    public void update(String guid, UserRo user) {

    }

    @Override
    public void delete(String guid) {

    }
}
