package org.usfirst.frc.team3735.robot.commands.sequences;

import org.usfirst.frc.team3735.robot.Robot;

import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeLiftDown;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DrivePlaceGear extends CommandGroup {


	public DrivePlaceGear() {
    	//addSequential(new DriveMoveDistanceExpVisionBumped(100, .7, Pipes.Peg));
    	addSequential(new GearIntakeDropOff());
    }

	@Override
	protected boolean isFinished() {
		return super.isFinished() || 
				Robot.oi.isOverriddenByDrive();
	}


    
    
}

