package org.firstinspires.ftc.teamcode.drive.competitioncode;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

import org.firstinspires.ftc.teamcode.drive.competitioncode.cam;

@Autonomous(name = "RedPark", group = "Concept")
public class RedPark extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null; //wheel
    private DcMotor rightFront = null; //wheel
    private DcMotor leftRear = null; //wheel
    private DcMotor rightRear = null; //wheel
    private DcMotor intake_wheels = null;
    private DcMotor elbow = null;
    private Servo claw = null;
    private Servo wrist = null;


    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(12, -65, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        //Control Hub (driving controls)
        //leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        //rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        //leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        //rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        //Expansion Hub
        intake_wheels = hardwareMap.get(DcMotor.class, "intake_wheels");
        elbow = hardwareMap.get(DcMotor.class, "elbow");
        claw = hardwareMap.get(Servo.class, "claw");
        wrist = hardwareMap.get(Servo.class,"wrist");

        cam Cam = new cam(hardwareMap, telemetry);

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        TrajectorySequence trajSeq1Middle = drive.trajectorySequenceBuilder(startPose)
                .forward(34)
                .waitSeconds(1)
                .build();

        TrajectorySequence trajSeq1Left = drive.trajectorySequenceBuilder(startPose)
                .forward(28)
                .turn(Math.toRadians(55))
                .waitSeconds(1)
                .build();

        TrajectorySequence trajSeq1Right = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(25)
                .forward(28)
                .waitSeconds(1)
                .build();

        TrajectorySequence trajSeq2Right = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(40, -34, Math.toRadians(180)), 180)
                .build();

        TrajectorySequence trajSeq2Left = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(40, -28, Math.toRadians(180)), 180)
                .build();

        TrajectorySequence trajSeq2Middle = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(39, -22, Math.toRadians(180)), 180)
                .build();

        TrajectorySequence trajSeqResetRight = drive.trajectorySequenceBuilder(startPose)
                .turn(Math.toRadians(30))
                .back(30)
                .build();

        TrajectorySequence trajSeqResetMiddle = drive.trajectorySequenceBuilder(startPose)
                .back(30)
                .build();

        elbow.setPower(0.3);

        //elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        //Cam.initTfod();

        while (opModeIsActive()) {

            //Cam.telemetryTfod();

            drive.followTrajectorySequence(trajSeq1Right);
            intake_wheels.setPower(-0.5);
            sleep(2000);
            intake_wheels.setPower(0);
            drive.followTrajectorySequence(trajSeqResetMiddle);
            drive.followTrajectorySequence(trajSeq2Middle);

            elbow.setTargetPosition(elbow.getCurrentPosition()+675);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setPower(0.4);

            sleep(1000);
            claw.setPosition(0);
            sleep(1000);
            claw.setPosition(1);
            sleep(200);
            claw.setPosition(0);
            sleep(1000);
            elbow.setPower(-0.5);
            sleep(1000);
            elbow.setPower(0);
            sleep(30000);


/*
                if (Cam.side == cam.Side.Right){

                    drive.followTrajectorySequence(trajSeq1Right);
                    intake_wheels.setPower(-0.75);
                    sleep(2000);
                    intake_wheels.setPower(0);
                    drive.followTrajectorySequence(trajSeq2Right);
                    elbow.setTargetPosition(800);
                    elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    claw.setPosition(1);
                    elbow.setTargetPosition(0);
                    drive.followTrajectorySequence(trajSeq3);
                }
                else if (Cam.side == cam.Side.Middle){

                    drive.followTrajectorySequence(trajSeq1Middle);
                    intake_wheels.setPower(-0.75);
                    sleep(2000);
                    intake_wheels.setPower(0);
                    drive.followTrajectorySequence(trajSeq2Middle);
                    elbow.setTargetPosition(800);
                    elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    claw.setPosition(1);
                    elbow.setTargetPosition(0);
                    drive.followTrajectorySequence(trajSeq3);
                }
                else if (Cam.side == cam.Side.Left){

                    drive.followTrajectorySequence(trajSeq1Left);
                    intake_wheels.setPower(-0.75);
                    sleep(2000);
                    intake_wheels.setPower(0);
                    drive.followTrajectorySequence(trajSeq2Left);
                    elbow.setTargetPosition(800);
                    elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    claw.setPosition(1);
                    elbow.setTargetPosition(0);
                    drive.followTrajectorySequence(trajSeq3);
                }
*/
        }   // end runOpMode()

    }   // end class
}