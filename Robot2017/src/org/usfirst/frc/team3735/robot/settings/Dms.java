package org.usfirst.frc.team3735.robot.settings;

//it stands for Dimensions if you haven't figured that out
public class Dms {
	//all in inches

	public class Bot{
		public class DriveBase{
			//???
			public static final double WIDTH = 28;
			public static final double HALFWIDTH = WIDTH/2.0;
			public static final double LENGTH = 31;
			public static final double HALFLENGTH = LENGTH/2.0;
		}
		public static final double WIDTH = 37;
		public static final double HALFWIDTH = WIDTH/2.0;
		public static final double LENGTH = 31;
		public static final double HALFLENGTH = LENGTH/2.0;
		
	}
	
	public class Field{
		//just guesses rn
		public static final double LENGTH = 653;
		public static final double HALFLENGTH = LENGTH/2.0;
		public static final double WIDTH = 324;
		public static final double HALFWIDTH = WIDTH/2.0;
		public static final double BASELINE = 93.3;

		public class AirShip{
			public static final double SIDELENGTH = 0;
			public static final double HALFSL = SIDELENGTH/2.0;
			public static final double DISTANCEFROMWALL = 114;
			public static final double MOUNTDISTANCEFROMWALL = 111;

		}
	}
	
	
	
	
//	  if (rotateValue * moveValue > 0.0) {
//	    leftMotorSpeed = Math.signum(moveValue) * (Math.abs(moveValue) - Math.abs(rotateValue));
//	    rightMotorSpeed = Math.signum(moveValue) * Math.max(Math.abs(moveValue), Math.abs(rotateValue));
//	  } else {
//	    leftMotorSpeed = Math.signum(moveValue) * Math.max(Math.abs(moveValue), Math.abs(rotateValue));
//	    rightMotorSpeed = Math.signum(moveValue) * (Math.abs(moveValue) - Math.abs(rotateValue));
//	  }
	    

	
}
