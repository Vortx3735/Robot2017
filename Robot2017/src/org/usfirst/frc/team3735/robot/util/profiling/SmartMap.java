package org.usfirst.frc.team3735.robot.util.profiling;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmartMap {
	
	private static final NetworkTable table = NetworkTable.getTable("Map");

	private static Position RobotPos;
	
	private static ArrayList<Position> positions = new ArrayList<Position>();
	private static double[] posxs;
	private static double[] posys;
	private static double[] angles;
	
	private static ArrayList<Location> locations = new ArrayList<Location>();
	private static double[] locxs;
	private static double[] locys;
	
	
	public static void putRobot(Position p) {
		RobotPos = p;
	}
	
	public static void putPosition(Position pos) {
		positions.add(pos);
		displayPositions();
	}
	
	public static void highLightPosition(Position pos) {
		if(pos != null) {
	    	table.putNumberArray("highlightedX", new double[]{RobotPos.x});
			table.putNumberArray("highlightedY", new double[]{RobotPos.y});
			table.putNumberArray("highlightedYaw", new double[]{RobotPos.yaw});
		}else {
	    	table.putNumberArray("highlightedX", new double[]{});
			table.putNumberArray("highlightedY", new double[]{});
			table.putNumberArray("highlightedYaw", new double[]{});
		}
	}
	
	public static void displayPositions() {
		posxs = new double[positions.size()];
		posys = new double[positions.size()];
		angles = new double[positions.size()];

		for(int i = 0; i < positions.size(); i++) {
			posxs[i] = positions.get(i).x;
			posys[i] = positions.get(i).y;
			angles[i] = positions.get(i).yaw;
		}
		
		table.putNumberArray("posX", posxs);
		table.putNumberArray("posY", posys);
		table.putNumberArray("posYaw", angles);	
	}

	public static void putLocation(Location loc) {
		locations.add(loc);
		displayLocations();
	}
	
	public static void highLightLocation(Location loc) {
		if(loc != null) {
	    	table.putNumberArray("highlightedLocX", new double[]{RobotPos.x});
			table.putNumberArray("highlightedLocY", new double[]{RobotPos.y});
		}else {
	    	table.putNumberArray("highlightedLocX", new double[]{});
			table.putNumberArray("highlightedLocY", new double[]{});
		}
	}
	
	public static void displayLocations() {
		locxs = new double[locations.size()];
		locys = new double[locations.size()];

		for(int i = 0; i < locations.size(); i++) {
			locxs[i] = locations.get(i).x;
			locys[i] = locations.get(i).y;
		}
		
		table.putNumberArray("locX", locxs);
		table.putNumberArray("locY", locys);
	}
	

	
	public static void displayRobot() {
		if(RobotPos != null) {
	    	table.putNumberArray("robotX", new double[]{RobotPos.x});
			table.putNumberArray("robotY", new double[]{RobotPos.y});
			table.putNumberArray("robotYaw", new double[]{RobotPos.yaw});
		}
	}
	
	public static void clear() {
		locations.clear();
		displayLocations();
		positions.clear();
		displayPositions();
	}
}
