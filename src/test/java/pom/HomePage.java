package pom;

import java.io.IOException;

import org.openqa.selenium.By;

import com.ssts.pcloudy.exception.ConnectError;

import genericClasses.Actions;
import genericClasses.VisualClasses;
import io.appium.java_client.android.AndroidDriver;

public class HomePage {

	AndroidDriver driver;
	Actions act;
	VisualClasses visual;

	public HomePage(AndroidDriver driver, Actions act) {
		this.driver = driver;
		this.act = act;
		visual = new VisualClasses(driver, act);
	}

	public By login_btn = By.id("com.pcloudyhackathon:id/login");

	public void clickLogin_btn() throws IOException, ConnectError {
		String LoginImgID = visual.uploadImage(act.TakeScreenshot());
		visual.getAndClickCoordinates(driver, LoginImgID, "LOGIN");
	}

}
