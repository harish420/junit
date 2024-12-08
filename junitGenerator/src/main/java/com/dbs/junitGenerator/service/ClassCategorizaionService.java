package com.dbs.junitGenerator.service;

import com.dbs.junitGenerator.beans.CategorizedClassBean;
import com.dbs.junitGenerator.service.impl.ClassCategorizationServiceImpl;

import java.io.File;
import java.util.List;

public interface ClassCategorizaionService {

    public CategorizedClassBean categorizeClasses(List<File> javaFiles);

}
