package com.employee.manager.application.util;

import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
public class LogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

    private LogUtil() {
        LOGGER.debug("LogUtil constructor");
    }

    static Gson gson = new Gson();

    public static void  printLogInfoRequest(String method ,Object request) {
        var requestStr = gson.toJson(request);
        LOGGER.info("METHOD: {} | REQUEST: {}",
                method, StringEscapeUtils.escapeJava(requestStr));
    }

    public static void  printLogInfoResponse(Object response) {
        var requestStr = gson.toJson(response);
        LOGGER.info("RESPONSE: {}",
                StringEscapeUtils.escapeJava(requestStr));
    }

    public static void  printLogError(String error, HttpStatusCode status) {
        LOGGER.error("ERROR: {} | CODE: {}",
                error, status);
    }

    public static void printHeaders(HttpHeaders headers){
        headers.forEach((key, value) ->
            LOGGER.info("Header '{}' = {}", key, value)
        );
    }



}
