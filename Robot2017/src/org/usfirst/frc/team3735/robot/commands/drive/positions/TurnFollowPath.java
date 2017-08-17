package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TurnFollowPath extends CommandGroup {

    public TurnFollowPath(Location[] locs) {
    	addSequential(new TurnTo(locs[0]),1);
        addSequential(new FollowPath(locs));
    }
}
