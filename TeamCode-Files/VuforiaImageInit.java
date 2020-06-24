package org.firstinspires.ftc.teamcode.Autonomous.Detectors;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import lib.Analyze;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class VuforiaImageInit extends Analyze {
  //Hardware Map Object:
  HardwareMap hardwareMap;

  /*
   * PRESET DETECTOR VALUES:
   *
   * Black: 0, 0, 0
   *
   */

  /* VUFORIA PIECES */

  //Vuforia Elements:
  VuforiaLocalizer vuforia;
  private static final String VUFORIA_KEY =
      "--YOUR VUFORIA KEY--";
  private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

  //Constructor:
  public VuforiaImageInit() {
    super();
  }

  /* VUFORIA METHODS */

  //Initialize Vuforia:
  public void initVuforia(HardwareMap hwMap, int[] detectorRGBArray,
    String detectorName, int zoomValue, boolean flash) {
    //Declares Hardware Map:
    hardwareMap = hwMap;

    //Inits the Vuforia Localizer:
    int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

    //Sets the Vuforia Elements:
    parameters.vuforiaLicenseKey = VUFORIA_KEY;
    parameters.cameraDirection = CAMERA_CHOICE;

    //Gets the Vuforia engine:
    vuforia = ClassFactory.getInstance().createVuforia(parameters);
    Vuforia.setFrameFormat(PIXEL_FORMAT.RGB888, true);

    //Flash and Zoom for Detection:
    setFlash(flash);
    setZoom(zoomValue);

    //Inits the Custom Detector:
    try {
      initDetector(detectorName, detectorRGBArray[0], detectorRGBArray[1], detectorRGBArray[2]);
    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Disables Vuforia After Tracking:
  public void disableVuforia() {
    //Turns off the Flash:
    com.vuforia.CameraDevice.getInstance().setFlashTorchMode(false);

    //Deactivates Vuforia Engine:
    vuforia = null;
  }

  //Sets the Zooming (If Needed):
  public void setZoom(int zoomValue) {
    //Converts the Zoom Factor to String:
    String zoomSetting = Integer.toString(zoomValue);

    //Sets the Zoom Settings:
    com.vuforia.CameraDevice.getInstance().setField("opti-zoom", "opti-zoom-on");
    com.vuforia.CameraDevice.getInstance().setField("zoom", zoomSetting);
  }

  //Sets the Flash (If Needed):
  public void setFlash(boolean flash) {
    //Sets the Flash On:
    com.vuforia.CameraDevice.getInstance().setFlashTorchMode(flash);
  }

  /* DETECTOR METHODS */

  //RGB Comparison Method:
  public int[] getAverageRGBValues(int singleRGBValues[][]) {
    //Main Array:
    int localAverage[] = new int[3];

    try {
      //Computes the Average:
      localAverage = averageRGBValues(singleRGBValues);
    }

    catch (Exception e) {
      e.printStackTrace();
    }

    //Returns RGB Average:
    return localAverage;
  }

  //Detection Boolean Method:
  public boolean detectObject(int[][] rgbValues, int[] lightingMargin, int pixelCount) {
    //Main Boolean:
    boolean mainBool = false; //Default Value

    try {
      //Finds the Value Number of Pixels and Sets Boolean:
      int binaryValues[][] = binaryDetector(rgbValues, lightingMargin);
      mainBool = getBodyBoolean(binaryValues, pixelCount);
    }

    catch (Exception e) {
      e.printStackTrace();
    }

    //Returns the Found Boolean:
    return mainBool;
  }

  //Detection Blob Method:
  public int detectBlobs(int[][] rgbValues, int[] lightingMargin, int distanceThreshold) {
    //Main Blob Count (w/ Default):
    int mainCount = 0;

    try {
      //Finds the Value Number of Pixels and Sets Boolean:
      int binaryValues[][] = binaryDetector(rgbValues, lightingMargin);
      mainCount = getBodyBlobs(binaryValues, distanceThreshold);
    }

    catch (Exception e) {
      e.printStackTrace();
    }

    //Returns the Blob Count:
    return mainCount;
  }

  /* CAPTURE METHODS */

  //Vuforia Capture Image Method:
  public Bitmap getImage(double resizedRatio) {
    //PARAMS: Resized Ratio cannot be Zero!!!
    Image rgbImage;
    Bitmap bitmapImage = null;

    //Converts Frame to BitMap of RGB Format:
    try {
      VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
      long numImages = frame.getNumImages();

      formatLoop:
      for (int i = 0; i < numImages; i++) {
        if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB888) {
          //Creates Bitmap Image:
          rgbImage = frame.getImage(i);
          bitmapImage = Bitmap.createBitmap(rgbImage.getWidth(), rgbImage.getHeight(), Bitmap.Config.RGB_565);
          bitmapImage.copyPixelsFromBuffer(rgbImage.getPixels());

          //Gets Values for Bitmap Resizing:
          double currentWidth = bitmapImage.getWidth();
          double currentHeight = bitmapImage.getHeight();

          int newWidth = (int) (currentWidth * resizedRatio);
          int newHeight = (int) (currentHeight * resizedRatio);

          //Resizing Bitmap:
          bitmapImage = getResizedBitmap(bitmapImage, newWidth, newHeight);

          break formatLoop;
        }
      }
    }

    catch (Exception e) {
      e.printStackTrace();
    }

    //Returns Image:
    return bitmapImage;
  }

  /* RGB METHODS */

  //Gets the Array of RGB Values from an Image:
  public int[][] getRGBArray(Bitmap image, int startX, int startY, int width, int height) {
    //Main RGB Array (w/ Default):
    int[][] rgbValues = new int[width][height];

    if (image != null) {
      //Gets RGB Values Array from Bitmap:
      int turnsWidth = startX;

      while (turnsWidth < width + startX) {
        int turnsHeight = startY;

        while (startY < height + startY) {
          rgbValues[turnsWidth - startX][turnsHeight - startY] = image.getPixel(turnsWidth, turnsHeight);

          turnsHeight++;
        }

        turnsWidth++;
      }
    }

    //Returns the Obtained RGB Array:
    return rgbValues;
  }

  //Resizing the Bitmap:
  public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
    //Resize Variables:
    int width = bm.getWidth();
    int height = bm.getHeight();

    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;

    //Create a Matrix for Manipulation:
    Matrix matrix = new Matrix();

    //Resize BitMap:
    matrix.postScale(scaleWidth, scaleHeight);

    //Re-render the BitMap:
    Bitmap resizedBitmap = Bitmap.createBitmap(
        bm, 0, 0, width, height, matrix, false);
    bm.recycle();

    //Return Bitmap:
    return resizedBitmap;
  }
}
