package org.usfirst.frc.team3735.robot.commands.sequences;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExp;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeLiftDown;
import org.usfirst.frc.team3735.robot.commands.vision.DriveAddVisionAssist;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAcquireGear extends CommandGroup {

    private static final double WIDTH_THRESH = 175;

	public DriveAcquireGear() {
    	addParallel(new DriveAddVisionAssist(Pipes.Gear));
    	addSequential(new GearIntakeLiftDown());
    	addSequential(new ExpDrive(.7,0));
  
    }

	@Override
	protected boolean isFinished() {
		return super.isFinished() || 
				Robot.vision.getWidth() > WIDTH_THRESH ||
				Robot.oi.isOverriddenByDrive();
	}

	@Override
	protected void end() {
		Robot.gearIntake.liftUp();
	}

	@Override
	protected void interrupted() {
		end();
	}
    
    
}

