package org.firstinspires.ftc.teamcode.teleops;


import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Controller;
import org.firstinspires.ftc.teamcode.subsystems.Extender;
import org.firstinspires.ftc.teamcode.subsystems.LiftOuttake;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@Config
@TeleOp(name = "TeleOp", group = "Linear Opmode")
//s0Ft ArabEsc
public class Teleop extends LinearOpMode {

    SampleMecanumDrive drive;
    boolean TUBING_STATE=false;
    boolean FORBAR_STATE=false;
    boolean EXT_STATE=false;
    boolean LIFT_STATE=false;
    boolean IDLE=false;
    boolean ACTIVE=true;
    boolean back, front = true;
    int BEAM_COUNTER = 0;
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    static float speed = 1f;
    private void wait(int ms) {

        try{
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            //nu face nimic
        }
    }
    @Override
    public void runOpMode() {

        EXT_STATE=IDLE;
        LIFT_STATE=IDLE;
        FORBAR_STATE=IDLE;
        Controller controller = new Controller(this);
        drive = new SampleMecanumDrive(hardwareMap);
        controller.INIT();
        waitForStart();

        //threaded drive
        new Thread(() -> {
            while (opModeIsActive() && !isStopRequested()) {
                drive.setWeightedDrivePower(
                        new Pose2d(
                                gamepad1.left_stick_y * speed,
                                gamepad1.right_trigger - gamepad1.left_trigger,
//                                -gamepad1.left_stick_x * speed,
                                gamepad1.right_stick_x * speed
                        )
                );
                drive.update();
            }
        }).start();
        while (opModeIsActive() && !isStopRequested()) {
            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);
            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            //extender+tubing+feed on return activate
            if(currentGamepad1.right_bumper && !previousGamepad1.right_bumper)
            {
                if(EXT_STATE==IDLE)
                {
                    EXT_STATE=ACTIVE;
                    TUBING_STATE=ACTIVE;
                    controller.setExtender(Extender.ExtState.FAR);
                    controller.intake.activateTubingAndForbarDown();
                    wait(500);
                }
                else
                {
                    EXT_STATE=IDLE;
                    TUBING_STATE=IDLE;
                    controller.setExtender(Extender.ExtState.INIT);
                    controller.storageSystem.flipSequence();
                    controller.lift.setLiftState(LiftOuttake.LiftState.INIT);
                    wait(1500);
                    controller.intake.deactivateTubingAndForbarUp();
                    controller.feedIntoOuttake();
                }
            }

            //toggle tubing
            if(currentGamepad2.dpad_left && !previousGamepad2.dpad_left)
            {
                if(TUBING_STATE==IDLE)
                {
                    TUBING_STATE=ACTIVE;
                    controller.intake.activateTubingAndForbarDown();
                }
                else
                {
                    TUBING_STATE=IDLE;
                    controller.intake.deactivateTubingAndForbarUp();
                }
            }

            //toggle tubing reverse
            if(currentGamepad2.dpad_right && !previousGamepad2.dpad_right)
            {
                if(TUBING_STATE==IDLE)
                {
                    TUBING_STATE=ACTIVE;
                    controller.intake.activateTubingReverseAndForbarDown();
                }
                else
                {
                    TUBING_STATE=IDLE;
                    controller.intake.deactivateTubingAndForbarUp();
                }
            }


            //flip transfer
            if(currentGamepad2.left_trigger>0 && previousGamepad2.left_trigger==0)
            {
                controller.storageSystem.flipSequence();
                controller.lift.setLiftState(LiftOuttake.LiftState.INIT);
                controller.lift.setForbarIn();
            }

            //manual feed to outtake
            if(currentGamepad2.right_trigger>0 && previousGamepad2.right_trigger==0)
            {
                controller.feedIntoOuttake();
            }

            //reset transfer
            if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper)
            {
                controller.storageSystem.storageINIT();
            }

            //lift high
            if(currentGamepad2.y && !previousGamepad2.y)
            {
                if(LIFT_STATE==IDLE)
                {
                    LIFT_STATE=ACTIVE;
                    FORBAR_STATE=ACTIVE;
                    controller.lift.setLiftState(LiftOuttake.LiftState.HIGH);
                    controller.lift.setForbarOut();
                }
                else
                {
                    LIFT_STATE=IDLE;
                    FORBAR_STATE=IDLE;
                    controller.lift.setLiftState(LiftOuttake.LiftState.INIT);
                    controller.lift.setForbarIn();
                }
            }

            //lift mid
            if(currentGamepad2.x && !previousGamepad2.x)
            {
                if(LIFT_STATE==IDLE)
                {
                    LIFT_STATE=ACTIVE;
                    FORBAR_STATE=ACTIVE;
                    controller.lift.setLiftState(LiftOuttake.LiftState.MID);
                    controller.lift.setForbarOut();
                }
                else
                {
                    LIFT_STATE=IDLE;
                    FORBAR_STATE=IDLE;
                    controller.lift.setLiftState(LiftOuttake.LiftState.INIT);
                    controller.lift.setForbarIn();

                }
            }

            //lift low
            if(currentGamepad2.a && !previousGamepad2.a) {
                if (LIFT_STATE == IDLE) {
                    LIFT_STATE = ACTIVE;
                    FORBAR_STATE = ACTIVE;
                    controller.lift.setLiftState(LiftOuttake.LiftState.LOW);
                    controller.lift.setForbarOut();
                } else {
                    LIFT_STATE = IDLE;
                    FORBAR_STATE = IDLE;
                    controller.lift.setLiftState(LiftOuttake.LiftState.INIT);
                    controller.lift.setForbarIn();
                }
            }
            //outtake control
            if(currentGamepad2.b && !previousGamepad2.b && FORBAR_STATE)
            {
                controller.lift.toggleVertical();
                if(LIFT_STATE == ACTIVE) controller.storageSystem.storageINIT();
            }

            //lift manual
            if(currentGamepad2.dpad_up)
            {
                controller.lift.LiftManualStep(10);
            }
            else if(currentGamepad2.dpad_down)
            {
                controller.lift.LiftManualStep(-10);
            }


            front= controller.storageSystem.beamFront.getState();
            back= controller.storageSystem.beamBack.getState();
            if(TUBING_STATE==ACTIVE && BEAM_COUNTER>300)
            {
               // controller.flipSequence();
            }

            //breakBeams both active loop counter
            if(!front)
            {
                if (!back) {
                    BEAM_COUNTER++;
                }
            }
            else
            {
                BEAM_COUNTER=0;
            }
            telemetry.addData("BEAM_FRONT", front);
            telemetry.addData("BEAM_BACK", back);
            telemetry.addData("BEAM_COUNTER", BEAM_COUNTER);
            telemetry.update();
        }
    }
}
