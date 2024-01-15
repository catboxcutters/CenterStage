package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

@Config
public class Extender {
    public DcMotor extenderLeft;
    public DcMotor extenderRight;
    public static int extenderinit = -10;
    public static int extenderfar = 1100;
    public MotorConfigurationType mct1, mct2;


    public Extender(OpMode opMode)
    {
        extenderLeft = opMode.hardwareMap.get(DcMotor.class, "extenderLeft");
        extenderLeft.setDirection(DcMotor.Direction.REVERSE);
        extenderLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extenderLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        //overclock
//        mct1 = extenderLeft.getMotorType().clone();
//        mct1.setAchieveableMaxRPMFraction(1.0);
//        extenderLeft.setMotorType(mct1);
//        //end overclock
        extenderLeft.setTargetPosition(extenderLeft.getCurrentPosition());
        extenderLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extenderLeft.setPower(1);

        extenderRight = opMode.hardwareMap.get(DcMotor.class, "extenderRight");
        extenderRight.setDirection(DcMotor.Direction.FORWARD);
        extenderRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extenderRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        //overclock
//        mct2 = extenderRight.getMotorType().clone();
//        mct2.setAchieveableMaxRPMFraction(1.0);
//        extenderRight.setMotorType(mct2);
//        //end overclock
        extenderRight.setTargetPosition(extenderRight.getCurrentPosition());
        extenderRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extenderRight.setPower(1);
    }
    public void extINIT()
    {
        setExtenderState(ExtState.INIT);
    }
    public enum ExtState{
        INIT,
        FAR,
        CURRENT;
    }
    public void setExtenderState(Extender.ExtState state){
        switch(state) {
            case INIT:
                extenderLeft.setTargetPosition(extenderinit);
                extenderRight.setTargetPosition(extenderinit);
                break;
            case FAR:
                extenderLeft.setTargetPosition(extenderfar);
                extenderRight.setTargetPosition(extenderfar);
                break;
            case CURRENT:
                extenderLeft.setTargetPosition(extenderLeft.getCurrentPosition());
                extenderRight.setTargetPosition(extenderRight.getCurrentPosition());
                break;
        }
    }
}
