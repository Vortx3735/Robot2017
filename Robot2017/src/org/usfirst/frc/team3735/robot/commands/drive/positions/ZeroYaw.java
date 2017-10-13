package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ZeroYaw extends InstantCommand {

    public ZeroYaw() {

    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.navigation.zeroYaw();
    }

}
