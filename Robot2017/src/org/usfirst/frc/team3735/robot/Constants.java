package org.usfirst.frc.team3735.robot;

public class Constants {
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
