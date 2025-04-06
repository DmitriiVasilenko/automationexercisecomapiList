package com.automationexercise.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonStructureValidator {

    public static boolean fieldExists(Object currentObject, String fieldName) {
        String[] fieldParts = fieldName.split("\\.");
        for (String field : fieldParts) {
            if (currentObject instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) currentObject;
                if (jsonObject.has(field)) {
                    currentObject = jsonObject.get(field);
                } else {
                    return false;
                }
            } else if (currentObject instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) currentObject;
                boolean fieldFound = false;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject arrayObject = jsonArray.getJSONObject(i);
                    if (arrayObject.has(field)) {
                        currentObject = arrayObject.get(field);
                        fieldFound = true;
                        break;
                    }
                }
                if (!fieldFound) {
                    return false;
                }
            } else {
                return false; // Unexpected structure
            }
        }
        return true;
    }
}