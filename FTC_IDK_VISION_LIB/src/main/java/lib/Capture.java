package lib;

public class Capture {
	/* RGB 888 CONVERSION METHODS */

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

	/* GRAYSCALE AND NORMALIZATION METHODS */

	//RGB to GrayScale Method:
	public static int getGrayscale(int rgb[]) throws Exception {
		//Weights;
		double redWeight = 0.2126;
		double greenWeight = 0.7152;
		double blueWeight = 0.0722;

		//Multiplies the RGB by the Weights:
		int redWeighted = (int)(rgb[0] * redWeight);
		int greenWeighted = (int)(rgb[1] * greenWeight);
		int blueWeighted = (int)(rgb[2] * blueWeight);

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
