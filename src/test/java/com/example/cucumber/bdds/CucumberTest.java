package com.example.cucumber.bdds;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//‘@CucumberOptions’ annotation is used to configure the directory that contains the feature files and output plugins.
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberTest extends SpringIntegrationTest{
}
