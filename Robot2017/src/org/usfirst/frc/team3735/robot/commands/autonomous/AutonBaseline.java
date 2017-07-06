package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
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
