package stepsDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

public class loginSteps {
     WebDriver driver;
     HomePage homePage;
     LoginPage loginPage;
     MyAccountPage myAccountPage;

     List<HashMap<String, String>> data_map; //Data driven

     Logger logger; //for logging
     ResourceBundle rb; // for reading properties file
     String br; //to store browser name



    @Before
    public void setup() throws IOException    //Junit hook - executes once before starting
    {
        
    	//for logging
        logger= LogManager.getLogger(this.getClass());
        //Reading config.properties (for browser) Approach 1
        rb=ResourceBundle.getBundle("config");
        br=rb.getString("browser");
        
        
        
        
    	/*
        // Reading config.properties (for browser) Approach 2
        File src = new File(System.getProperty("user.dir")+"/src/main/resources/config.properties");
        FileInputStream fis =  new FileInputStream(src);
        Properties pro = new Properties();
        pro.load(fis);
        br = pro.getProperty("browser");
        */
        
     
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Scenario status ======>"+scenario.getStatus());
        if(scenario.isFailed()) {
        	
        	TakesScreenshot ts=(TakesScreenshot) driver;
        	byte[] screenshot=ts.getScreenshotAs(OutputType.BYTES);
        	scenario.attach(screenshot, "image/png",scenario.getName());
        	            
        }
       driver.quit();
    }
    
    
    @Given("user Launch browser")
    public void user_launch_browser() {
    	if(br.equals("chrome"))
        {
           driver=new ChromeDriver();
        }
        else if (br.equals("firefox")) {
            driver = new FirefoxDriver();
        }
        else if (br.equals("edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Given("opens page {string}")
    public void opens_page(String url) {
    	logger.info("Visiting the testing site.....");
    	driver.get(url);
    	logger.info("Maximize the browser windows.....");
        driver.manage().window().maximize();
    }

    @When("user enters a username {string} in username field")
    public void user_enters_a_username_in_username_field(String username) {
    	logger.info("Entering username.....");
    	loginPage = new LoginPage(driver);
    	loginPage.enter_username(username);
    }

    @When("user enters a password {string} in password field")
    public void user_enters_a_password_in_password_field(String password) {
    	logger.info("Entering password.....");
    	loginPage.enter_password(password);
    }

    @When("punch Submit button")
    public void punch_submit_button() {
    	logger.info("Clicking the submit credential request button.....");
       loginPage.click_login();
    }

    @Then("verify new page URL contains {string}")
    public void verify_new_page_url_contains(String url_value) {
    	logger.info("Checking and validating if the current url is the same.....");
        homePage = new HomePage(driver);
        
        homePage.assert_url(url_value);
        
    }
    
    @Then("verify new page contains expected text {string} or {string}")
    public void verify_new_page_contains_expected_text_or(String first_expected, String second_expected) {
    	logger.info("Checking and validating the text after successfully login.....");
    	homePage.assert_login_successfully_subtitle(first_expected);
        homePage.assert_login_successfully_subtitle(second_expected);
    }
    
    

    @Then("verify button Log out is displayed on the new page")
    public void verify_button_log_out_is_displayed_on_the_new_page() {
    	logger.info("Checking if logout exists.....");
        homePage.assert_logout_exists();
    }

    @Then("close the browser")
    public void close_the_browser() {
    	logger.info("Closing the browser.....");
        driver.close();
    }
   

    @When("click on Login")
    public void click_on_login() {

    }
    @Then("check user navigates to MyAccount Page by passing Email and Password with excel row {string}")
    public void check_user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_row(String row_number) {
       logger.debug("Debugging....");
    	data_map = DataReader.data(System.getProperty("user.dir")+"//testData//LoginData.xlsx", "Sheet1");
    	
    	int index = Integer.parseInt(row_number) - 1;
    	String username = data_map.get(index).get("username");
    	String password = data_map.get(index).get("password");
    	String exp_res = data_map.get(index).get("res");
    	String note_message = data_map.get(index).get("note");
    	
    	loginPage = new LoginPage(driver);
    	homePage = new HomePage(driver);
    	
    	loginPage.enter_username(username);
    	loginPage.enter_password(password);
    	
    	loginPage.click_login();
    	
    	try {
    		if(exp_res.equals("Valid")) {
    			boolean logout_btn_exists = homePage.assert_logout_exists();
    			System.out.println(logout_btn_exists);
    			if(logout_btn_exists == true) {
    				homePage.click_logout();
    				Assert.assertTrue(true);
    			}else {
    				Assert.assertTrue(false);
    			}
    		}
    		
    		if(exp_res.equals("Invalid")) {
    			if(note_message.equals("wrong username")) {
    				loginPage.assert_error_message("Your username is invalid!");
    				Assert.assertTrue(true);
    			}else if(note_message.equals("wrong password")) {
    				loginPage.assert_error_message("Your password is invalid!");
    				Assert.assertTrue(true);
    			}
    			
    		}
    		
    	} catch (Exception e) {
    		Assert.assertTrue(false);
    	}
    	

    	driver.close();
    }

    @Then("assert invalid message {string}")
    public void assert_invalid_message(String error_message) {
        loginPage.assert_error_message(error_message);
    }




}