package lib.UILA;

import java.util.Arrays;
import java.util.Stack;

import lib.UILA.HandleData;

public class Trainer { 
  //SEE DOCCUMENTATION FOR MORE DETAILS//
	
  //Objects:
  private static Stack<String> stack = new Stack<String>(); //Stack Object
  private static HandleData hd = new HandleData(); //Handle Data Object

  //API USER: Override Method to customize training, within trainer object!
  public void train() throws Exception {}
  
  //API USER: Override Method to test values!
  public void runValue(double testValue) throws Exception {}
  
  //Set Values In Constructor:
  public Trainer() {
	//TODO: Set Your Constructor Variables if Needed.
  }
  
  //Outlier Removal Function:
  public static void removeOutliers(Double trainData[], Double targetResultData[]) throws Exception {
    if (trainData.length == targetResultData.length && trainData.length != 0 && targetResultData.length != 0) {	
	    Double fitnessVals[] = new Double[trainData.length];
	    Double sorted[] = new Double[trainData.length];
	    Double weights[] = new Double[trainData.length];
	
	    int turns = 0;
	    int startTurns = trainData.length;
	
	    mainLoop: while (turns < startTurns) {
	      double individualWeight = 0.0;
	      individualWeight = ((targetResultData[turns]/trainData[turns]) + (trainData[turns]/targetResultData[turns])) * 
	      (targetResultData[turns] + trainData[turns]);
	  
	      weights[turns] = individualWeight;
	  
	      turns++;
	    }
	
	    sorted = copyArray(weights);
	
	    Arrays.sort(sorted);
	    turns = 0;
	
	    int half = startTurns/2;
	    int quarter = half/2;
	    int threeByFour = quarter*3;
	 
	    double IQR, Q1, Q3, MEDIAN, differenceAmount, thresholdAbove, thresholdBelow;
	
	    if (trainData.length % 2 == 0.0) {
	      MEDIAN = ((sorted[half] + sorted[(half)-1])/2.0);
	      Q1 = ((sorted[quarter] + sorted[quarter-1])/2.0);
	      Q3 = ((sorted[threeByFour] + sorted[threeByFour-1])/2.0);
	      IQR = (Q3-Q1);
	      differenceAmount = (IQR*1.5);
	      thresholdAbove = (Q3+differenceAmount);
	      thresholdBelow = (Q1-differenceAmount);
	    }
	
	    else {	
	      MEDIAN = (sorted[half]);
	      Q1 = (sorted[quarter]);
	      Q3 = (sorted[threeByFour]);
	      IQR = (Q3-Q1);
	      differenceAmount = (IQR*1.5);
	      thresholdAbove = (Q3+differenceAmount);
	      thresholdBelow = (Q1-differenceAmount);
	    }
	
	    secondLoop: while (turns < startTurns) {
	      if (weights[turns] > thresholdAbove || weights[turns] < thresholdBelow) {
	        fitnessVals[turns] = 0.0;  
	      }
			  
	      else {
    	    fitnessVals[turns] = 1.0;  
	      } 
	 
	      turns++;
	    }
	
	    turns = 0;
	    int checkTurns = turns;
	
	    //Pre-Removal Debugs:
	    //System.out.println(Arrays.deepToString(fitnessVals));
	    //System.out.println(Arrays.deepToString(weights));
	    //System.out.println(Arrays.deepToString(trainData));
	    //System.out.println(Arrays.deepToString(targetResultData));
	    //System.out.println("Begining Removal...");
	
	    thirdLoop: while (turns < startTurns) {
	      if (fitnessVals[checkTurns] == 0.0) {
		      int removeTurns = turns+1;  
		  
	        trainData = removeData(trainData, removeTurns);
		      targetResultData = removeData(targetResultData, removeTurns);
		      weights = removeData(weights, removeTurns);	
		
		
		      turns--;
		      startTurns--;
	      }	
	  
	      turns++;
	      checkTurns++;
	    }
	 
	    //Post-Removal Debugs:
	    //System.out.println(Arrays.deepToString(fitnessVals));
	    //System.out.println(Arrays.deepToString(weights));
	    //System.out.println(Arrays.deepToString(trainData));
	    //System.out.println(Arrays.deepToString(targetResultData));
    }
   
    else {
	    System.err.println("Data Set Input Error!");   
    }
  }
  
