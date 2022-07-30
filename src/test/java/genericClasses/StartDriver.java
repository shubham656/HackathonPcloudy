package genericClasses;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import okhttp3.*;

public class StartDriver extends Config{

	String pcloudyDeviceNameList = "Samsung_GalaxyA9_Android_10.0.0_6eed1,Google_Pixel3_Android_12.0.0_a6091,Samsung_GalaxyS10_Android_9.0.0_01e83,Samsung_GalaxyM02_Android_11.0.0_51323,SAMSUNG_GalaxyM10_Android_10.0.0_a58e4,Samsung_GalaxyA31_Android_11.0.0_32c0a,Samsung_GalaxyA12_Android_11.0.0_334bc,ONEPLUS_7Pro_Android_10.0.0_b2d65,Google_Pixel5_Android_11.0.0_21d59,Samsung_GalaxyS8Plus_Android_9.0.0_2e32c,Samsung_GalaxyNote9_Android_10.0.0_6b251,Samsung_GalaxyM02_Android_10.0.0_51323,SAMSUNG_GalaxyA10s_Android_10.0.0_09401,SAMSUNG_GalaxyA51_Android_10.0.0_d52ba,Samsung_GalaxyJ6_Android_10.0.0_482da";

	public AndroidDriver driver;
	
	
	/*****************************************************
	 * Function Description : To initialize Android Driver
	 *****************************************************/
	
	

	public AndroidDriver StartDriverAndroidApp()
			throws MalformedURLException {
		
		String pcloudyDeviceName= getFirstFreePCloudyDevice();

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("pCloudy_Username", pCloudy_Username );
		capabilities.setCapability("pCloudy_ApiKey", pCloudy_ApiKey);
		capabilities.setCapability("pCloudy_DurationInMinutes", 200);
		capabilities.setCapability("newCommandTimeout", 600);
		capabilities.setCapability("launchTimeout", 900000);
		capabilities.setCapability("pCloudy_DeviceFullName", pcloudyDeviceName);
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("automationName", "uiautomator2");
		capabilities.setCapability("pCloudy_ApplicationName", "PCloudyHackathon.apk");
		capabilities.setCapability("appPackage", "com.pcloudyhackathon");
		capabilities.setCapability("appActivity", ".ui.login.LoginActivity");
		capabilities.setCapability("pCloudy_WildNet", "false");
		capabilities.setCapability("pCloudy_EnableVideo", "true");
		capabilities.setCapability("pCloudy_EnablePerformanceData", "false");
		capabilities.setCapability("pCloudy_EnableDeviceLogs", "false");

		try {
		driver = new AndroidDriver<WebElement>(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
		}
		 catch (Exception s) {
				s.printStackTrace();
				GoToSleep((int)(Math.random() * 30) + 1);
				pcloudyDeviceName=getFirstFreePCloudyDevice();	
				capabilities.setCapability("pCloudy_DeviceFullName", pcloudyDeviceName);
				
				System.out.println("Unable to launch App...Retrying on new device..."+pcloudyDeviceName);
				driver = new AndroidDriver<WebElement>(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
			}

		return driver;
	}
	

	
	/*****************************************************************************************
	 * Function Description : To get first free Pcloudy device out of provided list of devices
	 *****************************************************************************************/

	public String getFirstFreePCloudyDevice() {
		List<String> list = new ArrayList<String>(Arrays.asList(pcloudyDeviceNameList.split(",")));

		String freedeviceList = getPcloudyFreeDeviceList();

		System.out.println(freedeviceList);
		for (String device : list) {
			System.out.println(device);
			System.out.println(freedeviceList.contains(device.trim()));
			if (freedeviceList.contains(device.trim())) {
				return device.trim();
			}
		}
		return list.get(0);

	}
	
	/**************************************************************
	 * Function Description : To get all available Pcloudy devices 
	 **************************************************************/

	public String getPcloudyFreeDeviceList() {
		Response response = null;

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\n    \"token\": \"cjfkpcxrgn7wqgyxv3k8586h\",\n    \"duration\": 10,\n    \"platform\": \"android\",\n    \"available_now\": \"true\"\n}");
		Request request = new Request.Builder().url("https://device.pcloudy.com/api/devices").method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		try {
			response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void GoToSleep(int TimeInMillis) {
		try {
			Thread.sleep(TimeInMillis);
		} catch (Exception e) {
		}
	}


}
