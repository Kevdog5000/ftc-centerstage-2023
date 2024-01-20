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
    private Auto auto = new Auto(telemetry);

    @Override
    public void runOpMode() {
        auto.init(hardwareMap);

        waitForStart();

        auto.readSleeve();

        if (auto.sleeveSide == CamDetector.Side.FIRST) {
        }


    }   // end class
}