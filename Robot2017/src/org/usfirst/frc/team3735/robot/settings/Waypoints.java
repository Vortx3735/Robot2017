package org.usfirst.frc.team3735.robot.settings;

import org.usfirst.frc.team3735.robot.util.profiling.Location;

public class Waypoints {
	public static final Location center = new Location(Dms.Field.HALFLENGTH, 0);
	public static final Location topRight = new Location(Dms.Field.LENGTH, Dms.Field.HALFWIDTH);
	public static final Location topLeft = new Location(0, Dms.Field.HALFWIDTH);
	public static final Location bottomRight = new Location(Dms.Field.LENGTH, -Dms.Field.HALFWIDTH);
	public static final Location bottomLeft = new Location(0, -Dms.Field.HALFWIDTH);

	public static final Location Boiler = new Location(6, -155);
	public static final Location MiddleRope = new Location(88, 0);
	public static final Location upperRope = new Location(180, 53);
	public static final Location BottomRope = new Location(180, 53);
	public static final Location middlePeg =  new Location(Dms.Field.AirShip.DISTANCEFROMWALL, 0);
	public static final Location upperPeg = middlePeg
			.appendYawDistance(-90, Dms.Field.AirShip.HALFSL)
			.appendYawDistance(30, Dms.Field.AirShip.HALFSL);
	public static final Location lowerPeg = middlePeg
			.appendYawDistance(90, Dms.Field.AirShip.HALFSL)
			.appendYawDistance(-30, Dms.Field.AirShip.HALFSL);
	
	public static final Location middlePegLineup = middlePeg.appendYawDistance(180, 70);
	public static final Location upperPegLineup = upperPeg.appendYawDistance(-120, 70);
	public static final Location lowerPegLineup = lowerPeg.appendYawDistance(120, 70);
	
	
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
	
	
	//generated from the PathDrawer program
	public static final Location[] topGear = {new Location(33.8,103.8, true), new Location(61.0,100.4, true), new Location(80.6,94.2, true), new Location(95.3,83.9, true), new Location(108.1,68.1, true), new Location(121.9,45.3, true)};
	public static final Location[] bottomGear = {new Location(33.8,-108.7, true), new Location(60.5,-103.5, true), new Location(79.7,-95.9, true), new Location(93.2,-86.1, true), new Location(103.2,-74.1, true), new Location(111.8,-59.9, true), new Location(121.1,-43.7, true)};
	public static final Location[] topGearHopper = {new Location(95.6,75.0, true), new Location(86.3,92.8, true), new Location(83.2,107.0, true), new Location(86.1,117.8, true), new Location(94.9,125.8, true), new Location(109.5,131.3, true), new Location(129.8,134.5, true), new Location(155.7,135.9, true), new Location(187.0,135.9, true)};
	public static final Location[] bottomGearHopper = {new Location(88.1,-81.5, true), new Location(70.7,-102.5, true), new Location(63.9,-118.6, true), new Location(66.8,-130.1, true), new Location(78.6,-137.3, true), new Location(98.4,-140.2, true), new Location(125.2,-139.2, true)};
	public static final Location[] crossField = {new Location(83.2,-108.7, true), new Location(109.3,-110.4, true), new Location(133.4,-110.7, true), new Location(155.7,-109.7, true), new Location(176.3,-107.5, true), new Location(195.4,-104.2, true), new Location(213.0,-99.8, true), new Location(229.4,-94.5, true), new Location(244.6,-88.2, true), new Location(258.7,-81.1, true), new Location(271.9,-73.2, true), new Location(284.3,-64.6, true), new Location(296.1,-55.4, true), new Location(307.3,-45.6, true), new Location(318.1,-35.4, true), new Location(328.6,-24.7, true), new Location(339.0,-13.8, true), new Location(349.3,-2.5, true), new Location(359.8,8.9, true), new Location(370.4,20.5, true), new Location(381.4,32.1, true), new Location(392.9,43.7, true)};

}
