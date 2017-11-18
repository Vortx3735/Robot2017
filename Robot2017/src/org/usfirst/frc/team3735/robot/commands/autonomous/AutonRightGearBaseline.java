package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.Move;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.triggers.Bumped;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonRightGearBaseline extends CommandGroup {

    public AutonRightGearBaseline(){
//    	addSequential(new AutonRightGear());
//    	addSequential(new DriveMoveDistanceExpNavx(-10,1),1);
//    	addSequential(new DriveBrake(),.4);
//    	addSequential(new TurnTo(0),2);
//    	addSequential(new DriveMoveDistanceExpNavx(150,1),4);
    	
    	addSequential(new AutonRightGear());
    	addSequential(new Move(-10));
    	addSequential(new DriveBrake(),.4);
    	addSequential(new TurnTo(0),2);
    	addSequential(new Move(300).addT(new Bumped(.9)));
    	addSequential(new TurnTo(0),2);
     }
}
