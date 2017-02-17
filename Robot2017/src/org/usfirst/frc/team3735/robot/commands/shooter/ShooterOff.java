package org.usfirst.frc.team3735.robot.commands.shooter;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ShooterOff extends InstantCommand {

    public ShooterOff() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.shooter);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.shooter.setSpeed(0);
    }

}
