package lib;

import com.yamunasoftware.java.Main.*;
import com.yamunasoftware.java.Tools.*;

import java.util.ArrayList;

public class ImageAuth extends Analyze {
  //Global Data Sets:
  private static ArrayList<Double> distSet = new ArrayList<Double>();
  private static ArrayList<Double> colorSet = new ArrayList<Double>();

  //Global IDs:
  private static String brightnessID = "brightnessData";

  /* CONSTRUCTOR */
  public ImageAuth() { super(); }

  /*
     METHOD OF TRAINING TO BE DONE IN MAIN APPLICATION JAVA FILE (BACKEND)

     Removes Outliers First:
     Trainer.removeOutliers(trainingDataX, trainingDataY);

     Trains Data:
     Trainer.findBoxValues(trainingDataX, trainingDataY, modelIdentifier);
  */

  /* TRAINING METHODS */

  //Trains the Image Vectors:
  public static void trainImage(int rgbValues[][], double sideTrim, double vertTrim,
    double systemResize, double padding, int vectorGrid) throws Exception {
      //Checks the Case:
      if (sideTrim <= 0.4 && sideTrim >= 0 && vertTrim <= 0.4 && vertTrim >= 0
        && vectorGrid != 0) {
          //Trim Variable:
          int trimX = (int)(rgbValues.length*sideTrim);
          int trimWidth = (int)(rgbValues.length - (rgbValues.length*(sideTrim*2)));

          //Other Variables:
          int trimY = (int)(rgbValues[0].length*vertTrim);
          int trimHeight = (int)(rgbValues[0].length - (rgbValues[0].length*(vertTrim*2)));

          //Saves the Brightness Data:
          int avgBrightness = ImageProcessing.getAverageLighting(rgbValues);
          HandleData.saveData(brightnessID, avgBrightness);

          //Creates the Vector Grid:
          int gridValues[][] = getGrid(trimWidth, trimHeight, vectorGrid);

          //Loop Variables:
          int turns = 0;
          int vectorCalcWidth = trimWidth/vectorGrid,
              vectorCalcHeight = trimHeight/vectorGrid;

          //Loops through Array:
          mainLoop: while (turns < gridValues[0].length) {
            //Loop Variable:
            int turnsWidth = 0;
            secondLoop: while (turnsWidth < vectorCalcWidth
              && turnsWidth < trimWidth) {
              //Loop Variable:
              int turnsHeight = 0;
              thirdLoop: while (turnsHeight < vectorCalcHeight
                && turnsHeight < trimHeight) {
                //Color Conversion:
                int rgb[] = Capture.convertRGB(rgbValues[gridValues[0][turns] + turnsWidth]
                [gridValues[1][turns] + turnsHeight]);

                //Calculates the Average RGB Value (Greyscale):
                double totalRGB = ImageProcessing.grayscale(rgb);

                //Calculates the Average RGB Distance (Greyscale):
                double totalDistance = ImageProcessing.averageDistance(rgb);

                //Populates the Data Sets:
                distSet.add(totalDistance);
                colorSet.add(totalRGB);

                turnsHeight++;
              }

              turnsWidth++;
            }

            //Gets the Arrays for Training:
            Double distDataSet[] = distSet.toArray(new Double[distSet.size()]);
            Double colorDataSet[] = colorSet.toArray(new Double[colorSet.size()]);

            //Removes the Data Outliers:
            Trainer.removeOutliers(distDataSet, colorDataSet);

            //Trains the Vector Data Set:
            String identifier = "Vector" + turns;
            Trainer.findBoxValues(distDataSet, colorDataSet, padding, identifier);

            //Clears the Data Sets for Next Iteration:
            distSet.clear();
            colorSet.clear();

            turns++;
          }

      }

      else {
          //Error Debugs:
          System.err.println("Image Trimming Input Error!");
      }
  }

  /* AUTHENTICATION METHODS */

  //Authenticates Image:
  public static boolean authenticateImage(int rgbValues[][], double sideTrim,
    double vertTrim, double systemResize, double authPercent, int vectorGrid) throws Exception {
    //Main Boolean:
    boolean authentication = false; //Default Value

    //Checks the Case:
    if (sideTrim <= 0.4 && sideTrim >= 0 && vertTrim <= 0.4 && vertTrim >= 0 && vectorGrid != 0 &&
      authPercent <= 1) {
        //Trim Variable:
        int trimX = (int)(rgbValues.length*sideTrim);
        int trimWidth = (int)(rgbValues.length - (rgbValues.length*(sideTrim*2)));

        //Other Variables:
        int trimY = (int)(rgbValues[0].length*vertTrim);
        int trimHeight = (int)(rgbValues[0].length - (rgbValues[0].length*(vertTrim*2)));

        //Creates the Vector Grid:
        int gridValues[][] = getGrid(trimWidth, trimHeight, vectorGrid);

        //Loop Variables:
        int turns = 0;
        int vectorCalcWidth = trimWidth/vectorGrid, vectorCalcHeight = trimHeight/vectorGrid;
        int authCount = 0, vectorCount = 0;

        //Loops through Array:
        mainLoop: while (turns < gridValues[0].length) {
            //Loop Variable:
            int turnsWidth = 0;
            secondLoop: while (turnsWidth < vectorCalcWidth
                && turnsWidth < trimWidth) {
                //Loop Variable:
                int turnsHeight = 0;
                thirdLoop: while (turnsHeight < vectorCalcHeight
                    && turnsHeight < trimHeight) {
                    //Color Conversion:
                    int rgb[] = Capture.convertRGB(rgbValues[gridValues[0][turns] + turnsWidth]
                        [gridValues[1][turns] + turnsHeight]);

                    //Calculates RGB Adjustment for Brightness and Adjusts:
                    int avgBrightness = (int)(HandleData.openData(brightnessID));
                    int currPixelBrightness = ImageProcessing.getPixelLighting(rgb);
                    //rgb = ImageProcessing.setLightingAdjustment(rgb, avgBrightness, currPixelBrightness);

                    //Calculates the Average RGB Value (Greyscale):
                    double totalRGB = ImageProcessing.grayscale(rgb);

                    //Calculates the Average RGB Distance (Greyscale):
                    double totalDistance = ImageProcessing.averageDistance(rgb);

                    //Checks the Pixel in Data Set:
                    String identifier = "Vector" + turns;
                    boolean isInside = Neuron.getClass(totalDistance, totalRGB, identifier);

                    //Adds the Data For Count:
                    if (isInside) {
                        authCount++;
                    }

                    //Checks for the Authentication:
                    if (authCount >= ((vectorCalcWidth*vectorCalcHeight)*authPercent)) {
                        //Resets the Count:
                        authCount = 0;

                        //Adds to the Vector Count:
                        vectorCount++;

                        //Breaks the Loop:
                        break secondLoop;
                    }

                    else if (turnsWidth >= (vectorCalcWidth*authPercent)
                        && turnsHeight >= (vectorCalcHeight*authPercent)) {
                        //Breaks the Loop:
                        break secondLoop;
                    }

                    turnsHeight++;
                }

                turnsWidth++;
            }

            //Checks for Complete Vector Authentication:
            if (vectorCount >= ((vectorGrid*vectorGrid)*authPercent)) {
                //Sets the Authentication:
                authentication = true;

                //Breaks the Loop:
                break mainLoop;
            }

            else if (turns >= (gridValues[0].length*authPercent)) {
                //Breaks the Loop:
                break mainLoop;
            }
        }
    }

    //Returns the Authentication:
    return authentication;
  }
}