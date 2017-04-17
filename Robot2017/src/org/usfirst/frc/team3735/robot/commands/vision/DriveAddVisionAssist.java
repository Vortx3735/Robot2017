package org.usfirst.frc.team3735.robot.commands.vision;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveAddVisionAssist extends Command {

    private Pipes pipeline;
	private double prevWorking;

	public DriveAddVisionAssist(Pipes p) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.pipeline = p;
    	requires(Robot.vision);
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.vision.pause();
    	//Robot.vision.resume();
    	Robot.vision.setMainHandler(pipeline);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double in = Robot.vision.getRelativeCX();
    	if(in == -161){
    		Robot.drive.setVisionAssist(prevWorking * -1 * .006);
    		//Robot.drive.setVisionAssist(0);
    	}else{
    		prevWorking = in;
        	Robot.drive.setVisionAssist(in * .0025);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.vision.pause();
		Robot.drive.setVisionAssist(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
