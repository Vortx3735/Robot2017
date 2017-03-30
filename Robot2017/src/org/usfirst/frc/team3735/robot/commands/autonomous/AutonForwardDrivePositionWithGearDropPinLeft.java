package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistance;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.DriveForwardToCurrentGyroHeading;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToOffsetGyroHeading;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDrivePositionWithGearDropPinLeft extends CommandGroup {

    public AutonForwardDrivePositionWithGearDropPinLeft(){
    	addSequential(new DriveForwardToCurrentGyroHeading(20)); /* Straight To Pin*/
    	addSequential(new DriveTurnToOffsetGyroHeading(60)); /* Turn 30*/
    	addSequential(new DriveForwardToCurrentGyroHeading(20)); /* Straight To Pin*/
    	addSequential(new GearIntakeDropOff());
     }
}
