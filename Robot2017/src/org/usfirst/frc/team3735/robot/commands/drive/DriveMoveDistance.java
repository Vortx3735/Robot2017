package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMoveDistance extends Command {
	
	private double deltaDistance;
	private double startDistanceLeft;
	private double startDistanceRight;
	private double endPositionLeft;
	private double endPositionRight;
	
	private double timeOnTarget = 0;
	private double finishTime = Constants.Drive.driveFinishTime;
	
//	private static double P = 1;
//	private static double I = 0;
//	private static double D = 0;
//	private static double F = 0;

    public DriveMoveDistance(double distance){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.deltaDistance = distance;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	startDistanceLeft = Robot.drive.getPositionLeft();
    	endPositionLeft = startDistanceLeft + deltaDistance;
    	startDistanceRight = Robot.drive.getPositionRight();
    	endPositionRight = startDistanceRight + deltaDistance;
    	
    	Robot.drive.setupDriveForDistance();
    	Robot.drive.setControlMode(TalonControlMode.Position);
    	Robot.drive.setLeftRight(endPositionLeft, endPositionRight);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(isOnTarget()){
    		timeOnTarget += .02;
    	}else{
    		timeOnTarget = 0;
    	}
    }
    
    private boolean isOnTarget(){
    	return 	VortxMath.isWithinThreshold(Robot.drive.getInchesPositionLeft(),
										   	endPositionLeft,
										   	Constants.Drive.driveTolerance) &&
    			VortxMath.isWithinThreshold(Robot.drive.getInchesPositionRight(),
						   				   	endPositionRight,
						   				   	Constants.Drive.driveTolerance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return timeOnTarget >= finishTime;

    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.tankDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}

