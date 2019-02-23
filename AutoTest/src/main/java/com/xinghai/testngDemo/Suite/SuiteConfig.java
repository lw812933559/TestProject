package com.xinghai.testngDemo.Suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SuiteConfig {
    @BeforeSuite
    public void BeforeSuite(){
        System.out.println("suite test beforeSuite");
    }

    @AfterSuite
    public void AfterSuite(){
        System.out.println("suite test afterSuite");
    }

}
