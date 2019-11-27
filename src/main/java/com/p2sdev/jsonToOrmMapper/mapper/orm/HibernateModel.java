package com.p2sdev.jsonToOrmMapper.mapper.orm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p2sdev.jsonToOrmMapper.convert.JSONTypes;
import com.p2sdev.jsonToOrmMapper.convert.Table;

/**
 * This class follows JPA 2.1 specifications. Returned model
 * should therefore be compatible with ORMs implementing JPA 2.1
 * specifications, Hibernate included.
 * 
 * @author p2sdev.com
 * @version 2019-11-27
 *
 */
public class HibernateModel implements ORM {
	
	private StringBuilder orm = new StringBuilder("");
	private Map<String, StringBuilder> modelClasses = new HashMap<>();
	private List<Table> tables;
	private Template template = new Template();
	private Map<String, StringBuilder> attributeConstraints = new HashMap<>();
	private Map<String, StringBuilder> getterSetterConstraints = new HashMap<>();
	
	private enum DataTypes{
		STRING("String"), LONG("Long"), DATE("Date"), TIME("Time"), TIMESTAMP("Timestamp"), BOOLEAN("boolean");
		
		private String value;
		private DataTypes(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}

	@Override
	public String getOrm(List<Table> tables) {
		this.tables = tables;
		this.initOrm()
			.buildClasses()
			.setRelationships();
		
		for(Table table : tables) {
			orm.append(modelClasses.get(table.getTableName()).toString()).append("\n\n");
		}
		return orm.toString();
	}
	
	private HibernateModel initOrm() {
		return this;
	}
	
	private HibernateModel buildClasses() {
		tables.forEach(table ->{
			buildClass(table);
		});
		return this;
	}
	
	private void buildClass(Table table) {
		String entity = buildBaseClass(table.getTableName());
		entity = buildPrimaryKey(entity, table.getTableName());
		String[] attrGetterSetter = buildAttributes(table);
		entity = entity.replace("attr_list", attrGetterSetter[0]);
		entity = entity.replace("getters_setters", attrGetterSetter[1]);
		StringBuilder value = new StringBuilder(entity);
		modelClasses.put(table.getTableName(), value);
	}
	
	private String buildBaseClass(String tableName) {
		String entity = template.getBaseTemplate();
		entity = entity.replaceAll("class_name", formatClassName(tableName));
		return entity.replaceAll("table_name", tableName);
	}
	
	/**
	 * The primary key is a concatenation of the table name and "Id"
	 * @param entity
	 * @return
	 */
	private String buildPrimaryKey(String entity, String tableName) {
		String attr = formatAttrName(tableName);
		attr = attr+"Id";
		entity = entity.replaceAll("pk_name", attr);
		entity = entity.replaceAll("db_name_pk", tableName+"_id");
		return entity;
	}
	
	private String[] buildAttributes(Table table) {
		Map<String, JSONTypes> columnsDef = table.getTableColumnsDef();
		StringBuilder attrsBuilder = new StringBuilder();
		StringBuilder getterSettersBuilder = new StringBuilder();
		String attr = null;
		String getterSetter = null;
		for(String key : columnsDef.keySet()) {
			switch(columnsDef.get(key)) {
				case BOOLEAN:
					attr = template.getAttributeTemplate();
					attr = attr.replaceAll("attr_type", DataTypes.BOOLEAN.getValue());
					attr = attr.replaceAll("attr_name", formatAttrName(key));
					attr = attr.replaceAll("db_att_name", key);
					
					getterSetter = template.getGetterSetterTemplate();
					getterSetter = getterSetter.replaceAll("attr_type", DataTypes.BOOLEAN.getValue());
					getterSetter = getterSetter.replaceAll("attr_name", formatAttrName(key));
					getterSetter = getterSetter.replaceAll("setter_name", formatSetterName(key));
					getterSetter = getterSetter.replaceAll("getter_name", formatGetterName(key));
					
					break;
				case CHARACTER:
					break;
				case DATE:
					attr = template.getAttributeTemplate();
					attr = attr.replaceAll("attr_type", DataTypes.DATE.getValue());
					attr = attr.replaceAll("attr_name", formatAttrName(key));
					attr = attr.replaceAll("db_att_name", key);
					
					getterSetter = template.getGetterSetterTemplate();
					getterSetter = getterSetter.replaceAll("attr_type", DataTypes.DATE.getValue());
					getterSetter = getterSetter.replaceAll("attr_name", formatAttrName(key));
					getterSetter = getterSetter.replaceAll("setter_name", formatSetterName(key));
					getterSetter = getterSetter.replaceAll("getter_name", formatGetterName(key));
					break;
				case INVALID_JSON:
					break;
				case INTEGER:
					attr = template.getAttributeTemplate();
					attr = attr.replaceAll("attr_type", DataTypes.LONG.getValue());
					attr = attr.replaceAll("attr_name", formatAttrName(key));
					attr = attr.replaceAll("db_att_name", key);
					
					getterSetter = template.getGetterSetterTemplate();
					getterSetter = getterSetter.replaceAll("attr_type", DataTypes.LONG.getValue());
					getterSetter = getterSetter.replaceAll("attr_name", formatAttrName(key));
					getterSetter = getterSetter.replaceAll("setter_name", formatSetterName(key));
					getterSetter = getterSetter.replaceAll("getter_name", formatGetterName(key));
					break;
				case JSONARRAY:
					break;
				case JSONOBJECT:
					break;
				case STRING:
					attr = template.getAttributeTemplate();
					attr = attr.replaceAll("attr_type", DataTypes.STRING.getValue());
					attr = attr.replaceAll("attr_name", formatAttrName(key));
					attr = attr.replaceAll("db_att_name", key);
					
					getterSetter = template.getGetterSetterTemplate();
					getterSetter = getterSetter.replaceAll("attr_type", DataTypes.STRING.getValue());
					getterSetter = getterSetter.replaceAll("attr_name", formatAttrName(key));
					getterSetter = getterSetter.replaceAll("setter_name", formatSetterName(key));
					getterSetter = getterSetter.replaceAll("getter_name", formatGetterName(key));
					break;
				default:
					break;
			
			}
			
			if(attr != null)
				attrsBuilder.append(attr);
			if(getterSetter != null)
				getterSettersBuilder.append(getterSetter);
		}
		
		String[] data = {attrsBuilder.toString(), getterSettersBuilder.toString()};
		return data;
	}
	
