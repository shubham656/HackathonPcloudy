package pom;

import org.openqa.selenium.By;

import genericClasses.Actions;
import io.appium.java_client.android.AndroidDriver;

public class TapActivity {


	AndroidDriver driver;
	Actions act;
		
		public TapActivity(AndroidDriver driver,Actions act) {
			this.driver = driver;
			this.act = act;
		}
		
		
		public By clickHere_btn = By.id("com.pcloudyhackathon:id/click_here");
		public By doubleTapAlert = By.xpath("//android.widget.TextView[@text='Click Detected']");
		
		public void click_ClickHerebtn() {
			act.TapOnElement_Android(clickHere_btn);
		}
		
		public void doubleTapClickHere_btn() {
			act.doubleTap_Android(clickHere_btn);
		}

		public boolean visibilityOfTapAlert() {
			
			if(driver.findElement(doubleTapAlert).isDisplayed())
				return true;
			
			
			return false;
		}
}
