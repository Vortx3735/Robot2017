package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVision;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVisionBumped;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonRightGear extends CommandGroup {

    public AutonRightGear(){
    	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
    	addSequential(new DriveBrake(),.4);
    	addSequential(new DriveTurnToAnglePIDCtrl(-60),2);
    	//addSequential(new DriveMoveDistanceNavx(88.7),3);
    	addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
    	addSequential(new GearIntakeDropOff(),3);
     }
}
