package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RecordAverageRate extends Command {

	private static final double endTime = 1;
	private double sum;
	private double numValues;
	private double timer;
	private boolean done;
    public RecordAverageRate() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	sum = 0;
    	numValues = 0;
    	timer = 0;
    	done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	timer+=.02;
    	if((timer) > endTime){
    		done = true;
    	}
    	sum += Robot.navigation.getRate();
    	numValues++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.print("Average Rate: " + sum/numValues);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
