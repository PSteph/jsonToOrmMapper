package com.p2sdev.jsonToOrmMapper.mapper;

public interface JSONTo {
	enum ORMModel{
		HIBERNATE, DOCTRINE, DJANGO_MODEL, ROOM
	}
	
	enum DATABASE {
		POSTGRES, MYSQL, SQLITE
	}
	
	enum SQLOperation{
		CREATE, CREATE_INSERT
	}
	
	public String getORMModel(ORMModel model);
	
	public String getSQL(DATABASE db, SQLOperation operation);
}
