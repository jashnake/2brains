package com.employee.manager.application.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeException  extends Exception{
    private final ErrorMessage errorMessage;

    public EmployeeException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
