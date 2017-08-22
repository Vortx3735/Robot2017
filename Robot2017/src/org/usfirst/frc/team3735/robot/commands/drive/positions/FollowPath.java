package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FollowPath extends CommandGroup {

    public FollowPath(Location[] locs, boolean rev) {
        for(Location l : locs) {
        	addSequential(new HitWaypoint(l, rev));
        }
    }
    public FollowPath(Location[] locs) {
       this(locs, false);
    }
    
}
