package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGearBaseline extends CommandGroup {

    public AutonLeftGearBaseline(){
    	addSequential(new AutonLeftGear());
    	addSequential(new Wait(2));
    	addSequential(new DriveMoveDistanceExpNavx(-10,1),1);
    	addSequential(new DriveBrake(),.4);
    	addSequential(new DriveTurnToAnglePIDCtrl(0),2);
    	addSequential(new DriveMoveDistanceExpNavx(150,1),4);
     }
}
