package lib;

@SuppressWarnings("unused")
public class Capture {
	/* RGB CONVERSION METHODS */

	//Converts Standard RGB into Usable RGB:
	public static int[] convertRGB(int rgbValue) throws Exception {
		//RGB Array:
		int rgbValues[] = new int[3];

		//Array Values:
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

	//Converts from 2D Array to Single Array RGB:
	public static int[] singleArrayRGB(int rgb[][]) throws Exception {
		//Main Array (w/ Default):
		int array[] = new int[(rgb.length * rgb[0].length)];

		//Loop Variable:
		int turnsWidth = 0;
		int turnCounter = 0;

		//Loops through Array:
		mainLoop: while (turnsWidth < rgb.length) {
			//Loop Variable:
			int turnsHeight = 0;
			secondLoop: while (turnsHeight < rgb[0].length) {
				//Sets the Array:
				array[turnCounter] = rgb[turnsWidth][turnsHeight];

				turnsHeight++;
				turnCounter++;
			}

			turnsWidth++;
		}

		//Returns the Array:
		return array;
	}

	/* GENERAL CALCULATION METHODS */

	//Distance Method:
	public static double getDistance(int first[], int second[]) throws Exception {
		//Distance Value (w/ Default):
		double distance = 0;

		//Checks the Case:
		if (first.length == second.length) {
			//Loop Variable:
			int turns = 0;

			//Loops through Array:
			mainLoop: while (turns < first.length) {
				//Gets the Individual Distance:
				distance += ((second[turns] - first[turns]) * (second[turns] - first[turns]));

				turns++;
			}
		}

		else {
			//Error Debugs:
			System.out.println("Invalid Array Lengths!");
		}

		//Calculates the Overall Distance:
		distance = Math.sqrt(distance);

		//Returns the Distance:
		return distance;
	}

	//Pixel Within Range Method:
	public static boolean isWithinRange(int rgb[], int check[], int margin[]) throws Exception {
		//Main Boolean (w/ Default):
		boolean isWithin = false;

		//Checks the Case:
		if (rgb.length == 3 && check.length == 3 && margin.length == 3) {
			//Gets the Differences:
			int diffRed = Math.abs((check[0] - rgb[0]));
			int diffGreen = Math.abs((check[1] - rgb[1]));
			int diffBlue = Math.abs((check[2] - rgb[2]));

			//Checks the Case:
			if (diffRed <= margin[0] && diffGreen <= margin[1] && diffBlue <= margin[2]) {
				//Sets the Boolean:
				isWithin = true;
			}
		}

		else {
			//Error Debugs:
			System.out.println("Invalid Array Input!");
		}

		//Returns the Boolean:
		return isWithin;
	}

	//Checks Value Within Range:
	public static boolean isValueRange(int value, int min, int max) throws Exception {
		//Checks the Case:
		if (value >= min && value <= max) {
			//Returns Value:
			return true;
		}

		else {
			//Returns Value:
			return false;
		}
	}

	//Random Method:
	public static double randomDouble(double min, double max) throws Exception {
		//Returns the Random Value:
		return (min + (Math.random() * (Math.abs((max - min)))));
	}

	/* GRAYSCALE AND NORMALIZATION METHODS */

	//RGB to GrayScale Method:
	public static int getGrayscale(int rgb[]) throws Exception {
		//Weights;
		double redWeight = 0.2126;
		double greenWeight = 0.7152;
		double blueWeight = 0.0722;

		//Multiplies the RGB by the Weights:
		int redWeighted = (int) (rgb[0] * redWeight);
		int greenWeighted = (int) (rgb[1] * greenWeight);
		int blueWeighted = (int) (rgb[2] * blueWeight);

		//Gets the GrayScale Value:
		int grayscale = redWeighted + greenWeighted + blueWeighted;

		//Returns the GrayScale Value:
		return grayscale;
	}

	//RGB Normalization:
	public static int normalizeValue(int rgbValue) throws Exception {
		//Normalized RGB:
		int newRGBValue = rgbValue;

		//Checks the Case:
		if (newRGBValue > 255) {
			//Sets the RGB Value:
			newRGBValue = 255;
		}

		else if (newRGBValue < 0) {
			//Sets the RGB Value:
			newRGBValue = 0;
		}

		//Returns the Normalized Array:
		return newRGBValue;
	}
}
