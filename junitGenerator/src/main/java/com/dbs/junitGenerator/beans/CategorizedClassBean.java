package com.dbs.junitGenerator.beans;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class CategorizedClassBean {
    private List<File> pojoClasses;
    private List<File> methodClasses;
}
