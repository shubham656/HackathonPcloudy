package pcloudyHackathon;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.ssts.pcloudy.exception.ConnectError;

import genericClasses.Actions;
import genericClasses.Config;
import genericClasses.StartDriver;
import genericClasses.VisualClasses;
import io.appium.java_client.android.AndroidDriver;
import pom.DragAndDrop;
import pom.HomePage;
import pom.ListMenu;
import pom.TapActivity;

public class HackathonTestScenarios extends Config {

	VisualClasses visual;
	AndroidDriver driver;
	DragAndDrop dragDrop;
	Actions act;
	HomePage home;
	ListMenu listMenu;
	TapActivity tapPage;
	static ExtentTest test;
	static ExtentReports report;

	@BeforeClass
	public static void startTest() {

		String directory = System.getProperty("user.dir").replace("\\", "\\\\");

		report = new ExtentReports(directory + File.separator + "ExtentReportResults.html", true);

		test = report.startTest("Pcloudy Hackathon Test Report");
	}

	@BeforeMethod()
	public void StartDriver() throws MalformedURLException {

		StartDriver startDriver = new StartDriver();

		driver = startDriver.StartDriverAndroidApp();

		act = new Actions(driver);
		visual = new VisualClasses(driver, act);
		dragDrop = new DragAndDrop(act);

		home = new HomePage(driver, act);
		listMenu = new ListMenu(act);
		tapPage = new TapActivity(driver, act);

	}

	@AfterMethod()
	public void stopDriver(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.FAIL, test.addScreenCapture(act.TakeScreenshot()), "Test Failed");
		} else
			test.log(LogStatus.PASS, test.addScreenCapture(act.TakeScreenshot()), "Test Passed");

		driver.quit();
	}

	@AfterClass
	public static void endTest() {
		report.endTest(test);
		report.flush();
	}

	/**************************************************************************
	 * Function Description : To perform click on login button finding its
	 * coordinate via text on Image and Double tap on element
	 *********************************************************************/

	@Test
	public void TC_Scenario001_ValidateDoubleTap() throws IOException, ConnectError, InterruptedException {

		home.clickLogin_btn();
		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Login Button");
		listMenu.click_doubleTapMenu();
		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Double tap menu");
		tapPage.doubleTapClickHere_btn();
		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Double tapped on button");
		Assert.assertTrue(tapPage.visibilityOfTapAlert());

	}

	/*****************************************************
	 * Function Description : To Draw Emoji on draw pad
	 *****************************************************/

	@Test
	public void TC_Scenario002_ToDrawGivenEmoji() throws IOException, ConnectError, InterruptedException {

		home.clickLogin_btn();
		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Login Button");
		listMenu.click_drawMenu();
		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Draw Menu");
		act.drawFace();

	}

	/******************************************************************
	 * Function Description : To drag and drop element across the page
	 *****************************************************************/
	@Test
	public void TC_Scenario003_ToVerifyDragAndDropUseCase() throws IOException, ConnectError, InterruptedException {

		home.clickLogin_btn();
		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Login Button");
		listMenu.click_dragAndDropMenu();
		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Drag Drop Menu");
		dragDrop.DoDragAndDropOfButton();

	}

	/******************************************************************************
	 * Function Description : To find difference of two images and assert the text
	 * on image
	 ******************************************************************************/
	@Test
	public void TC_Scenario04_ValidateImageDiffAndTextCompare() throws IOException, ConnectError, InterruptedException {

		home.clickLogin_btn();

		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Login Button");

		listMenu.click_baseImage();

		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Base Image Menu");

		String baseImgID = visual.uploadImage(act.TakeScreenshot());

		driver.navigate().back();

		listMenu.click_secondaryImage();

		test.log(LogStatus.INFO, test.addScreenCapture(act.TakeScreenshot()), "Clicked on Secondary Image Menu");

		String secondImgID = visual.uploadImage(act.TakeScreenshot());

		String diffImg = visual.CompareImage(baseImgID, secondImgID);

		test.log(LogStatus.INFO, test.addScreenCapture(diffImg), "Diff Image");

		Assert.assertTrue(visual.getTextFromImage(baseImgID).contains("Titans"));

	}
}
