package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;
import org.usfirst.frc.team3735.robot.util.profiling.Line;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

public class HasPassedWaypoint extends ComTrigger{
	

	private Location location;
	private Line toCross;
	private boolean isTopRightofLine;

	public HasPassedWaypoint(Location loc){
		this.location = loc;
	}
	
	

	@Override
	public void initialize() {
		Line toWaypoint = new Line(Robot.navigation.getPosition(), location);
		toCross = toWaypoint.getPerpendicular(location);
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
	
	
}
