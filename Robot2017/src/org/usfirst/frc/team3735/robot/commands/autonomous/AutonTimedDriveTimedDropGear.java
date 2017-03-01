package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistance;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonTimedDriveTimedDropGear extends CommandGroup {

    public AutonTimedDriveTimedDropGear(){
    	/* Let Move FWD then Drop Pin */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	
    	addSequential(new ExpDrive(0.65, 0.0),2.5); /* Straight To Pin*/
    	addSequential(new GearIntakeDropOff(),1.0); /* Drop Gear */
    	/** THE STEPS AFTER THESE LINES NEED TESTING **/
//    	addSequential(new ExpDrive(0.65, 0.7),1.0); /* Turn Right */
//    	addSequential(new ExpDrive(-0.65, 0.0),1.5); /* Move BAck Straight */
//    	addSequential(new ExpDrive(0.65, -0.7),1.5); /* Turn Left */
//    	addSequential(new ExpDrive(0.65, 0.0),1.25); /* Drive Straight*/
//    	addSequential(new ExpDrive(0.65, -0.7),1.1); /* Turn Right */
//    	addSequential(new ExpDrive(0.65, 0.0),1.25); /* Drive Straight*/
//    	
    	/**********************************************/
    	
    	
    	
    	
    	
    	
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
