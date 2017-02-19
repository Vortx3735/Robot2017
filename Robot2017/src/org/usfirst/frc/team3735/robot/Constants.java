package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.util.Point;

public class Constants {
	
	public static class PointsLeft {
		public final static double halfLength = RobotDimensions.length/2;
		
		public final static Point startTop = new Point(halfLength, 80.7);
		public final static Point startMiddle = new Point(halfLength, 0);
		public final static Point startBottom = new Point(halfLength, -72.9);
		
		public final static Point topLift = new Point(123.9425, 45.059);
		public final static Point middleLift = new Point(Field.fromStartToGearLiftMiddle-halfLength, 0);
		public final static Point bottomLift = new Point(123.9425, 45.059);
		
		public final static Point retrivalAndBaseLine = new Point(93.25, 126.6);
		public final static Point opSideRetrivalAndBaseLine = new Point(558.7, 126.6);
		
		public final static Point midBackUp = new Point(12+halfLength, Field.midHeight);
		public final static Point crossBottomBaseLine = new Point(140, -117.45);
		
		public final static Point lineUpWithBoiler = new Point(46.65, -117.45);
		public final static Point boiler = new Point(40.373, 38.555);
		public final static Point boilerExact = new Point(0, -162);
		
		public final static Point lineUpWithTopLift = new Point(93.25, 98.34);
		public final static Point lineUpWithBottomLift = new Point(93.25, -98.34);
	}
	
	public class PointsRight {
		
	}
	
	public class Field {
		public final static double toBaseLine = 93.25;
		public final static double toLaunchpadLine = 185.25;
		public final static double baseLineToLaunchpad = 92;
		public final static double fromStartToGearLiftMiddle = 114.75;
		public final static double fromStartToGearLiftSide = 132.375;
		public final static double height = 324;
		public final static double length = 652;
		public final static double midHeight = 162;
	}
	
	public class RobotDimensions {
		public final static double length = 33.65;
		public final static double width = 40;
		public final static double height = 23.5;
	}
	
	public class Drive{
		public final static double acceleration = 1;
		public final static double cruiseVelocity = 4;
		public static final double wheelDiameter = 4;
	}
	
	public class GearIntake{
		public final static double rollerOutSpeed = .5;
		public final static double rollerInSpeed = -.5;
		public final static double feedingSpeed = -.5;
		
		public static final double dropOffRollDelay = .5;
		public static final double dropOffJerkDelay = .3;
		public static final double dropOffEndDelay = .5;

	}
	
	public class Scaler{
		public final static double upCurrent = 1.0;
		public final static double powerMax = 100;
	}
	
	public class BallIntake{
		public final static double rollerInSpeed = .5;
	}
	
	public class Shooter{
		public final static double shootSpeed = .67;
	}
}
