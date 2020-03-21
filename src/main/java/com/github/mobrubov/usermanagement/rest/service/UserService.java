package com.github.mobrubov.usermanagement.rest.service;

import com.github.mobrubov.usermanagement.rest.ro.UserRo;

import java.util.List;

public interface UserService {
    UserRo create(UserRo user);
    List<UserRo> getAll();
    UserRo getOne(String guid);
    void update(String guid, UserRo user);
    void delete(String guid);
}
