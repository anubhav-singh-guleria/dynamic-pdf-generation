package com.anubhav.pdf.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {
    private JsonUtility() {
    }

    public static <T> T toObject(String json, Class<T> classType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(json, classType);
        } catch (Exception var) {
            throw new RuntimeException(var);
        }
    }
}
