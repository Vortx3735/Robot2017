package Commands;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToAngle extends Command {

	private final double P = 1;
	private final double I = 0;
	private final double D = 0;

	double targetAngle;
	double currentAngle;
	double error;
	
	final double updatePeriod = .05;	//in seconds
	double tick = 0;					//in seconds
	private double timeDone = .25;		//in seconds
	
	private double allowedError = 5; 	//in degrees

	
    public TurnToAngle(double theta){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	targetAngle = theta;
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	currentAngle = Robot.drive.ahrs.getFusedHeading();
    	if(targetAngle-currentAngle > 180){
    		targetAngle -= 360;
    	}else if(targetAngle-currentAngle < -180){
    		targetAngle += 360;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle = Robot.drive.ahrs.getFusedHeading();
    	error = targetAngle - currentAngle;
    	Robot.drive.turn(P*error);
    	
    	if(Math.abs(error) < allowedError){
    		tick += updatePeriod;
    	}else{
    		tick = 0;
    	}
    	
    	Timer.delay(updatePeriod);
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(tick > timeDone){
        	return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
