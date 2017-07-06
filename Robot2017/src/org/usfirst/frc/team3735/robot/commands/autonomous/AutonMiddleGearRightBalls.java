package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOn;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonMiddleGearRightBalls extends CommandGroup {

    public AutonMiddleGearRightBalls(){
    	addSequential(new AutonMiddleGear());
    	addSequential(new DriveTurnToAnglePIDCtrl(120));
    	addSequential(new DriveMoveDistanceExpNavx(40,.8,new Double(120),.4));
    	addSequential(new ShooterAgitatorOn(30000, 10),10);
     }
}
