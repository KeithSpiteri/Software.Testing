package Cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

 
@RunWith(Cucumber.class)

@CucumberOptions(plugin={"pretty"},glue = { "Cucumber.stepdefs" }, features = { "src/test/resources/" }, monochrome = true)
public class Runner {
}