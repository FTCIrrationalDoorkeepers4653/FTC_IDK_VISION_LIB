package org.firstinspires.ftc.teamcode.Autonomous.Detectors;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;
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

  /* VUFORIA PIECES!!! */

    //Vuforia Elements:
    VuforiaLocalizer vuforia;
    private static final String VUFORIA_KEY =
            "AR7KPuz/////AAABmSKvAg58mkBSqvvfxvaYqxMN8S2CvbOIzcpLyLVqb9hLPXQf3hPCERtF9azaj5sBUezFRBqdVA53ZAsNmlWW/ThqkaHtmpKNqXneP6p8VhN4liG3ofA7Cidx234PKNIhalLvby0jdmuxT5Uhh4dJjST6taoZGArAQz7Df8hzPG26Nd92L1ATW3mO4qzNAny2UK5YrzG92bUIxqvpDLkjeq8UNTLHYD4ulI1i+Jl/dPzU2PdeNPEqlsykdshGvcuRWRz8qeMXfpKVZ9TXmLxqvuTe6K291gxuKtfWXJ11rYJHTJlUAvooMpPaAh2/isv6LUy83+3UhIyl1kNxaNeMHK52iqEjpswOiOmVkniWTblp";
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

  /* Constructor */
  public VuforiaImageInit() {
    super();
  }

  /* METHODS!!! */

  //Initialize Vuforia:
  public void initVuforia(HardwareMap hwMap, int[] detectorRGBArray, String detectorName, boolean flash) {
      //PARAMS: MUST BE HARDWARE MAP OBJECT, RGB ARRAY OF 3, STRING NAME FOR DETECTOR

      //Declares Hardware Map:
      hardwareMap = hwMap;

      /* Initaites Vuforia: */
      int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
      VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

      //Sets the Vuforia Elements:
      parameters.vuforiaLicenseKey = VUFORIA_KEY;
      parameters.cameraDirection   = CAMERA_CHOICE;

      //Instantiates the Vuforia engine:
      vuforia = ClassFactory.getInstance().createVuforia(parameters);
      Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

      //Turns on the Flash for Detection:
      com.vuforia.CameraDevice.getInstance().setFlashTorchMode(flash);

      /* INITS the Custom Detector */
      try {
          setDetectorRGB(detectorRGBArray[0], detectorRGBArray[1], detectorRGBArray[2]);
          setDetectorName(detectorName);
      }

      catch (Exception e) {
          e.printStackTrace();
      }
  }

  //Disables Vuforia After Tracking:
  public void  disableVuforia() {
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

  //Detection Method:
  public boolean detectObject(int[][] rgbValues, int lightingMargin, int pixelCount) {
    //Main Boolean:
    boolean mainBool = false; //Default Value

    try {
      //Finds the Value Number of Pixels and Sets Boolean:
      int binaryValues[][] = boxDetector(rgbValues, lightingMargin);
      mainBool = getBodyBoolean(binaryValues, pixelCount);
    }

    catch (Exception e) {
      e.printStackTrace();
    }

    //Returns the Found Boolean:
    return mainBool;
  }

  //Vuforia Capture Image Method:
    public int[][] getRGBArray(Double resizedRatio, int startX, int startY, int width, int height) {
      //PARAMS: RESIZED RATIO CANNOT BE ZERO, THE STARTX AND STARTY + THE WIDTH AND HEIGHT MUST BE LESS THAN OR EQUAL TO IMAGE WIDTH AND HEIGHT (Usually Starts at 1280x720)!!!
        Image rgbImage;
        Bitmap bitmapImage;
        int rgbValues[][] = new int[width][height];

        //Converts Frame to BitMa of RGB Format:
        try {
            VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
            long numImages = frame.getNumImages();

            formatLoop: for (int i = 0; i < numImages; i++) {
                if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                    //Creates Bitmap Image:
                    rgbImage = frame.getImage(i);
                    bitmapImage = Bitmap.createBitmap(rgbImage.getWidth(), rgbImage.getHeight(), Bitmap.Config.RGB_565);
                    bitmapImage.copyPixelsFromBuffer(rgbImage.getPixels());

                    //Gets Values for Bitmap Resizing:
                    double currentWidth = bitmapImage.getWidth();
                    double currentHeight = bitmapImage.getHeight();

                    int newWidth = (int)(currentWidth*resizedRatio);
                    int newHeight = (int)(currentHeight*resizedRatio);

                    //Resizing Bitmap:
                    bitmapImage = getResizedBitmap(bitmapImage, newWidth, newHeight);

                    //Gets RGB Values Array from Bitmap:
                    int turnsWidth = startX;

                    while (turnsWidth < width+startX) {
                        int turnsHeight = startY;
                        while (startY < height+startY) {
                            rgbValues[turnsWidth-startX][turnsHeight-startY] = bitmapImage.getPixel(turnsWidth, turnsHeight);

                            turnsHeight++;
                        }

                        turnsWidth++;
                    }

                    //Breaks the for loop:
                    break formatLoop;
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
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
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
