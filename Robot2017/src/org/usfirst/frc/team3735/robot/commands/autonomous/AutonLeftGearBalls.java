package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOn;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOnAgitatorHigh;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGearBalls extends CommandGroup {

    public AutonLeftGearBalls() {
    	addSequential(new AutonLeftGear(),8);
    	addSequential(new Wait(.4));
    	addSequential(new DriveMoveDistanceExpNavx(-50,1),2);
    	addSequential(new DriveTurnToAnglePIDCtrl(-135),2);
    	//addSequential(new DriveMoveDistanceExpNavx(40,1,-135));
    	addSequential(new ShooterAgitatorOn(30000,10),8);
    }
}
