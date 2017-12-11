package org.usfirst.frc.team3735.robot.commands.sequences;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;

import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeLiftDown;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveGoToPeg extends CommandGroup {


	public DriveGoToPeg() {
    	addSequential(new TurnTo(Pipes.Peg));
  //  	addSequential(new DriveMoveDistanceExpVisionBumped(100, .7, Pipes.Peg));
    }

	@Override
	protected boolean isFinished() {
		return super.isFinished() || Robot.oi.isOverriddenByDrive();
	}


    
    
}

