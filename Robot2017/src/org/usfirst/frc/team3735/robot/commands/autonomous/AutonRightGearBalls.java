package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonRightGearBalls extends CommandGroup {

    public AutonRightGearBalls() {
    	addSequential(new AutonRightGear(),4);
    	addSequential(new Wait(.4));
    	addSequential(new DriveMoveDistanceExpNavx(-30,1),2);
    	addSequential(new TurnTo(135),2);
    	//addSequential(new DriveMoveDistanceExpNavx(40,1,-135));
    	addSequential(new ShooterAgitatorOn(30000,10),8);
    }
}
