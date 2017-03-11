package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveTwistAngle;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDriveSquare extends CommandGroup {

    public AutonForwardDriveSquare(){
    	
    	addSequential(new DriveMoveDistanceInches(25)); /* */
    	addSequential(new DriveMoveTwistAngle(90)); /* */
    	
    	addSequential(new DriveMoveDistanceInches(25)); /* */
    	addSequential(new DriveMoveTwistAngle(90)); /* */
    	
    	addSequential(new DriveMoveDistanceInches(25)); /* */
    	addSequential(new DriveMoveTwistAngle(90)); /* */
    	
    	addSequential(new DriveMoveDistanceInches(25)); /* */
    	addSequential(new DriveMoveTwistAngle(90)); /* */
    	
     }
}
