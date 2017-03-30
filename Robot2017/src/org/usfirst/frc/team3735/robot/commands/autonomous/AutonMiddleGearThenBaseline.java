package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.TurnWithTime;
import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonMiddleGearThenBaseline extends CommandGroup {

    public AutonMiddleGearThenBaseline(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	//112-26-10.5 = 75.5
    	//75.5 + 3 inches = 79
    	//addSequential(new DriveMoveDistanceInches(86),2.6); /* Straight To Pin*/
    	addSequential(new DriveMoveDistanceNavx(86),2.5); /* Straight To Pin*/
    	addSequential(new Wait(.4));
    	addSequential(new GearIntakeDropOff());
    	addSequential(new DriveMoveDistancePID(-30));
    	addSequential(new TurnWithTime(-1),2);
    	addSequential(new DriveMoveDistancePID(70));
    	addSequential(new TurnWithTime(1),2);
    	addSequential(new DriveMoveDistancePID(100));
     }
}
