package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.TrapProfile;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveDistanceProfile extends Command {
	private double targetAngle;
	private double startAngle;
	private double direction;
	private TrapProfile profile;
	
	private double currentAngle;
	private double acceleratingDistance = -1;
	
	private final double V = 20;	//degrees per second
	private final double A = 2;		//degrees per second per second
	private final double vpdps = 1;	//volts per degrees per second
    public MoveDistanceProfile(double a) {
    	targetAngle = a;
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startAngle = Robot.drive.getYaw();
    	
    	if(targetAngle - startAngle > 180){
    		targetAngle -= 360;
    	}else if(targetAngle - startAngle < -180){
    		targetAngle += 360;
    	}
    	if(targetAngle < startAngle){
    		direction = -1;
    	}else{
    		direction = 1;
    	}
    	
    	Robot.drive.setUpDriveForSpeedControl();
    	profile = new TrapProfile(V,A);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle = Robot.drive.getYaw();
    	if(acceleratingDistance == -1 && profile.isCruising()){
    		acceleratingDistance = Math.abs(currentAngle - startAngle);
    	}
    	if(Math.abs(targetAngle - currentAngle) <= acceleratingDistance){
    		profile.startDeccelerating();
    	}
    	
    	profile.step();
    	Robot.drive.setLeftRightOutputs(vpdps*profile.getVelocity()*direction, vpdps* -profile.getVelocity()*direction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return profile.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setLeftRightOutputs(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
