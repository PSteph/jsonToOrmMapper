package com.p2sdev.jsonToOrmMapper.convert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Mapping string content into a List<Table>. 
 * If the string content is a @JSONObject we can proceed with the mapping.
 * If the String content is a @JSONArray we throw and exception.
 * 
 * @author p2sdev.com
 * @version 2019-11-27
 *
 */
public class MapJsonToTable {
	
	private JSONObject baseJsonObj;
	
	private Map<String, JSONTypes> keysToJSONTypes = new HashMap<>();

	private List<Table> tables = new ArrayList<>();
	
	/**
	 * JSONObject throws a checked JSONException if there 
	 * is a syntax error in the source string or a duplicated key.
	 * It will also throw an error if the source string is a json array
	 */
	protected MapJsonToTable(String content) {
		if(content == null)
			throw new IllegalArgumentException("JSON cannot be null");
		
		baseJsonObj = new JSONObject(content);
		keysToJSONTypes = mapJsonKeyToJSONType(baseJsonObj);
		new BuildTables();
	}
	
	protected List<Table> getTables(){
		return tables;
	}
	
	/**
	 * @param @JSONObject
	 * @return hashMap mapping JSON key to a specific @JSONTypes 
	 */
	private Map<String, JSONTypes> mapJsonKeyToJSONType(JSONObject json) {
		Map<String, JSONTypes> attrs = new HashMap<>();
		Iterator<String> keys = json.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			Object value = json.get(key);
			attrs.put(key, getType(value));
		}
		return attrs;
	}
	
	/**
	 * Given the value of a json key identify the matching @JSONTypes
	 * @param value : the value of a particular json key
	 * @return @JSONTypes
	 */
	private JSONTypes getType(Object value) {
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
	
	/**
	 * 
	 * @author p2sdev.com
	 * This class build the @Table representing each @JSONObject of
	 * the source JSON data. The keys of the @JSONObject will be the attributes
	 * of the @Table.
	 *
	 */
	private class BuildTables{
		
		public BuildTables() {
			convertToTable(null, keysToJSONTypes, baseJsonObj);
			buildTables(baseJsonObj, keysToJSONTypes);
		}
		
		public List<Table> buildTables(JSONObject json, Map<String, JSONTypes> jsonAttributes) {
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
				attributes.put(aKey, getType(value));
			}
			convertToTable(key, attributes, json);
			buildTables(json, attributes);
		}
		
		/**
		 * Check if the data object can be converted into a table and convert if convertible
		 * To be converted into a table the data Object should have one or more @DataTypes
		 * @param key : Used as table name.
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
			
			Table table = null;
			
			if((tableName == null || tableName.isEmpty()) && intersection.size() != 0)
				table = new Table.TableBuilder()
						.setAttributes(attributes)
						.setRawJson(json)
						.build();
			
			else
				table = new Table.TableBuilder()
					.setAttributes(attributes)
					.setTableName(tableName)
					.setRawJson(json)
					.build();
			
			if(table != null)
				tables.add(table);
		}
		
	}
}
