package org.usfirst.frc.team3735.robot.ois;

import org.usfirst.frc.team3735.robot.commands.InterruptOperations;
import org.usfirst.frc.team3735.robot.commands.ballintake.BallIntakeRollerIn;
import org.usfirst.frc.team3735.robot.commands.ballintake.BallIntakeRollerOff;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveLeft;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveRight;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveChangeToBallDirection;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveChangeToGearDirection;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeFeeding;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeRollersIn;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeRollersOut;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerOff;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerUp;
import org.usfirst.frc.team3735.robot.commands.sequences.DriveGoToPeg;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOff;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOn;
//import org.usfirst.frc.team3735.robot.util.oi.DriveOI;
import org.usfirst.frc.team3735.robot.util.oi.ChineseBoard;
import org.usfirst.frc.team3735.robot.util.oi.DriveOI;

public class ChineseOI implements DriveOI{
	
	ChineseBoard joystick;
	
	public ChineseOI() {
		joystick = new ChineseBoard();
		joystick.l.whenPressed(new DriveGoToPeg());
		joystick.mJoyButton.whenPressed(new GearIntakeDropOff());
		joystick.rJoyButton.whileHeld(new GearIntakeFeeding());
		//main.x.whileHeld(new DriveAddSensitiveLeft());
		//main.y.whileHeld(new DriveAddSensitiveRight());
		joystick.k.whenPressed(new DriveChangeToGearDirection());
		joystick.m.whenPressed(new DriveChangeToBallDirection());
		//Co-Driver
		joystick.lWhiteButton.whenPressed(new ScalerUp(1));
		joystick.rWhiteButton.whenPressed(new ScalerOff());
		joystick.a.whileHeld(new GearIntakeRollersIn());
		joystick.b.whileHeld(new GearIntakeRollersOut());
		joystick.c.whenPressed(new ShooterAgitatorOn(31000, 10));
		joystick.d.whenPressed(new ShooterAgitatorOn(28000, 10));
		joystick.e.whenPressed(new ShooterAgitatorOn(25000, 10));
		joystick.f.whenPressed(new ShooterAgitatorOff());	
		joystick.g.whenPressed(new BallIntakeRollerOff());
		joystick.h.whenPressed(new BallIntakeRollerIn());
		joystick.j.whenPressed(new InterruptOperations());
	}

	public double getDriveMove() {
		return getMainRightY();
	}

	 
	public double getDriveTurn() {
		return getMainRightX()* + getMainLeftX() + getMainRightZ()*.6;
	}
	
	public double getMainRightMagnitude() {
		return joystick.getMiddleMagnitude();
	}
	
	public boolean isOverriddenByDrive(){
		return Math.abs(getMainLeftX()) > .1 || getDriveMove() > .1;
	}

	// returns the angle of the right main joystick in degrees in the range
	// (-180, 180]
	public double getMainRightAngle() {
		return joystick.getMiddleAngle();
	}

	 
	public double getMainLeftX() {
		return joystick.getMiddleX();
	}

	 
	public double getMainLeftY() {
		return joystick.getMiddleY();
	}
	
	public double getMainLeftZ() {
		return joystick.getMiddleZ();
	}
	
	public double getMainRightZ() {
		return joystick.getRightZ();
	}

	 
	public double getMainRightX() {
		return joystick.getRightX();
	}

	 
	public double getMainRightY() {
		return joystick.getRightY();
	}
	 
	public double getCoLeftX() {
		return joystick.getLeftX();
	}

	 
	public double getCoLeftY() {
		return joystick.getLeftY();
	}

	@Override
	public double getFODMag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFODAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub
		
	}

}
