package org.firstinspires.ftc.teamcode.Autonomous.Extra;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.Movement.IDKAutonomousRobot;

//@Autonomous(name="Custom_Vuforia_TEST")
@TeleOp(name="Custom_Vuforia_TEST")
//@Disabled
public class CustomVuforia_Test extends LinearOpMode {
    //Objects:
    IDKAutonomousRobot autoRobot = new IDKAutonomousRobot();

    //Variables (w/ Default Values):
    int twoObjectPosition = 2;
    int threeObjectPosition = 2;
    String startSide = "Blue";

    //Runtime Object:
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Hardware INITS:
        autoRobot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        //Loop for Detector:
        while (!isStopRequested()) {
            //Pipeline Runs (Blue Alliance, Reverse Values for Red Alliance):

            //Two Object Pipeline:
            twoObjectPosition = autoRobot.twoObjectPipeline(startSide);

            //Three Object Detection Pipeline:
            threeObjectPosition = autoRobot.threeObjectPipeline(startSide);

            //Telemetry Data:
            telemetry.addData(autoRobot.detectorName + " Two Object Classification: ", twoObjectPosition);
            telemetry.addData(autoRobot.detectorName + " Three Object Classification: ", threeObjectPosition);
            telemetry.update();
        }

        //Stops OpMode:
        autoRobot.stopAuto();
    }
}

