package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnToSmartDashboardAngle extends Command {
	
	private double setAngle;
	private String key = "Angle Setpoint (-180, 180)";
	
    public DriveTurnToSmartDashboardAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	SmartDashboard.putNumber(key, 0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//  FIXME  	setAngle = Robot.drive.getYaw();
//    	Robot.drive.setSetpoint(setAngle);
//    	Robot.drive.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	setAngle = SmartDashboard.getNumber(key, 0);
// FIXME     	Robot.drive.setSetpoint(setAngle);
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
 //FIXME   	Robot.drive.disable();
    }
}
