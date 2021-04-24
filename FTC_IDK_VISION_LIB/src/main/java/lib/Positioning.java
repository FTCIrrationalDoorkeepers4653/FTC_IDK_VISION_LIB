package lib;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Positioning extends Capture {
  /* VISION POSITIONING VARIABLES */

  //Positioning Data (w/ Defaults):
  private static int centerPixel[] = new int[2];
  private static int frameWidth = 0, frameHeight = 0;
  private static double cameraOffsetHorizontal = 0, cameraOffsetVertical = 0;
  private static double yRatio = 0, xRatio = 0;

  //Vision Positioning Output Variables (w/ Defaults):
  private static ArrayList<Double> offset = new ArrayList<Double>();
  private static ArrayList<Double> distance = new ArrayList<Double>();

  /* VISION POSITIONING SETUP METHODS */

  //Constructor:
  public Positioning() {
    super();
  }

  //Initialize Vision Positioning:
  public static void initVisionPosition(int width, int height, double distanceRatio,
    double offsetRatio, double camOffsetX, double camOffsetY) throws Exception {
    //Sets Ratios:
    yRatio = distanceRatio;
    xRatio = offsetRatio;

    //Sets Offsets:
    cameraOffsetHorizontal = camOffsetX;
    cameraOffsetVertical = camOffsetY;

    //Sets Frame Data:
    frameWidth = width;
    frameHeight = height;

    //Pixel Settings:
    centerPixel[0] = (frameWidth / 2);
    centerPixel[1] = frameHeight;
  }

  /* VISION POSITIONING METHODS */

  //Vision Positioning Method:
  public static void getVisionPosition(int x[], int y[]) throws Exception {
    //Checks the Case:
    if (x.length == y.length) {
      //Loop Variables:
      int turns = 0;

      //Loops through Arrays:
      mainLoop: while (turns < x.length) {
        //Gets and Sets Values:
        double pointPosition[] = getPointPosition(x[turns], y[turns]);
        distance.add(pointPosition[0]);
        offset.add(pointPosition[1]);

        turns++;
      }
    }

    else {
      //Error Debugs:
      System.err.println("Invalid Array Lengths!");
    }
  }

  //Point Positioning Method:
  public static double[] getPointPosition(int x, int y) throws Exception {
    //Return Array:
    double returnArray[] = new double[2];

    //Gets Calculation:
    int localPixel[] = {x, y};
    double triangle[] = getTriangle(centerPixel, localPixel);

    //Gets the Output Values:
    double realDistance = ((triangle[0] * yRatio) + cameraOffsetVertical);
    double realOffset = (triangle[1] * xRatio);

    //Checks the Case:
    if (x > centerPixel[0]) {
      //Sets the Offset:
      realOffset -= cameraOffsetHorizontal;
    }

    else {
      //Sets the Offset:
      realOffset += cameraOffsetHorizontal;
    }

    //Sets Array:
    returnArray[0] = realDistance;
    returnArray[1] = realOffset;

    //Returns Array:
    return returnArray;
  }

  /* VISION POSITION CALCULATION METHODS */

  //Vision Offset Correction Method:
  public static double getVisionOffsetCorrection(double offset) throws Exception {
    //Checks the Case:
    if (offset > cameraOffsetHorizontal) {
      //Returns the New Value:
      return -offset;
    }

    else {
      //Returns the New Value:
      return offset;
    }
  }

  //Triangle Calculation Method:
  public static double[] getTriangle(int a[], int b[]) throws Exception {
    //Gets the Distance Values:
    double values[] = new double[4];
    double hypotenuse = getDistance(a, b);

    //Gets the Angle Values:
    double yLeg = a[1] - b[1];
    double xLeg = a[0] - b[0];

    //Formats and Returns Values:
    values[0] = hypotenuse;
    values[1] = xLeg;
    values[2] = yLeg;
    return values;
  }

  /* VISION POSITION UTILITY METHODS */

  //Get Alignment X Distance Method:
  public static ArrayList<Double> getOffset() throws Exception {
    //Returns the X Alignments:
    return offset;
  }

  //Get Distance Method:
  public static ArrayList<Double> getDistance() throws Exception {
    //Returns the Distances:
    return distance;
  }
}