  //Box Value Equation Function:
  public static double[][] findBoxValues(Double trainData[], Double targetResultData[], String identifier) throws Exception {
	  //Main 2D Array:
	  double allBoxValues[][] = new double[2][5];
  	
	  //Checks for Data Set Errors:
	  if (trainData.length == targetResultData.length && trainData.length != 0 && targetResultData.length != 0) {
	    //Arrays:	
	    Double trainSorted[] = new Double[trainData.length];
	    Double targetSorted[] = new Double[targetResultData.length];
	  
	    //Sets Sort Arrays Data:
	    trainSorted = copyArray(trainData);
	    targetSorted = copyArray(targetResultData);
	  
	    //Sorts Arrays:
	    Arrays.sort(trainSorted);
	    Arrays.sort(targetSorted);
	  
	    //Box Variables:
	    double maxX, minX, maxY, minY;
	  
	    //Top Left:
	    double[] TL = new double[2];
	    //Bottom Left:
	    double[] BL = new double [2];
	    //Top Right:
	    double[] TR = new double[2];
	    //Bottom Right
	    double[] BR = new double[2]; 
	    //Midpoint:
	    double[] M = new double[2];
	  
	    //Setting Maxs and Mins:
	    maxX = trainSorted[trainSorted.length-1]; //Sets Maximum X 
	    minX = trainSorted[0]; //Minimum X
	  
	    maxY = targetSorted[targetSorted.length-1]; //Sets Maximum Y
	    minY = targetSorted[0]; //Sets Minimum Y
	  
	    //Setting Box Values:
	    TL[0] = minX; //Top Left X
	    TL [1] = maxY; //Top Left Y
	  
	    BL[0] = minX; //Bottom Left X
	    BL [1] = minY; //Bottom Left Y
	  
	    TR[0] = maxX; //Top Right X
	    TR [1] = maxY; //Top Right Y
	  
	    BR[0] = maxX; //Bottom Right X
	    BR[1] = minY; //Bottom Right Y
	  
	    M = getMidpoint(trainData, targetResultData); //Midpoint X and Y 
	  
	    //Saves the Data (as a model):
	    HandleData.saveData(identifier + "tlX.txt", TL[0]); //TL X
	    HandleData.saveData(identifier + "tlY.txt", TL[1]); //TL Y
	  
	    HandleData.saveData(identifier + "blX.txt", BL[0]); //BL X
	    HandleData.saveData(identifier + "blY.txt", BL[1]); //BL Y
	  
	    HandleData.saveData(identifier + "trX.txt", TR[0]); //TR X
	    HandleData.saveData(identifier + "trY.txt", TR[1]); //TR Y
	  
	    HandleData.saveData(identifier + "brX.txt", BR[0]); //BR X
	    HandleData.saveData(identifier + "brY.txt", BR[1]); //BR Y
	  
	    HandleData.saveData(identifier + "mX.txt", M[0]); //M X
	    HandleData.saveData(identifier + "mY.txt", M[1]); //M Y
	  
	    //Value Debugs:
	    //System.out.println("TL: " + Arrays.toString(TL));
	    //System.out.println("BL: " + Arrays.toString(BL));
	    //System.out.println("TR: " + Arrays.toString(TR));
	    //System.out.println("BR: " + Arrays.toString(BR));
	    //System.out.println("M: " + Arrays.toString(M));
	    
	    //Sets the Array Values (TL, BL, TR, BR, M):
	    
	    //TL Values:
	    allBoxValues[0][0] = TL[0];
	    allBoxValues[1][0] = TL[1];
	    
	    //BL Values:
	    allBoxValues[0][1] = BL[0];
	    allBoxValues[1][1] = BL[1];
	    
	    //TR Values:
	    allBoxValues[0][2] = TR[0];
	    allBoxValues[1][2] = TR[1];
	    
	    //BR Values:
	    allBoxValues[0][3] = BR[0];
	    allBoxValues[1][3] = BR[1];
	    
	    //M Values:
	    allBoxValues[0][4] = M[0];
	    allBoxValues[1][4] = M[1];
	  }  
	
	  else {
	    System.err.println("Data Set Input Error!");   
    }
	  
	  //Returns 2D Array:
	  return allBoxValues;
  }
  
  //Calculates the Correct Midpoint:
  public static double[] getMidpoint(Double trainData[], Double targetResultData[]) throws Exception {
    //Main Array:
	  double midPointValues[] = new double[2];
  	
    //Checks for Data Set Errors:
    if (trainData.length == targetResultData.length && trainData.length != 0 && targetResultData.length != 0) {
  	
  	  //Loop Values:
  	  int startTurns = trainData.length;
  	  int turns = 0;
  	  double trainAvg = 0.0, targetAvg = 0.0;
  	
  	  mainLoop: while (turns < startTurns) {
  	  	trainAvg += trainData[turns];
  	  	targetAvg += targetResultData[turns];
  	  	
  	  	turns++;
  	  }
  	  
  	  //Computes Average:
  	  trainAvg /= startTurns;
  	  targetAvg /= startTurns;
  	  
  	  //Adds To Array:
  	  midPointValues[0] = trainAvg;
  	  midPointValues[1] = targetAvg;
    }
    
    else {
    	System.err.println("Data Set Input Error!");	
    }

	  //Returns The Value:
	  return midPointValues;
  }
  
