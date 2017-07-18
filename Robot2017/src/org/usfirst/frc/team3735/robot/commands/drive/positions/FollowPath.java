package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FollowPath extends CommandGroup {

    public FollowPath(Location[] locs) {
        for(Location l : locs) {
        	addSequential(new HitWaypoint(l));
        }
    }
}
