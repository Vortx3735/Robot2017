package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.util.Point;

public class Constants {
	
	public static class PointsLeft {
		public final static double halfLength = RobotDimensions.length/2;
		public final static Point startTop = new Point(halfLength, 242.7);
		public final static Point startMiddle = new Point(halfLength, 162);
		public final static Point startBottom = new Point(halfLength, 89.1);
		public final static Point topLift = new Point(0-halfLength, 0); //calculate this
		public final static Point middleLift = new Point(Field.startToGearLiftMiddle-halfLength, 162);
		public final static Point bottomLift = new Point(0-halfLength, 0); //calculate this
		public final static Point retrivalAndBaseLine = new Point(93.25, 288.6);
		public final static Point opSideRetrivalAndBaseLine = new Point(558.7, 288.6);
		public final static Point midBackUp = new Point(12+halfLength, 162);
		public final static Point lineUpWithBoiler = new Point(46.65,44.55);
		public final static Point boiler = new Point(0,0); //calculate this
		
	}
	
	public class PointsRight {
		
	}
	
	public class Field {
		public final static double toBaseLine = 93.25;
		public final static double toLaunchpadLine = 185.25;
		public final static double baseLineToLaunchpad = 92;
		public final static double startToGearLiftMiddle = 114.75;
		public final static double startToGearLiftSide = 132.375;
		public final static double height = 324;
		public final static double length = 652;
	}
	
	public class RobotDimensions {
		public final static double length = 33.65;
		public final static double width = 40;
		public final static double height = 23.5;
	}
	
	public class Drive{
		public final static double acceleration = 1;
		public final static double cruiseVelocity = 1;
		public static final double wheelDiameter = 4;
	}
	
	public class GearIntake{
		public final static double rollerOutSpeed = .1;
		public final static double rollerInSpeed = -.1;
		public final static double feedingSpeed = .1;
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
