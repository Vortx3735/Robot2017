package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOn;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonMiddleGearLeftBalls extends CommandGroup {

    public AutonMiddleGearLeftBalls(){
    	addSequential(new AutonMiddleGear());
    	addSequential(new DriveMoveDistanceExpNavx(-10,1),1);
    	addSequential(new TurnTo(-105),2);
    	addSequential(new DriveMoveDistanceExpNavx(40,.8,new Double(-105),.4));
    	addSequential(new ShooterAgitatorOn(28000, 10),10);
    	
     }
}
