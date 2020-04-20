package com.github.mobrubov.usermanagement.rest.controller;

import java.util.List;

import com.github.mobrubov.usermanagement.common.exception.ErrorCode;
import com.github.mobrubov.usermanagement.common.exception.UserManagementException;
import com.github.mobrubov.usermanagement.rest.Constraint;
import com.github.mobrubov.usermanagement.rest.ro.ErrorRo;
import com.github.mobrubov.usermanagement.rest.ro.UserRo;
import com.github.mobrubov.usermanagement.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
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

    @PatchMapping("/{guid}")
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

    @ExceptionHandler(UserManagementException.class)
    public ResponseEntity<ErrorRo> handleUserManagementException(UserManagementException e) {
        log(e);
        ErrorRo error = new ErrorRo();
        error.setCode(e.getErrorCode());
        error.setMessage(e.getMessage());
        switch(e.getErrorCode()) {
            case BAD_REQUEST:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            case NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case UNKNOWN:
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
                          MethodArgumentNotValidException.class,
                          MissingPathVariableException.class,
                          MissingServletRequestParameterException.class,
                          HttpMessageNotReadableException.class,
                          HttpRequestMethodNotSupportedException.class,
                          BindException.class,
                          MethodArgumentTypeMismatchException.class
                      })
    public ErrorRo handleRestValidationException(RuntimeException e) {
        log(e);
        ErrorRo error = new ErrorRo();
        error.setCode(ErrorCode.BAD_REQUEST);
        error.setMessage(e.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorRo handleOtherException(RuntimeException e) {
        log(e);
        ErrorRo error = new ErrorRo();
        error.setCode(ErrorCode.UNKNOWN);
        error.setMessage(e.getMessage());
        return error;
    }

    private void log(Exception e) {
        log.error("Error while processing request", e);
    }
}
