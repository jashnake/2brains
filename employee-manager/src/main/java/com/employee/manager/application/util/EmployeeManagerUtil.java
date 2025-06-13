package com.employee.manager.application.util;

import org.springframework.stereotype.Component;

@Component
public class EmployeeManagerUtil {

    public static String getErrorText(String error, String value) {
        var errorText = new StringBuilder();
        errorText.append(error);
        errorText.append(value);
        return errorText.toString();
    }
}
