package com.p2sdev.jsonToOrmMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.p2sdev.jsonToOrmMapper.mapper.JSONTo.ORMModel;
import com.p2sdev.jsonToOrmMapper.mapper.JSONToConverter;
import com.p2sdev.jsonToOrmMapper.mapper.JSONTo.DATABASE;
import com.p2sdev.jsonToOrmMapper.mapper.JSONTo.SQLOperation;

/**
 * java -jar /path/to/executable.jar --filename /path/to/file.json --option [hibernate|django|postgres]
 * @author p2sdev.com
 *
 */
public class CommandLine {

	private static List<String> options = Arrays.asList("hibernate", "django", "postgres");
	private static String cmdFormat = "java -jar /path/to/executable.jar --filename /path/to/file.json --option [hibernate|django|postgres]";
	
	public static void run(String[] args) {
		if(args.length < 4) {
			System.out.println("[ERROR] You can use the command as follow");
			System.out.println(cmdFormat);
			return;
		}
		
		List<String> argsList = Arrays.asList(args);
		int indexOfFilename = argsList.indexOf("--filename");
		int indexOfOption = argsList.indexOf("--option");
		String filePath = null;
		String option = null;
		
		if(indexOfFilename == -1) {
			System.out.println("[ERROR] You need to specify the file location with --filename");
			System.out.println(cmdFormat);
			return;
		}
		
		if(indexOfOption == -1) {
			System.out.println("[ERROR] You need to specify the option with --option");
			System.out.println(cmdFormat);
			return;
		}
		
		if( !isFileExist(argsList.get(indexOfFilename+1)) ) {
			System.out.printf("[ERROR] File %s doesn't exist", (args[indexOfFilename+1]));
			return;
		}
		
		filePath = argsList.get(indexOfFilename+1);
		
		if( !isValidOption(argsList.get(indexOfOption+1)) ) {
			System.out.println("[ERROR] Wrong ORM. You should choose one of hibernate or django");
			return;
		}
		
		option = argsList.get(indexOfOption+1);
		
		ORMModel modelToGenerate = null;
		DATABASE db = null;
		SQLOperation operation = SQLOperation.CREATE;
		String model = null;
		
		if(argsList.get(indexOfOption+1).equals("hibernate"))
			modelToGenerate = ORMModel.HIBERNATE;
		else if(argsList.get(indexOfOption+1).equals("django"))
			modelToGenerate = ORMModel.DJANGO_MODEL;
		else if(option.equals("postgres"))
			db = DATABASE.POSTGRES;
		
		
		if(modelToGenerate != null)
			model = new JSONToConverter(getContent(filePath)).getORMModel(modelToGenerate);
		
		if(db != null)
			model = new JSONToConverter(getContent(filePath)).getSQL(db, operation);
		
		System.out.println(model);
		
	}
	
	private static String getContent(String name) {
		name = name.replace("~", System.getProperty("user.home"));
    	Path path = Paths.get(name);
    	StringBuffer content = new StringBuffer();
    	
    	try {
    		List<String> contentList = Files.readAllLines(path);
    		contentList.forEach(data -> content.append(data));
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	
		return content.toString();
	}
	
	private static boolean isFileExist(String name) {
    	name = name.replace("~", System.getProperty("user.home"));
    	Path path = Paths.get(name);
    	return Files.isRegularFile(path);
    }
    
    private static boolean isValidOption(String option) {
    	return options.contains(option);
    }
}
