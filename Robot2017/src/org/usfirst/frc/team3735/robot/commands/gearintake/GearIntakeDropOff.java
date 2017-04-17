package org.usfirst.frc.team3735.robot.commands.gearintake;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Constants.GearIntake;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExp;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.util.Setting;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeDropOff extends CommandGroup {
	public static final double dropOffRollDelay = .5;
	public static final double dropOffJerkDelay = 1;
	public static final double dropOffEndDelay = .5;
	public static final double dropOffDriveTime = .6;
	public static final double dropOffTotalTime = 
		dropOffRollDelay + dropOffJerkDelay + dropOffEndDelay + dropOffDriveTime;
	public static final double dropOffDrivePercent = -.8;
	

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
    	addSequential(new ExpDrive(.5,0),.1);
    	
    	addParallel(new GearIntakeRollersOut());
    	addSequential(new Wait(.1));
    	
    	addSequential(new GearIntakeLiftDown());
    	addSequential(new Wait(.1));
    	
    	addSequential(new DriveMoveDistanceExpNavx(-20, .6, null, .8),1);
    	
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
		Robot.gearIntake.setRollerVoltage(0);
	}

	@Override
	protected void interrupted() {
		end();
	}
    
    
}
