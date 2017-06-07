package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.RollingAverage;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RecordVoltageData extends Command {

	private RollingAverage roll;
	
    public RecordVoltageData() {
        roll = new RollingAverage(3){
        	@Override
        	public double get(){
        		return Robot.drive.getAverageSpeed();
        	}
        };
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	roll.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	roll.compute();
    	SmartDashboard.putNumber("Average Speed", roll.getAverage());

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
