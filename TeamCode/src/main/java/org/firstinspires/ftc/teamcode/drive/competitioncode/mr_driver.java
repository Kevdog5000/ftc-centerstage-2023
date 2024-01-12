package org.firstinspires.ftc.teamcode.drive.competitioncode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="mr_driver", group="Linear Opmode")
//@Disabled
public class mr_driver extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null; //wheel
    private DcMotor rightFront = null; //wheel
    private DcMotor leftRear = null; //wheel
    private DcMotor rightRear = null; //wheel
    private DcMotor intake_wheels = null;
    private DcMotor elbow = null;
    private Servo claw = null;
    private DcMotor hang = null;
    private Servo airplane = null;
    private Servo wrist = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //names wheels for the phones
        //Control Hub (driving controls)
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        //Expansion Hub
        intake_wheels = hardwareMap.get(DcMotor.class, "intake_wheels");
        elbow = hardwareMap.get(DcMotor.class, "elbow");
        claw = hardwareMap.get(Servo.class, "claw");
        hang = hardwareMap.get(DcMotor.class, "hang");
        airplane = hardwareMap.get(Servo.class, "airplane");
        wrist = hardwareMap.get(Servo.class,"wrist");

        //elbow.setDirection(DcMotorSimple.Direction.REVERSE);

        float elbowPos = (float)elbow.getCurrentPosition();
        float elbowSensitivity = 1f;
        elbow.setPower(0.7);
        elbow.setTargetPosition((int)elbowPos);
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        String driveMode = "normal";
        boolean isModeSwitched = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        runtime.reset();

        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
            rightRear.setDirection(DcMotorSimple.Direction.REVERSE);


            // Setup a variable for each drive wheel to save power level for telemetry
            double r = Math.hypot(gamepad1.left_stick_y, -gamepad1.left_stick_x);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            double v1 = r * Math.cos(robotAngle);
            double v2 = r * Math.sin(robotAngle);
            double v3 = r * Math.sin(robotAngle);
            double v4 = r * Math.cos(robotAngle);

            //speed control
            double speedcontrol;
            if (gamepad1.right_trigger>0) {
                speedcontrol = 2.5;
            } else{
                speedcontrol = 1;
            }

            //applying variables
            leftFront.setPower((-v1 + gamepad1.right_stick_x) / speedcontrol);
            leftRear.setPower((-v2 + gamepad1.right_stick_x) / speedcontrol);
            rightFront.setPower((-v3 - gamepad1.right_stick_x) / speedcontrol);
            rightRear.setPower((-v4 - gamepad1.right_stick_x) / speedcontrol);


            // setting buttons
            // intake
            if (gamepad2.y) {
                intake_wheels.setPower(.5);
            }
            else if (gamepad2.x) {
                intake_wheels.setPower(-0.5);
            }
            else if (gamepad2.right_bumper){
                intake_wheels.setPower(0.3);
            }
            else {
                intake_wheels.setPower(0);
            }

            if (gamepad2.right_bumper) {
                elbowPos += elbowSensitivity;
            }
            else if (gamepad2.left_bumper){
                elbowPos -= elbowSensitivity;
            }
            elbow.setTargetPosition((int)elbowPos);

            telemetry.addData("target position",elbowPos );
            telemetry.addData("current position", elbow.getCurrentPosition());

            if (gamepad2.dpad_left) {
                claw.setPosition(1);
            }
            else if (gamepad2.dpad_right) {
                claw.setPosition(0);
            }

            if (gamepad2.dpad_up) {
                wrist.setPosition(1);
            }
            else if (gamepad2.dpad_down) {
                wrist.setPosition(0);
            }
            else {
                wrist.setPosition(0.48);
            }

            if (gamepad1.x) {
                airplane.setPosition(0);
            }

            if (gamepad1.dpad_up) {
                hang.setPower(1);
            }
            else if (gamepad1.dpad_down) {
                hang.setPower(-1);
            }
            else {
                hang.setPower(0);
            }

            if (gamepad2.a) {
                wrist.setPosition(0.1);
            }

            // testing the encoder
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

        }
    }
}