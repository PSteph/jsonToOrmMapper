package com.p2sdev.jsonToOrmMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;

import com.p2sdev.jsonToOrmMapper.mapper.JSONToConverter;
import com.p2sdev.jsonToOrmMapper.mapper.JSONTo.ORMModel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    
    @DisplayName("If an empty string is passed in a JSONException should be thrown")
    public void testWhenEmptyContentThrowsException_ThenAssertionSucceeds() {
    	assertThrows(JSONException.class, ()->{
    		new JSONToConverter("").getORMModel(ORMModel.DJANGO_MODEL);
    	});
    }
    
    @DisplayName("If a JSONArray is passed in a JSONException should be thrown")
    public void testWhenJSONArrayContentThrowsException_ThenAssertionSucceeds() {
    	assertThrows(JSONException.class, ()->{
    		new JSONToConverter("[{ 'person': {'f_name':'John', 'l_name':'Doe'} }]").getORMModel(ORMModel.DJANGO_MODEL);
    	});
    }
    
    @DisplayName("If an invalid JSONObject is passed in a JSONException should be thrown")
    public void testWhenInvalidJSONObjectContentThrowsException_ThenAssertionSucceeds() {
    	assertThrows(JSONException.class, ()->{
    		new JSONToConverter("{ 'person': 'f_name':'John', 'l_name':'Doe'} }").getORMModel(ORMModel.DJANGO_MODEL);
    	});
    }
    
    @DisplayName("Testing a valid JSONObject")
    public void testValidJSONObject() {
    	String expected = "from django.db import models\r\n" + 
    			"\r\n" + 
    			"class Person(models.Model):\r\n" + 
    			"	person_id = models.AutoField(primary_key=True)\r\n" + 
    			"	f_name = models.CharField(max_length=100)\r\n" + 
    			"	l_name = models.CharField(max_length=100)\r\n" + 
    			"	\n" + 
    			"";
    	
    	String actual = new JSONToConverter("{'person': {'f_name':'John', 'l_name':'Doe'} }").getORMModel(ORMModel.DJANGO_MODEL);
    	
    	assertEquals(expected, actual);
    }
    
}
