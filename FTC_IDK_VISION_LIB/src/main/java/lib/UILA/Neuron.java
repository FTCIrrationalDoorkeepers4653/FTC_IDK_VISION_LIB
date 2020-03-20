package lib.UILA;

import java.util.Arrays;
//import com.yamunasoftware.java.Tools.*;

public class Neuron extends Trainer {
  //Data Objects:
  private static HandleData hd = new HandleData();
  
  //Constructor: Gets Methods from Super class:
  public Neuron() {
	super();  
  }
  
  //Allows User to Classify Just One Class:
  public static boolean getClass(double dataX, double dataY, String identifier) throws Exception {
  	//Boolean Value:
  	boolean classification = false; //Default Value
  	
   //Box Variables:
  	//Top Left:
  	double[] TL = new double[2];
  	//Bottom Left:
  	double[] BL = new double [2];
  	//Top Right:
      double[] TR = new double[2];
  	//Bottom Right
  	double[] BR = new double[2]; 
  	
  	TL[0] = HandleData.openData(identifier + "tlX.txt"); //TL X
		TL[1] = HandleData.openData(identifier + "tlY.txt"); //TL Y
		
		BL[0] = HandleData.openData(identifier + "blX.txt"); //BL X
		BL[1] = HandleData.openData(identifier + "blY.txt"); //BL Y
		
		TR[0] = HandleData.openData(identifier + "trX.txt"); //TR X
		TR[1] = HandleData.openData(identifier + "trY.txt"); //TR Y
		
		BR[0] = HandleData.openData(identifier + "brX.txt"); //BR X
		BR[1] = HandleData.openData(identifier + "brY.txt"); //BR Y	
		
		if (dataX >= BL[0] && dataX <= TR[0] && dataY >= BL[1] && dataY <= TR[1]) {
		  classification = true;	
		}
		
		else {
		  classification = false;	
		}
		
		//Returns Value:
		return classification;
  }
  
  //Allows User to Classify the Test Data: //fix
  public static double classification(double dataX, double dataY, String identifiers[]) throws Exception {
	//Variables:
	Double classificationIndex[] = new Double[identifiers.length]; 
	int startTurns = identifiers.length, turns = 0;
	Double timesClassifiedIndex[] = {}; //Default Value
	int timesClassified = 0; //Default Value
	Double confidences[] = {};
	double finalClassIndex = 0.0, finalClassValue = 0.0; 
	
	//Box Variables:
	//Top Left:
	double[] TL = new double[2];
	//Bottom Left:
	double[] BL = new double [2];
	//Top Right:
    double[] TR = new double[2];
	//Bottom Right
	double[] BR = new double[2]; 
	
	//Loops through and classifies:
	mainLoop: while (turns < startTurns) {
		TL[0] = HandleData.openData(identifiers[turns] + "tlX.txt"); //TL X
		TL[1] = HandleData.openData(identifiers[turns] + "tlY.txt"); //TL Y
		
		BL[0] = HandleData.openData(identifiers[turns] + "blX.txt"); //BL X
		BL[1] = HandleData.openData(identifiers[turns] + "blY.txt"); //BL Y
		
		TR[0] = HandleData.openData(identifiers[turns] + "trX.txt"); //TR X
		TR[1] = HandleData.openData(identifiers[turns] + "trY.txt"); //TR Y
		
		BR[0] = HandleData.openData(identifiers[turns] + "brX.txt"); //BR X
		BR[1] = HandleData.openData(identifiers[turns] + "brY.txt"); //BR Y	
		
		if (dataX >= BL[0] && dataX <= TR[0] && dataY >= BL[1] && dataY <= TR[1]) {
		  classificationIndex[turns] = 1.0;	
		}
		
		else {
		  classificationIndex[turns] = 0.0;	
		}
		
		turns++;
	}
	
	//Resets Turn Value:
	turns = 0;
	
	//Checks for Multiple Classifications:
	secondLoop: while (turns < startTurns) {
      if (classificationIndex[turns] == 1.0) {  
    	timesClassifiedIndex = populate(timesClassifiedIndex, turns);  
    	timesClassified++;
      }		
		
	  turns++;	
	}
	
	//Debug:
	System.out.println(Arrays.deepToString(timesClassifiedIndex));
	
	//Checks for times Classified:
	if (timesClassified > 1) {
	  //Resets turn values:
	  turns = 0;
	  startTurns = timesClassifiedIndex.length;
		
	  //Calculates Confidences:
	  calculateLoop: while (turns < startTurns) {
		double indexOf = timesClassifiedIndex[turns];
		int stringIndex = (int) indexOf;
		  
		double indexConfidence = calculateDistValue(dataX, dataY, identifiers[stringIndex]);  
		confidences = populate(confidences, indexConfidence);
		
		turns++;
	  } 	
	  
	  //Debug:
	  System.out.println(Arrays.deepToString(confidences));
	  
	  //Resets turn Value:
	  turns = 0;
	  
	  //Finds Lowest Dist Value:
	  distLoop: while (turns < startTurns) {
		if (turns > 0) {
		  double currentClassValue = confidences[turns];
		  
		  //Sets New Values, if smaller:
		  if (currentClassValue <= finalClassValue) {
			finalClassValue = currentClassValue;
			finalClassIndex = (double) turns;
		  }
		} 
		
		else {
		  finalClassValue = confidences[0];
		  finalClassIndex = 0;	
		}
		
		turns++;
	  } 
	  
	  //Debugs:
	  System.out.println("Class Index: " + finalClassIndex);
	  System.out.println("Class Value: " + finalClassValue);
	  
	  return finalClassIndex;
	}
	
	else { 
	 if (timesClassifiedIndex.length == 1) {	
	  return timesClassifiedIndex[0];
	 }
	 
	 else {
	   return -1.0;	 //Negative Means No Index
	 }
	}
  }
  
  //API USER: Use to run any of the above methods:
  public void classify(double xData, double yData) throws Exception {}
}