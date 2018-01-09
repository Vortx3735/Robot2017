package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.settings.Setting;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SendSDSpeed extends Command {

	private static Setting speed;
    public SendSDSpeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	speed = new Setting("Drive Speed Input", 0);
    	
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.setLeftRight(speed.getValue(), speed.getValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
