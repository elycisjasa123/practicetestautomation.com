package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	// Elements
	@FindBy(name="username")
	WebElement username_text_field;
	
	@FindBy(name="password")
	WebElement password_field;
	
	@FindBy(id="submit")
	WebElement login_btn;
	
	@FindBy(id="error")
	WebElement error_message;
	
	
	// Action Methods
	public void enter_username(String username) {
		username_text_field.sendKeys(username);
	}
	
	
	public void enter_password(String password) {
		password_field.sendKeys(password);
	}
	
	public void click_login(){
		login_btn.click();
	}
	
	public void assert_error_message(String expected_text) {
		Assert.assertEquals(error_message.getText(), expected_text);
	}
}
