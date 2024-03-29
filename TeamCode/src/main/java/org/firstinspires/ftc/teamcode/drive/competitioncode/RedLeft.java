package org.firstinspires.ftc.teamcode.drive.competitioncode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "RedLeft", group = "Concept")
public class RedLeft extends LinearOpMode {
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


        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        TrajectorySequence trajSeq1Middle = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(20)
                .forward(37)
                .waitSeconds(1)
                .build();

        TrajectorySequence trajSeq2Middle = drive.trajectorySequenceBuilder(trajSeq1Middle.end())
                .strafeLeft(25)
                .forward(29)
                .turn(Math.toRadians(-90))
                .forward(115)
                .build();

        TrajectorySequence trajSeq1Left = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(31)
                .forward(25)
                .build();

        TrajectorySequence trajSeq2Left = drive.trajectorySequenceBuilder(trajSeq1Left.end())
                .strafeRight(23)
                .forward(38)
                .turn(Math.toRadians(-90))
                .forward(95)
                .build();

        TrajectorySequence trajSeq1Right = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(10)
                .forward(30)
                .turn(Math.toRadians(-55))
                .forward(5)
                .build();

        TrajectorySequence trajSeq2Right = drive.trajectorySequenceBuilder(trajSeq1Right.end())
                .back(5)
                .turn(Math.toRadians(55))
                .strafeLeft(10)
                .forward(34)
                .turn(Math.toRadians(-90))
                .forward(100)
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

                    drive.followTrajectorySequence(trajSeq2Left);
                    sleep(30000);
                }
                else if (auto.sleeveSide == CamDetector.Side.SECOND){
                    drive.followTrajectorySequence(trajSeq1Middle);
                    intake_wheels.setPower(-0.5);
                    sleep(2000);
                    intake_wheels.setPower(0);

                    drive.followTrajectorySequence(trajSeq2Middle);
                    sleep(30000);
                }
                else if (auto.sleeveSide == CamDetector.Side.THIRD){

                    drive.followTrajectorySequence(trajSeq1Right);
                    intake_wheels.setPower(-0.5);
                    sleep(2000);
                    intake_wheels.setPower(0);

                    drive.followTrajectorySequence(trajSeq2Right);
                    sleep(30000);
                }

        }   // end runOpMode()

    }   // end class
