package org.usfirst.frc.team3735.robot.commands.drive.positions;

import java.awt.geom.Line2D;

import org.usfirst.frc.team3735.robot.util.profiling.Location;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FollowPathTight extends CommandGroup {

	public FollowPathTight(Location[] locs) {
		this(locs, false);
	}
	
    public FollowPathTight(Location[] locs, boolean rev) {
    	addSequential(new HitWaypoint(locs[0], rev));
        for(int i = 1; i < locs.length; i++) {
        	addSequential(new HitWaypoint(locs[i], locs[i-1], rev));
        }
    }
    
    

}
