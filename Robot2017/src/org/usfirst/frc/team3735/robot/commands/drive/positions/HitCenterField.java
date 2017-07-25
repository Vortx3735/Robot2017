package org.usfirst.frc.team3735.robot.commands.drive.positions;


import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Waypoints;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

public class HitCenterField extends HitWaypoint{
	public HitCenterField() {
		super(Waypoints.center, false);
	}

	@Override
	protected void initialize() {
		target = Robot.navigation.getClosestLocation(Waypoints.centerFieldSquares);
		super.initialize();
	}
	

	
	
}
