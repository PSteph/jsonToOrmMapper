package com.p2sdev.jsonToOrmMapper.mapper.orm;

import java.util.List;

import com.p2sdev.jsonToOrmMapper.convert.Table;

public interface ORM {
	String getOrm(List<Table> tables);
}
