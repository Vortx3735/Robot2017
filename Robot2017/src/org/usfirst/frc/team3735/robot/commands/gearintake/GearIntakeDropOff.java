package org.usfirst.frc.team3735.robot.commands.gearintake;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Constants.GearIntake;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeDropOff extends CommandGroup {

    public GearIntakeDropOff() {
    	addSequential(new ExpDrive(.5,0),.1);
    	addParallel(new GearIntakeRollersOut(),Constants.GearIntake.dropOffTotalTime);
    	addSequential(new Wait(Constants.GearIntake.dropOffRollDelay));
    	addSequential(new GearIntakeLiftDown());
    	addParallel(new DriveMoveDistancePIDBroken(-37), 1);
    	//addSequential(new ExpDrive(Constants.GearIntake.dropOffDrivePercent,0),Constants.GearIntake.dropOffDriveTime);
    	addSequential(new Wait(Constants.GearIntake.dropOffJerkDelay));
    	addSequential(new GearIntakeLiftUp());
    	addSequential(new Wait(GearIntake.dropOffEndDelay));
    	//addSequential(new GearIntakeRollersOff());
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
    @Override
    public void execute(){
    	if(Robot.oi.isOverriddenByDrive()){
    		Robot.gearIntake.liftUp();
    		cancel();
    	}
    }
}
