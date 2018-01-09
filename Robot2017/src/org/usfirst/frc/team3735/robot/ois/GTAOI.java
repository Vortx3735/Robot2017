package org.usfirst.frc.team3735.robot.ois;

//import org.usfirst.frc.team3735.robot.commands.DriveTurnToAngleHyperbola;
import org.usfirst.frc.team3735.robot.commands.*;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveLeft;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveRight;
import org.usfirst.frc.team3735.robot.commands.gearintake.*;
import org.usfirst.frc.team3735.robot.commands.scaler.*;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.util.oi.DriveOI;
import org.usfirst.frc.team3735.robot.util.oi.XboxController;

public class GTAOI implements DriveOI{

	public XboxController main;
	public XboxController co;

	public GTAOI() {

		main = new XboxController(0);
		co = new XboxController(1);
		main.rb.get();
		//Baby Driver
		//main.pov180.whenPressed(new DriveGoToPeg());
		main.pov90.whileHeld(new DriveAddSensitiveRight());
		main.pov270.whileHeld(new DriveAddSensitiveLeft());


		main.b.whenPressed(new GearIntakeDropOff());
		main.a.whileHeld(new GearIntakeFeeding());
		main.x.whenPressed(new GearIntakeLiftDown());
		main.y.whenPressed(new GearIntakeLiftUp());
		main.start.whenPressed(new ExpDrive());
//		main.x.whileHeld(new DriveAddSensitiveLeft());
//		main.y.whileHeld(new DriveAddSensitiveRight());

//		main.start.whenPressed(new DriveChangeToGearDirection());
//		main.back.whenPressed(new DriveChangeToBallDirection());
		
		//CoDriver
		co.y.whenPressed(new ScalerUp(1));
		co.x.whenPressed(new ScalerOff());
		co.a.whileHeld(new GearIntakeRollersIn());
		co.b.whileHeld(new GearIntakeRollersOut());


		
		co.start.whenPressed(new InterruptOperations());
		
		
		
	}
	
	
	public double getDriveMove() {
		return (main.getRightTrigger() - main.getLeftTrigger());
		//return main.getLeftY();
	}

	public double getDriveTurn() {
		return main.getLeftX();
		//return main.getRightX();
	}
	
	@Override
	public double getFODMag() {
		//return main.getRightMagnitude();
		return 0;
	}
	
	public double getFODAngle(){
		//return main.getRightAngle();
		return 0;
	}

	
	public boolean isOverriddenByDrive(){
		return Math.abs(getDriveMove()) > .4 || Math.abs(getDriveTurn()) > .4;
	}

	
	public void log() {
//		SmartDashboard.putNumber("right joystick angle", getMainRightAngle());
//		SmartDashboard.putNumber("right joystick magnitude",
//				getMainRightMagnitude());

	}




}
