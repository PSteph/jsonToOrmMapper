package com.p2sdev.jsonToOrmMapper.mapper.orm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p2sdev.jsonToOrmMapper.enums.JSONTypes;
import com.p2sdev.jsonToOrmMapper.convert.Table;

/**
 * Build Django model that correspond to the List<Table> 
 * @author p2sdev.com
 * @version 2019-11-27
 * 
 */
public class DjangoModel implements ORM{
	
	private StringBuilder orm = new StringBuilder("");
	private Map<String, StringBuilder> modelClasses = new HashMap<>();
	private List<Table> tables;
	
	private enum Model{
		IMPORT("from django.db import models"), CLASS_DEF("class {name}(models.Model):"),
		PRIMARY_KEY(" = models.AutoField(primary_key=True)"), MANYTOONE(" = models.ForeignKey({to}, on_delete=models.CASCADE)"),
		MANYTOMANY(" = models.ManyToManyField({to})"), 
		ONETOONE(" = models.OneToOneField({to},on_delete=models.CASCADE)"), 
		VARCHAR_30("models.CharField(max_length=30)"),VARCHAR_100(" = models.CharField(max_length=100)"), 
		INTEGER(" = models.IntegerField()"),BOOLEAN(" = models.BooleanField()"), DATE(" = models.DateField()"),
		DATETIME(" = models.DateTimeField()"), EMAIL(" = models.EmailField(max_length=254)");
		
		private String value;
		private Model(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}

	@Override
	public String getOrm(List<Table> tables) {
		this.tables = tables;
		this.initOrm()
			.buildClasses()
			.setRelationships();
		
		for(Table table : tables) {
			orm.append(modelClasses.get(table.getTableName()).toString()).append("\n");
		}
		
		return orm.toString();
	}
	
	private DjangoModel initOrm() {
		orm.append(Model.IMPORT.getValue())
			.append("\n\n");
		return this;
	}
	
	private DjangoModel buildClasses() {
		tables.forEach(table ->{
			buildClass(table);
		});
		return this;
	}
	
	private void buildClass(Table table) {
		StringBuilder modelClass = new StringBuilder("");
		
		String classDef = Model.CLASS_DEF.getValue();
		String nameUppercase = table.getTableName().substring(0, 1).toUpperCase() + table.getTableName().substring(1);
		classDef = classDef.replace("{name}", nameUppercase);
		
		modelClass.append(classDef).append("\n\t");
		
		// set pk
		modelClass.append(table.getTableName()+"_id")
			.append(Model.PRIMARY_KEY.getValue())
			.append("\n\t");
		
		Map<String, JSONTypes> tableColumnsDef = table.getTableColumnsDef();
		
		for(String key : tableColumnsDef.keySet()) {
			switch(tableColumnsDef.get(key)) {
				case BOOLEAN:
					modelClass.append(key)
					.append(Model.BOOLEAN.getValue())
					.append("\n\t");
					break;
				case CHARACTER:
					break;
				case DATE:
					break;
				case INVALID_JSON:
					break;
				case INTEGER:
					modelClass.append(key)
					.append(Model.INTEGER.getValue())
					.append("\n\t");
					break;
				case JSONARRAY:
					break;
				case JSONOBJECT:
					break;
				case STRING:
					modelClass.append(key)
					.append(Model.VARCHAR_100.getValue())
					.append("\n\t");
					break;
				default:
					break;
				
			}
		}
		
		modelClasses.put(table.getTableName(), modelClass);
		
	}
	
	private DjangoModel setRelationships() {
		for(Table table : tables) {
			for(String tableName : table.getRelationships().keySet()) {
				String toUppercase = table.getTableName().substring(0, 1).toUpperCase() + table.getTableName().substring(1);
				
				StringBuilder modelClass = modelClasses.get(tableName);
				switch(table.getRelationships().get(tableName)) {
					case MANYTOMANY:
						
						break;
					case ONETOMANY:
						String manytoone = Model.MANYTOONE.getValue();
						manytoone = manytoone.replace("{to}", toUppercase);
						modelClass.append(table.getTableName()+"_id")
							.append(manytoone)
							.append("\n\t");
						
						break;
					case ONETOONE:
						String onetoone = Model.ONETOONE.getValue();
						onetoone = onetoone.replace("{to}", toUppercase);
						modelClass.append(table.getTableName()+"_id")
							.append(onetoone)
							.append("\n\t");
						
						break;
					default:
						break;
				}
				
			}
			
		}
		return this;
	}
	
}
