package org.usfirst.frc.team3735.robot.util.profiling;

public class Location {
	public double x,y;
	
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	public Location appendYawDistance(double yaw, double distance) {
		double ang = Math.toRadians(-yaw);
		return new Location(x + Math.cos(ang), y + Math.sin(ang));
	}
	
	public Location appendXY(double addX, double addY) {
		return new Location(x + addX, y + addY);
	}
	
	public double distanceFrom(Location other) {
		return Math.hypot(this.x - other.x, this.y - other.y);
	}
	
}
