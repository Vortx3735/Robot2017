//values for the practice robot

//package org.usfirst.frc.team3735.robot;
//
//public class RobotMap {
//
//	public static class Drive{
//		public static int leftMotor1 =		4;
//		public static int leftMotor2 = 		5;
//		public static int leftMotor3 = 		6;
//		
//		public static int rightMotor1 = 	1;
//		public static int rightMotor2 = 	2;
//		public static int rightMotor3 = 	3;
//	}
//	public static class GearIntake{
//		public static int topRoller = 		9;
//		public static int bottomRoller = 	7;
//		public static int topFeedSolenoid =			1;
//		public static int liftSolenoid = 			0;
//	}
//	public static class Shooter{
//		public static int drum = 			12;
//		public static int drum2 = 			13;
//		public static int agitator =   		8;
//		public static int encoder1 = 			0;
//		public static int encoder2 = 			1;
//	}
//	public static class BallIntake{
//		public static int roller = 			11;
//	}
//	public static class Scaler{
//		public static int motor = 			10;
//	}
//		
//}

//values for the final robot

package org.usfirst.frc.team3735.robot;

public class RobotMap {

	public static class Drive{
		public static int leftMotor1 =		4;//4
		public static int leftMotor2 = 		5;//5
		public static int leftMotor3 = 		6;//6
		
		public static int rightMotor1 = 	10;//10
		public static int rightMotor2 = 	11;//11
		public static int rightMotor3 = 	12;//12
	}
	public static class GearIntake{
		public static final boolean topRollerInverted = true;
		public static int topRoller = 		8;
		//public static int bottomRoller = 	9;
		public static int topFeedSolenoid =			0;
		public static int liftSolenoid = 			1;
	}
	public static class Shooter{
		public static final boolean drumInverted = false;
		public static final boolean agitatorInverted = false;
		public static int drum = 			2;
		public static int drum2 = 			7;
		public static int agitator =   		13;
//		public static int encoder1 = 			0;
//		public static int encoder2 = 			1;
	}
	public static class BallIntake{
		public static int roller = 			1;
		public static final boolean rollerInverted = false;
	}
	public static class Scaler{
		public static final boolean scalerInverted = false;
		public static int motor = 			3;
	}
		
}
