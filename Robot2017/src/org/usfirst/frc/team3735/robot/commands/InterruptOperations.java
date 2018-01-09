package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class InterruptOperations extends InstantCommand {

    public InterruptOperations() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.gearIntake);
        requires(Robot.scaler);

    }

    // Called once when the command executes
    protected void initialize() {
    }

}
