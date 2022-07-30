package pom;

import org.openqa.selenium.By;

import genericClasses.Actions;
import io.appium.java_client.android.AndroidDriver;

public class ListMenu {

	Actions act;

	public ListMenu(Actions act) {

		this.act = act;
	}

	public By doubleTap = By.xpath("//android.widget.TextView[@text='Double Tap']");
	public By baseImage = By.xpath("//android.widget.TextView[@text='Base Image']");
	public By secondaryImage = By.xpath("//android.widget.TextView[@text='Seconday Image']");
	public By drawMenu = By.xpath("//android.widget.TextView[@text='Draw']");
	public By dragAndDropMenu = By.xpath("//android.widget.TextView[@text='Drag & Drop']");

	public void click_doubleTapMenu() {
		act.TapOnElement_Android(doubleTap);
	}

	public void click_baseImage() {
		act.TapOnElement_Android(baseImage);
	}

	public void click_secondaryImage() {

		act.TapOnElement_Android(secondaryImage);
	}

	public void click_drawMenu() {
		act.TapOnElement_Android(drawMenu);
	}

	public void click_dragAndDropMenu() {
		act.TapOnElement_Android(dragAndDropMenu);
	}

}