  //Calculates the Confidence:
  public static double calculateDistValue(double dataX, double dataY, String identifier) throws Exception {
	  //Confidence Variable:
	  double confidence;
	
	  /* 
	   * DistValue is calculated by finding the distance of the data point to the 
	   * midpoint of the box in relation to the closest landmark.
	   * 
	   */
	
	  //Box Variables:
	  //Top Left:
	  double[] TL = new double[2];
	  //Bottom Left:
    double[] BL = new double [2];
    //Top Right:
    double[] TR = new double[2];
    //Bottom Right
    double[] BR = new double[2]; 
    //Midpoint:
    double[] M = new double[2];
    
    //Opens the Data:
	  TL[0] = HandleData.openData(identifier + "tlX.txt"); //TL X
	  TL[1] = HandleData.openData(identifier + "tlY.txt"); //TL Y
	
	  BL[0] = HandleData.openData(identifier + "blX.txt"); //BL X
	  BL[1] = HandleData.openData(identifier + "blY.txt"); //BL Y
	
	  TR[0] = HandleData.openData(identifier + "trX.txt"); //TR X
	  TR[1] = HandleData.openData(identifier + "trY.txt"); //TR Y
	
	  BR[0] = HandleData.openData(identifier + "brX.txt"); //BR X
	  BR[1] = HandleData.openData(identifier + "brY.txt"); //BR Y
	
	  M[0] = HandleData.openData(identifier + "mX.txt"); //M X
	  M[1] = HandleData.openData(identifier + "mY.txt"); //M Y
	
	  //Finds the Distance to the Landmarks:
	  double distanceToTL, distanceToBL, distanceToTR, distanceToBR, distanceToM, avgDistance;
	  double[] allDistances = new double[4];
	
	  distanceToTL = Math.sqrt( ((Math.abs(dataY-TL[1]))*(Math.abs(dataY-TL[1]))) + ((Math.abs(dataX-TL[0]))*(Math.abs(dataX-TL[0]))) ); //TL
	  distanceToBL = Math.sqrt( ((Math.abs(dataY-BL[1]))*(Math.abs(dataY-BL[1]))) + ((Math.abs(dataX-BL[0]))*(Math.abs(dataX-BL[0]))) ); //BL
	  distanceToTR = Math.sqrt( ((Math.abs(dataY-TR[1]))*(Math.abs(dataY-TR[1]))) + ((Math.abs(dataX-TR[0]))*(Math.abs(dataX-TR[0]))) ); //TR
	  distanceToBR = Math.sqrt( ((Math.abs(dataY-BR[1]))*(Math.abs(dataY-BR[1]))) + ((Math.abs(dataX-BR[0]))*(Math.abs(dataX-BR[0]))) ); //BR
	  distanceToM = Math.sqrt( ((Math.abs(dataY-M[1]))*(Math.abs(dataY-M[1]))) + ((Math.abs(dataX-M[0]))*(Math.abs(dataX-M[0]))) ); //M
	
	  //Adds Distances to Array and Sorts:
	  allDistances[0] = distanceToTL;
	  allDistances[1] = distanceToBL;
	  allDistances[2] = distanceToTR;
	  allDistances[3] = distanceToBR;
	
	  Arrays.sort(allDistances);
	
	  //Sets Nearest Landmark Distance:
	  avgDistance = (allDistances[0]+allDistances[1]+allDistances[2]+allDistances[3])/4.0;
	
	  //Calculates Confidence:
	  confidence = (distanceToM+avgDistance); //Returns Dist Value
	  //System.out.println("DistValue: " + confidence); //Dist Val Debug
	
	  //Returns Value:
	  return confidence;  
  }
  
  //API USER: Use method to populate arrays!
  public static Double[] populate(Double array[], double data) throws Exception {  
    Double newTrainArray[] = new Double[array.length+1];	  
	int length = 0;
	
	mainLoop: while (length < array.length) {
	  newTrainArray[length] = array[length];
	  length++;
	}
	
	newTrainArray[length] = data;
	//System.out.println(Arrays.deepToString(array)); //Populate Debug
   
	return newTrainArray;
  }
  
  //API USER: This is a remove data function, customize to fit your needs with your arrays!
  public static Double[] removeData(Double array[], int dataPoint) throws Exception {
	dataPoint--;
	Double newTrainArray[] = new Double[array.length-1]; 
	  if (array.length != 0 && dataPoint >= 0 && dataPoint < array.length) {	
		int length = 0;
			 
		firstLoop: while (length < array.length) {
		  if (length == dataPoint) {
			length++;
			break firstLoop;  
		  }	 
			   
			newTrainArray[length] = array[length];
			length++;
		}
			 
		secondLoop: while (length < array.length) {
		  newTrainArray[length-1] = array[length];
		  length++;  	
	    }
			 
		  //System.out.println(Arrays.deepToString(array)); //Removal Debug
		  return newTrainArray;
		}
		   
		else {
		  System.err.println("Invalid Data Set Lengths or Out of Bounds!");  
		  return null;
		}
  }
  
  //Copy Array Data:
  public static Double[] copyArray(Double array[]) {
   if (array.length != 0) {	  
	//Copied Array:
	Double copiedArray[] = new Double[array.length];
	
	//Loop Variables:
	int turns = 0;
	int startTurns = array.length;
	
	mainLoop: while (turns < startTurns) {
	  copiedArray[turns] = array[turns];
	  
      turns++;	
	}
	  
	//System.out.println(Arrays.deepToString(array)); //Copy Debug
	return copiedArray;
   }
   
   else {
	 System.err.println("Data Set Input Error!");
	 return null;
   }
  }
}