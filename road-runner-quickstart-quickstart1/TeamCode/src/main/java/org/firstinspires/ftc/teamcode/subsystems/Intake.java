package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

@Config
public class Intake{
    DcMotor tubing;
    static Servo forbarIntake;
    public static double forbarIntakeInitTeleop=0.9f;
    public static double forbarIntakeInitAuto=0.2f;
    public static double forbarIntakeCollecting=0.85f;
    public MotorConfigurationType mct1;
    private static boolean active=false;
    public static double TubingActive=1, TubingInActive=0;
    public Intake(LinearOpMode opMode) {
        tubing = opMode.hardwareMap.get(DcMotor.class, "tubing");
        //overclock
        mct1 = tubing.getMotorType().clone();
        mct1.setAchieveableMaxRPMFraction(1.0);
        tubing.setMotorType(mct1);
        //end overclock
        tubing.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        forbarIntake = opMode.hardwareMap.get(Servo.class, "forbarIntake");
    }
    public void intakeINIT()
    {
        tubing.setDirection(DcMotorSimple.Direction.FORWARD);
        forbarIntake.setPosition(forbarIntakeInitTeleop);
    }
    public void activateTubingAndForbarDown()
    {
        tubing.setDirection(DcMotorSimple.Direction.FORWARD);
        tubing.setPower(TubingActive);
        putForbarIntakeDown();
        active=true;
    }
    public void deactivateTubingAndForbarUp()
    {
        tubing.setPower(TubingInActive);
        putForbarIntakeUp();
        active=false;
    }
    public void activateTubingReverseAndForbarDown()
    {
        tubing.setDirection(DcMotorSimple.Direction.REVERSE);
        tubing.setPower(TubingActive);
        putForbarIntakeDown();
        active=true;
    }
    public void activateTubing()
    {
        tubing.setDirection(DcMotorSimple.Direction.FORWARD);
        tubing.setPower(TubingActive);
        active=true;
    }
    public void deactivateTubing()
    {
        tubing.setPower(TubingInActive);
        active=false;
    }
    public static void putForbarIntakeDown(){
        forbarIntake.setPosition(forbarIntakeCollecting);
    }
    public static void putForbarIntakeUp(){
        forbarIntake.setPosition(forbarIntakeInitTeleop);
    }
    public static void putForbarInitAuto(){
        forbarIntake.setPosition(forbarIntakeInitAuto);
    }
    public static void setForbarIntake(double pos){
        forbarIntake.setPosition(pos);
    }
}
