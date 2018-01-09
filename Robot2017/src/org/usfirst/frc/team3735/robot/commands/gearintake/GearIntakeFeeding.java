
package org.usfirst.frc.team3735.robot.commands.gearintake;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntakeFeeding extends Command {

    public GearIntakeFeeding() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearIntake.liftDown();    	
    	Robot.gearIntake.setRollerSpeed(Constants.GearIntake.feedingVoltage);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearIntake.setRollerSpeed(Constants.GearIntake.feedingVoltage);
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
    	Robot.gearIntake.liftUp();
    	Robot.gearIntake.setRollerSpeed(0);
    }
}
