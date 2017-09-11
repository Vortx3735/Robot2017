package org.usfirst.frc.team3735.robot.util.profiling;

import java.util.ArrayList;

import org.usfirst.frc.team3735.robot.Robot.Side;
import org.usfirst.frc.team3735.robot.settings.Dms;

public class Location {
	public double x,y;
	private static ArrayList<Location> staticLocations = new ArrayList<Location>();
	private static boolean onLeftSide = true;
	
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Location(double x, double y, boolean flag) {
		this(x,y);
		if(flag)staticLocations.add(this);
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
	
	public static void changeSide(Side side) {
		if((side == Side.Left) != onLeftSide) {
			for(Location loc : staticLocations) {
				loc.x = Dms.Field.LENGTH - loc.x;
			}
			onLeftSide = !onLeftSide;
		}
	}
	
}
