package com.p2sdev.jsonToOrmMapper;

import com.p2sdev.jsonToOrmMapper.mapper.JSONToConverter;
import com.p2sdev.jsonToOrmMapper.mapper.JSONTo.ORMModel;

/**
 * @author p2sdev.com
 * @version 2019-11-27
 *
 */
public class App {
    public static void main( String[] args ){
    	String content = "{ 'person': {'f_name':'John', 'l_name':'Doe'} }";
    	System.out.println(new JSONToConverter(content).getORMModel(ORMModel.DJANGO_MODEL));
    	if(args.length != 0)
    		CommandLine.run(args);
    }
}
