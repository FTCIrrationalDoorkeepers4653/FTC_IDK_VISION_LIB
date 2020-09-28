package lib;

import java.awt.Color;

public class Capture {
	/* UNIVERSAL RGB CONVERSION METHODS */

	//Universal INT to RGB Conversion:
	public static int[] convertUniversalRGB(int rgbValue) {
		//RGB Array:
		int rgb[] = new int[3];

		//Gets the Values:
		int red = new Color(rgbValue).getRed();
		int green = new Color(rgbValue).getGreen();
		int blue = new Color(rgbValue).getBlue();

		//Sets the Values:
		rgb[0] = red;
		rgb[1] = green;
		rgb[2] = blue;

		//Returns the Array:
		return rgb;
	}

	//Universal RGB to INT Conversion:
	public static int convertUniversalINT(int rgbArray[]) {
		//RGB Integer and Color:
		int rgb = 0;
		rgb = new Color(rgbArray[0], rgbArray[1], rgbArray[2]).getRGB();

		//Returns the Integer:
		return rgb;
	}

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
}
