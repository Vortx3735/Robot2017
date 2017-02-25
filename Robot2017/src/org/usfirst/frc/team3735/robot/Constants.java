package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.util.Point;

public class Constants {
	
	public class Drive{
		public final static double acceleration = 1;	//???
		public final static double cruiseVelocity = 4;	//???
		public static final double wheelDiameter = 4.5;	//in inches
		
		public static final double scaledMaxOutput = 1;	//(0,1] this is directly to the motor percentage
		public static final double turnCorrection = 0;

		//normal drive - but we usually use EXP!!!! this does nothing right now
		public static final double sensitivity = .5;	//raise for sharper turns
		
		//exp drive
		public static final double moveReactivity = .150;	//(0,1] (least reactive, most reactive]
		public static final double turnReactivity = .200;	//(0,1] (least reactive, most reactive]	
		public static final double scaledMaxMove = .5;
		public static final double scaledMaxTurn = .400;	//(0,1] directly to the arcadedrive turn value
		//these retain the range but shift more of the action towards lower values as the exponent is raised higher
		//graph y = x * x^(p-1) {-1 < x < 1} for visualization
		public static final double moveExponent = 2;			//[1,inf) 1 is linear, 2 is squared (normal), etc.
		public static final double turnExponent = 4;			//[1,inf) 
		
		//power for using the driveMoveDistanceTimed exeriment
		public static final double timedPower = .4;
		
		//time for the turnToAngle command to finish being on target
		public static final double turnFinishTime = .5;
	}
	
	public class GearIntake{
		public final static double rollerOutVoltage = 6;
		public final static double rollerInVoltage = -6;
		public final static double feedingVoltage = -6;
		
		//constants for dropping off the gear
		public static final double dropOffRollDelay = .5;
		public static final double dropOffJerkDelay = .3;
		public static final double dropOffEndDelay = .5;
		public static final double dropOffDriveTime = .6;
		public static final double dropOffTotalTime = 
			dropOffRollDelay + dropOffJerkDelay + dropOffEndDelay + dropOffDriveTime;
		public static final double dropOffDrivePercent = -1;

	}
	
	public class Scaler{
		public final static double upCurrent = -1.0;
		public final static double powerMax = 1000; //350;
	}
	
	public class BallIntake{
		public final static double rollerInSpeed = -.7;
	}
	
	public class Shooter{
		public final static double shootVoltage = -8.5;
		public static final double agitatorVoltage = -3;
	}

	public class RobotDimensions {
		public final static double length = 33.65;
		public final static double width = 40;
		public final static double height = 23.5;
	}

	public static class PointsLeft {
		public final static double halfLength = RobotDimensions.length/2;
		
		public final static Point startTop = new Point(halfLength, 80.7);
		public final static Point startMiddle = new Point(halfLength, 0);
		public final static Point startBottom = new Point(halfLength, -72.9);
		
		public final static Point topLift = new Point(123.9425, 45.059);
		public final static Point middleLift = new Point(Field.fromStartToGearLiftMiddle-halfLength, 0);
		public final static Point bottomLift = new Point(123.9425, 45.059);
		
		public final static Point retrivalAndBaseLine = new Point(93.25, 126.6);
		public final static Point opSideRetrivalAndBaseLine = new Point(420, 126.6);
		
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

}
