package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceVisionExp;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

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
    	addSequential(new DriveMoveDistanceNavx(83),2.6); /* Straight To Pin*/
    	//addSequential(new DriveMoveDistanceVisionExp(Pipes.Peg,.5,83));
    	//addSequential(new DriveMoveDistance(86),2.6); /* Straight To Pin*/
    	addSequential(new DriveBrake(),.4);
    	//addSequential(new Wait(.4));
    	addSequential(new GearIntakeDropOff());
     }
}
