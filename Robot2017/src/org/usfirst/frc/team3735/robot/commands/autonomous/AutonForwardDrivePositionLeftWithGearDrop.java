package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDrivePositionLeftWithGearDrop extends CommandGroup {

    public AutonForwardDrivePositionLeftWithGearDrop(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	addSequential(new DriveMoveDistanceNavx(74.8),3);
    	addSequential(new DriveTurnToAnglePID(60));
    	//addSequential(new ExpDrive(.6,4),1);
    	addSequential(new DriveMoveDistanceNavx(88.7),3);
    	addSequential(new GearIntakeDropOff(),3);
     }
}
