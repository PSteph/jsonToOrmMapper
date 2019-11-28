package com.p2sdev.jsonToOrmMapper.mapper;

import com.p2sdev.jsonToOrmMapper.convert.MapJsonToTable;
import com.p2sdev.jsonToOrmMapper.mapper.database.Database;
import com.p2sdev.jsonToOrmMapper.mapper.database.PostgreSQL;
import com.p2sdev.jsonToOrmMapper.mapper.orm.DjangoModel;
import com.p2sdev.jsonToOrmMapper.mapper.orm.HibernateModel;

/**
 * Converting the JSON content into the desired ORM or SQL
 * @author p2sdev.com
 * @version 2019-11-27
 *
 */
public class JSONToConverter extends MapJsonToTable implements JSONTo{
	
	/**
	 * Mapping JSON Object to List<Table> by calling @MapJsonToTable constructor
	 * @param json : the string content representing the @JSONObject to be mapped
	 */
	public JSONToConverter(String json) {
		super(json);	
	}

	@Override
	public String getORMModel(ORMModel model) {
		String orm = "";
		switch(model) {
			case DJANGO_MODEL:
				orm = new DjangoModel().getOrm(getTables());
				break;
			case DOCTRINE:
				break;
			case HIBERNATE:
				orm = new HibernateModel().getOrm(getTables());
				break;
			case ROOM:
				break;
			default:
				break;
		
		}
		return orm;
	}

	@Override
	public String getSQL(DATABASE db, SQLOperation operation) {
		String sql = "";
		switch(db) {
			case MYSQL:
				break;
			case POSTGRES:
				sql = getSql(new PostgreSQL(), operation);
				break;
			case SQLITE:
				break;
		}
		return sql;
	}
	
	private String getSql(Database postgreSQL, SQLOperation operation) {
		String sql = "";
		switch(operation) {
			case CREATE:
				sql = postgreSQL.getSQLCreate(getTables());
				break;
			case CREATE_INSERT:
				sql = postgreSQL.getSQLInsert(getTables());
				break;
		}
		return sql;
	}
}
