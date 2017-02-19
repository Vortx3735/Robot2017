package org.usfirst.frc.team3735.robot.commands.gearintake;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeLiftDown extends InstantCommand {

    public GearIntakeLiftDown() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        //requires(Robot.gearIntake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.gearIntake.liftDown();
    	System.out.println("Lift down init");
    }

}
