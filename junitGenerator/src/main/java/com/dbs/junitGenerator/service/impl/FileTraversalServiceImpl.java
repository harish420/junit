package com.dbs.junitGenerator.service.impl;

import com.dbs.junitGenerator.service.ClassCategorizaionService;
import com.dbs.junitGenerator.service.FileTraversalService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileTraversalServiceImpl implements FileTraversalService {

    @Autowired
    private ClassCategorizaionService classCategorizaionService;

    private static final Logger logger = LoggerFactory.getLogger(FileTraversalServiceImpl.class);

    // Method to traverse the repository and get Java class files
    public List<File> getJavaClasses(String basePath) {
        if (StringUtils.isNotBlank(basePath)) {
            logger.error("Base path is null or empty.");
            return new ArrayList<>();
        }

        File baseDirectory = new File(basePath);
        if (!baseDirectory.isDirectory()) {
            logger.error("Invalid base directory: {}", basePath);
            return new ArrayList<>();
        }

        List<File> javaClasses = new ArrayList<>();
        traverseDirectory(baseDirectory, javaClasses);
        logger.info("Found {} Java class files in path: {}", javaClasses.size(), basePath);
        classCategorizaionService.categorizeClasses(javaClasses);
        return javaClasses;
    }

    // Recursive method to traverse the directory structure
    private void traverseDirectory(File directory, List<File> javaClasses) {
        File[] files = directory.listFiles();
        if (files == null) {
            logger.info("No files found in directory: {}", directory.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                traverseDirectory(file, javaClasses);
            } else if (file.getName().endsWith(".java") && isClassFile(file)) {
                logger.info("Identified Java class file: {}", file.getAbsolutePath());
                javaClasses.add(file);
            }
        }
    }

    // Helper method to determine if a file is a Java class (not an interface)
    private boolean isClassFile(File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));

            content = content.replaceAll("//.*$", "");
            content = content.replaceAll("/\\*.*?\\*/", "");

            String fileNameWithoutExtension = file.getName().replace(".java", "");

            if (content.contains("public class " + fileNameWithoutExtension)) {
                return true; // It's a normal class
            }
        } catch (Exception e) {
            logger.error("Error reading file: {}", file.getAbsolutePath(), e);
        }
        return false;
    }
}
