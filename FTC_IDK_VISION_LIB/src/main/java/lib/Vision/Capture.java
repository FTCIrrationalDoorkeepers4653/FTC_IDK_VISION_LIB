package lib.Vision;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import lib.UILA.HandleData;
//import com.github.sarxos.webcam.*;
//import com.yamunasoftware.java.Tools.*;

public class Capture {
  //Webcam Device:	
  //private static Webcam webcam;
  
  //Objects:
  private static HandleData handleData = new HandleData();
  
  //Constructor:
  public Capture() {
  
  }
  
  /* FILE ALTERATION METHODS */
	
//  //Capture Method:
//  public static void captureImage(String imageIdentifier) throws Exception {
//	  //Opens the Cam:
//	  webcam = Webcam.getDefault();
//	  webcam.open();
//
//	  //Captures Image:
//	  ImageIO.write(webcam.getImage(), "JPG", new File( imageIdentifier + ".jpg"));
//	  webcam.close();
//  }
  
  //Resize Image Method:
  public static String resizeImage(String imageIdentifier, int width, int height, String resizeIdentifier) throws Exception {
  	//Variables:
  	String resizedIndicator = imageIdentifier + resizeIdentifier;
  	
  	//Original Image file:
    File originalFile = new File(imageIdentifier + ".jpg");
 	  BufferedImage originalImage = ImageIO.read(originalFile);
  	
 	  //Resized Image:
 	  BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
    Graphics2D graphics = resizedImage.createGraphics();
  	
 	  //Resizing Image:
 	  graphics.drawImage(originalImage, 0, 0, width, height, null);
 	  graphics.dispose();
  	
    //Saving the File:
 	  ImageIO.write(resizedImage, "JPG", new File(resizedIndicator + ".jpg")); 	
  	
  	//Returns the Value:
  	return resizedIndicator;
  }
  
  //Delete Image Method:
  public static void deleteImage(String imageIdentifier) throws Exception {
  	//Main Variable:
  	String fileType = ".jpg";

  	//Removes the File:
  	HandleData.delete(imageIdentifier + fileType);
  }
  
  /* FILE INFORMATION METHODS */
  
  //Gets Image Width: 
  public static int getWidth(String imageIdentifier) throws Exception {
	  //Opens Image:
	  File f = new File(imageIdentifier + ".jpg"); 
    BufferedImage img = ImageIO.read(f); 
      
    //Gets the Value:
    int value = img.getWidth();
      
    //Returns:
    return value;
  }
  
  //Gets Image Height:
  public static int getHeight(String imageIdentifier) throws Exception {
	  //Opens Image:
	  File f = new File(imageIdentifier + ".jpg"); 
      BufferedImage img = ImageIO.read(f); 
      
      //Gets the Value:
      int value = img.getHeight();
      
      //Returns:
      return value;
  }
  
  //Gets Pixel RGB Value:
  public static int getRGB(String imageIdentifier, int x, int y) throws Exception {
	  //Opens Image:
	  File f = new File(imageIdentifier + ".jpg"); 
    BufferedImage img = ImageIO.read(f); 
      
    //Gets the Value:
    int value = img.getRGB(x, y);
      
    //Returns:
    return value;  
  }
  
  //Can Set the RGB Values:
  public static void setRGB(String imageIdentifier, int x, int y, int[] rgbValues) throws Exception {
    //Opens Image:
	  File f = new File(imageIdentifier + ".jpg"); 
    BufferedImage img = ImageIO.read(f); 
    
    //Converts to Singular RGB:
    int rgbValue = convertSingularRGB(rgbValues);
      
    //Sets the Value:
    img.setRGB(x, y, rgbValue);
  }
  
  /* BUFFERED IMAGE METHODS */
  
  //Resize Local Buffered Image Method:
  public static BufferedImage resizeLocalImage(BufferedImage userImage, int width, int height) throws Exception {
  	//Checks Case:
  	if (userImage != null) {
  		Image tmp = userImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

      Graphics2D g2d = dimg.createGraphics();
      g2d.drawImage(tmp, 0, 0, null);
      g2d.dispose();

      return dimg;
  	}
  	
  	else {
  		//Error Message:
  		System.out.println("Null Buffered Image Detected!");
  		return null;
  	}
  }
  
  //Buffered Image Width Method:
  public static int getBufferedWidth(BufferedImage userImage) throws Exception {
    //Main Width:
  	int width = 0; //Default Value
  
  	//Checks Case:
  	if (userImage != null) {
  		width = userImage.getWidth();
  	}
  	
  	else {
  	  //Error Message:
  		System.out.println("Null Buffered Image Detected!");	
  	}
  	
  	//Returns the Value
  	return width;
  }
  
  //Buffered Image Height Method:
  public static int getBufferedHeight(BufferedImage userImage) throws Exception {
    //Main Height:
  	int height = 0; //Default Value
  
  	//Checks Case:
  	if (userImage != null) {
  		height = userImage.getHeight();
  	}
  	
  	else {
  	  //Error Message:
  		System.out.println("Null Buffered Image Detected!");	
  	}
  	
  	//Returns the Value:
  	return height;
  }
  
  //Buffered Image Getting RGB Value:
  public static int getBufferedRGB(BufferedImage userImage, int x, int y) throws Exception {
  	//Main RGB Value:
  	int rgb = 0; //Default Value
  	
  	if (userImage != null) {
  		rgb = userImage.getRGB(x, y);
  	}
  	
  	else {
  	  //Error Message:
  		System.out.println("Null Buffered Image Detected!");	
  	}
  	
  	//Returns the Value:
  	return rgb;
  }
  
  //Buffered Image Setting RGB Value:
  public static void setBufferedRGB(BufferedImage userImage, int x, int y, int[] rgbValues) throws Exception {
  	//Checks Case:
  	if (userImage != null) {
  		//Converts RGB Values:
  		int singleRGB = convertSingularRGB(rgbValues);
  		
  		//Sets the RGB Value for the Pixel:
  		userImage.setRGB(x, y, singleRGB);
  	}
  	
  	else {
  	  //Error Message:
  		System.out.println("Null Buffered Image Detected!");	
  	}
  }
  
  /* CONVERSION METHODS */
  
  //Converts Standard RGB into Usable RGB:
  public static int[] convertRGB(int rgbValue) throws Exception {
	  //RGB Array:
	  int rgbValues[] = new int[3];  
	  int blue = (rgbValue) & 0x0ff;
	  int green = (rgbValue >> 8) & 0x0ff;
	  int red = (rgbValue >> 16) & 0x0ff;
	
	  //Inputs into Array:
	  rgbValues[0] = red;
	  rgbValues[1] = green;
	  rgbValues[2] = blue;
	
	  //Returns Array:
	  return rgbValues;
  }
  
  //Converts from User RGB to Computer RGB:
  public static int convertSingularRGB(int[] rgbValues) throws Exception {
  	//Color Variables:
  	int standardColorValue = (((rgbValues[0] & 0x0ff) << 16) | ((rgbValues[1] & 0x0ff) << 8) | ((rgbValues[2] & 0x0ff)));
  	
  	//Returns Value:
  	return standardColorValue;
  }
}
