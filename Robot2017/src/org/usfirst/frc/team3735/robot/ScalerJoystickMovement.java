package org.usfirst.frc.team3735.robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ScalerJoystickMovement extends Command {

	boolean isDone = false;
    public ScalerJoystickMovement() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.scaler);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.scaler.getPower() < -300 || Robot.scaler.getPower() > 10){
    		Robot.scaler.setVoltage(0);
    		System.out.println("overload");
    		isDone = true;
    	}else if (isDone == false){
    		Robot.scaler.setVoltage(Robot.oi.getMainRightY());
    	}else if(Math.abs(Robot.scaler.getPower()) < .5 && isDone == true && Math.abs(Robot.oi.getMainLeftY()) < .015){
			isDone = false;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
	
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
