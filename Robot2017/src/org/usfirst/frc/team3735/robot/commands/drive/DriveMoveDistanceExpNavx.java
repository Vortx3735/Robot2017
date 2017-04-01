package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.Setting;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMoveDistanceExpNavx extends Command {

    private static final double K_FILTERCOEF_Y = 0.15;
	private double deltaDistance;

   	
	private double moveStick;
		
	private double moveMotor;
	
	private double moveMotorPrevious;
	private double turnCorrection;
	
	private Setting coefficient;
	
	private double startDistanceLeft;
	private double endPositionRight;
	private double endPositionLeft;
	private double startDistanceRight;
	
	private double timeOnTarget = 0;
	private double finishTime = .2;
	private double power;
   	
    public DriveMoveDistanceExpNavx(double distance, double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.power = power;
    	deltaDistance = distance;
    	
    	requires(Robot.drive);
    	coefficient = new Setting("Navx Drive Coeffecient", 1);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	moveStick = power;
     	startDistanceLeft = Robot.drive.getLeftPositionInches();
    	startDistanceRight = Robot.drive.getRightPositionInches();
    	endPositionLeft = startDistanceLeft + deltaDistance;
    	endPositionRight = startDistanceRight + deltaDistance;
    	
    	Robot.drive.setSetpoint(Robot.drive.getYaw());
    	
    	Robot.drive.setUpDriveForSpeedControl();
    	
		moveMotor			= 0.0;
		moveMotorPrevious = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	turnCorrection = (Robot.drive.getPIDController().getError()/180.0) * coefficient.getValue();
	
		moveMotor = (moveStick-moveMotorPrevious)*K_FILTERCOEF_Y + moveMotorPrevious;
		//turnMotor = (ZDriveStick-ZDriveMotorPrevious)*K_FILTERCOEF_Z + ZDriveMotorPrevious;


		moveMotorPrevious = moveMotor; 
					
		moveMotor = moveMotor * Math.pow(Math.abs(moveMotor), Constants.Drive.moveExponent - 1);
		//turnMotor = turnMotor * Math.pow(Math.abs(turnMotor), Constants.Drive.turnExponent - 1);
		//turnMotor = turnMotor * Constants.Drive.scaledMaxTurn;
		Robot.drive.arcadeDrive(moveMotor, turnCorrection, false);	
		//log();
		
		if(isOnTarget()){
    		timeOnTarget += .02;
    	}else{
    		timeOnTarget = 0;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return timeOnTarget >= finishTime;
    }
    
    private boolean isOnTarget(){
    	return 	VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(),
										   	endPositionLeft,
										   	Constants.Drive.driveTolerance) ||
    			VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(),
						   				   	endPositionRight,
						   				   	Constants.Drive.driveTolerance);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
