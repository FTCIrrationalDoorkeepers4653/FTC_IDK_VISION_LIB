package lib.Vision;

//import com.yamunasoftware.java.Tools.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import lib.UILA.Neuron;

//import com.yamunasoftware.java.Main.*;

public class Analyze extends Capture {
	//Objects:
	private static Neuron neuron = new Neuron();
	
  //Locked Midpoint Values:
	public static int lockMidpointX, lockMidpointY;
	public static int[] midpointRGBValues = new int[3];
	
	//Detector Settings (Public Settings):
	private static int detectorRed, detectorGreen, detectorBlue;
	private static String detectorName;
	
	/* CONSTRUCTOR AND SET METHODS!!! */
	public Analyze() {
		super();
	}

	/* 
	 * PRESET DETECTOR VALUES:
	 *
	 * Black: 0, 0, 0
	 * 
	*/

	//Initializes Detector:
	public static void initDetector(String localDetectorName, int red, int green, int blue) throws Exception {
		//Sets the Detector Name:
		detectorName = localDetectorName; //Used for Debugging

		//Checks Case:
		if (red <= 255 && red >= 0 && green <= 255 && green >= 0 && blue <= 255 && blue >= 0) {
			//Setting the Detector Values:
			detectorRed = red;
			detectorGreen = green;
			detectorBlue = blue;
		}

		else {
			//Setting the Detector Values (Default):
			detectorRed = 0;
			detectorGreen = 0;
			detectorBlue = 0;
		}
	}
	
	//Prints Detector Settings:
	public static void printDetectorSettings() {
		//RGB Array:
		int localRGBSettingsArray[] = {detectorRed, detectorGreen, detectorBlue}; 
		
		//Simply Prints the Settings of the Detector:
		System.out.println(detectorName + " with RGB Settings of: " + Arrays.toString(localRGBSettingsArray));
	}
	
	/* BUFFERED IMAGE PROCESSING METHODS */
	
	//Resizes the Buffered Image Using the Aspect Ratio:
	public static BufferedImage resizeBufferedImageRatio(BufferedImage userImage, Double resizeRatio) throws Exception {
		if (resizeRatio != 0.0) {
			//Gets the Image Width and Height using the Methods:
			double width = getBufferedWidth(userImage);
			double height = getBufferedHeight(userImage);

			//Calculates the new Width and Height:
			int newWidth = Math.abs((int) (width*resizeRatio));
			int newHeight = Math.abs((int) (height*resizeRatio));

			//Resizes the Buffered Image:
			BufferedImage newImage = resizeLocalImage(userImage, newWidth, newHeight);

			//System Debugs:
			//System.out.println(userImage.getWidth() + ", " + userImage.getHeight());

			//Returns the Image:
			return newImage;
		}

		else {
			//Error Statement:
			System.out.println("Invalid Resize Ratio!");
			return null;
		}
	}
	
	//Finds all RGB Values for Selected Area of User Buffered Image:
	public static int[][] findSelectedBufferedRGB(BufferedImage userImage, int startX, int startY, int width, int height) throws Exception {
		//Main Arrays:
		int zeroArray[][] = {{0}}; //Incorrect Enterance Array
		
		//Checks Case:
		if (userImage != null) {
		  //Array Values:
			int imageWidth = Capture.getBufferedWidth(userImage);
			int imageHeight = Capture.getBufferedHeight(userImage);
			int allValueList[][] = new int[width][height];
			
		  //Loop Values:
			int turnsWidth = startX;
			
			//Checks the Case:
	    if (startX >= 0 && startX+width <= imageWidth && startY >= 0 && startY+height <= imageHeight) {
			  //Loops through and Finds All Values:
			  mainLoop: while (turnsWidth < startX+width) {
			    //Loop Variable:
				  int turnsHeight = startY;
				  secondLoop: while (turnsHeight < startY+height) {
					  allValueList[turnsWidth-startX][turnsHeight-startY] = Capture.getBufferedRGB(userImage, turnsWidth, turnsHeight);
					
					  turnsHeight++;
				  }

				  turnsWidth++;
			  }
	    
			  //Returns Array:
			  return allValueList;
	    }
	    
	    else {
	    	System.out.println("Selected Area is not Possible!");
	    	
	    	//Returns False Array:
	    	return zeroArray;
	    }
		}
		
		else {
			//Error Message:
			System.out.println("Null Buffered Image Detected!");
			return zeroArray;
		}
	}

	/* IMAGE PROCESSING METHODS */

