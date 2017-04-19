package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.util.Setting;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMoveDistanceExpNavx extends Command {

    private double MOVE_FILTER = 0.15;	//(0,1]
	private static final double TOLERANCE = 2;		//in inches
	private static final double MOVE_EXPONENT = 3;
	private Double deltaDistance;   	
	private double moveStick;
	private double moveMotor;
	private double moveMotorPrevious;
	private double turnCorrection;
	private double startDistanceLeft;
	private double endPositionRight;
	private double endPositionLeft;
	private double startDistanceRight;
	private double power;
	private Double targetAngle = null;
   	
    public DriveMoveDistanceExpNavx(double distance, double power) {
    	this.power = Math.signum(distance) * Math.abs(power);
    	deltaDistance = distance;
    	requires(Robot.drive);
    	requires(Robot.navigation);
    }
    
    public DriveMoveDistanceExpNavx(Double distance, double power) {
    	this.power = power;
    	deltaDistance = distance;
    	requires(Robot.drive);
    	requires(Robot.navigation);
    }
    
    public DriveMoveDistanceExpNavx(double distance, double power, double angle) {
    	this.power = Math.signum(distance) * Math.abs(power);
    	deltaDistance = distance;
    	requires(Robot.drive);
    	targetAngle = angle;
    	requires(Robot.navigation);
    }
    
    public DriveMoveDistanceExpNavx(double distance, double power, Double angle, double filter) {
    	this.power = Math.signum(distance) * Math.abs(power);
    	deltaDistance = distance;
    	requires(Robot.drive);
    	MOVE_FILTER = filter;
    	targetAngle = angle;
    	requires(Robot.navigation);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	moveStick = Math.signum(deltaDistance) * Math.abs(power);

     	startDistanceLeft = Robot.drive.getLeftPositionInches();
    	startDistanceRight = Robot.drive.getRightPositionInches();
    	endPositionLeft = startDistanceLeft + deltaDistance;
    	endPositionRight = startDistanceRight + deltaDistance;
    	
    	if(targetAngle == null){
        	Robot.navigation.getController().setSetpoint(Robot.navigation.getYaw());
    	}else{
        	Robot.navigation.getController().setSetpoint(targetAngle.doubleValue());
    	}
    	Robot.drive.setUpDriveForSpeedControl();
    	
		moveMotor			= 0.0;
		moveMotorPrevious = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	turnCorrection = (Robot.navigation.getController().getError()/180.0) * Navigation.coefficient.getValue();
	
		moveMotor = (moveStick-moveMotorPrevious)*MOVE_FILTER + moveMotorPrevious;
		moveMotorPrevious = moveMotor; 
		moveMotor = moveMotor * Math.pow(Math.abs(moveMotor), MOVE_EXPONENT - 1);
		
		Robot.drive.arcadeDrive(moveMotor, turnCorrection, false);	
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return isOnTarget();
    }
    
    private boolean isOnTarget(){
    	return 	VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(),
										   	endPositionLeft,
										   	TOLERANCE) ||
    			VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(),
						   				   	endPositionRight,
						   				   	TOLERANCE);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
