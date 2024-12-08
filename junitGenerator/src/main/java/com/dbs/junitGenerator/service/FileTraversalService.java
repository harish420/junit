package com.dbs.junitGenerator.service;

import java.io.File;
import java.util.List;

public interface FileTraversalService {

    public List<File> getJavaClasses(String basePath);

}
