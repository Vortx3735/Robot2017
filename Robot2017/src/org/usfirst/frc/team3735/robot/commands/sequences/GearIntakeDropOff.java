package org.usfirst.frc.team3735.robot.commands.sequences;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.assists.NavxAssist;
import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveExp;

import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeLiftDown;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeLiftUp;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeRollersOff;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeRollersOut;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeDropOff extends CommandGroup {
	
    public GearIntakeDropOff() {
//    	addSequential(new ExpDrive(.5,0),.1);
//    	addParallel(new GearIntakeRollersOut(),Constants.GearIntake.dropOffTotalTime);
//    	addSequential(new Wait(Constants.GearIntake.dropOffRollDelay));
//    	addSequential(new GearIntakeLiftDown());
//    	addParallel(new DriveMoveDistancePIDBroken(-37), 1);
//    	//addSequential(new ExpDrive(Constants.GearIntake.dropOffDrivePercent,0),Constants.GearIntake.dropOffDriveTime);
//    	addSequential(new Wait(Constants.GearIntake.dropOffJerkDelay));
//    	addSequential(new GearIntakeLiftUp());
//    	addSequential(new Wait(GearIntake.dropOffEndDelay));
//    	//addSequential(new GearIntakeRollersOff());
    	
    	//new code
    	addSequential(new DriveExp(.2,0),.1);
    	
    	addParallel(new GearIntakeRollersOut());
    	addSequential(new Wait(.1));
    	
    	addSequential(new GearIntakeLiftDown());
    	addSequential(new Wait(.1));

    	//addSequential(new DriveMoveDistanceExpNavx(-20, .6, null, .8),2);
    	addSequential(new DriveExp(-.7,0,.4,.4).addA(new NavxAssist()), 1.2);
    	//addSequential(new DriveExp(-.8,0),.7);
    	addSequential(new GearIntakeLiftUp());
    	addSequential(new GearIntakeRollersOff());
    	
    	
    }

	@Override
	protected boolean isFinished() {
		return super.isFinished() || Robot.oi.isOverriddenByDrive();
	}

	@Override
	protected void end() {
		Robot.gearIntake.liftUp();
		Robot.gearIntake.setRollerSpeed(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
    
    
}
