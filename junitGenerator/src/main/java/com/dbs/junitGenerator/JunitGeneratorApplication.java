package com.dbs.junitGenerator;

import com.dbs.junitGenerator.service.FileTraversalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JunitGeneratorApplication {

	@Autowired
	private FileTraversalService fileTraversalService;

	public static void main(String[] args) {
		SpringApplication.run(JunitGeneratorApplication.class, args);
	}

	public void startGeneration() {
		String directoryPath = "C:\\Users\\sruji\\Desktop\\Junit\\loan-application";
		fileTraversalService.getJavaClasses(directoryPath);
	}

}
