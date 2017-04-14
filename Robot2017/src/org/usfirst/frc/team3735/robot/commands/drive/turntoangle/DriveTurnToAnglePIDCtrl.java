package org.usfirst.frc.team3735.robot.commands.drive.turntoangle;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.PIDCtrl;
import org.usfirst.frc.team3735.robot.util.Setting;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnToAnglePIDCtrl extends Command implements PIDOutput, PIDSource{
	
    private double targetAngle;
    private static PIDCtrl controller;
    private static Setting iZone = new Setting("Turning IZone", 10);
    
	public DriveTurnToAnglePIDCtrl(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.targetAngle = angle;
    	controller = new PIDCtrl(.015,0.0,0.0,this,this);
    	controller.setOutputRange(-.25, .25);
    	controller.setInputRange(-180, 180);
    	controller.setContinuous();
    	controller.setIsUsingIZone(true);
    	controller.setIZone(10);
    	SmartDashboard.putData("PID Ctrl turning Controller",controller);

    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	controller.setSetpoint(targetAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	controller.setIZone(iZone.getValue());
    	controller.updateI();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return null;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		return Robot.drive.getPIDController().getError();
	}

	@Override
	public void pidWrite(double output) {
		Robot.drive.setLeftRightOutputs(output, -output);
	}
}
