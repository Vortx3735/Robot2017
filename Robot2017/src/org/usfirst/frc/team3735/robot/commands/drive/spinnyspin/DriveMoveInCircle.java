package org.usfirst.frc.team3735.robot.commands.drive.spinnyspin;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveMoveInCircle extends Command {

    private static final double DRIVE_WIDTH = 28;		//in inches
	private static final double targetThreshold = 3;	//in degrees of error
    
	private double power;
	private double angle;
	private double curve;

	private boolean isRelative;
	
	
	/*
	 * @param angle		the absolute angle were trying to turn to
	 * @param power		the power to send to the motors; the speed
	 * @param radius	the radius to turn in. Positive if turning right, negative if turning left
	 */
	public DriveMoveInCircle(double angle, double power, double radius) {
    	isRelative = false;
		this.angle = angle;
    	this.power = power;
    	this.curve = radiusToCurve(radius);
    	
    	requires(Robot.drive);
    	requires(Robot.navigation);
    }
	
	/*
	 * @param angle		the absolute angle were trying to turn to
	 * @param power		the power to send to the motors; the speed
	 * @param radius	the radius to turn in. Positive if turning right, negative if turning left
	 */
	public DriveMoveInCircle(double angle, double power, double radius, boolean relative){
		isRelative = true;
    	this.angle = angle;
    	this.power = power;
    	this.curve = radiusToCurve(radius);
    	
    	requires(Robot.drive);
    	requires(Robot.navigation);
	}


    // Called just before this Command runs the first time
    protected void initialize() {
    	if(isRelative){
        	Robot.navigation.getController().setSetpoint(
    			VortxMath.continuousLimit(
					(Robot.navigation.getYaw() + angle), -180, 180
				)
			);
    	}else{
        	Robot.navigation.getController().setSetpoint(angle);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.radialDrive(power, curve);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.navigation.getController().getError()) < targetThreshold;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.radialDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private double radiusToCurve(double r){
    	if(r > 0){
        	return Math.exp(-(r/DRIVE_WIDTH));
    	}else{
    		return -Math.exp(-(r/DRIVE_WIDTH));
    	}
    }
}
