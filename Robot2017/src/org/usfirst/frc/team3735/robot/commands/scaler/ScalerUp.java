package org.usfirst.frc.team3735.robot.commands.scaler;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ScalerUp extends Command {

    public ScalerUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.scaler.setCurrent(Constants.Scaler.upCurrent);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.scaler.setCurrent(0);
    	System.out.println("This is the Scaler End method");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.scaler.setCurrent(0);
    	System.out.println("This is the Scaler Interrupted method");
    }
}
