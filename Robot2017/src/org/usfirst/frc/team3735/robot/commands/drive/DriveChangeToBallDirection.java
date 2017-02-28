package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveChangeToBallDirection extends InstantCommand {

    public DriveChangeToBallDirection() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.drive.changeToReverse();
    }

}
