package com.p2sdev.jsonToOrmMapper.mapper.database;

import java.util.List;

import com.p2sdev.jsonToOrmMapper.convert.Table;

public interface Database {
	
	/**
	 * returns SQL Create and Insert
	 * @param tables @Table
	 * @return
	 */
	public abstract String getSQL(List<Table> tables);
	
	/**
	 * Returns a string representing the SQL Create statement for the tables in parameter
	 * @param tables
	 * @return
	 */
	public abstract String getSQLCreate(List<Table> tables);
	
	/**
	 * Returns a string representing the SQL Insert statement for the tables in parameter
	 * @param tables
	 * @return
	 */
	public abstract String getSQLInsert(List<Table> tables);
	
}
