package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class LiftOuttake {
    public DcMotor lift;
    public Servo forbarLift;
    public Servo OuttakeRotate;
    public Servo OuttakeVertical;
    public Servo OuttakeHorizontal;
    public Servo OuttakeBrake;

    public static double OuttakeRotateHorizontal=0.3;
    public static double OuttakeRotateVertical=0;
    public static double OuttakeRotateInit=0.115;
    public static double HZ_IN=0;
    public static double HZ_OUT=1;
    public static double VR_IN=0.5;
    public static double VR_OUT=0;
    public static double forbarLiftIn=0;
    public static double forbarLiftOut=0.5;
    public static int liftinit = 0;
    public static int liftlow = 450; //300 test cu 400 rava suge
    public static int liftmid = 700; //600
    public static int lifthigh = 900; //900


    public LiftOuttake(LinearOpMode opMode) {
        forbarLift = opMode.hardwareMap.get(Servo.class, "forbarLift");

        OuttakeRotate = opMode.hardwareMap.get(Servo.class, "outtakerotate");
        OuttakeBrake = opMode.hardwareMap.get(Servo.class, "outtakebrake");
        OuttakeHorizontal = opMode.hardwareMap.get(Servo.class, "outtakehorizontal");
        OuttakeVertical = opMode.hardwareMap.get(Servo.class, "outtakevertical");

        lift = opMode.hardwareMap.get(DcMotor.class, "lift");
        lift.setDirection(DcMotor.Direction.FORWARD);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(lift.getCurrentPosition());
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);

    }
    public void liftINIT()
    {
        setLiftState(LiftState.INIT);
        forbarLift.setPosition(forbarLiftIn);
        OuttakeRotate.setPosition(OuttakeRotateInit);
        OuttakeVertical.setPosition(VR_IN);
        OuttakeHorizontal.setPosition(0);
        OuttakeBrake.setPosition(0);
    }
    public void setLiftState(LiftOuttake.LiftState state){
        switch (state) {
            case INIT:
                lift.setTargetPosition(liftinit);
                break;
            case LOW:
                lift.setTargetPosition(liftlow);
                break;
            case MID:
                lift.setTargetPosition(liftmid);
                break;
            case HIGH:
                lift.setTargetPosition(lifthigh);
                break;
            case CURRENT:
                lift.setTargetPosition(lift.getCurrentPosition());
                break;
        }

    }
    public void setForbarOut(){forbarLift.setPosition(forbarLiftOut); setInVertical();}
    public void setForbarIn(){forbarLift.setPosition(forbarLiftIn);}
    public void setPower(double power) {
        lift.setPower(power);
    }
    public void setForbarLiftTargetPosition(double position) {
        forbarLift.setPosition(position);
    }
    public void setOutHorizontal(){OuttakeHorizontal.setPosition(HZ_OUT);}
    public void setInHorizontal(){OuttakeHorizontal.setPosition(HZ_IN);}
    public void setOutVertical(){OuttakeVertical.setPosition(VR_OUT);}
    public void setInVertical(){OuttakeVertical.setPosition(VR_IN);}
    public void toggleVertical(){
        if(OuttakeVertical.getPosition()>VR_OUT+0.1) setOutVertical();
        else setInVertical();
    }


    public void setOuttakeRotateVertical(){
        OuttakeVertical.setPosition(OuttakeRotateVertical);
    }
    public void setOuttakeRotateHorizontal(){
        OuttakeVertical.setPosition(OuttakeRotateHorizontal);
    }
    private void wait(int ms) {

        try{
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            //nu face nimic
        }
    }
    public void setLiftTargetPosition(int ticks) {
        lift.setTargetPosition(ticks);
    }
    public void LiftManualStep(int liftUpStep){
        lift.setTargetPosition(lift.getTargetPosition()+liftUpStep);
        wait(10);
    }

    public enum LiftState{
        INIT,
        LOW,
        MID,
        HIGH,
        CURRENT;
    }
}
