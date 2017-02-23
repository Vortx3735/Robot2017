package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class InterruptEverything extends InstantCommand {

    public InterruptEverything() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drive);
        requires(Robot.ballIntake);
        requires(Robot.gearIntake);
        requires(Robot.scaler);
        requires(Robot.shooter);
    }

    // Called once when the command executes
    protected void initialize() {
    }

}