	/**
	 * @Table getTableName() method returns a name in lower case.
	 * Words forming the name are separated by underscore. We want
	 * to format the name for Java. Example: visited_cities to VisitedCities
	 * @return
	 */
	private String formatClassName(String tableNameToFormat) {
		StringBuilder formatted = new StringBuilder();
		String[] splitedName = tableNameToFormat.split("_");
		Arrays.asList(splitedName).forEach( name -> {
			formatted.append(name.toString().substring(0, 1).toUpperCase()+name.toString().substring(1));
		});
		
		return formatted.toString();
	}
	
	/**
	 * The Java attribute name should be camelCase 
	 * @param attr
	 * @return
	 */
	private String formatAttrName(String attr) {
		StringBuilder formatted = new StringBuilder();
		String[] splitedName = attr.split("_");
		for(int i=0; i<splitedName.length; i++) {
			if(i==0){
				formatted.append(splitedName[i]);
			}else {
				formatted.append(splitedName[i].toString().substring(0, 1).toUpperCase()+splitedName[i].toString().substring(1));
			}
		}
		
		return formatted.toString();
	}
	
	/**
	 * The Java getter and setter should follow java beans format 
	 * @param attr
	 * @return javaBeans formatted name
	 */
	private String formatGetterName(String attr) {
		StringBuilder formatted = new StringBuilder();
		String[] splitedName = attr.split("_");
		for(int i=0; i<splitedName.length; i++) {
			if(i==0)
				formatted.append("get");
			formatted.append(splitedName[i].toString().substring(0, 1).toUpperCase()+splitedName[i].toString().substring(1));
		}
		return formatted.toString();
	}
	
	/**
	 * The Java getter and setter should follow java beans format 
	 * @param attr
	 * @return javaBeans formatted name
	 */
	private String formatSetterName(String attr) {
		StringBuilder formatted = new StringBuilder();
		String[] splitedName = attr.split("_");
		for(int i=0; i<splitedName.length; i++) {
			if(i==0)
				formatted.append("set");
			
			formatted.append(splitedName[i].toString().substring(0, 1).toUpperCase()+splitedName[i].toString().substring(1));
		}
		return formatted.toString();
	}
	
	private HibernateModel setRelationships() {
		
		for(Table table : tables) {
			buildRelationship(table);
		}
		
		for(Table table : tables) {
			String entity = modelClasses.get(table.getTableName()).toString();
			if(attributeConstraints.get(table.getTableName()) != null) {
				entity = entity.replace("tab_const_attr", attributeConstraints.get(table.getTableName()));
			}else {
				entity = entity.replace("tab_const_attr", "");
			}
			
			if(getterSetterConstraints.get(table.getTableName()) != null) {
				entity = entity.replace("tab_const_get_sett", getterSetterConstraints.get(table.getTableName()));
			}else {
				entity = entity.replace("tab_const_get_sett", "");
			}
			
			modelClasses.put(table.getTableName(), new StringBuilder(entity));
		}
		return this;
	}
	
