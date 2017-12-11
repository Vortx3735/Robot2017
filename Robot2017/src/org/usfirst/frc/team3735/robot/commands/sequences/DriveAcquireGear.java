package org.usfirst.frc.team3735.robot.commands.sequences;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.assists.NavxVisionAssist;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveExp;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeLiftDown;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAcquireGear extends CommandGroup {

    private static final double WIDTH_THRESH = 175;

	public DriveAcquireGear() {
    	addSequential(new GearIntakeLiftDown());
    	addSequential(new DriveExp(.7,0).addA(new NavxVisionAssist(Pipes.Gear)));
  
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

