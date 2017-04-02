package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceVisionExp;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceVisionExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavx;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGear extends CommandGroup {

    public AutonLeftGear(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
    	addSequential(new DriveBrake(),.4);
    	
    	addSequential(new DriveTurnToAnglePID(60),2);
    	//addSequential(new ExpDrive(.6,4),1);
    	
    	//addSequential(new DriveMoveDistanceNavx(88.7),3);
    	addSequential(new DriveMoveDistanceVisionExp(Pipes.Peg, .7, 89),3);
    	//addSequential(new DriveMoveDistanceVisionExpNavx(Pipes.Peg, .7, 89),3);

    	addSequential(new GearIntakeDropOff(),3);
     }
}
