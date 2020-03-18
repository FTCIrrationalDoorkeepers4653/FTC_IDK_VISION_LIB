package lib;

//import com.yss.java.Tools.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

//import com.yamunasoftware.java.Tools.*;

/* ALL PARTS OF CLASS ARE CUSTOMIZABLE!!! */
public class Executable extends Analyze {
	//Objects:
	private static Trainer trainer = new Trainer();
	private static Capture capture = new Capture();
	private static HandleData handleData = new HandleData();
	
	//Detector Arrays (Given-Customize):
	private static int cardBoardRGBValues[] = { 169, 120, 53 };
	private static int indicatorRGBValues[] = { 255, 0, 0 };
	private static int blackRGBValues[] = { 0, 0, 0 };
	
	//ML Data Sets (Given Data Sets-Customize):
	
	//CardBoard Box Data Sets:
	private static Double cbBoxWidths[] = {};
	private static Double cbBoxHeights[] = {};
	
	//Indicator Sticker Box Data Sets:
	private static Double indicatorWidths[] = {};
	private static Double indicatorHeights[] = {};
	
	//ML Labels (Given Customize):
	private static String indicatorLabels[] = {"Box", "Indicator", "Black"};
	
	//Constructor:
	public Executable() {
		
	}
	
  //Main Run Method:
  public static void main(String args[]) throws Exception {
  	//Setup (INITIALIZATION):
  	setDetectorRGB(blackRGBValues[0], blackRGBValues[1], blackRGBValues[2]);
  	setDetectorName(indicatorLabels[2]);
  	
  	/* 
  	 * Detector RGB and Name Settings Should Match!
  	*/
  	
  	//Identifier (USE IF NEEDED):
  	String id = "skystoneTracking";
  	String resizeID = "R";
  	
  	//Image Processing (User Use Case):

  }
  
  /* EXECUTABLE ML METHODS */
  
  //ML Training Method:
  public static void train(String type) throws Exception {
  	if (type.equalsIgnoreCase("Box")) {
  		//Removes Outliers First:
  		Trainer.removeOutliers(cbBoxWidths, cbBoxHeights);

  		//Trains Data:
  		Trainer.findBoxValues(cbBoxWidths, cbBoxHeights, indicatorLabels[0]);
  	}

  	else if (type.equalsIgnoreCase("Indicator")) {
  	  //Removes Outliers First:
  		Trainer.removeOutliers(indicatorWidths, indicatorHeights);

  		//Trains Data:
  		Trainer.findBoxValues(indicatorWidths, indicatorHeights, indicatorLabels[1]);
  	}

  	else {
  	  //Prints Error Statement:
      System.out.println("No Data Set Available for Given Value!");
  	}
  }
  
  /* USE CASE METHODS */
  
  //User Case Boolean Identification:
  public static boolean booleanIdentification(String id, int startX, int startY, int width, int height, Double systemResize, int marginOfError, int percentagePixelCount, String resizeIdentifier) throws Exception {
    //Image Resizing:
	  String newID = resizeImageRatio(id, systemResize, resizeIdentifier);

	  //Image Processing:
    int allRGBValues[][] = findSelectedRGB(newID, startX, startY, width, height);
    int binaryVals[][] = boxDetector(allRGBValues, marginOfError);
  
    //Identifying Pixel Count Amount:
    int percentage = (percentagePixelCount/100);
    int pixelCountValue = ((binaryVals.length * binaryVals[0].length)*(percentage));
  
    //Image Detection:
    boolean found = getBodyBoolean(binaryVals, pixelCountValue);
    
    //System Debugs:
    printDetectorSettings(); 
    System.out.println(found);
    
    //Returns the Value:
    return found;
  }
  
  /* NO RESIZING DUE TO BUFFERED IMAGE!!! RESIZING DONE OUTSIDE OF METHOD!!! */
  
  //User Case Buffered Image Boolean Identification:
  public static boolean bufferedBooleanIdentification(BufferedImage userImage, int startX, int startY, int width, int height, int marginOfError, int percentagePixelCount) throws Exception {
  	//Image Processing:
    int allRGBValues[][] = findSelectedBufferedRGB(userImage, startX, startY, width, height);
    int binaryVals[][] = boxDetector(allRGBValues, marginOfError);
    
    //Identifying Pixel Count Amount:
    int percentage = (percentagePixelCount/100);
    int pixelCountValue = ((binaryVals.length * binaryVals[0].length)*(percentage));
  
    //Image Detection:
    boolean found = getBodyBoolean(binaryVals, pixelCountValue);
    
    //System Debugs:
    printDetectorSettings(); 
    System.out.println(found);
    
    //Returns the Value:
    return found;
  }
}
