package com.playtech.dtos;

import java.util.HashMap;
import java.util.Map;

public class LogDTO {

    Map<String,String> fields = new HashMap<>();

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
