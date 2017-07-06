package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxVision;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonTest extends CommandGroup {

    public AutonTest(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	//112 + 20
    	//addSequential(new DriveMoveDistanceExpNavx(132, .7)); /* Straight To Pin*/
    	addSequential(new DriveMoveDistanceExpNavxVision(30,.7,Pipes.Peg),3);
    	//addSequential(new DriveMoveDistanceNavxExpNaik(132));
    	//addSequential(new DriveMoveDistance(10));
     }
}
