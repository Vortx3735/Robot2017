package org.usfirst.frc.team3735.robot.commands.drive.turntoangle;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.util.PIDCtrl;
import org.usfirst.frc.team3735.robot.util.Setting;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTurnToAnglePIDCtrl extends Command{
	
    private double targetAngle;
	private double finishTime = .5;
	private double timeOnTarget = 0;
	private Pipes pipeline;
	private boolean isRelative;
	private double deltaAngle;

	public DriveTurnToAnglePIDCtrl(double angle) {
    	requires(Robot.drive);
    	requires(Robot.navigation);
    	this.targetAngle = angle;
    	this.deltaAngle = 0;
    	isRelative = false;
    }
	
	public DriveTurnToAnglePIDCtrl(double angle, boolean flag) {
    	requires(Robot.drive);
    	requires(Robot.navigation);
    	this.targetAngle = 0;
    	this.deltaAngle = angle;
    	isRelative = true;
    }
	
	public DriveTurnToAnglePIDCtrl(Pipes p) {
    	requires(Robot.drive);
    	requires(Robot.navigation);
    	requires(Robot.vision);
    	pipeline = p;
    	this.targetAngle = 0;
    	this.deltaAngle = 0;
    	isRelative = false;
    }
	

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(pipeline != null){
    		targetAngle = VortxMath.continuousLimit(
    				Robot.navigation.getYaw() + (Robot.vision.getRelativeCX() * Robot.vision.dpp.getValue()),
    				-180, 180);
    	}else if(isRelative){
    		targetAngle = VortxMath.continuousLimit(Robot.navigation.getYaw() + deltaAngle, -180, 180);
    	}
    	Robot.navigation.getController().setSetpoint(targetAngle);
    	Robot.navigation.getController().enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(pipeline != null){
//    		targetAngle = VortxMath.continuousLimit(
//    				Robot.navigation.getYaw() + (Robot.vision.getRelativeCX() * Robot.vision.dpp.getValue()),
//    				-180, 180);
//    		Robot.navigation.getController().setSetpoint(targetAngle);
//    	}
    	Robot.navigation.getController().setIZone(Navigation.iZone.getValue());
    	Robot.navigation.getController().updateI(Navigation.actingI.getValue());
    	
    	if(Robot.navigation.getController().onTarget()){
    		timeOnTarget += .02;
    	}else{
    		timeOnTarget = 0;
    	}
    }

	// Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return timeOnTarget >= finishTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.navigation.getController().disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
