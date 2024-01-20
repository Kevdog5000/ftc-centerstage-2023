package org.firstinspires.ftc.teamcode.drive.competitioncode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "BlueNoPark", group = "Concept")
public class BlueNoPark extends LinearOpMode {
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
        Pose2d startPose = new Pose2d(-12, 65, Math.toRadians(-90));
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

        TrajectorySequence trajSeq1Right = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(25)
                .forward(28)
                .waitSeconds(1)
                .build();

        TrajectorySequence trajSeq2 = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(23)
                .forward(11)
                .strafeLeft(72)
                .strafeLeft(45)
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
            drive.followTrajectorySequence(trajSeq2);
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