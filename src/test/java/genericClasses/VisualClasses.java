package genericClasses;

import java.awt.image.BufferedImage;

import java.io.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.openqa.selenium.Point;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.exception.ConnectError;

import io.appium.java_client.android.AndroidDriver;
import pojo.Coordinates;
import pojo.Datum;

public class VisualClasses extends Config {

	AndroidDriver driver;

	Actions act;

	public VisualClasses(AndroidDriver driver, Actions act) {
		this.driver = driver;
		this.act = act;

	}

	public String uploadImage(String filePath) throws IOException, ConnectError {
		Connector con = new Connector("https://device.pcloudy.com");
		String authToken = con.authenticateUser(pCloudy_Username, pCloudy_ApiKey);
		// File fileToBeUploaded = new
		// File("/Users/surbhit/Desktop/SatJul30124839IST2022.png");
		File fileToBeUploaded = new File(filePath);
		String baseImageId = con.getImageId(authToken, fileToBeUploaded);
		System.out.println(baseImageId);
		return baseImageId;

	}

	public String getTextFromImage(String baseImageId) {
		Map<String, Object> params = new HashMap<>();
		params.put("baseImageId", baseImageId);
		System.out.println(driver.executeScript("mobile:ocr:text", params));

		return driver.executeScript("mobile:ocr:text", params).toString();

	}

	public String CompareImage(String baseImageId, String secondImageId) throws IOException {
		String directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "\\\\");

		Map<String, Object> params = new HashMap<>();
		params.put("baseImageId", baseImageId);
		params.put("secondImageId", secondImageId);
		File imgFile = new File(directory + File.separator + "diff.png");

		String base64 = (String) driver.executeScript("mobile:visual:imageDiff", params);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(base64)));
		ImageIO.write(img, "png", imgFile);
		
		return imgFile.toString();

	}

	public void getAndClickCoordinates(AndroidDriver driver, String baseImageId, String text) {
		Map<String, Object> params = new HashMap<>();
		params.put("imageId", baseImageId);
		params.put("word", text);
		System.out.println(driver.executeScript("mobile:ocr:coordinate", params));
		String response = driver.executeScript("mobile:ocr:coordinate", params).toString();

		Gson gson = new GsonBuilder().serializeNulls().create();
		Coordinates coor = gson.fromJson(response, Coordinates.class);
		List<Datum> data = coor.getData();

		int x = data.get(0).getLeft();
		int y = data.get(0).getTop();

		System.out.println(x);
		System.out.println(y);

		act.clickOnCoordinates(x, y);

	}

}
