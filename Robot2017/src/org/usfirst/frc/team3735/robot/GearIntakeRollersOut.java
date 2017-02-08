package org.usfirst.frc.team3735.robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeRollersOut extends InstantCommand {

    public GearIntakeRollersOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearIntake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.gearIntake.turnRollersOut();
    }

}
