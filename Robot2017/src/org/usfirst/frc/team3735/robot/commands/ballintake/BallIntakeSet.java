package org.usfirst.frc.team3735.robot.commands.ballintake;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BallIntakeSet extends Command {

	Double speed;
	
    public BallIntakeSet(double spd) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ballIntake);
    	speed = spd;
    }
    
    public BallIntakeSet() {
    	requires(Robot.ballIntake);
    	speed = null;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(speed == null) {
        	Robot.ballIntake.setRollerSmartDashboard();
    	}else {
    		Robot.ballIntake.setRollerCurrent(speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ballIntake.setRollerCurrent(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
