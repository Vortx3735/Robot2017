package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DriveResetPosition extends InstantCommand {

    public DriveResetPosition() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.drive.resetEncodersPositions();
    	
    }

}
