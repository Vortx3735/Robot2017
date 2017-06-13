package org.usfirst.frc.team3735.robot.util.oi;

public interface DriveOI {
	
	public double getDriveMove();
	public double getDriveTurn();
	
	public double getFODMag();
	public double getFODAngle();

	public boolean isOverriddenByDrive();
	
	public void log();
	
}
