package lib;

import java.util.ArrayList;
import lib.ImageProcessing.CONV_TYPE;
import com.yamunasoftware.java.Tools.*;
import com.yamunasoftware.java.Main.*;

public class ImageRecognition extends Analyze {
	//Global Data Sets:
	private static ArrayList<Double> xSet = new ArrayList<Double>();
	private static ArrayList<Double> ySet = new ArrayList<Double>();
	
	/* CONSTRUCTOR */
	public ImageRecognition() {
		super();
	}
	
	/* TRAINING METHODS */
	
	@SuppressWarnings("unused")
	//Train Image for Image Recognition (BufferedImage): 
	public static void trainImage(String identifier, int rgbValues[][], double sideTrim, 
		double vertTrim, double padding, int vectorGrid) throws Exception {
		//Checks the Case:
		if (sideTrim <= 0.4 && sideTrim >= 0 && vertTrim <= 0.4 && vertTrim >= 0 && vectorGrid != 0) {
		  //Trim Variable:
		  int trimX = (int)(rgbValues.length*sideTrim);
		  int trimWidth = (int)(rgbValues.length - (rgbValues.length*(sideTrim*2)));
		  
		  //Other Variables:
		  int trimY = (int)(rgbValues[0].length*vertTrim);
		  int trimHeight = (int)(rgbValues[0].length - (rgbValues[0].length*(vertTrim*2)));
		  
		  //Gets the New RGB Values:
		  int newRGBValues[][] = getSection(rgbValues, trimX, trimY, trimWidth, trimHeight);
		  
		  //Gets the Applied Filters:
		  int vectorFilter[][] = 
		  	ImageProcessing.applyFilter(newRGBValues, SOBEL_VERT, CONV_TYPE.SUM);
		  int vectorCalculation[][] = 
		  	ImageProcessing.applyFilter(newRGBValues, SOBEL_HORI, CONV_TYPE.SUM);
		  
		  //Creates the Vector Grid:
		  int gridValues[][] = getGrid(vectorFilter.length, vectorFilter[0].length, vectorGrid); 
		  
		  //Loop Variables:
		  int turns = 0;
		  int vectorCalcWidth = vectorFilter.length/vectorGrid, vectorCalcHeight = vectorFilter[0].length/vectorGrid;
		  
		  //Loops through Array:
		  mainLoop: while (turns < gridValues[0].length) {
		  	//Loop Variable:
		  	int turnsWidth = 0;
		  	secondLoop: while (turnsWidth < vectorCalcWidth 
		  			&& turnsWidth < trimWidth) {
		  		//Loop Variable:
		  		int turnsHeight = 0;
		  		thirdLoop: while (turnsHeight < vectorCalcHeight
		  				&& turnsHeight < trimHeight) {
		  		  //Calculates the Vector:
			  		double vectorOutput[] = 
			  			calculateVector(
			  				vectorFilter[gridValues[0][turns] + turnsWidth][gridValues[1][turns] + turnsHeight], 
			  				vectorCalculation[gridValues[0][turns] + turnsWidth][gridValues[1][turns] + turnsHeight]	
			  			);
		  			
		  			//Populates the Data Sets:
		  			xSet.add(vectorOutput[0]);
		  			ySet.add(vectorOutput[1]);
		  			
		  			turnsHeight++;
		  		}
		  		
		  		turnsWidth++;
		  	}
		  	
		  	//Gets the Arrays for Training:
		  	Double xDataSet[] = xSet.toArray(new Double[xSet.size()]);
		  	Double yDataSet[] = ySet.toArray(new Double[ySet.size()]);
		  	
		  	//Removes the Data Outliers:
		  	Trainer.removeOutliers(xDataSet, yDataSet);
		  	
		  	//Trains the Vector Data Set:
		  	String localIdentifier = identifier + turns;
		  	Trainer.findBoxValues(xDataSet, yDataSet, padding, localIdentifier);
		  	
		  	//Clears the Data Sets for Next Iteration:
		  	xSet.clear();
		  	ySet.clear();
		  	
		  	turns++;
		  }
		}
		
		else {
			//Error Debugs:
			System.err.println("Image Trimming Input Error!");
		}
	}
	
	/* AUTHENTICATION METHODS */
	
