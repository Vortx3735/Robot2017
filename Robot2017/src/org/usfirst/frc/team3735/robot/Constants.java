package org.usfirst.frc.team3735.robot;

public class Constants {
	
	public class Drive{
		
		public static final double InchesPerRotation = 12.94;	//in inches
		
		
		public static final double scaledMaxOutput = 1;	//(0,1] this is directly to the motor percentage
		public static final double turnCorrection = 0;

		//normal drive - but we usually use EXP!!!! this does nothing right now
		public static final double sensitivity = .5;	//raise for sharper turns
		
		//exp drive
		public static final double moveReactivity = .600;	//(0,1] (least reactive, most reactive]
		public static final double turnReactivity = .700;	//(0,1] (least reactive, most reactive]	
		public static final double scaledMaxMove = 1;
		public static final double scaledMaxTurn = 1;	//(0,1] directly to the arcadedrive turn value
		//these retain the range but shift more of the action towards lower values as the exponent is raised higher
		//graph y = x * x^(p-1) {-1 < x < 1} for visualization
		public static final double moveExponent = 3;		//[1,inf) 1 is linear, 2 is squared (normal), etc.
		public static final double turnExponent = 5;		//[1,inf) 
		
		//power for using the driveMoveDistanceTimed experiment
		public static final double timedPower = .4;			//seconds
		
		//values for turning to angles and driving distance
		public static final double turnFinishTime = .5;		//seconds
		public static final double turnTolerance = .5;		//degrees
		public static final double driveFinishTime = .5;	//seconds
		public static final double driveTolerance = 2;		//inches

		//for turning slowly with lb and rb
		public static final double lowSensitivityLeftTurn = -.2;
		public static final double lowSensitivityRightTurn = .2;

	}
	
	public class GearIntake{
		public final static double rollerOutVoltage = 3;
		public final static double rollerInVoltage = -10;
		public final static double feedingVoltage = -6;
		
		//public final static boolean topRollerInverted = true;
		//public final static boolean bottomRollerInverted = true;

		//constants for dropping off the gear
		public static final double dropOffRollDelay = .5;
		public static final double dropOffJerkDelay = 1;
		public static final double dropOffEndDelay = .5;
		public static final double dropOffDriveTime = .6;
		public static final double dropOffTotalTime = 
			dropOffRollDelay + dropOffJerkDelay + dropOffEndDelay + dropOffDriveTime;
		public static final double dropOffDrivePercent = -.8;

	}
	
	public class Scaler{

		public final static double upCurrent = 1.0;
		public final static double powerMax = 20000; //350;
		//public final static boolean scalerInverted = true;
		public static final double rampRate = .02;
	}
	
	public class BallIntake{
		public final static double rollerInSpeed = .7;
		//public final static boolean rollerInverted = true;
	}
	
	public class Shooter{
		public final static double shootSpeed = 32000;	//in rpms
		
		public static final double lowAgitatorVoltage = 3;
		public static final double agitatorVoltage = 7;
		public static final double highAgitatorVoltage = 12;
		
		//public final static boolean drumInverted = false;
		//public final static boolean agitatorInverted = false;

		
	}

}