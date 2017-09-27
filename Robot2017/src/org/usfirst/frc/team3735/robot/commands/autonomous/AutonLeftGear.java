package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.assists.NavxAssist;
import org.usfirst.frc.team3735.robot.commands.drive.DriveExp;
import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.triggers.Bumped;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGear extends CommandGroup {

    public AutonLeftGear(){
    	
//    	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
//    	addSequential(new DriveBrake(),.4);
//    	addSequential(new DriveTurnToAnglePIDCtrl(60),2);
//    	addSequential(new DriveTurnToAnglePIDCtrl(Pipes.Peg),1.5);
//    	//addSequential(new DriveMoveDistanceExpNavx(88.7),3);
//    	//addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
//    	addSequential(new DriveMoveDistanceExpNavxBumped(89, .7),2.5);
//    	addSequential(new GearIntakeDropOff(),2);
    	
    	addSequential(new DriveExp(.7,0).addA(new NavxAssist()).addT(new HasMoved(70)));
    	addSequential(new DriveBrake(),.4);
    	addSequential(new TurnTo(60),2);
    	addSequential(new TurnTo(Pipes.Peg),1.5);
    	//addSequential(new DriveMoveDistanceExpNavx(88.7),3);
    	//addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
    	//addSequential(new DriveMoveDistanceExpNavxBumped(89, .7),2.5);
    	addSequential(new DriveExp(.7,0).addA(new NavxAssist()).addT(new HasMoved(90)).addT(new Bumped()));
    	addSequential(new GearIntakeDropOff(),2);
    	

    	
     }
}