	@SuppressWarnings("unused")
	//Authenticates the Input Using Reverse Training [PARAMS: Identifiers Must Be True Then False]:
	public static boolean authenticateImage(String identifiers, int rgbValues[][], 
		double sideTrim, double vertTrim, double authPercent, int vectorGrid) throws Exception {
	  //Main Boolean:
		boolean authentication = false; //Default Value
			
		//Checks the Case:
		if (sideTrim <= 0.4 && sideTrim >= 0 && vertTrim <= 0.4 && vertTrim >= 0 && vectorGrid != 0 &&
				authPercent <= 1) {
		  //Trim Variable:
		  int trimX = (int)(rgbValues.length*sideTrim);
		  int trimWidth = (int)(rgbValues.length - (rgbValues.length*(sideTrim*2)));
		  
		  //Other Variables:
		  int trimY = (int)(rgbValues[0].length*vertTrim);
		  int trimHeight = (int)(rgbValues[0].length - (rgbValues[0].length*(vertTrim*2)));
		  
		  //Gets the New RGB Values:
		  int newRGBValues[][] = getSection(rgbValues, trimX, trimY, trimWidth, trimHeight);
		  
		  //Gets the Applied Filters:
		  int vectorFilter[][] = 
		  	ImageProcessing.applyFilter(newRGBValues, SOBEL_VERT, CONV_TYPE.SUM);
		  int vectorCalculation[][] = 
		  	ImageProcessing.applyFilter(newRGBValues, SOBEL_HORI, CONV_TYPE.SUM);
		  		
		  //Creates the Vector Grid:
		  int gridValues[][] = getGrid(vectorFilter.length, vectorFilter[0].length, vectorGrid); 
		  
		  //Loop Variables:
		  int turns = 0;
		  int vectorCalcWidth = vectorFilter.length/vectorGrid, vectorCalcHeight = vectorFilter[0].length/vectorGrid;
			int authCount = 0, deAuthCount = 0, vectorCount = 0, deAuthVectorCount = 0;
			  
			//Loops through Array:
			mainLoop: while (turns < gridValues[0].length) {
			  //Loop Variable:
			  int turnsWidth = 0;
			  secondLoop: while (turnsWidth < vectorCalcWidth 
			  		&& turnsWidth < trimWidth) {
			  	//Loop Variable:
			  	int turnsHeight = 0;
			  	thirdLoop: while (turnsHeight < vectorCalcHeight
			  			&& turnsHeight < trimHeight) {
			  	  //Calculates the Vector:
			  		double vectorOutput[] = 
			  			calculateVector(
			  				vectorFilter[gridValues[0][turns] + turnsWidth][gridValues[1][turns] + turnsHeight], 
			  				vectorCalculation[gridValues[0][turns] + turnsWidth][gridValues[1][turns] + turnsHeight]
			  			);
			  			
			  		//Checks the Pixel in Data Set (Only for that Vector, Not All Vectors):
			  		String localIdentifiers = identifiers + turns;
			  		boolean classification = Neuron.getClass(vectorOutput[0], vectorOutput[1], localIdentifiers);
			  		
			  		//Adds the Data For Count:
			  		if (classification) {
			  			authCount++;
			  		}
			  		
			  		else {
			  			deAuthCount++;
			  		}
			  		
			  		//Checks for the Authentication:
			  		if (authCount >= ((vectorCalcWidth*vectorCalcHeight)*authPercent)) {
			  			//Resets the Count:
			  			authCount = 0;
			  			deAuthCount = 0;
			  			
			  			//Adds to the Vector Count:
			  			vectorCount++;
			  			
			  			//Breaks the Loop:
			  			break secondLoop;
			  		}
			  		
			  		else if (deAuthCount >= ((vectorCalcWidth*vectorCalcHeight)*authPercent)) {
			  		  //Resets the Count:
			  			deAuthCount = 0;
			  			authCount = 0;
			  			
			  		  //Adds to the Vector Count:
			  			deAuthVectorCount++;
			  			
			  		  //Breaks the Loop:
			  			break secondLoop;
			  		}
			  			
			  		turnsHeight++;
			  	}
			  		
			  	turnsWidth++;
			  }
			  
			  //Checks for Complete Vector Authentication:
			  if (vectorCount >= ((vectorGrid*vectorGrid)*authPercent)) {
			  	//Sets the Authentication:
			  	authentication = true;
			  	
			  	//Breaks the Loop:
			  	break mainLoop;
			  }
			  
			  else if (deAuthVectorCount >= ((vectorGrid*vectorGrid)*authPercent)) {
			    //Breaks the Loop:
			  	break mainLoop;	
			  }
			}
		}
			
		//Returns the Authentication:
		return authentication;		
	}
	
	/* VECTOR METHODS */
	
	//Vector Calculation Method:
	public static double[] calculateVector(int xDiff, int yDiff) throws Exception {
		//Main Vector Output Array:
		double vectorOutput[] = new double[2];
		
		//Normalization:
		int normalX = normalizeVector(xDiff, 1);
		int normalY = yDiff;
	
	  //Vector Output Calculation (HOG Magnitude and Orientation):
		double x = Math.sqrt(((normalX * normalX) + (normalY * normalY)));
		double y = Math.atan(normalY/normalX);
		
		//Adds the Values to the Output:
		vectorOutput[0] = x;
		vectorOutput[1] = y;
		
		//Returns the Vector Output:
		return vectorOutput;
	}
	
	//Vector Input Normalization:
	public static int normalizeVector(int input, int normalizeValue) {
		//Main Output (w/ Defaults):
		int output = 0;
		
		//Checks the Normal Value:
		if (normalizeValue != 0) {
		  //Checks the Case:
		  if (input == 0) {
			  //Normalizes the Input:
			  output = normalizeValue;
		  }
		
		  else {
		    //Normalizes the Input:
			  output = input;
		  }
		}
		
		else {
			//Error Debugs:
			System.err.println("Zero Input Error!");
		}
		
		//Returns the Output:
		return output;
	}
	
	/* KERNELS */
	
	//Sobel Vertical Mask:
	private static int SOBEL_VERT[][] = {
		{ -8, 0, 8 },
		{ -8, 0, 8 },
		{ -8, 0, 8 }
	};
	
  //Sobel Horizontal Mask:
	private static int SOBEL_HORI[][] = {
		{ -8, -8, -8 },
		{  0,  0,  0 },
		{  8,  8,  8 }
	};
}