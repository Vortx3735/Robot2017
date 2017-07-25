package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TurnFollowPath extends CommandGroup {

    public TurnFollowPath(Location[] locs) {
    	addSequential(new DriveTurnToAnglePIDCtrl(locs[0]),1);
        addSequential(new FollowPath(locs));
    }
}
