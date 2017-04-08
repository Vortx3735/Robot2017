package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

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
public class DriveMoveDistanceMotionMagic extends Command {
	
	private double deltaDistance;
	private double startDistanceLeft;
	private double startDistanceRight;
	private double endPositionLeft;
	private double endPositionRight;
	
	private double timeOnTarget = 0;
	private double finishTime = Constants.Drive.driveFinishTime;
	
	private double p = .025;
	private double i = 0;
	private double d = 0;
	private double f = 0;

    public DriveMoveDistanceMotionMagic(double distance){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.deltaDistance = distance;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	startDistanceLeft = Robot.drive.getLeftPositionInches();
    	startDistanceRight = Robot.drive.getRightPositionInches();
    	endPositionLeft = startDistanceLeft + deltaDistance;
    	endPositionRight = startDistanceRight + deltaDistance;
    	
    	Robot.drive.setupDriveForPositionControl();
    	//Robot.drive.setPIDSettings(0.1,0.00015,0);
    	Robot.drive.setPIDSettings(p,i,d);
    	//Robot.drive.setLeftRightDistance(endPositionLeft, endPositionRight);
    	
    	timeOnTarget = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.drive.setLeftRightDistance(endPositionLeft, endPositionRight);
    	if(isOnTarget()){
    		timeOnTarget += .02;
    	}else{
    		timeOnTarget = 0;
    	}
    }
    
    private boolean isOnTarget(){
    	return 	VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(),
										   	endPositionLeft,
										   	Constants.Drive.driveTolerance) &&
    			VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(),
						   				   	endPositionRight,
						   				   	Constants.Drive.driveTolerance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return timeOnTarget >= finishTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setUpDriveForSpeedControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}

