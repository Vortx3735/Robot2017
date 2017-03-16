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
public class AutonForwardDrivePositionWithGearDrop extends CommandGroup {

    public AutonForwardDrivePositionWithGearDrop(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	//112-26-10.5 = 75.5
    	addSequential(new DriveMoveDistanceInches(75.5)); /* Straight To Pin*/
    	addSequential(new GearIntakeDropOff());
     }
}
