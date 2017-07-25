package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.assists.NavxAssist;
import org.usfirst.frc.team3735.robot.commands.drive.DriveExp;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxBumped;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxVision;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.triggers.Bumped;
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
    	//addSequential(new DriveMoveDistanceExpNavxVision(30,.7,Pipes.Peg),3);
    	
    	//addSequential(new DriveMoveDistanceNavxExpNaik(132));
    	//addSequential(new DriveMoveDistance(10));
    	
    	//addSequential(new DriveMoveDistanceExpNavx(70,.7),2.6);
    	addSequential(new DriveExp(.7,0).addAssist(new NavxAssist()).addTrigger(new HasMoved(70)),2.6);
    	addSequential(new DriveBrake(),.4);
    	addSequential(new DriveTurnToAnglePIDCtrl(60),2);
    	addSequential(new DriveTurnToAnglePIDCtrl(Pipes.Peg),1.5);
    	//addSequential(new DriveMoveDistanceExpNavx(88.7),3);
    	//addSequential(new DriveMoveDistanceExpVisionBumped(89, .7, Pipes.Peg),3);
    	//addSequential(new DriveMoveDistanceExpNavxBumped(89, .7),2.5);
    	addSequential(new DriveExp(.7,0).addAssist(new NavxAssist()).addTrigger(new HasMoved(70)).addTrigger(new Bumped()),2.6);
    	addSequential(new GearIntakeDropOff(),2);
     }
}
