package lib;

public class ImageProcessing extends Analyze {
    /* CONSTRUCTOR */
    public ImageProcessing() {
        super();
    }

	/* LIGHTING METHODS */

    //Gets the Overall Lighting Value:
    public static int getAverageLighting(int rgbValues[][]) throws Exception {
        //Main Lighting Variable:
        int avgLighting = 0;

        //Gets the Average RGB Values:
        int avgRGB[] = averageRGBValues(rgbValues);

        //Gets the Average Lighting:
        avgLighting = avgRGB[0] + avgRGB[1] + avgRGB[2];

        //Returns the Average Lighting:
        return avgLighting;
    }

    //Gets the Pixel Lighting:
    public static int getPixelLighting(int rgb[]) {
        //Main Lighting Variable:
        int pixLighting = 0;

        //Gets the Pixel Lighting:
        pixLighting = rgb[0] + rgb[1] + rgb[2];

        //Returns the Pixel Lighting:
        return pixLighting;
    }

    //Gets the Lighting Adjustment Needed for the Pixel:
    public static int[] setLightingAdjustment(int rgb[], int averageLighting, int pixelLighting) {
        //Main Lighting Array:
        int rgbArray[] = new int[3];

        //RGB Max/Min Values:
        int rgbMax = 255;
        int rgbMin = 0;

        //Calculates the Adjustment:
        int adjustment = (averageLighting - pixelLighting)/3;

        //Adjusts for Brightness:
        rgbArray[0] = rgb[0] + adjustment;
        rgbArray[1] = rgb[1] + adjustment;
        rgbArray[2] = rgb[2] + adjustment;

        //Checks the Case (Red):
        if (rgbArray[0] < rgbMin) {
            //Sets the RGB Value:
            rgbArray[0] = rgbMin;
        }

        else if (rgbArray[0] > rgbMax) {
            //Sets the RGB Value:
            rgbArray[0] = rgbMax;
        }

        //Checks the Case (Green):
        if (rgbArray[1] < rgbMin) {
            //Sets the RGB Value:
            rgbArray[1] = rgbMin;
        }

        else if (rgbArray[1] > rgbMax) {
            //Sets the RGB Value:
            rgbArray[1] = rgbMax;
        }

        //Checks the Case (Blue):
        if (rgbArray[2] < rgbMin) {
            //Sets the RGB Value:
            rgbArray[2] = rgbMin;
        }

        else if (rgbArray[2] > rgbMax) {
            //Sets the RGB Value:
            rgbArray[2] = rgbMax;
        }

        //Returns the New RGB Array:
        return rgbArray;
    }

    /* USEFUL RGB APPLIANCE METHODS */

    //Finds the Average RGB in a Pixel:
    public static double averageRGB(int rgb[]) {
        //Calculates the Average RGB:
        double avgRGB = (rgb[0] + rgb[1] + rgb[2])/3;

        //Returns the Average RGB:
        return avgRGB;
    }

    //Finds the Average Distance Between RGB in a Pixel:
    public static double averageDistance(int rgb[]) {
        //Calculates the Distances:
        double redToGreen = Math.sqrt((rgb[0] * rgb[0]) + (rgb[1] * rgb[1]));
        double greenToBlue = Math.sqrt((rgb[1] * rgb[1]) + (rgb[2] * rgb[2]));
        double redToBlue = Math.sqrt((rgb[0] * rgb[0]) + (rgb[2] * rgb[2]));

        //Calculates the Average of the Distances:
        double avgDistance = (redToGreen + greenToBlue + redToBlue)/3;

        //Returns the Average Distance:
        return avgDistance;
    }

    //Converts Pixel from RGB to Grayscale:
    public static double grayscale(int rgb[]) {
        //RGB Weights:
        double redWeight = 0.21;
        double greenWeight = 0.72;
        double blueWeight = 0.07;

        //Calculates the Grayscale:
        double grayscaleValue = (rgb[0]*redWeight) + (rgb[1]*greenWeight) + (rgb[2]*blueWeight);

        //Returns the Grayscale Value:
        return grayscaleValue;
    }

    //Gets the Average Distance Using the Greyscale Function:
    public static double grayscaleDistance(int rgb[]) {
        //RGB Weights:
        double redWeight = 0.21;
        double greenWeight = 0.72;
        double blueWeight = 0.07;

        //Calculates the Weighted Grayscale Values:
        double redWeighted = rgb[0]*redWeight;
        double greenWeighted = rgb[1]*greenWeight;
        double blueWeighted = rgb[2]*blueWeight;

        //Calculates the Distances Based on the Grayscale Weighted Values:
        double redToGreen = Math.sqrt((redWeighted * redWeighted) + (greenWeighted * greenWeighted));
        double greenToBlue = Math.sqrt((greenWeighted * greenWeighted) + (blueWeighted * blueWeighted));
        double redToBlue = Math.sqrt((redWeighted * redWeighted) + (blueWeighted * blueWeighted));

        //Calculates the Average of the Distances:
        double greyscaleDistance = (redToGreen + greenToBlue + redToBlue)/3;

        //Returns the Calculated Distance:
        return greyscaleDistance;
    }
}
