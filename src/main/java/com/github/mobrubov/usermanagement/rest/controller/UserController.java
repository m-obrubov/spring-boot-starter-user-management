package com.github.mobrubov.usermanagement.rest.controller;

import com.github.mobrubov.usermanagement.rest.Constraint;
import com.github.mobrubov.usermanagement.rest.ro.UserRo;
import com.github.mobrubov.usermanagement.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserRo create(@RequestBody @Validated(Constraint.Create.class) UserRo user) {
        return userService.create(user);
    }

    @GetMapping
    public List<UserRo> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{guid}")
    public UserRo getOne(@PathVariable("guid") String guid) {
        return userService.getOne(guid);
    }

    @PutMapping("/{guid}")
    public void update(
            @PathVariable("guid") String guid,
            @RequestBody @Validated(Constraint.Update.class) UserRo user
    ) {
        userService.update(guid, user);
    }

    @DeleteMapping("/{guid}")
    public void delete(@PathVariable("guid") String guid) {
        userService.delete(guid);
    }
}
