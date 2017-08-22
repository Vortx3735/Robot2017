package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.settings.Waypoints;
import org.usfirst.frc.team3735.robot.util.Func;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GoToCenterField extends CommandGroup {

    public GoToCenterField() {
    	addSequential(new TurnTo(new Func() {
			@Override
			public double get() {
				Location targetLocation = Robot.navigation.getClosestLocation(Waypoints.centerFieldSquares);
	    		return Robot.navigation.getAngleToLocationCorrected(targetLocation);
			}
    	}));
    	addSequential(new HitCenterField());
    }
}
