package com.dbs.junitGenerator.service;

import java.io.File;
import java.util.List;

public interface TestGeneratorService {

    public void generateJUnitTestCases(List<File> pojoClasses);

}
