package com.p2sdev.jsonToOrmMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * java -jar /path/to/executable.jar --filename /path/to/file.json --orm [hibernate|django]
 * @author p2sdev.com
 *
 */
public class CommandLine {

	private static List<String> options = Arrays.asList("hibernate", "django", "postgres");
	private static String cmdFormat = "java -jar /path/to/executable.jar --filename /path/to/file.json --orm [hibernate|django]";
	
	public static void run(String[] args) {
		if(args.length < 4) {
			System.out.println("[ERROR] You can use the command as follow");
			System.out.println(cmdFormat);
			return;
		}
		
		List<String> argsList = Arrays.asList(args);
		int indexOfFilename = argsList.indexOf("--filename");
		int indexOfOption = argsList.indexOf("--orm");
		
		if(indexOfFilename == -1) {
			System.out.println("[ERROR] You need to specify the file location with --filename");
			System.out.println(cmdFormat);
			return;
		}
		
		if(indexOfOption == -1) {
			System.out.println("[ERROR] You need to specify the option with ---orm");
			System.out.println(cmdFormat);
			return;
		}
		
		if( !isFileExist(argsList.get(indexOfFilename+1)) ) {
			System.out.printf("[ERROR] File %s doesn't exist", (args[indexOfFilename+1]));
			return;
		}
		
		if( !isValidOption(argsList.get(indexOfOption+1)) ) {
			System.out.println("[ERROR] Wrong option. You should choose one of hibernate or django");
			return;
		}
		
		
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
