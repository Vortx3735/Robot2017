package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOnAgitatorHigh;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGearBalls extends CommandGroup {

    public AutonLeftGearBalls() {
    	addSequential(new AutonLeftGear());
    	addSequential(new DriveTurnToAnglePID(-135));
    	addSequential(new ShooterOnAgitatorHigh(),9);
    }
}
