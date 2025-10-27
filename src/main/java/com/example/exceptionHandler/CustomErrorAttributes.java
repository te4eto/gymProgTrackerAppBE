package com.example.exceptionHandler;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

//    @Override
//    public Map<String, Object> getErrorAttributes(
//            WebRequest webRequest, ErrorAttributeOptions options) {
//
//        // Only include status and error
//        Map<String, Object> defaultAttrs = super.getErrorAttributes(webRequest, options);
//        return Map.of(
//                "status", defaultAttrs.get("status"),
//                "error", defaultAttrs.get("error")
//        );
//    }
}
