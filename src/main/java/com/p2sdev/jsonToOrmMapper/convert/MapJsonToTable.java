package com.p2sdev.jsonToOrmMapper.convert;


import com.p2sdev.jsonToOrmMapper.enums.JSONTypes;
import static com.p2sdev.jsonToOrmMapper.convert.JsonTypes.getType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	private Map<String, JSONTypes> keysToJSONTypes;

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
		tables = new BuildTables(keysToJSONTypes, baseJsonObj).getTables();
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

}
