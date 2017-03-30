package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavxExpNaik;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
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
    	//75.5 + 3 inches = 79
    	//addSequential(new DriveMoveDistanceInches(86),2.6); /* Straight To Pin*/
    	// THIS WAS USED AT LONE JESUITaddSequential(new DriveMoveDistanceNavx(86),2.6); /* Straight To Pin*/
    	addSequential(new DriveMoveDistanceNavxExpNaik(85)); /* Straight To Pin*/
    	
    	//addSequential(new DriveMoveDistance(86),2.6); /* Straight To Pin*/
    	addSequential(new DriveBrake(),.4);
    	//addSequential(new Wait(.4));
    	addSequential(new GearIntakeDropOff());
     }
}
