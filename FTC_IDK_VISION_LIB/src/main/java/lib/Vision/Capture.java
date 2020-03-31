package lib.Vision;

public class Capture {
  //Constructor:
  public Capture() {}

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
