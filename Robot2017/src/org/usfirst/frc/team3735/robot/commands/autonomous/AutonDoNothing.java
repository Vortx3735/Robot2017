package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonDoNothing extends CommandGroup {

    public AutonDoNothing() {
    	addSequential(new DriveBrake());

    }
}
