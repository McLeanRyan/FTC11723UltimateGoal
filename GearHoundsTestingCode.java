package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class GearHoundsTestingCode extends OpMode {
    private DcMotor leftFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;
    private DcMotor flywheelLeft = null;
    private DcMotor flywheelRight = null;
    private DcMotor conveyor = null;
    private Servo flipLeft = null;
    private Servo flipRight = null;

    @Override
    public void init() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
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

        telemetry.addData("flipLeft Pos:", flipLeft.getPosition());
        telemetry.addData("flipRight Pos:", flipRight.getPosition());

        telemetry.addLine("Initialization Complete");
        telemetry.addLine("Press Play to Start");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("flipLeft Pos:", flipLeft.getPosition());
        telemetry.addData("flipRight Pos:", flipRight.getPosition());
        telemetry.update();

        double px = gamepad1.left_stick_x;
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
        while (gamepad1.right_trigger > 0.2)    {
            leftFront.setPower(1);
            leftBack.setPower(-1);
            rightFront.setPower(-1);
            leftBack.setPower(1);
        }

        while (gamepad1.left_trigger > 0.2)       {
            leftFront.setPower(-1);
            leftBack.setPower(1);
            rightFront.setPower(1);
            leftBack.setPower(-1);
        }

        while (gamepad1.right_bumper)    {
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
        }else {
            conveyor.setPower(0);
        }

        /*if (gamepad2.a) {
            flipLeft.setPosition(0.4);
            flipRight.setPosition(0.4);
        }*/

        if (gamepad2.b) {
            flipLeft.setPosition(0);
            flipRight.setPosition(0);
        }
    }
}
