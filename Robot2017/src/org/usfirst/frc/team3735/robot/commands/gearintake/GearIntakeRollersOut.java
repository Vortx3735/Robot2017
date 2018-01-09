package org.usfirst.frc.team3735.robot.commands.gearintake;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntakeRollersOut extends Command {

    public GearIntakeRollersOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearIntake.setRollerSpeed(Constants.GearIntake.rollerOutVoltage);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearIntake.setRollerSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