	//Resizes the Image Using the Aspect Ratio:
	public static String resizeImageRatio(String imageIdentifier, Double resizeRatio, String resizeIdentifier) throws Exception {
		if (resizeRatio != 0.0) {
			//Image Variables:
			double imageWidth = getWidth(imageIdentifier);
			double imageHeight = getHeight(imageIdentifier);

			//Makes the Conversions:
			double resizedWidth = Math.abs((imageWidth*resizeRatio));
			double resizedHeight = Math.abs((imageHeight*resizeRatio));

			//Resizes the Image:
			String id = resizeImage(imageIdentifier, (int)resizedWidth, (int)resizedHeight, resizeIdentifier);

			//Returns the Value:
			return id;
		}

		else {
			//Error Statement:
			System.out.println("Incorrect Resize Ratio!");
			return null;
		}
	}
	
	/* COLOR ANALYSIS METHODS!!! */

	//Finds all RGB Values:
	public static int[][] findAllRGB(String imageIdentifier) throws Exception {
		//Array Values:
		int imageWidth = Capture.getWidth(imageIdentifier);
		int imageHeight = Capture.getHeight(imageIdentifier);
		int allValueList[][] = new int[imageWidth][imageHeight];

		//Loop Values:
		int turnsWidth = 0;

		//Loops through and Finds All Values:
		mainLoop: while (turnsWidth < allValueList.length) {
			//Loop Variable:
			int turnsHeight = 0;
			secondLoop: while (turnsHeight < allValueList[turnsWidth].length) {
				allValueList[turnsWidth][turnsHeight] = Capture.getRGB(imageIdentifier, turnsWidth, turnsHeight);
				turnsHeight++;
			}

			turnsWidth++;
		}

		//Returns Array:
		return allValueList;
	}
	
	//Finds Only Selected Area of RGB:
	public static int[][] findSelectedRGB(String imageIdentifier, int startX, int startY, int width, int height) throws Exception {
		//Check Variables:
		int imageWidth = Capture.getWidth(imageIdentifier);
		int imageHeight = Capture.getHeight(imageIdentifier);
		
		//Main Arrays:
		int allValueList[][] = new int[width][height];
		int zeroArray[][] = {{0}}; //Incorrect Enterance Array
		
	  //Loop Values:
		int turnsWidth = startX;
		
		//Checks the Case:
    if (startX >= 0 && startX+width <= imageWidth && startY >= 0 && startY+height <= imageHeight) {
		  //Loops through and Finds All Values:
		  mainLoop: while (turnsWidth < startX+width) {
		    //Loop Variable:
			  int turnsHeight = startY;
			  secondLoop: while (turnsHeight < startY+height) {
				  allValueList[turnsWidth-startX][turnsHeight-startY] = Capture.getRGB(imageIdentifier, turnsWidth, turnsHeight);
				
				  turnsHeight++;
			  }

			  turnsWidth++;
		  }
    
		  //Returns Array:
		  return allValueList;
    }
    
    else {
    	System.out.println("Selected Area is not Possible!");
    	
    	//Returns False Array:
    	return zeroArray;
    }
	}

	/* ENTERANCE DEBUGS ADDED FOR NEXT TWO METHODS DUE TO USE CASES!!! */

	//Get Average RGB Values (FOR COMPARISON):
	public static int[] averageRGBValues(int[][] rgbValues) throws Exception {
		//Main Variable:
		int averageRGB[] = { 0, 0, 0 };

		//Checks the Case:
		if (rgbValues.length != 0 && rgbValues[0].length != 0) {
			//Loop Variables:
			int totalAmount = (rgbValues.length)*(rgbValues[0].length);
			int turnsWidth = 0;

			//Finds the Average RGB Values:
			mainLoop: while (turnsWidth < rgbValues.length) {
				//Loop Variable:
				int turnsHeight = 0;
				secondLoop: while (turnsHeight < rgbValues[0].length) {
					//Converts to Standard RGB Values:
					int localRGBValues[] = convertRGB(rgbValues[turnsWidth][turnsHeight]);

					//Adds to Current Average Values:
					averageRGB[0] += localRGBValues[0];
					averageRGB[1] += localRGBValues[1];
					averageRGB[2] += localRGBValues[2];

					turnsHeight++;
				}

				turnsWidth++;
			}

			//Finds the Average:
			averageRGB[0] /= totalAmount;
			averageRGB[1] /= totalAmount;
			averageRGB[2] /= totalAmount;
		}

		else {
			//Error Message:
			System.out.println("Incorrect RGB Values Length!");
		}

		//Returns the Value:
		return averageRGB;
	}

