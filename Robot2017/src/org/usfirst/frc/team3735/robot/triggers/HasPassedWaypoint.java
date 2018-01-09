package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;
import org.usfirst.frc.team3735.robot.util.profiling.Line;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

public class HasPassedWaypoint extends ComTrigger{
	

	private Location targetLocation;
	private Location fromLocation;
	public Line toCross;
	private boolean isTopRightofLine;

	public HasPassedWaypoint(Location loc){
		this(loc, null);
	}
	
	public HasPassedWaypoint(Location target, Location from){
		this.targetLocation = target;
		fromLocation = from;
	}
	

	@Override
	public void initialize() {
		Location from;
		if(fromLocation == null) {
			from = Robot.navigation.getPosition();
		}else {
			from = fromLocation;
		}
		Line toWaypoint = new Line(from, targetLocation);
		toCross = toWaypoint.getPerpendicular(targetLocation);
		isTopRightofLine = evaluateLocation();
	}



	private boolean evaluateLocation() {
		Location currentPos = Robot.navigation.getPosition();
		if(toCross.isVertical) {
			return currentPos.x > toCross.xOffset;
		}else {
			return currentPos.y > toCross.func(currentPos.x);
		}
	}



	@Override
	public boolean get() {
		return evaluateLocation() != isTopRightofLine;
	}
	
	@Override
	public String getHaltMessage() {
		return "Passed Waypoint (" + targetLocation.x + ", " + targetLocation.y + ")";
	}
	
}
