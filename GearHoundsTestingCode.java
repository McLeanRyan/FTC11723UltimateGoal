package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp //@TeleOp shows that this code is being run during the 2 minute driver controlled period of the game. You would change this to be @Autonomous if this was in the 30 second autonomous, or preprogrammed period
public class GearHoundsTestingCode extends OpMode { //extends OpMode tells the program to continuously repeat the code until a driver presses stop. To make the code run through once, change it to say extends LinearOpMode

    private DcMotor leftFront = null; //This is where we declare the variables for the motors and servos used on the robot
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;
    private DcMotor flywheelLeft = null;
    private DcMotor flywheelRight = null;
    private DcMotor conveyor = null;
    private Servo flipLeft = null;
    private Servo flipRight = null;

    @Override
    public void init() { //This method is the code that runs in the initialization phase, before the drivers press play
        leftFront = hardwareMap.dcMotor.get("leftFront"); //These lines of code, in conjunction with the robot configuration done on the drivers station phone tell the robot where to find different hardware devices
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        flywheelLeft = hardwareMap.dcMotor.get("flywheelLeft");
        flywheelRight = hardwareMap.dcMotor.get("flywheelRight");
        conveyor = hardwareMap.dcMotor.get("conveyor");
        flipLeft = hardwareMap.servo.get("flipLeft");
        flipRight = hardwareMap.servo.get("flipRight");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("flipLeft Pos:", flipLeft.getPosition()); //Telemetry is a specialized print function that is used to print messages on to the drivers station phone for the drivers to see
        telemetry.addData("flipRight Pos:", flipRight.getPosition());

        telemetry.addLine("Initialization Complete");
        telemetry.addLine("Robot Ready");
        telemetry.addLine("When you're ready, press play to start driving.");
    }

    @Override
    public void loop() { //This method contains the code that runs after the drivers have pressed play. This is where you will keep all of your controls.
        double px = gamepad1.left_stick_x; //This
        if (Math.abs(px) < 0.05) px = 0;
        double py = -gamepad1.left_stick_y;
        if (Math.abs(py) < 0.05) py = 0;
        double pa = -(gamepad1.right_stick_x*(.70));
        if (Math.abs(pa) < 0.05) pa = 0;
        double plf = -px + py - pa;
        double plb = px + py + -pa;
        double prf = -px + py + pa;
        double prb = px + py + pa;
        double max = Math.max(1.0, Math.abs(plf));
        max = Math.max(max, Math.abs(plb));
        max = Math.max(max, Math.abs(prf));
        max = Math.max(max, Math.abs(prb));
        plf /= max;
        plb /= max;
        prf /= max;
        prb /= max;
        leftFront.setPower(plf);
        leftBack.setPower(plb);
        rightFront.setPower(prf);
        rightBack.setPower(prb);
        while (gamepad1.right_trigger > 0.2)  { //This loop controls the strafe controls of the robot. Motors can accept a decimal power value of -1 to 1. 1 is full speed forward and -1 is full speed backwards
            leftFront.setPower(1);
            leftBack.setPower(-1);
            rightFront.setPower(-1);
            leftBack.setPower(1);
        }

        while (gamepad1.left_trigger > 0.2)   {
            leftFront.setPower(-1);
            leftBack.setPower(1);
            rightFront.setPower(1);
            leftBack.setPower(-1);
        }

        while (gamepad1.right_bumper)  { //This loop strafes at half speed
            leftFront.setPower(0.5);
            leftBack.setPower(-0.5);
            rightFront.setPower(-0.5);
            leftBack.setPower(0.5);
        }

        while (gamepad1.left_bumper)       {
            leftFront.setPower(-0.5);
            leftBack.setPower(0.5);
            rightFront.setPower(0.5);
            leftBack.setPower(-0.5);
        }

        if (gamepad2.right_bumper) {
            flywheelLeft.setPower(1);
            flywheelRight.setPower(1);
        }else {
            flywheelLeft.setPower(0);
            flywheelRight.setPower(0);
        }

        if (gamepad2.left_bumper) {
            conveyor.setPower(0.75);
        } else {
            conveyor.setPower(0);
        }

        /*if (gamepad2.a) {
        }
        */
        if (gamepad2.b) {
         flipLeft.setPosition(0); //Servos don't take a power input, but instead you set the position you want them to move to as a decimal value.
         flipLeft.setPosition(0);
        }
    }
}