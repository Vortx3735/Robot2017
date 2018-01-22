package org.usfirst.frc.team3735.robot.commands.drive.arcing;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceProfile;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;
import org.usfirst.frc.team3735.robot.triggers.HasReachedAngle;
import org.usfirst.frc.team3735.robot.util.calc.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.VortxCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveMoveInCircleProfile extends VortxCommand {

    private static final double DRIVE_WIDTH = 28;	//in inches

	private static final double MAX_SPEED = 800 * Constants.Drive.InchesPerTick /60.0; //inches per sec
    
	private HasReachedAngle angler;
	private DriveMoveDistanceProfile profile;
	private Double distance;
	private double radius;
	
	private double leftMult;
	private double rightMult;
	
	/**
	 * 
	 * @param radius		the radius to turn in. Positive if turning right, negative if turning left, 
	 * 						for both forward and back
	 * @param angle			the angle we're trying to turn to
	 * @param isRelative	indicates that the angle is relative to the starting angle on initialize
	 * @param v				the cruise velocity for the profile
	 * @param a				the acceleration of the profile
	 * @param exitV			the exit velocity of the profile
	 */
	public DriveMoveInCircleProfile(double radius, double angle, boolean isRelative, double v, double a, double exitV) {
    	this.radius = radius;
    	angler = new HasReachedAngle(angle, isRelative);
    	distance = new Double(Math.PI * radius * (angle/180.0));
    	if(Math.abs(v) > Math.abs(vMax(radius, DRIVE_WIDTH))){
    		v = Math.signum(v) * Math.abs(vMax(radius, DRIVE_WIDTH));
    		System.out.println("The Cruise Velocity was too great. Reducing to " + v);
    	}
    	profile = new DriveMoveDistanceProfile(distance, v, a, exitV);
    	addT(angler);
    	
    	leftMult = 1.0 + (DRIVE_WIDTH/radius);
    	rightMult = 1.0 - (DRIVE_WIDTH/radius);
    	
    }
	


    // Called just before this Command runs the first time
    protected void initialize() {
    	super.initialize();
    	if(angler.isRelative()){
        	distance = new Double(Math.PI * radius * (angler.degreesToGo()/180.0));
    	}
    	profile.initialize();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	profile.updateProfile();
    	double s = profile.currentSpeed;
    	Robot.drive.setLeftRight(Drive.speedToPercent(s * leftMult), Drive.speedToPercent(s * rightMult));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return profile.isFinished() || super.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private double vMax(double r, double w){
    	return (MAX_SPEED * Math.abs(r)) / (Math.abs(r) + w);
    }
}
