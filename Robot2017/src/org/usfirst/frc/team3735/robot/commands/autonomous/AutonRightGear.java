package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.Move;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.triggers.Bumped;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonRightGear extends CommandGroup {

    public AutonRightGear(){
//    	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
//    	addSequential(new DriveBrake(),.4);
//    	addSequential(new DriveTurnToAnglePIDCtrl(-60),2);
//    	//addSequential(new DriveMoveDistanceNavx(88.7),3);
//    	addSequential(new DriveTurnToAnglePIDCtrl(Pipes.Peg),2);
//    	addSequential(new DriveMoveDistanceExpNavxBumped(89, .7),3);
//    	//addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
//    	addSequential(new GearIntakeDropOff(),3);
    	
//    	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
//    	addSequential(new DriveBrake(),.4);
//    	addSequential(new TurnTo(-60),2);
//    	addSequential(new TurnTo(Pipes.Peg),2);
//    	//addSequential(new DriveMoveDistanceExpNavx(88.7),3);
//    	//addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
//    	addSequential(new DriveMoveDistanceExpNavxBumped(89, .7),3);
//    	addSequential(new GearIntakeDropOff(),3);
    	
    	addSequential(new Move(60));
    	addSequential(new DriveBrake(),.4);
    	addSequential(new TurnTo(-60),2);
    	addSequential(new TurnTo(Pipes.Peg),2);
    	//addSequential(new DriveMoveDistanceExpNavx(88.7),3);
    	//addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
    	//addSequential(new DriveMoveDistanceExpNavxBumped(89, .7),2.5);
    	addSequential(new Move(45,.5).addT(new Bumped(.7)));
    	addSequential(new GearIntakeDropOff(),2);
     }
}
