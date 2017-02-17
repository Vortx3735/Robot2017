package org.usfirst.frc.team3735.robot.commands.scaler;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ScalerOff extends InstantCommand {

    public ScalerOff() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.scaler);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.scaler.setCurrent(0);
    }

}
