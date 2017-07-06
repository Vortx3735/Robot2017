package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonMiddleGear extends CommandGroup {

    public AutonMiddleGear(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	//112-26-10.5 = 75.5
    	//75.5 + 3 inches = 79
    	//addSequential(new DriveMoveDistanceInches(86),2.6); /* Straight To Pin*/
    	//addSequential(new DriveMoveDistanceNavx(86),2.6); /* Straight To Pin*/
    	//addSequential(new DriveMoveDistanceVisionExp(Pipes.Peg,.7,75));
    	addSequential(new DriveMoveDistanceExpNavx(75,.7),2.6);

    	//addSequential(new DriveMoveDistance(86),2.6); /* Straight To Pin*/
    	addSequential(new DriveBrake(),.4);
    	//addSequential(new Wait(.4));
    	addSequential(new GearIntakeDropOff(),4);
     }
}
