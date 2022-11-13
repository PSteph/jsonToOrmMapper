package com.p2sdev.jsonToOrmMapper.convert;

import com.p2sdev.jsonToOrmMapper.enums.JSONTypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonTypes {

    /**
     * Given the value of a json key identify the matching @JSONTypes
     * @param value : the value of a particular json key
     * @return @JSONTypes
     */
    public static JSONTypes getType(Object value) {
        String data = value.toString();
        if(data == null) {
            return JSONTypes.STRING;
        }

        // Check if it is a JSONObject
        try {
            new JSONObject(data);
            return JSONTypes.JSONOBJECT;
        }catch(JSONException e) {}

        // Then check if it is JSONArray
        try {
            new JSONArray(data);
            return JSONTypes.JSONARRAY;
        }catch(JSONException e) {}

        // Then check if it is a Boolean
        try {
            if(data.equals("true") || data.equals("false"))
                return JSONTypes.BOOLEAN;
        }catch(Exception e) {}

        // Then check if it is a Character
        try {
            if(data.length() == 1) {
                try {
                    Integer.parseInt(data);
                    return JSONTypes.INTEGER;
                }catch(Exception e) {}

                data.charAt(0);
                return JSONTypes.STRING;
            }
        }catch(Exception e) {}

        // Then check if it is an Integer/Number
        try {
            Integer.parseInt(data);
            return JSONTypes.INTEGER;
        }catch(Exception e) {}

        // Then check if it is a Date
        try {
            // make date to accept date and date/time format
            DateTimeFormatter f1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//			DateTimeFormatter f2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate.parse(data, f1);
            return JSONTypes.DATE;
        }catch(Exception e) {}

        // Lastly if none of the above return String type
        return JSONTypes.STRING;
    }
}
