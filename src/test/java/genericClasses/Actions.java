package genericClasses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Actions {

	AndroidDriver driver;

	public Actions(AndroidDriver driver) {
		this.driver = driver;
	}
	/*****************************************************
	 * Function Description : To click on desired coordinates
	 *****************************************************/
	

	public void clickOnCoordinates(int x, int y) {

		TouchAction action = new TouchAction(driver);

		action.press(PointOption.point(x, y)).release().perform();

	}
	
	/*************************************************************************
	 * Function Description : To tap on desired element by providing the locator
	 *************************************************************************/
	

	public void TapOnElement_Android(By locator) {

		this.WaitUntilVisibilityOfElement(locator);
		try {
			driver.findElement(locator).click();
		} catch (Exception e) {

			driver.findElement(locator).click();
		}
	}
	
	/*********************************************************************************
	 * Function Description : To double tap on desired element by providing the locator
	 *********************************************************************************/

	public void doubleTap_Android(By locator) {
		MobileElement imageElement = (MobileElement) driver.findElement(locator);

		((JavascriptExecutor) driver).executeScript("mobile: doubleClickGesture",
				ImmutableMap.of("elementId", ((MobileElement) imageElement).getId()));

	}
	
	
	/*********************************************************************************
	 * Function Description : To drag and drop desired element 
	 *********************************************************************************/

	public void dragAndDrop(By element) {
		MobileElement ele = (MobileElement) driver.findElement(element);

		Point p = ele.getLocation();

		Dimension size = ele.getSize();
		int width = size.getWidth();
		int height = size.getHeight();

		int midx = p.getX() + width / 2;
		int midy = p.getY() + (height / 2);

		Dimension screenSize = driver.manage().window().getSize();
		int swidth = screenSize.getWidth();
		int sheight = screenSize.getHeight();

		TouchAction action = new TouchAction(driver);
		action.longPress(PointOption.point(midx, midy)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
				.moveTo(PointOption.point(swidth / 2, (sheight * 3) / 4)).release().perform();

	}
	
	/*********************************************************************************
	 * Function Description : To take screen shot
	 *********************************************************************************/


	public String TakeScreenshot() throws IOException {
		String directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "\\\\");

		String SaveName = Calendar.getInstance().getTime().toString().replace(":", "").replace(" ", "").trim();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {

			FileUtils.copyFile(scrFile,
					new File(directory + File.separator + "screenshots" + File.separator + SaveName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String directoryT = System.getProperty("user.dir");

		String filename = directoryT + File.separator + "screenshots" + File.separator + SaveName + ".png";
		System.out.println(filename);

		this.conversion(filename, filename);

		return filename;
	}
	
	/*********************************************************************************
	 * Function Description : To convert image from any format PNG
	 *********************************************************************************/

	public void conversion(String inputFile, String outputFile) throws IOException {
		// read a jpeg from a inputFile
		BufferedImage bufferedImage = ImageIO.read(new File(inputFile));

		// write the bufferedImage back to outputFile
		ImageIO.write(bufferedImage, "png", new File(outputFile));
	}

	public void WaitUntilVisibilityOfElement(By element) {
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Exception e) {
		}
	}

	Duration STEP_DURATION = Duration.ofMillis(20);
	Duration NO_TIME = Duration.ofMillis(0);
	Origin VIEW = Origin.viewport();

	
	
	/*********************************************************************************
	 * Function Description : To draw Face Emoji
	 *********************************************************************************/
	public void drawFace() {

		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();

		Point head = new Point(width / 2, height / 2);
		Point leftEye = head.moveBy(-width / 5, -width / 5);
		Point rightEye = head.moveBy(width / 5, -width / 5);
		Point mouth = head.moveBy(0, width / 8 - 5);

		drawCircle(driver, head, (width / 2) - 30, 60);
		drawCircle(driver, leftEye, width / 8, 60);
		drawCircle(driver, rightEye, width / 8, 60);
		drawArc(driver, mouth, width / 4 - 5, 60);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException ign) {
		}
	}

	private Point getPointOnCircle(int step, int totalSteps, Point origin, double radius) {
		double theta = 2 * Math.PI * ((double) step / totalSteps);
		int x = (int) Math.floor(Math.cos(theta) * radius);
		int y = (int) Math.floor(Math.sin(theta) * radius);
		return new Point(origin.x + x, origin.y + y);
	}

	private Point getPointOnArc(int step, int totalSteps, Point origin, double radius) {
		double theta = Math.PI * ((double) step / totalSteps);
		int x = (int) Math.floor(Math.cos(theta) * radius);
		int y = (int) Math.floor(Math.sin(theta) * radius);
		return new Point(origin.x + x, origin.y + y);
	}

	private void drawCircle(AndroidDriver driver, Point origin, double radius, int steps) {
		Point firstPoint = getPointOnCircle(0, steps, origin, radius);

		PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
		Sequence circle = new Sequence(finger, 0);
		circle.addAction(finger.createPointerMove(NO_TIME, VIEW, firstPoint.x, firstPoint.y));
		circle.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));

		for (int i = 1; i < steps + 1; i++) {
			Point point = getPointOnCircle(i, steps, origin, radius);
			circle.addAction(finger.createPointerMove(STEP_DURATION, VIEW, point.x, point.y));
		}

		circle.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
		driver.perform(Arrays.asList(circle));
	}

	private void drawArc(AndroidDriver driver, Point origin, double radius, int steps) {
		Point firstPoint = getPointOnCircle(0, steps, origin, radius);

		PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
		Sequence circle = new Sequence(finger, 0);
		circle.addAction(finger.createPointerMove(NO_TIME, VIEW, firstPoint.x, firstPoint.y));
		circle.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));

		for (int i = 1; i < steps + 1; i++) {
			Point point = getPointOnArc(i, steps, origin, radius);
			circle.addAction(finger.createPointerMove(STEP_DURATION, VIEW, point.x, point.y));
		}

		circle.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
		driver.perform(Arrays.asList(circle));
	}

}
