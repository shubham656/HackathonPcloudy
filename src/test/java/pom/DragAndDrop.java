package pom;

import org.openqa.selenium.By;

import genericClasses.Actions;
import io.appium.java_client.MobileElement;

public class DragAndDrop {
	
	Actions act;

	public DragAndDrop(Actions act) {

		this.act = act;
	}


	public By dragDrop_btn = By.id("com.pcloudyhackathon:id/button");
	
	
	public void DoDragAndDropOfButton() {
		
		act.dragAndDrop(dragDrop_btn);
	}

}
