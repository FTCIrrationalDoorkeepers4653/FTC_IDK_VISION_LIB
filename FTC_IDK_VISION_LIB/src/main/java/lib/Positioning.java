package lib;

import java.util.ArrayList;

public class Positioning {
  /* VISION POSITIONING VARIABLES */

  //Positioning Data (w/ Defaults):
  private static int referencePixel[] = new int[2], centerPixel[] = new int[2];
  private static double referenceDistanceInches = 0, cameraOffsetX = 0, cameraOffsetY = 0;

  //Vision Positioning Output Variables (w/ Defaults):
  private static ArrayList<Double> alignX = new ArrayList<Double>();
  private static ArrayList<Double> alignY = new ArrayList<Double>();

  /* VISION POSITIONING SETUP METHODS */

  //Initialize Vision Positioning:
  public static void initVisionPosition(int reference[], double referenceDistance, double camOffsetX,
    double camOffsetY) {
    //Sets Distances and Offsets:
    referenceDistanceInches = referenceDistance;
    cameraOffsetX = camOffsetX;
    cameraOffsetX = camOffsetY;

    //Checks the Case:
    if (reference.length == 2) {
      //Sets the Reference Pixel:
      referencePixel = reference;
    }

    else {
      //Sets the Reference Pixel Coordinates:
      referencePixel[0] = 0;
      referencePixel[1] = 0;
    }
  }

  /* VISION POSITIONING METHODS */

  //Gets the Distances

  /* VISION POSITIONING CALCULATION METHODS */

  //Center of Image Method:
  public static int[] getCenter(int width, int height) {
    //Gets the Center Coordinates:
    int x = (width / 2);
    int y = height;

    //Formats and Returns Array:
    int center[] = {x, y};
    centerPixel = center;
    return center;
  }

  //Get Ray Coordinates Method:
  public static int[] getRayCast(int coordinate[]) {
    //Main RayCast Coordinate:
    int rayCoordinate[] = new int[2];
    rayCoordinate[1] = referencePixel[1];

    //Gets the Initial Slope and X-Coordinate:
    double initialSlope = ((Math.abs(coordinate[1] - centerPixel[1])) / (Math.abs(coordinate[0] - centerPixel[0])));
    rayCoordinate[0] = (int)(((Math.abs(rayCoordinate[1] - coordinate[1])) / initialSlope) + coordinate[0]);
    return rayCoordinate;
  }

  //Distance Calculation Method:
  public static double getDistance(int firstPoint[], int secondPoint[]) {
    //Gets the Differences:
    double diffX = ((secondPoint[0] - firstPoint[0]) * (secondPoint[0] - firstPoint[0]));
    double diffY = ((secondPoint[1] - firstPoint[1]) * (secondPoint[1] - firstPoint[1]));

    //Gets the Distance:
    double distance = Math.sqrt((diffX + diffY));
    return distance;
  }

  /* VISION POSITION UTILITY METHODS */

  //Get Alignment X Distance Method:
  public static ArrayList<Double> getAlignX() {
    //Returns the X Alignments:
    return alignX;
  }

  //Get Alignment Y Distance Method:
  public static ArrayList<Double> getAlignY() {
    //Returns the Y Alignments:
    return alignY;
  }
}
