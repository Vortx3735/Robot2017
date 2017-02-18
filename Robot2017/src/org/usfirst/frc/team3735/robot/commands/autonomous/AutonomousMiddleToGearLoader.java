package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistance;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
// Start for the middle then drive to gear lift and place gear.
// Then back up and and drive to gear loader
public class AutonomousMiddleToGearLoader extends CommandGroup {
	
    public AutonomousMiddleToGearLoader() {
    	//Drive To gear loader
    	addSequential(new DriveMoveDistance(114.775-Constants.RobotDimensions.length));
    	
    	//addSequential(new put get drop off class)
    	addSequential(new DriveMoveDistance(-65));
    	addSequential(new DriveTurnToAngle(-60));
    	addSequential(new DriveMoveDistance(173));
    	addSequential(new DriveTurnToAngle(60));
    	addSequential(new DriveMoveDistance(464));
    	
    	//finish at 52 in away from other team wall and 12 inches down from top wall
    	
    	
    	
    	
    	
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
