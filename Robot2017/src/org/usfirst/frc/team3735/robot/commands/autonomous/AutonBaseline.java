package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavxExpNaik;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonBaseline extends CommandGroup {

    public AutonBaseline(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	//112 + 20
    	addSequential(new DriveMoveDistanceExpNavx(132, .7)); /* Straight To Pin*/
    	//addSequential(new DriveMoveDistanceNavxExpNaik(132));
    	//addSequential(new DriveMoveDistance(10));
     }
}
