package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;
import org.usfirst.frc.team3735.robot.util.calc.TrapProfile;
import org.usfirst.frc.team3735.robot.util.calc.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.VortxCommand;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMoveDistanceProfile extends VortxCommand {


	private static final double FRAMERATE = 50;
	
	
    private final double cruiseVelocity;	//inches per second
	private final double acceleration;	//inches per second^2
	private final double exitVelocity;	//inches per second

	public double currentSpeed;
	private State state;
	private double acc;
	
	public HasMoved distHandler;
	
	
	private enum State{
		rampingUp,
		cruising,
		rampingDown
	}

	public DriveMoveDistanceProfile(Double distance, double v, double a, double exitV) {
		if(Math.abs(exitV) > v){
			exitV = v;
		}
		acceleration = Math.copySign(a, distance);
		cruiseVelocity = Math.copySign(v, distance);
		exitVelocity = Math.copySign(exitV, distance);

    	requires(Robot.drive);
    	distHandler = new HasMoved(distance);
    	addT(distHandler);
    	acc = acceleration/FRAMERATE;

    }

    // Called just before this Command runs the first time
    public void initialize() {
    	super.initialize();
    	distHandler.initialize();
    	acc = acceleration/FRAMERATE;
    	currentSpeed = Robot.drive.getAverageSpeed();

    	state = State.rampingUp;
    	if(currentSpeed * distHandler.distance() < 0){
    		System.out.println("Profile Error: Robot is moving in the wrong direction");
    	}

    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	super.execute();
    	sendErrorReport();
		updateProfile();

    	//Robot.drive.setLeftRightOutputs(speedToPercent(currentSpeed), speedToPercent(currentSpeed));
    	Robot.drive.arcadeDrive(Drive.speedToPercent(currentSpeed), 0);
    }

    public void updateProfile() {
    	if(needsToRampDown()){
    		System.out.println("Profile: Starting Ramp Down");
			acc = calcAcceleration()/ FRAMERATE;
			state = State.rampingDown;
		}
    	
    	switch(state){
	    	case rampingUp:
	    		currentSpeed += acc;
	    		if(Math.abs(currentSpeed) > Math.abs(cruiseVelocity)){
	    			currentSpeed = cruiseVelocity;
	    			state = State.cruising;
	    		}
	    		break;
	    	case cruising:
	    		
	    		break;
	    	case rampingDown:
	    		currentSpeed += acc;
	    		break;    			
    	}		
	}

	private void sendErrorReport() {
    	SmartDashboard.putNumber("Profile currentSpeed: ", currentSpeed);
    	SmartDashboard.putNumber("Profile actual Speed", Robot.drive.getAverageSpeed());
    	SmartDashboard.putNumber("Profile Error", currentSpeed - Robot.drive.getAverageSpeed());
    }

	private double calcAcceleration() {
    	return (Math.pow(exitVelocity, 2) - Math.pow(Robot.drive.getAverageSpeed(), 2)) / 
 			   (2 * (distHandler.distanceToGo()));
	}

	private boolean needsToRampDown() {
		return Math.abs(calcAcceleration()) > Math.abs(acceleration);
	}

	// Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        if(super.isFinished()){
        	System.out.println("Profile canceled by distance");
        	return true;
        }else if(isProfileFinished()){
        	System.out.println("Profile canceled by itself");
        	return true;
        }
        return false;
    }

    private boolean isProfileFinished() {
		if(cruiseVelocity > 0){
			return (state == State.rampingDown) && (Robot.drive.getAverageSpeed() < exitVelocity);
		}else{
			return (state == State.rampingDown) && (Robot.drive.getAverageSpeed() > exitVelocity);
		}
	}

	// Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    

}
