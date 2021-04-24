package lib;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Positioning extends Capture {
  /* VISION POSITIONING VARIABLES */

  //Positioning Data (w/ Defaults):
  private static int centerPixel[] = new int[2];
  private static int frameWidth = 0, frameHeight = 0;
  private static double cameraOffsetX = 0, cameraOffsetY = 0, distanceOfField = 0;

  //Vision Positioning Output Variables (w/ Defaults):
  private static ArrayList<Double> alignX = new ArrayList<Double>();
  private static ArrayList<Double> alignY = new ArrayList<Double>();
  private static ArrayList<Double> distance = new ArrayList<Double>();
  private static ArrayList<Double> theta = new ArrayList<Double>();

  /* VISION POSITIONING METHODS */

  //Constructor:
  public Positioning() {
    super();
  }

  //Initialize Vision Positioning:
  public static void initVisionPosition(int width, int height, double depthOfField,
    double camOffsetX, double camOffsetY) throws Exception {
    //Sets Distances and Offsets:
    distanceOfField = depthOfField;
    cameraOffsetX = camOffsetX;
    cameraOffsetX = camOffsetY;
    frameWidth = width;
    frameHeight = height;

    //Pixel Settings:
    centerPixel[0] = (width / 2);
    centerPixel[1] = height;
  }

  //Vision Positioning Method:
  public static void getVisionPosition(int x[], int y[]) throws Exception {
    //Checks the Case:
    if (x.length == y.length) {
      //Loop Variables:
      int turns = 0;
      double distancePerPixel = (frameWidth / distanceOfField);

      //Loops through Arrays:
      mainLoop: while (turns < x.length) {
        //Gets the Triangle Values:
        int localPixel[] = {x[turns], y[turns]};
        double triangle[] = getTriangle(centerPixel, localPixel);

        //Gets the Output Values:
        double localOffsetX = ((triangle[1] * distancePerPixel) + cameraOffsetX);
        double localOffsetY = ((triangle[2] * distancePerPixel) + cameraOffsetY);
        double localDistance = (triangle[0] * distancePerPixel);
        double localTheta = triangle[3];

        //Sets the ArrayLists:
        alignX.add(localOffsetX);
        alignY.add(localOffsetY);
        distance.add(localDistance);
        theta.add(localTheta);

        turns++;
      }
    }

    else {
      //Error Debugs:
      System.err.println("Invalid Array Lengths!");
    }
  }

  /* VISION POSITION CALCULATION METHODS */

  //Triangle Calculation Method:
  public static double[] getTriangle(int a[], int b[]) throws Exception {
    //Gets the Distance Values:
    double values[] = new double[4];
    double hypotenuse = getDistance(a, b);

    //Gets the Angle Values:
    double yLeg = b[1] - a[1];
    double xLeg = b[0] - b[0];
    double theta = convertAngle(Math.atan2(xLeg, yLeg), true);

    //Formats and Returns Values:
    values[0] = hypotenuse;
    values[1] = xLeg;
    values[2] = yLeg;
    values[3] = theta;
    return values;
  }

  //Angle Conversion Method:
  public static double convertAngle(double angle, boolean degrees) throws Exception {
    //Degrees Double:
    double angleDegrees = ((180.0 / Math.PI) * angle);
    double angleRadians = ((Math.PI / 180.0) * angle);

    //Checks the Case:
    if (degrees) {
      //Returns the Angle:
      return angleDegrees;
    }

    else {
      //Returns the Angle:
      return angleRadians;
    }
  }

  /* VISION POSITION UTILITY METHODS */

  //Get Alignment X Distance Method:
  public static ArrayList<Double> getAlignX() throws Exception {
    //Returns the X Alignments:
    return alignX;
  }

  //Get Alignment Y Distance Method:
  public static ArrayList<Double> getAlignY() throws Exception {
    //Returns the Y Alignments:
    return alignY;
  }

  //Get Distance Method:
  public static ArrayList<Double> getDistance() throws Exception {
    //Returns the Distances:
    return distance;
  }

  //Get Theta Method:
  public static ArrayList<Double> getTheta() throws Exception {
    //Returns the Thetas:
    return theta;
  }
}