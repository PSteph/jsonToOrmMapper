package com.p2sdev.jsonToOrmMapper.convert;

import com.p2sdev.jsonToOrmMapper.enums.JSONTypes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author p2sdev.com
 * This class build the @Table representing each @JSONObject of
 * the source JSON data. The keys of the @JSONObject will be the attributes
 * of the @Table.
 *
 */
class BuildTables {

    private List<Table> tables;

    public BuildTables(Map<String, JSONTypes> keysToJSONTypes, JSONObject baseJsonObj) {
        convertToTable(null, keysToJSONTypes, baseJsonObj);
        buildTables(baseJsonObj, keysToJSONTypes);
    }

    public List<Table> getTables(){
        return tables;
    }

    private List<Table> buildTables(JSONObject json, Map<String, JSONTypes> jsonAttributes) {
        for(String key : jsonAttributes.keySet()) {
            if(jsonAttributes.get(key) == JSONTypes.JSONOBJECT) {
                JSONObject jsonSub = json.getJSONObject(key);
                buildTable(jsonSub, key);
            }else if(jsonAttributes.get(key) == JSONTypes.JSONARRAY) {
                // get the first element and ignore the rest
                // Only process if array element is JSONObject
                try {
                    JSONObject jsonSub = json.getJSONArray(key).getJSONObject(0);
                    buildTable(jsonSub, key);
                }catch(JSONException e) {
                    // JSONArray doesn't hold JSONObjects
                    System.out.println("Error: JSONArray doesn't hold JSONObjects");
                }

            }
        }
        return tables;
    }

    private void buildTable(JSONObject json, String key) {
        Map<String, JSONTypes> attributes = new HashMap<>();

        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String aKey = keys.next();
            Object value = json.get(aKey);
            attributes.put(aKey, JsonTypes.getType(value));
        }
        convertToTable(key, attributes, json);
        buildTables(json, attributes);
    }

    /**
     * Check if the data object can be converted into a table and convert if convertible
     * To be converted into a table the data Object should have one or more @DataTypes
     * @param tableName : Used as table name.
     * @param attributes : a mapping of JSON keys to @JSONTypes
     * @param json : a json Object from the source data that will be converted into a @Table
     */
    private void convertToTable(String tableName, Map<String, JSONTypes> attributes, JSONObject json) {

        Collection<JSONTypes> currentDataTypes = attributes.values();

        List<JSONTypes> dataTypes = Arrays.asList(JSONTypes.BOOLEAN,
                JSONTypes.CHARACTER, JSONTypes.INTEGER, JSONTypes.DATE, JSONTypes.STRING);


        List<JSONTypes> intersection = currentDataTypes.stream()
                .distinct()
                .filter(dataTypes::contains)
                .collect(Collectors.toList());

        if((tableName == null || tableName.isEmpty()) && intersection.size()==0 ) {
            // the jsonObject cannot be converted into a table
            return;
        }

        Table table;

        if((tableName == null || tableName.isEmpty()) && intersection.size() != 0) {
            table = new Table.TableBuilder()
                    .setAttributes(attributes)
                    .setRawJson(json)
                    .build();
        }

        else {
            table = new Table.TableBuilder()
                    .setAttributes(attributes)
                    .setTableName(tableName)
                    .setRawJson(json)
                    .build();
        }

        if(table != null) {
            if(tables == null) {
                tables = new ArrayList<>();
            }
            tables.add(table);
        }
    }

}