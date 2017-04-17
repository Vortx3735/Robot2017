package org.usfirst.frc.team3735.robot.commands.drive.turntoangle;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnToAngleHyperbola extends Command {

	private double setpoint;
	private double timeOnTarget = 0;
	private double finishTime = .2;
	private double error;
	private double max = .5;
	
    public DriveTurnToAngleHyperbola(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	Robot.drive.setUpDriveForSpeedControl();
    	setpoint = angle;
    	SmartDashboard.putNumber("min turn value", .2);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.navigation.getController().setSetpoint(setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute(){
    	error = Robot.navigation.getController().getError();
    	Robot.drive.arcadeDrive(0, hype(error), false);
    	System.out.println("Turning to angle hype");
    	if(Robot.navigation.getController().onTarget()){
    		timeOnTarget += .02;
    	}else{
    		timeOnTarget = 0;
    	}
    }

    private double hype(double x){
    	double h = SmartDashboard.getNumber("min turn value", .2);
    	double o = Math.sqrt((Math.pow(h/max, 2))+(x*x));
    	SmartDashboard.putNumber("hyperbola output", (x > 0)? o : -o);
		return (x > 0)? o : -o;
	}

	// Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.arcadeDrive(0, hype(error), false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
