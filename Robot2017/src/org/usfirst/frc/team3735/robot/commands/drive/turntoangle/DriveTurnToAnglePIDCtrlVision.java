package org.usfirst.frc.team3735.robot.commands.drive.turntoangle;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
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
public class DriveTurnToAnglePIDCtrlVision extends Command{
	    
	private double finishTime = .5;
	private double timeOnTarget = 0;
	private Pipes pipeline;

    
	public DriveTurnToAnglePIDCtrlVision(Pipes p) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		this.pipeline = p;
    	requires(Robot.drive);
    	requires(Robot.navigation);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.vision.setMainHandler(pipeline);
    	Robot.navigation.getController().setSetpoint(Robot.navigation.getYaw() + (Robot.vision.getRelativeCX() * Robot.vision.dpp.getValue()));
    	Robot.navigation.getController().enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
