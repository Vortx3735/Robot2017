package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistance;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDrivePosition extends CommandGroup {

    public AutonForwardDrivePosition(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	//112 + 20
    	addSequential(new DriveMoveDistanceInches(132)); /* Straight To Pin*/
    	//addSequential(new DriveMoveDistance(10));
     }
}
