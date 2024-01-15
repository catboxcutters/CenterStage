package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "BreakBeamTest", group = "Linear Opmode")
//s0Ft ArabEsc
public class BreakBeamTest extends LinearOpMode {
    // SampleMecanumDrive drive;
    DigitalChannel sensor;

    public void runOpMode() {
        sensor = hardwareMap.digitalChannel.get("beamfront");
        waitForStart();
        while(opModeIsActive() && !isStopRequested()) {
            telemetry.addData("time", "elapsed time: " + Double.toString(this.time));
            telemetry.addData("state", ":  " + sensor.getState());
            telemetry.update();
        }
    }

}