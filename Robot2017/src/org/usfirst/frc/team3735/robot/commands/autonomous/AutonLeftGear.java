package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxBumped;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVision;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVisionBumped;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxVision;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGear extends CommandGroup {

    public AutonLeftGear(){
//    	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
//    	addSequential(new DriveBrake(),.4);
//    	addSequential(new DriveTurnToAnglePIDCtrl(60),2);
//    	//addSequential(new DriveMoveDistanceNavx(88.7),3);
//    	addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
//    	//addSequential(new DriveMoveDistanceExpNavxVision(80, .7, ));
//    	addSequential(new GearIntakeDropOff(),3);
    	
//     	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
//    	addSequential(new DriveBrake(),.4);
//    	addSequential(new DriveTurnToAnglePIDCtrl(60),2);
//    	//addSequential(new DriveMoveDistanceNavx(88.7),3);
//    	addSequential(new DriveMoveDistanceExpNavxVision(89, .7, Pipes.Peg){
//
//			@Override
//			protected boolean isFinished() {
//				return super.isFinished() || Robot.navigation.isBumped();
//			}
//    		
//    	},3);
//    	//addSequential(new DriveMoveDistanceExpNavxVision(80, .7, ));
//    	addSequential(new GearIntakeDropOff(),3);
    	
    	
    	addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
    	addSequential(new DriveBrake(),.4);
    	addSequential(new DriveTurnToAnglePIDCtrl(60),2);
    	addSequential(new DriveTurnToAnglePIDCtrl(Pipes.Peg),2);
    	//addSequential(new DriveMoveDistanceNavx(88.7),3);
    	//addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
    	addSequential(new DriveMoveDistanceExpNavxBumped(89, .7),3);
    	addSequential(new GearIntakeDropOff(),3);
    	
     }
}
