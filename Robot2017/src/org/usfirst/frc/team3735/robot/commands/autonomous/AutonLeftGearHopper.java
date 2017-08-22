package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxBumped;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOnAgitatorHigh;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGearHopper extends CommandGroup {

    public AutonLeftGearHopper() {
    	addSequential(new AutonLeftGear());
    	addSequential(new TurnTo(-90));
    	addSequential(new DriveMoveDistanceExpNavxBumped(80,1),3);
    	addSequential(new Wait(2));
    	addSequential(new DriveMoveDistanceExpNavx(-20,.7),1);
    	addSequential(new TurnTo(-5),1);
    	addSequential(new DriveMoveDistanceExpNavx(100,1),3);

    }
}
