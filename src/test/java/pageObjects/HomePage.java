package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	// Elements
	@FindBy(how=How.CLASS_NAME, using="post-title")
	WebElement login_successful_text;
	
	@FindBy(linkText="Log out")
	WebElement logout_btn;
	
	@FindBy(xpath="//p[@class='has-text-align-center']")
	WebElement login_successful_subtext;
	
	
	
	
	//Action methods
	
	public void click_logout() {
		logout_btn.click();
	}
	
	public boolean assert_logout_exists() {
		return logout_btn.isDisplayed();
	}
	
	public boolean assert_login_successfully_text(String expected_text) {
		return login_successful_text.getText().contains(expected_text);
	}
	
	public boolean assert_url(String expected_url) {
		String actual_url =  driver.getCurrentUrl();
		return actual_url.contains(expected_url);
		
	}
	
	public boolean assert_login_successfully_subtitle(String expected_text) {
		return login_successful_subtext.getText().contains(expected_text);
	}
}
