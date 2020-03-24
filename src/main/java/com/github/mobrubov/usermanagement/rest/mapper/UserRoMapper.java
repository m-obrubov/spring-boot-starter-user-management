package com.github.mobrubov.usermanagement.rest.mapper;

import java.util.List;
import java.util.UUID;

import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.rest.ro.UserRo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoMapper {
    UserRo map(User user);

    @Mapping(target = "guid", ignore = true)
    User map(UserRo userRo);

    List<UserRo> map(List<User> users);

    default String uuid(UUID uuid) {
        return uuid.toString();
    }

    default UUID uuid(String uuid) {
        return UUID.fromString(uuid);
    }
}
