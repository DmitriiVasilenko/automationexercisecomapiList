package com.automationexercise.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.automationexercise.steps.get",
        tags = "@GetAllProducts",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class GetAllProductsListTestRunner extends AbstractTestNGCucumberTests {
}
