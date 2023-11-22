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
    	String expected = "from django.db import modelsclass" +
                " Person(models.Model):" +
                "person_id = models.AutoField(primary_key=True)" +
                "f_name = models.CharField(max_length=100)" +
                "l_name = models.CharField(max_length=100)" +
                "p_id = models.IntegerField()";
    	
    	expected = expected.replaceAll("\\n|\\r\\n|\\r|\\t", "");
    	
    	String actual = new JSONToConverter("{'person': {'f_name':'John', 'l_name':'Doe', 'p_id':1} }").getORMModel(ORMModel.DJANGO_MODEL);
    	actual = actual.replaceAll("\\n|\\r\\n|\\r|\\t", "");
    	
    	assertEquals(expected, actual);
    }
    
}
