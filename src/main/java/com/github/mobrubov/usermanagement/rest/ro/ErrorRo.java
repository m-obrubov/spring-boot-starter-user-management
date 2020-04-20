package com.github.mobrubov.usermanagement.rest.ro;

import com.github.mobrubov.usermanagement.common.exception.ErrorCode;
import lombok.Data;

/**
 * @author Максим
 * Created on 20.04.2020
 */
@Data
public class ErrorRo {
    private ErrorCode code;
    private String message;
}
