package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.settings.Setting;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SendSDVoltage extends Command {

	private static Setting voltage;
    public SendSDVoltage() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	voltage = new Setting("Drive Voltage Input", 0);
    	
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.changeControlMode(TalonControlMode.Voltage);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.setLeftRight(voltage.getValue(), voltage.getValue());
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
