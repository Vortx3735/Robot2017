package org.usfirst.frc.team3735.robot.settings;

import org.usfirst.frc.team3735.robot.util.profiling.Location;

public class Waypoints {
	public static final Location center = new Location(Dms.Field.HALFLENGTH, 0);
	public static final Location topRight = new Location(Dms.Field.LENGTH, Dms.Field.HALFWIDTH);
	public static final Location topLeft = new Location(0, Dms.Field.HALFWIDTH);
	public static final Location bottomRight = new Location(Dms.Field.LENGTH, -Dms.Field.HALFWIDTH);
	public static final Location bottomLeft = new Location(0, -Dms.Field.HALFWIDTH);

	
	public static final Location middlePeg =  new Location(Dms.Field.AirShip.DISTANCEFROMWALL, 0);
	public static final Location upperPeg = middlePeg
			.appendYawDistance(-90, Dms.Field.AirShip.HALFSL)
			.appendYawDistance(30, Dms.Field.AirShip.HALFSL);
	public static final Location lower = middlePeg
			.appendYawDistance(90, Dms.Field.AirShip.HALFSL)
			.appendYawDistance(-30, Dms.Field.AirShip.HALFSL);
	
	public static final Location middlePegLineup = middlePeg.appendYawDistance(180, 70);
	public static final Location upperPegLineup = middlePeg.appendYawDistance(-120, 70);
	public static final Location lowerPegLineup = middlePeg.appendYawDistance(120, 70);
	
	
	public static final Location pegSquareUpTop = center.appendXY(-100, 100);
	public static final Location pegSquareUpBottom = center.appendXY(-100, -100);
	public static final Location keySquareUpTop = center.appendXY(100, 100);
	public static final Location keySquareUpBottom = center.appendXY(100, -100);
	
	public static final Location keyLeft = topRight.appendXY(-100, -100);
	public static final Location keyBottom = topRight.appendXY(-50, -200);
	public static final Location keyBottomPath1 = bottomRight.appendXY(-200, 70);
	public static final Location keyBottomPath2 = bottomRight.appendXY(-70, 200);
	
	public static final Location[] centerFieldSquares = new Location[] {
			pegSquareUpTop, pegSquareUpBottom, keySquareUpTop, keySquareUpBottom
	};
	public static final Location[] toTopGear= {pegSquareUpTop, upperPegLineup};
	public static final Location[] toMiddleGearTop= {pegSquareUpTop, upperPegLineup, middlePegLineup};
	public static final Location[] toMiddleGearBottom= {pegSquareUpBottom, lowerPegLineup, middlePegLineup};
	public static final Location[] toBottomGear= {pegSquareUpBottom, lowerPegLineup};
	
	public static final Location[] toKeyBottom= {keySquareUpBottom, keyBottomPath1, keyBottomPath2, keyBottom};
	public static final Location[] toKeyLeft= {keySquareUpTop, keyLeft};


}
