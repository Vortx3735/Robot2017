package org.usfirst.frc.team3735.robot.util;

public interface DriveOI {
	
	public double getDriveMove();
	
	public double getDriveTurn();
	
	public double getMainLeftX();
	public double getMainLeftY();
	public double getMainRightX();
	public double getMainRightY();
	public double getMainLeftTrigger();
	public double getMainRightTrigger();
	
	public double getCoLeftX();
	public double getCoLeftY();
	public double getCoRightX();
	public double getCoRightY();
	public double getCoLeftTrigger();
	public double getCoRightTrigger();
	
	public void log();
	
}
