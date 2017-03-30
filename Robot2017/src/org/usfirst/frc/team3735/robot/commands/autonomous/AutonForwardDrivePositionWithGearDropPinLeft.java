package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceNavxExpNaik;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAngleArcadePIDNaik;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDrivePositionWithGearDropPinLeft extends CommandGroup {

    public AutonForwardDrivePositionWithGearDropPinLeft(){
    	addSequential(new DriveMoveDistanceNavxExpNaik(20)); /* Straight To Pin*/
    	addSequential(new DriveTurnToAngleArcadePIDNaik(60)); /* Turn 30*/
    	addSequential(new DriveMoveDistanceNavxExpNaik(20)); /* Straight To Pin*/
    	addSequential(new GearIntakeDropOff());
     }
}
