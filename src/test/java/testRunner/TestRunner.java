package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions
		(
				features= {".//Features//"},
			//	features= "@target/rerun.txt", // To run the only failed test execution
				glue="stepsDefinitions",
				plugin= {
						"pretty",
						"html:reports/myreport.html",
						"json:reports/myreport.json",
						"rerun:target/rerun.txt",
				},
				dryRun=false,
				monochrome=true
				// tags = "@sanity"
				
				
		)

public class TestRunner {

}
