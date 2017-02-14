package org.usfirst.frc.team3735.robot.commands;

import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousTest extends CommandGroup {

    public AutonomousTest() {
    	addSequential(new DriveTurnToAngle(90));
    	addSequential(new DriveTurnToAngle(180));
    	addSequential(new DriveTurnToAngle(0));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
