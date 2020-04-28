package lib;

import java.util.Arrays;

public class Analyze extends Capture {
	//Detector Settings (Public Settings):
	private static int detectorRed, detectorGreen, detectorBlue;
	private static String detectorName;
	
	/* CONSTRUCTOR AND SET METHODS!!! */
	public Analyze() { super(); }

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
	public static String printDetectorSettings() {
		//RGB Array:
		int localRGBSettingsArray[] = {detectorRed, detectorGreen, detectorBlue};

		//Detector Settings String:
		String detectorSettings = detectorName + " with RGB Settings of: " + Arrays.toString(localRGBSettingsArray);

		//Simply Prints the Settings of the Detector:
		//System.out.println(detectorSettings);

		//Returns Settings:
		return detectorSettings;
	}
	
	/* COLOR ANALYSIS METHODS!!! */

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

	//Binary Detector (Converts to Zeros and Ones):
	public static int[][] binaryDetector(int[][] rgbValues, int[] lightingMargin) throws Exception {
		//Box Values Array:
		int boxValuesArray[][] = new int[rgbValues.length][rgbValues[0].length];
		int setRGBValues[] = new int[3];

		//Sets the RGB Value:
		setRGBValues[0] = detectorRed;
		setRGBValues[1] = detectorGreen;
		setRGBValues[2] = detectorBlue;

		//System Debugs:
		//System.out.println(Arrays.toString(setRGBValues));

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

					if (Math.abs(redDif) <= lightingMargin[0] && Math.abs(greenDif) <= lightingMargin[1] && Math.abs(blueDif) <= lightingMargin[2] &&
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

	//Get Selected Area Boolean (IF PRESENT):
	public static boolean getBodyBoolean(int[][] detectionArray, int pixelCount) throws Exception {
		//Main Value:
		boolean valuePresent = false; //Default Value

		//Loop Variables:
		int turnsWidth = 0;
		int trueCount = 0;

		//Count Value (Helps Mitigate Stray Pixels):
		int countValue = pixelCount; //Default Number of Pixels counted true before classifying (Customizable)

		//Loops Through Area for Confirmation:
		mainLoop:
		while (turnsWidth < detectionArray.length) {
			//Loop Variable:
			int turnsHeight = 0;
			secondLoop:
			while (turnsHeight < detectionArray[turnsWidth].length) {
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

	/* IMAGE SCREENING */

	//Grid Creation Method (Compatible with Arrays and Loops):
	public static int[][] getGrid(int imageWidth, int imageHeight, int squareValue)
			throws Exception {
		//Main Array:
		int gridValues[][] = new int[2][(squareValue*squareValue)];

		//Checks the Case:
		if (squareValue != 0) {
			//Standard Variables:
			int width = imageWidth / squareValue;
			int height = imageHeight / squareValue;

			//Loop Variables:
			int turns = 0;
			int squareWidthCount = 0, squareHeightCount = 0;

			//Loops to Find Values:
			mainLoop: while (turns < gridValues[0].length) {
				//Sets the First Coordinates:
				if (turns == 0) {
					//Setting the First Coordinates:
					gridValues[0][turns] = 0;
					gridValues[1][turns] = 0;

					//Adds to the Count:
					squareWidthCount++;
					squareHeightCount++;
				}

				else {
					if (squareWidthCount < squareValue) {
						//Setting Coordinates:
						gridValues[0][turns] = gridValues[0][turns-1] + width;
						gridValues[1][turns] = gridValues[1][turns-1];

						//Adds to the Count:
						squareWidthCount++;
					}

					else if (squareWidthCount >= squareValue && squareHeightCount < squareValue) {
						//Setting Coordinates:
						gridValues[0][turns] = 0;
						gridValues[1][turns] = gridValues[1][turns-1] + height;

						//Resets the Count:
						squareWidthCount = 1;
						squareHeightCount++;
					}
				}

				turns++;
			}
		}

		else {
			//Error Debugs:
			System.err.println("Invalid Square Enterance!");
		}

		//Returns the Grid Array:
		return gridValues;
	}
}
