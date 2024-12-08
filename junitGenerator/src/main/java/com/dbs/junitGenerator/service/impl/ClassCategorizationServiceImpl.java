package com.dbs.junitGenerator.service.impl;

import com.dbs.junitGenerator.beans.CategorizedClassBean;
import com.dbs.junitGenerator.service.ClassCategorizaionService;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassCategorizationServiceImpl implements ClassCategorizaionService {

    private static final Logger logger = LoggerFactory.getLogger(ClassCategorizationServiceImpl.class);

    private final JavaParser javaParser = new JavaParser();

    public CategorizedClassBean categorizeClasses(List<File> javaFiles) {
        CategorizedClassBean categorizedClassBean = new CategorizedClassBean();
        List<File> pojoClasses = new ArrayList<>();
        List<File> methodClasses = new ArrayList<>();
        if (null != javaFiles && !javaFiles.isEmpty()) {
            for (File javaFile : javaFiles) {
                try {
                    CompilationUnit compilationUnit = javaParser.parse(javaFile).getResult().orElse(null);
                    if (compilationUnit == null) {
                        logger.info("Could not parse file: {}", javaFile.getAbsolutePath());
                        continue;
                    }

                    // Check if the file contains a class
                    compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
                        if (!classDecl.isInterface() && hasMethods(classDecl)) {
                            methodClasses.add(javaFile);
                        } else {
                            pojoClasses.add(javaFile);
                        }
                    });
                } catch (Exception e) {
                    logger.error("Error processing file: {}", javaFile.getAbsolutePath(), e);
                }
            }
        }
        logger.info("Categorized classes: {} method classes, {} POJOs.", methodClasses.size(), pojoClasses.size());
        categorizedClassBean.setMethodClasses(methodClasses);
        categorizedClassBean.setPojoClasses(pojoClasses);

        return categorizedClassBean;
    }

    // Helper method to check if a class has methods
    private boolean hasMethods(ClassOrInterfaceDeclaration classDecl) {
        return !classDecl.findAll(MethodDeclaration.class).isEmpty();
    }
}

