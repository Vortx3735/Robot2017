package org.usfirst.frc.team3735.robot.commands.shooter;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSmartDashboard extends Command {

	double speed;
	double voltage;
	String key = "Shooter setVoltage";
	String akey = "Agitator setVoltage";
    public ShooterSmartDashboard() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    	SmartDashboard.putNumber(key, Constants.Shooter.shootVoltage);
    	SmartDashboard.putNumber(akey, Constants.Shooter.agitatorVoltage);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = SmartDashboard.getNumber(key, 0);
    	Robot.shooter.setSpeed(speed);
    	voltage = SmartDashboard.getNumber(akey, 0);
    	Robot.shooter.setAgitatorVoltage(voltage);
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
