package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetPosition extends InstantCommand {

    public ResetPosition() {
        this.setRunWhenDisabled(true);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.navigation.resetPosition();
    }

}
