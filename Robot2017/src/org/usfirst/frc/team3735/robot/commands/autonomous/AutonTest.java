package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxVision;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceMotionMagic;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceProfile2;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;

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
