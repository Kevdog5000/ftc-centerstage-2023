package org.firstinspires.ftc.teamcode.drive.competitioncode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "BlueLeft", group = "Concept")
public class BlueLeft extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null; //wheel
    private DcMotor rightFront = null; //wheel
    private DcMotor leftRear = null; //wheel
    private DcMotor rightRear = null; //wheel
    private DcMotor intake_wheels = null;
    private DcMotor elbow = null;
    private Servo claw = null;
    private Servo wrist = null;

    private Auto auto = new Auto(telemetry);

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(12, 65, Math.toRadians(-90));
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


        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        TrajectorySequence trajSeq1Middle = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(20)
                .forward(36)
                .waitSeconds(1)
                .build();

        TrajectorySequence trajSeq1Right = drive.trajectorySequenceBuilder(startPose)
        //        .forward(28)
        //        .turn(Math.toRadians(55))
        //        .waitSeconds(1)
        //        .build();
                .strafeLeft(10)
                .forward(30)
                .turn(Math.toRadians(-55))
                .forward(7)
                .build();

        TrajectorySequence trajSeq1Left = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(30)
                .forward(28)
                .waitSeconds(1)
                .build();

        TrajectorySequence trajSeqResetRight = drive.trajectorySequenceBuilder(trajSeq1Right.end())
                .back(15)
                .build();

        TrajectorySequence trajSeqResetMiddle = drive.trajectorySequenceBuilder(startPose)
                .back(30)
                .build();

        TrajectorySequence trajSeqResetLeft = drive.trajectorySequenceBuilder(trajSeq1Left.end())
                .back(30)
                .build();

        TrajectorySequence trajSeq2Right = drive.trajectorySequenceBuilder(trajSeqResetRight.end())
                .splineToSplineHeading(new Pose2d(50, 19, Math.toRadians(180)), 180)
                .build();

        TrajectorySequence Right3 = drive.trajectorySequenceBuilder(trajSeq2Right.end())
                .back(15)
                .build();

        TrajectorySequence trajSeq2Left = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(30, 34, Math.toRadians(180)), 180)
                .build();

        TrajectorySequence Left3 = drive.trajectorySequenceBuilder(trajSeq2Left.end())
                .back(15)
                .build();

        TrajectorySequence trajSeq2Middle = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(40, 29, Math.toRadians(180)), 180)
                .build();

        TrajectorySequence Middle3 = drive.trajectorySequenceBuilder(trajSeq2Middle.end())
                .back(10)
                .build();

        elbow.setPower(0.3);

        //elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        auto.init(hardwareMap);
        waitForStart();
        auto.readSleeve();

        if (auto.sleeveSide == CamDetector.Side.FIRST){
            drive.followTrajectorySequence(trajSeq1Left);
            intake_wheels.setPower(-0.5);
            sleep(2000);
            intake_wheels.setPower(0);
            drive.followTrajectorySequence(trajSeqResetLeft);
            drive.followTrajectorySequence(trajSeq2Left);

            intake_wheels.setPower(0.3);
            elbow.setTargetPosition(elbow.getCurrentPosition()+615);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setPower(0.36);

            sleep(3000);

            intake_wheels.setPower(0);
            drive.followTrajectorySequence(Left3);

            claw.setPosition(0);
            sleep(1000);
            claw.setPosition(1);
            sleep(200);
            claw.setPosition(0);
            sleep(1000);
            elbow.setPower(-0.5);
            sleep(1000);
            elbow.setPower(0);
            sleep(1000);

            elbow.setTargetPosition(elbow.getCurrentPosition() - 600);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setPower(-0.4);
            sleep(3000);
            elbow.setPower(0);
            sleep(30000);
        }
        else if (auto.sleeveSide == CamDetector.Side.SECOND){
            drive.followTrajectorySequence(trajSeq1Middle);
            intake_wheels.setPower(-0.5);
            sleep(2000);
            intake_wheels.setPower(0);
            drive.followTrajectorySequence(trajSeqResetMiddle);
            drive.followTrajectorySequence(trajSeq2Middle);

            elbow.setTargetPosition(elbow.getCurrentPosition()+615);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setPower(0.36);

            sleep(2000);

            drive.followTrajectorySequence(Middle3);

            claw.setPosition(0);
            sleep(1000);
            claw.setPosition(1);
            sleep(200);
            claw.setPosition(0);
            sleep(1000);
            elbow.setPower(-0.5);
            sleep(1000);
            elbow.setPower(0);
            sleep(1000);

            elbow.setTargetPosition(elbow.getCurrentPosition() - 600);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setPower(-0.4);
            sleep(3000);
            elbow.setPower(0);
            sleep(30000);
        }
        else if (auto.sleeveSide == CamDetector.Side.THIRD){

            drive.followTrajectorySequence(trajSeq1Right);
            intake_wheels.setPower(-0.5);
            sleep(2000);
            intake_wheels.setPower(0);
            drive.followTrajectorySequence(trajSeqResetRight);
            drive.followTrajectorySequence(trajSeq2Right);

            elbow.setTargetPosition(elbow.getCurrentPosition()+615);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setPower(0.36);

            drive.followTrajectorySequence(Right3);

            sleep(1000);
            claw.setPosition(0);
            sleep(1000);
            claw.setPosition(1);
            sleep(200);
            claw.setPosition(0);
            sleep(1000);
            elbow.setPower(-0.4);
            sleep(1000);
            elbow.setPower(0);
            sleep(1000);

            elbow.setTargetPosition(elbow.getCurrentPosition() - 600);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setPower(-0.6);
            sleep(3000);
            elbow.setPower(0);
            sleep(30000);
        }



        }   // end runOpMode()

    }   // end class