	//Cardboard Box Detector:
	public static int[][] boxDetector(int[][] rgbValues, int lightingMargin) throws Exception {
		//Box Values Array:
		int boxValuesArray[][] = new int[rgbValues.length][rgbValues[0].length];
		int setRGBValues[] = new int[3];

		//Sets the RGB Value:
		setRGBValues[0] = detectorRed;
		setRGBValues[1] = detectorGreen;
		setRGBValues[2] = detectorBlue;

		//System Debugs:
		//System.out.println(Arrays.toString(setRGBValues));

		//Margin of Error Value:
		int margin = lightingMargin; //Used for Different Lighting Conditions (Customizable)

		//Checks the Case:
		if (rgbValues.length != 0 && rgbValues[0].length != 0) {
			//Loop Variables:
			int turnsWidth = 0;

			//Loops Through the Array (and checks case):
			mainLoop: while (turnsWidth < boxValuesArray.length) {
				//Loop Variable:
				int turnsHeight = 0;
				secondLoop: while (turnsHeight < boxValuesArray[turnsWidth].length) {
					//Converts to Regular RGB:
					int converted[] = convertRGB(rgbValues[turnsWidth][turnsHeight]);

					//System Debugs:
					//System.out.println(Arrays.toString(converted));

					//System Debugs:
					//System.out.println(turnsWidth + ", " + turnsHeight);

					//Difference Values:
					int redDif = setRGBValues[0]-converted[0];
					int greenDif = setRGBValues[1]-converted[1];
					int blueDif = setRGBValues[2]-converted[2];

					//System Debugs:
					//System.out.println(redDif + ", " + greenDif + ", " + blueDif);

					//Multiply Values:
					int testRG = redDif*greenDif;
					int testRB = redDif*blueDif;
					int testGB = greenDif*blueDif;

					//System Debugs:
					//System.out.println(testRG + ", " + testRB + ", " + testGB);

					if (Math.abs(redDif) <= margin && Math.abs(greenDif) <= margin && Math.abs(blueDif) <= margin &&
							testRG >= 0 && testRB >= 0 && testGB >= 0) {
						boxValuesArray[turnsWidth][turnsHeight] = 1;
					}

					else {
						boxValuesArray[turnsWidth][turnsHeight] = 0;
					}

					turnsHeight++;
				}

				turnsWidth++;
			}
		}

		else {
			//Error Message:
			System.out.println("Incorrect RGB Values Length!");
		}

		//Returns the Accepted Values Array:
		return boxValuesArray;
	}

	/* VALUE METHODS */
	
	/* 
	 * Method of Detection:
	 * 
	 * Loop until first instance... 
	 * Find Neighbors in 2D, Loop until neighbors ends...
	 * Get Data and test using ML...
	 * CLassify...Repeat
	 * 
	*/
	
	//Gets Body Values (IF PRESENT):
	public static int[][] getBodyValues(int detectionArray[][]) throws Exception {
		//Body Value Array:
		int bodyValues[][] = new int[2][4];
		
		//Returns Array:
		return bodyValues;
	}
	
	//Get Selected Area Boolean (IF PRESENT):
	public static boolean getBodyBoolean(int detectionArray[][], int pixelCount) throws Exception {
		//Main Value:
		boolean valuePresent = false; //Default Value
		
	  //Loop Variables:
		int turnsWidth = 0;
		int trueCount = 0;
		
		//Count Value (Helps Mitigate Stray Pixels):
		int countValue = pixelCount; //Default Number of Pixels counted true before classifying (Customizable)
		
		  //Loops Through Area for Confirmation:
		  mainLoop: while (turnsWidth < detectionArray.length) {
		  	//Loop Variable:
		  	int turnsHeight = 0;
			  secondLoop: while (turnsHeight < detectionArray[turnsWidth].length) {
			  	//Checks the Case:
				  if (detectionArray[turnsWidth][turnsHeight] == 1) {
				    //System Debugs:
				  	//System.out.println("(" + turnsWidth + ", " + turnsHeight + ")");
				  	
				  	//Adds to the TRUE pixels:
				  	trueCount++;
				  	
				  	if (trueCount >= countValue) {
					    valuePresent = true;
					    break mainLoop;
				  	}
				  }
				
				  turnsHeight++;
			  }
		
	      //System Debugs:
  	    //System.out.println(turnsWidth + ", " + turnsHeight);
  	    
		    turnsWidth++;
		  }
		
		//Returns Value:
		return valuePresent;
	}
	
	/* Uses Returned Values as Follows: TL, BL, TR, BR */
	
	//Gets Width of Area (IF PRESENT):
	public static int getBodyWidth(int[][] valuesArray) throws Exception {
		//Width Variable:
		int colorBodyWidth = 0; //Default Value
		
		//Gets the Values:
		int topWidth = valuesArray[0][2] - valuesArray[0][0]; //TR X - TL X
		int bottomWidth = valuesArray[0][3] - valuesArray[0][1]; //BR X - BL X
		
		//Checks the Case:
		if (topWidth == bottomWidth) {
			colorBodyWidth = topWidth;
		}
		
		//Returns the Value:
		return colorBodyWidth;
	}
	
