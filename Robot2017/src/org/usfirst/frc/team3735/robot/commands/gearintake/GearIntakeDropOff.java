package org.usfirst.frc.team3735.robot.commands.gearintake;

import org.usfirst.frc.team3735.robot.Constants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeDropOff extends CommandGroup {

    public GearIntakeDropOff() {
    	addParallel(new GearIntakeRollersOut());
    	Timer.delay(Constants.GearIntake.dropOffRollDelay);
    	addSequential(new GearIntakeLiftDown());
    	Timer.delay(Constants.GearIntake.dropOffJerkDelay);
    	addSequential(new GearIntakeLiftUp());
    	Timer.delay(Constants.GearIntake.dropOffEndDelay);
    	addSequential(new GearIntakeRollersOff());
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
