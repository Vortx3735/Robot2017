package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveStopRobot extends InstantCommand {

    public DriveStopRobot() {
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drive);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.drive.setLeftRightOutputs(0, 0);
    }

}
