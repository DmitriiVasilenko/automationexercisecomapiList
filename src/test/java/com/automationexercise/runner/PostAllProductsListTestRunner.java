package com.automationexercise.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.automationexercise.steps.post",
        tags = "@PostAllProducts",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class PostAllProductsListTestRunner extends AbstractTestNGCucumberTests {
}