	private void buildRelationship(Table table) {
		
		for(String key : table.getRelationships().keySet()) {
			String attrConst = null;
			String getSetConst = null;
			switch(table.getRelationships().get(key)) {
				case MANYTOMANY:
					break;
				case ONETOMANY:
					// manyToOne
					attrConst = template.getManyToOneTemplate();
					getSetConst = template.getGetterSetterTemplate();
					
					attrConst = attrConst.replaceAll("fk_id", table.getTableName()+"_id");
					attrConst = attrConst.replaceAll("fk_table_name", formatClassName(table.getTableName()));
					attrConst = attrConst.replaceAll("fk_attr_name", formatAttrName(table.getTableName()));
					
					attrConst += "\n\n";
					
					getSetConst = template.getGetterSetterTemplate();
					getSetConst = getSetConst.replaceAll("attr_type", formatClassName(table.getTableName()));
					getSetConst = getSetConst.replaceAll("attr_name", formatAttrName(table.getTableName()));
					getSetConst = getSetConst.replaceAll("setter_name", formatSetterName(table.getTableName()));
					getSetConst = getSetConst.replaceAll("getter_name", formatGetterName(table.getTableName()));
					
					getSetConst += "\n\n";
					
					break;
				case ONETOONE:
					attrConst = template.getOneToOneTemplate();
					getSetConst = template.getGetterSetterTemplate();
					
					attrConst = attrConst.replaceAll("fk_id", table.getTableName()+"_id");
					attrConst = attrConst.replaceAll("fk_table_name", formatClassName(table.getTableName()));
					attrConst = attrConst.replaceAll("fk_attr_name", formatAttrName(table.getTableName()));
					
					attrConst += "\n";
					
					getSetConst = template.getGetterSetterTemplate();
					getSetConst = getSetConst.replaceAll("attr_type", formatClassName(table.getTableName()));
					getSetConst = getSetConst.replaceAll("attr_name", formatAttrName(table.getTableName()));
					getSetConst = getSetConst.replaceAll("setter_name", formatSetterName(table.getTableName()));
					getSetConst = getSetConst.replaceAll("getter_name", formatGetterName(table.getTableName()));
					
					getSetConst += "\n\n";
					
					break;
				default:
					break;
			}
			
			if(attrConst != null) {
				if(attributeConstraints.containsKey(key)) {
					attributeConstraints.get(key).append(attrConst);
				}else {
					attributeConstraints.put(key, new StringBuilder(attrConst));
				}
			}
			
			if(getSetConst != null) {
				if(getterSetterConstraints.containsKey(key)) {
					getterSetterConstraints.get(key).append(getSetConst);
				}else {
					getterSetterConstraints.put(key, new StringBuilder(getSetConst));
				}
			}

		}
	}
	
	class Template{
		private String baseTemplate = "import javax.persistence.*;\r\n" + 
				"\r\n" + 
				"@Entity\r\n" + 
				"@Table(name = \"table_name\")\r\n" + 
				"public class class_name {\r\n" + 
				"   @Id @GeneratedValue\r\n" + 
				"   @Column(name = \"db_name_pk\")\r\n" + 
				"   private Long pk_name;\n\n" +
				"attr_list" +
				"tab_const_attr" +
				"   public class_name() {}\r\n"+
				"\r\n" + 
				"getters_setters\n"+
				"tab_const_get_sett" +
				"}";
		
		private String emptyConstructorTemplate = "public class_name() {}";
		
		private String attributeTemplate = "   @Column(name = \"db_att_name\")\r\n" +
				"   private attr_type attr_name;\r\n\n";
		
		private String getterSetterTemplate = "   public attr_type getter_name() {\r\n" + 
				"      return attr_name;\r\n" + 
				"   }\r\n" + 
				"   \r\n" +
				"   public void setter_name( attr_type attr_name ) {\r\n" + 
				"      this.attr_name = attr_name;\r\n" + 
				"   }\r\n\n";
		
		private String manyToOneTemplate = "   @ManyToOne(fetch = FetchType.LAZY)\r\n" + 
				"   @JoinColumn(name=\"fk_id\", nullable=false)\r\n" + 
				"   private fk_table_name fk_attr_name;";
		
		private String oneToOneTemplate = "   @OneToOne(fetch = FetchType.LAZY)\r\n" + 
				"    @JoinColumn(name=\"fk_id\", nullable=false)\r\n" + 
				"    private fk_table_name fk_attr_name;";

		public String getBaseTemplate() {
			return baseTemplate;
		}

		public String getEmptyConstructorTemplate() {
			return emptyConstructorTemplate;
		}

		public String getAttributeTemplate() {
			return attributeTemplate;
		}

		public String getGetterSetterTemplate() {
			return getterSetterTemplate;
		}

		public String getManyToOneTemplate() {
			return manyToOneTemplate;
		}

		public String getOneToOneTemplate() {
			return oneToOneTemplate;
		}
		
		
	}

}
