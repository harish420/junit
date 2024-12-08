package com.dbs.junitGenerator.service.impl;

import com.dbs.junitGenerator.Utils.TestTemplateUtil;
import com.dbs.junitGenerator.service.TestGeneratorService;
import com.github.javaparser.JavaParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class TestGeneratorServiceImpl implements TestGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(TestGeneratorServiceImpl.class);
    private final JavaParser javaParser = new JavaParser();

    // Sub Method 2: Create JUnit test cases for POJO classes
    public void generateJUnitTestCases(List<File> pojoClasses) {
        for (File pojoClass : pojoClasses) {
            try {
                String className = pojoClass.getName().replace(".java", "");
                String testClassName = className + "Test";
                String testFilePath = pojoClass.getAbsolutePath()
                        .replace("src/main/java", "src/test/java")
                        .replace(className + ".java", testClassName + ".java");

                // Create test case file based on template
                File testFile = new File(testFilePath);
                testFile.getParentFile().mkdirs(); // Create directories if they don't exist
                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write(TestTemplateUtil.generateTestTemplate(className));
                    logger.info("Test case generated for POJO: {}", testClassName);
                }
            } catch (Exception e) {
                logger.error("Error generating test case for POJO: {}", pojoClass.getAbsolutePath(), e);
            }
        }
    }
}

