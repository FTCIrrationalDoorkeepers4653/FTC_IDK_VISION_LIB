package org.firstinspires.ftc.teamcode.Autonomous.Extra;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.Detectors.VuforiaImageInit;
import org.firstinspires.ftc.teamcode.Autonomous.IDKAutonomousRobot;

@Autonomous(name="Custom_Vuforia_TEST")
//@Disabled
public class CustomVuforia_Test extends LinearOpMode {
    //Objects:
    IDKAutonomousRobot autoRobot = new IDKAutonomousRobot("None");
    VuforiaImageInit imageInit = new VuforiaImageInit();

    //Variables (w/ Default Values):
    boolean leftSide = false, rightSide = false;
    int objectPosition = 2;

    //Skystone Detector Settings (w/ Default Values):
    int skystoneDetector[] = { 0, 0, 0 };
    String detectorName = "Skystone Detector";
    boolean turnOnFlash = true;
    int zoomInit = 30; //Use If Needed

    //Runtime Object:
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Hardware INITS:
        autoRobot.init(hardwareMap);
        imageInit.initVuforia(hardwareMap, skystoneDetector, detectorName, turnOnFlash);

        //Zoom INIT (If Needed):
        imageInit.setZoom(zoomInit);

        waitForStart();
        runtime.reset();

        //Pipeline Runs (Blue Alliance, Reverse Values for Red Alliance):

        /* Two Objects Pipeline:
        leftSide = sampleLeftPipeline();
        rightSide = sampleRightPipeline();

        //Checking Case:
        if (leftSide == true && rightSide == false) {
          objectPosition = 1;
        }

        else if (leftSide == false && rightSide == true) {
          objectPosition = 2;
        }

        else if (leftSide == false && rightSide == false) {
          objectPosition = 3;
        }

        else {
          objectPosition = 2; //Default Value
        } */

        //Three Object Detection Pipeline:
        objectPosition = compareLightValues();

        //Disables Vuforia:
        imageInit.disableVuforia();

        //Telemetry Data:
        telemetry.addData(detectorName + " Classification: ", objectPosition);
        telemetry.update();
        sleep(10000);
    }

    //Sample Detection Pipelines (For Moto G5 Camera [1280x720 resized 128x72]):

    //Two Object Detection Pipelines:
    public boolean sampleLeftPipeline() {
      int leftRGBArray[][] = imageInit.getRGBArray(0.1, 12, 20, 40, 32);
      boolean foundLeft = imageInit.detectObject(leftRGBArray, 25, 10);

      return foundLeft;
    }

    public boolean sampleRightPipeline() {
      int rightRGBArray[][] = imageInit.getRGBArray(0.1, 76, 20, 40, 32);
      boolean foundRight = imageInit.detectObject(rightRGBArray, 25, 10);

      return foundRight;
    }

    //Three Object Detection Pipeline:
    public int compareLightValues() {
      int position = 2; //Default Value (1-Bridge, 2-Center, 3-Wall)

      //Finds RGB Arrays for All Positions:
      int leftRGBArray[][] =  imageInit.getRGBArray(0.1, 5, 25, 32, 22);
      int centerRGBArray[][] = imageInit.getRGBArray(0.1, 47, 25, 32, 22);
      int rightRGBArray[][] = imageInit.getRGBArray(0.1, 89, 25, 32, 22);

      //Finds Average RGB Values (0-Darker -> 765-Lighter):
      int leftStandardRGB[] = imageInit.getAverageRGBValues(leftRGBArray);
      int centerStandardRGB[] = imageInit.getAverageRGBValues(centerRGBArray);
      int rightStandardRGB[] = imageInit.getAverageRGBValues(rightRGBArray);

      int leftRGB = leftStandardRGB[0] + leftStandardRGB[1] + leftStandardRGB[2];
      int centerRGB = centerStandardRGB[0] + centerStandardRGB[1] + centerStandardRGB[2];
      int rightRGB = rightStandardRGB[0] + rightStandardRGB[1] + rightStandardRGB[2];

      //Checks for the Darkest Value:
      if (leftRGB < rightRGB && leftRGB < centerRGB) {
        position = 1;
      }

      else if (centerRGB < leftRGB && centerRGB < rightRGB) {
        position = 2;
      }

      else if (rightRGB < leftRGB && rightRGB < centerRGB) {
        position = 3;
      }

      else {
        position = 2;
      }

      //Returns Position:
      return position;
    }
}

