package org.usfirst.frc.team3735.robot.commands.drive.simple;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.calc.VortxMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveBrakeUntilStopped extends CommandGroup {

    protected static final double SPEED_THRESH = .4;

	public DriveBrakeUntilStopped() {
        addSequential(new DriveBrake(){
        	@Override
        	public boolean isFinished(){
        		return super.isFinished() || 
    				(Math.abs(Robot.drive.getLeftSpeed()) < SPEED_THRESH &&
    				 Math.abs(Robot.drive.getRightSpeed()) < SPEED_THRESH);
    				
        	}
        });
    }
}
