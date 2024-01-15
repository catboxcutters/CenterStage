package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Controller {
    public Extender extender;
    public StorageTransfer storageSystem;
    public Intake intake;
    public LiftOuttake lift;
    boolean TUBING_STATE=false;
    boolean FORBAR_STATE=false;
    boolean EXT_STATE=false;
    boolean LIFT_STATE=false;
    boolean IDLE=false;
    boolean ACTIVE=true;

    private void wait(int ms) {

        try{
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            //nu face nimic
        }
    }
    public Controller(LinearOpMode opMode){
        this.lift = new LiftOuttake(opMode);
        this.extender = new Extender(opMode);
        this.storageSystem = new StorageTransfer(opMode);
        this.intake = new Intake(opMode);
    }

    public void INIT()
    {
        extender.extINIT();
        intake.intakeINIT();
        lift.liftINIT();
        storageSystem.storageINIT();
    }
    public void feedIntoOuttake(){
        lift.setForbarIn();
        lift.setOutVertical();
        wait(200);
        storageSystem.feedSequence();
        wait(300);
        lift.setInVertical();
    }
    public void flipSequenceEXT(){
        storageSystem.flipSequence();
        setExtender(Extender.ExtState.INIT);
    }
    public void setExtender(Extender.ExtState state)
    {
        extender.setExtenderState(state);
    }
}
