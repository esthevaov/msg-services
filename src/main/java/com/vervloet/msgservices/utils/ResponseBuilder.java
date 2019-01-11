package com.vervloet.msgservices.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
  //private Map<String, Object> responseMap = new HashMap<>();

  public static ResponseEntity<Map<String, Object>> createErrorResponse(String error, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("error", error);
    responseMap.put("data", "");

    return new ResponseEntity<>(responseMap, status);
  }

  public static ResponseEntity<Map<String, Object>> createDataResponse(Object data, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("error", "");
      responseMap.put("data", data);
    return new ResponseEntity<>(responseMap, status);
  }
}
