package org.usfirst.frc.team3735.robot.commands.vision;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.util.Setting;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveAddVisionAssistNavx extends Command {

    private Pipes pipeline;
	private double prevWorking;

	public DriveAddVisionAssistNavx(Pipes p) {
    	this.pipeline = p;
    	prevWorking = 0;
    	requires(Robot.vision);
    	requires(Robot.navigation);
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.vision.setMainHandler(pipeline);
		prevWorking = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double input = Robot.vision.getRelativeCX();
    	if(input != -161){
    		if(input != prevWorking){
		    	Robot.navigation.getController().setSetpoint(
		    			VortxMath.continuousLimit(
		        				Robot.navigation.getYaw() + (input * Robot.vision.dpp.getValue()),
		        				-180, 180)
				);
				prevWorking = input;
			}
    		Robot.drive.setVisionAssist((Robot.navigation.getController().getError()/180) * 5);
    	}else{
    		Robot.drive.setVisionAssist(0);

    	}
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.drive.setVisionAssist(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
