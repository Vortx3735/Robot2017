package org.usfirst.frc.team3735.robot.commands.scaler;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ScalerUp extends Command {

    private double speed;

	public ScalerUp(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.speed = speed;
    	requires(Robot.scaler);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.scaler.setCurrent(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.scaler.setCurrent(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.scaler.getOverloaded();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.scaler.setCurrent(0);
    	System.out.println("This is the Scaler End method");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.scaler.setPercent(0);
    	System.out.println("This is the Scaler Interrupted method");
    	end();
    }
}
