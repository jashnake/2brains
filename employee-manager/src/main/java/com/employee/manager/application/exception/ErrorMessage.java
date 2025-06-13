package com.employee.manager.application.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ErrorMessage implements Serializable {
    private static final long serialVersionUID = 6911271061904665512L;
    private String reason;
    private Integer httpCode;
}
