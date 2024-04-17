package org.firstinspires.ftc.teamcode.drive.competitioncode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "CamTest", group = "Concept")
public class CamTest extends LinearOpMode {
    // Needs telemetry to shows the coverages
    private Auto auto = new Auto(telemetry);

    @Override
    public void runOpMode() {
        // need to start the camera and takes about 5 seconds to turn on
        auto.init(hardwareMap);

        waitForStart();
        // main function that does all the camera code
        auto.readSleeve();
        // if code and do stuff
        if (auto.sleeveSide == CamDetector.Side.FIRST) {
            // do stuff
        } else if (auto.sleeveSide == CamDetector.Side.SECOND){
         // do stuff
        } else if (auto.sleeveSide == CamDetector.Side.THIRD){
            // do stuff
        }else{
            // you don't really need this but it would be nice to run something if you didn't detect anything
            // Worst thing to do is just sit there and do nothing
            // Camera code works 100% of the time unless camera got move and the boxes are looking at the wrong thing
        }
        sleep (30000);

    }   // end class
}