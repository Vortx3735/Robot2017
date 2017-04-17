package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOnAgitatorHigh;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonRightGearBalls extends CommandGroup {

    public AutonRightGearBalls() {
    	addSequential(new AutonRightGear());
    	addSequential(new DriveTurnToAnglePID(135));
    	addSequential(new ShooterOnAgitatorHigh(),9);
    }
}
