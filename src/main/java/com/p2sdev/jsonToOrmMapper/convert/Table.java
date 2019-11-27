package com.p2sdev.jsonToOrmMapper.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Table {
	public enum Cardinalities {
		ONETOONE, ONETOMANY, MANYTOMANY
	}
	private String tableName;
	private Map<String, Cardinalities> relationships = new HashMap<>();
	private Map<String, JSONTypes> tableColumnsDef;
	private JSONObject json;
	
	private Table(Map<String, JSONTypes> attrs, String name, JSONObject json) {
		this.json = json;
		tableColumnsDef = formatColumnName(checkJSONArray(attrs));
		tableName = formatName(name);
		relationships = buildRelationShips();
	}
	
	/**
	 * Constructor for tables that do not have a table name
	 * @param name
	 * @param json
	 */
	private Table(Map<String, JSONTypes> attrs, JSONObject json) {
		this(attrs, "BaseTable", json);
	}
	
	/**
	 * Transform JSONArray not containing JSONObjects into String type
	 */
	private Map<String, JSONTypes> checkJSONArray(Map<String, JSONTypes> attrs) {
		for(String key : attrs.keySet()) {
			if(attrs.get(key) == JSONTypes.JSONARRAY) {
				// Only process if array element is JSONObject
				try {
					json.getJSONArray(key).getJSONObject(0);
				}catch(JSONException e) {
					attrs.put(key, JSONTypes.STRING);
				}
			}
		}
		
		return attrs;
	}
	
	/**
	 * Build relationships between this table and other tables
	 * Given a table name Person with an attribute object {'address':{'post code':'', 'city':'London'}}
	 * We have a ONETOONE relationship between Person and Address
	 * Given a table name Person with an attribute object {'Visited Cities':[{'Name':'London', 'Country':'UK'}]}
	 * We have a ONETOMANY relationship between Person and Visited cities table
	 */
	private Map<String, Cardinalities> buildRelationShips() {
		Map<String, Cardinalities> relationships = new HashMap<>();
		for(String key : tableColumnsDef.keySet()) {
			if(tableColumnsDef.get(key) == JSONTypes.JSONOBJECT)
				relationships.put(key, Cardinalities.ONETOONE);
			else if(tableColumnsDef.get(key) == JSONTypes.JSONARRAY)
				relationships.put(key, Cardinalities.ONETOMANY);
		}
		return relationships;
	}
	
	/**
	 * Format the name to return a valid SQL table / column name
	 * Should have 122 (128-6 to add fk prefix to table if needed) characters all lower case. Separates words with underscore (_)
	 * @return 
	 */
	private String formatName(String name) {
		String[] names = name.split(" ");
		String theName = String.join("_", names);
		theName = theName.toLowerCase();
		return theName.length() < 128 ? theName : theName.substring(0, 128);
	}
	
	/**
	 * Format the table column to return a valid SQL table column
	 * @return @tableColumnsDef
	 */
	private Map<String, JSONTypes> formatColumnName(Map<String, JSONTypes> attrs) {
		Map<String, JSONTypes> attributes = new HashMap<>();
		for(String key : attrs.keySet()) {
			attributes.put(formatName(key), attrs.get(key));
		}
		return attributes;
	}
	
	public String getTableName() {
		return tableName;
	}

	public Map<String, Cardinalities> getRelationships() {
		return new HashMap<String, Cardinalities>(relationships);
	}

	public Map<String, JSONTypes> getTableColumnsDef() {
		return new HashMap<String, JSONTypes>(tableColumnsDef);
	}

	public JSONObject getJson() {
		return new JSONObject(json);
	}

	public static class TableBuilder{
		
		private Map<String, JSONTypes> attributes = new TreeMap<>();
		private String name;
		private JSONObject json;
		
		public TableBuilder setAttributes(Map<String, JSONTypes> attrs){
			if(attrs == null || attrs.isEmpty())
				throw new IllegalArgumentException("Table attributes cannot be null or empty");
			attributes = attrs;
			return this;
		}
		
		public TableBuilder setTableName(String name) {
			if(name == null || name.isEmpty())
				throw new IllegalArgumentException("Table name cannot be null empty");
			this.name = name;
			return this;
		}
		
		public TableBuilder setRawJson(JSONObject json) {
			if(json == null || json.isEmpty())
				throw new IllegalArgumentException("json cannot be null or empty");
			this.json = json;
			return this;
		}
		
		public Table build() {
			
			if(attributes == null || attributes.isEmpty())
				throw new IllegalArgumentException("Table attributes cannot be null or empty. Set with setAttributes");
			
			if(json == null || json.isEmpty())
				throw new IllegalArgumentException("json cannot be null or empty. Set with setRawJson");
			
			if(name== null || name.isEmpty())
				return new Table(attributes, json);
			
			return new Table(attributes, name, json);
		}
	}
	
	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", relationships=" + relationships + ", tableColumnsDef="
				+ tableColumnsDef + "]";
	}
}