  //Gets Height of Area (IF PRESENT):  
	public static int getBodyHeight(int[][] valuesArray) throws Exception {
		//Height Variable:
		int colorBodyHeight = 0; //Default Value
		
	  //Gets the Values:
		int rightHeight = valuesArray[1][2] - valuesArray[1][3]; //TR Y - BR Y
		int leftHeight = valuesArray[1][0] - valuesArray[1][0]; //TL Y - BL Y
			
		//Checks the Case:
		if (rightHeight == leftHeight) {
			colorBodyHeight = rightHeight;
		}
		
		//Returns the Value:
		return colorBodyHeight;
	}
	
	//Gets The Midpoint X (IF PRESENT):
	public static int getMidpointX(int[][] valuesArray) throws Exception {
		//X Variable:
		int colorMidX = 0; //Default Value
		
	  //Gets the Values:
		int topWidth = valuesArray[0][2] - valuesArray[0][0]; //TR X - TL X
		int bottomWidth = valuesArray[0][3] - valuesArray[0][1]; //BR X - BL X
		
		//Checks Case:
		if (topWidth == bottomWidth) {
			int midX = (valuesArray[0][2] + valuesArray[0][0])/2;
			colorMidX = midX;
		}
		
		//Returns the Value:
		return colorMidX;
	}
	
  //Gets The Midpoint Y (IF PRESENT):
	public static int getMidpointY(int[][] valuesArray) throws Exception {
		//Y Variable:
		int colorMidY = 0; //Default Value
		
	  //Gets the Values:
		int rightHeight = valuesArray[1][2] - valuesArray[1][3]; //TR Y - BR Y
		int leftHeight = valuesArray[1][0] - valuesArray[1][0]; //TL Y - BL Y
				
		//Checks the Case:
		if (rightHeight == leftHeight) {
			int midY = (valuesArray[1][2] + valuesArray[1][3])/2;
			colorMidY = midY;
		}
		
		//Returns the Value:
		return colorMidY;
	}

	/* MACHINE LEARNING METHODS!!! */
	
	//Uses Width and Height to Use Model to Classify Box:
	public static boolean classifyBox(String modelIdentifier, int width, int height) throws Exception {
		//Classification Value:
		boolean boxClass = false; //Default Value

		//Classifies Box:
		boxClass = Neuron.getClass((double) width, (double) height, modelIdentifier);

		return boxClass;
	}
	
	/* 
	   METHOD OF TRAINING TO BE DONE IN MAIN APPLICATION JAVA FILE (BACKEND)
	  
	   Removes Outliers First:
		 Trainer.removeOutliers(trainingDataX, trainingDataY);
		
		 Trains Data:
		 Trainer.findBoxValues(trainingDataX, trainingDataY, modelIdentifier); 
	*/
	
	/* MOVEMENT DETECTION METHODS */
	
	//Locks Midpoint Values:
	public static void lockMidpoint(String imageIdentifier, boolean classification, int midpointX, int midpointY) throws Exception {	
		if (classification) {
			//Locks Midpoint Values:
		  lockMidpointX = midpointX;
		  lockMidpointY = midpointY;
		  
		  //Locks Midpoint RGB Values:
		  int rgb = Capture.getRGB(imageIdentifier, lockMidpointX, lockMidpointY);
		  int localMidpointRGB[] = Capture.convertRGB(rgb);
		  midpointRGBValues = localMidpointRGB;
		}
		
		else {
			System.out.print("No Package Detected!");
		}
	}
	
	//Tracks Midpoint RGB Values:
	public static boolean trackMidpoint(String imageIdentifier, int lightingMargin) throws Exception {
		//Current Midpoint Values:
		int currentRGB = Capture.getRGB(imageIdentifier, lockMidpointX, lockMidpointY);
		int currentRGBValues[] = Capture.convertRGB(currentRGB);
		
		//Margin Value:
		int margin = lightingMargin; //Used for Different Lighting Conditions (Customizable)
		
		//Sets Checking Values:
		int redDif = midpointRGBValues[0]-currentRGBValues[0];
		int greenDif = midpointRGBValues[1]-currentRGBValues[1];
		int blueDif = midpointRGBValues[2]-currentRGBValues[2];
		
	  //Multiply Values:
		int testRG = redDif*greenDif;
		int testRB = redDif*blueDif;
		int testGB = greenDif*blueDif;
		
		//Checks Condition:
		if (Math.abs(redDif) <= margin && Math.abs(greenDif) <= margin && Math.abs(blueDif) <= margin && 
				testRG >= 0 && testRB >= 0 && testGB >= 0) {
			
			//Returns True Value (Box is Present):
			return true;
		}
		
		else {
			//Returns False Value (Box is not Present):
			return false;
		}
	}
}
