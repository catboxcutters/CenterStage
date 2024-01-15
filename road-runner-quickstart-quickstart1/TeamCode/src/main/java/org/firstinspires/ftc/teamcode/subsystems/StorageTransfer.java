package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class StorageTransfer {
    Servo TransferServoRight;
    Servo TransferServoLeft;
    Servo TransferForbar;
    public DigitalChannel beamFront;
    public DigitalChannel beamBack;
    public static double transferLeftInit = 0;
    public static double transferRightInit = 1;
    public static double transferLeftBrake = 0.1;
    public static double transferRightBrake = 0.9;
    public static double transferLeftFeed = 1;
    public static double transferRightFeed = 0;
    public static double transferForbarInit = 1;
    public static double transferForbarFeed = 0.294;
    private void wait(int ms) {

        try{
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            //nu face nimic
        }
    }
    public StorageTransfer(LinearOpMode opMode){
        beamFront = opMode.hardwareMap.get(DigitalChannel.class, "beamfront");
        beamBack = opMode.hardwareMap.get(DigitalChannel.class, "beamback");
        TransferServoRight = opMode.hardwareMap.get(Servo.class, "transferRight");
        TransferServoLeft = opMode.hardwareMap.get(Servo.class, "transferLeft");
        TransferForbar = opMode.hardwareMap.get(Servo.class, "forbarTransfer");
    }

    public void storageINIT(){
        TransferForbar.setPosition(transferForbarInit);
        TransferServoLeft.setPosition(transferLeftInit);
        TransferServoRight.setPosition(transferRightInit);
    }
    public void flipSequence(){
        TransferServoLeft.setPosition(transferLeftBrake);
        TransferServoRight.setPosition(transferRightBrake);
        TransferForbar.setPosition(transferForbarFeed);
    }
    public void feedSequence(){
        TransferServoLeft.setPosition(transferLeftFeed);
        TransferServoRight.setPosition(transferRightFeed);
        TransferForbar.setPosition(transferForbarFeed);
    }

}
