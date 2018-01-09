package org.usfirst.frc.team3735.robot.ois;

//import org.usfirst.frc.team3735.robot.commands.DriveTurnToAngleHyperbola;
import org.usfirst.frc.team3735.robot.commands.*;
import org.usfirst.frc.team3735.robot.commands.drive.*;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveLeft;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveRight;
import org.usfirst.frc.team3735.robot.commands.gearintake.*;
import org.usfirst.frc.team3735.robot.commands.scaler.*;
import org.usfirst.frc.team3735.robot.commands.sequences.DriveGoToPeg;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;


import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.util.oi.DriveOI;
import org.usfirst.frc.team3735.robot.util.oi.JoystickPOVButton;
import org.usfirst.frc.team3735.robot.util.oi.JoystickTriggerButton;
import org.usfirst.frc.team3735.robot.util.oi.XboxController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NormieOI implements DriveOI{

	public XboxController main;
	public XboxController co;

	public NormieOI() {

		main = new XboxController(0);
		co = new XboxController(1);

		//Baby Driver
		//main.pov180.whenPressed(new DriveGoToPeg());

		
		main.x.whileHeld(new DriveAddSensitiveLeft());
		main.y.whileHeld(new DriveAddSensitiveRight());
		
		main.lt.whenPressed(new GearIntakeDropOff());
		main.rt.whileHeld(new GearIntakeFeeding());
		
		//CoDriver

		
		co.start.whenPressed(new InterruptOperations());
		
		co.y.whenPressed(new ScalerUp(1));
		co.x.whenPressed(new ScalerOff());
		co.a.whileHeld(new GearIntakeRollersIn());
		co.b.whileHeld(new GearIntakeRollersOut());



	}
	
	@Override
	public double getDriveMove() {
		return main.getLeftY();
	}

	@Override
	public double getDriveTurn() {
		return main.getRightX();
	}
	
	@Override
	public double getFODMag() {
		return 0;
	}
	
	@Override
	public double getFODAngle(){
		return 0;
	}

	@Override
	public boolean isOverriddenByDrive(){
		return Math.abs(getDriveMove()) > .1 || Math.abs(getDriveTurn()) > .1;
	}

	@Override
	public void log() {
//		SmartDashboard.putNumber("right joystick angle", getMainRightAngle());
//		SmartDashboard.putNumber("right joystick magnitude",
//				getMainRightMagnitude());

	}




}
