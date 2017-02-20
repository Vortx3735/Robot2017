package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveChangeScaledMaxOutput extends Command {
	
	String key = "Drive Scaled Max Output";
    public DriveChangeScaledMaxOutput() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	SmartDashboard.putNumber(key, .5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.changeScaledMaxOutput(SmartDashboard.getNumber(key, .5));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.changeScaledMaxOutput(SmartDashboard.getNumber(key, .5));
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
    	Robot.drive.changeScaledMaxOutput(Constants.Drive.scaledMaxOutput);
    }
}
