package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.util.DriveOI;
import org.usfirst.frc.team3735.robot.util.JoystickPOVButton;
import org.usfirst.frc.team3735.robot.util.JoystickTriggerButton;
//import org.usfirst.frc.team3735.robot.commands.DriveTurnToAngleHyperbola;
import org.usfirst.frc.team3735.robot.commands.*;
import org.usfirst.frc.team3735.robot.commands.ballintake.*;
import org.usfirst.frc.team3735.robot.commands.drive.*;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrlVision;
import org.usfirst.frc.team3735.robot.commands.gearintake.*;
import org.usfirst.frc.team3735.robot.commands.scaler.*;
import org.usfirst.frc.team3735.robot.commands.shooter.*;
import org.usfirst.frc.team3735.robot.commands.vision.*;


import org.usfirst.frc.team3735.robot.subsystems.Vision2.Pipes;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GTAOI implements DriveOI {

	Joystick joy;
	Joystick cojoy;

	public GTAOI() {

		// joystick port mapping
		joy = new Joystick(0);
		cojoy = new Joystick(1);

		// Button Mapping for driver joy-stick
		Button a = new JoystickButton(joy, 1);
		Button b = new JoystickButton(joy, 2);
		Button x = new JoystickButton(joy, 3);
		Button y = new JoystickButton(joy, 4);
		Button lb = new JoystickButton(joy, 5);
		Button rb = new JoystickButton(joy, 6);
		Button back = new JoystickButton(joy, 7);
		Button start = new JoystickButton(joy, 8);
		Button ls = new JoystickButton(joy, 9);
		Button rs = new JoystickButton(joy, 10);

		Button pov0 = new JoystickPOVButton(joy, 0);
		Button pov45 = new JoystickPOVButton(joy, 45);
		Button pov90 = new JoystickPOVButton(joy, 90);
		Button pov135 = new JoystickPOVButton(joy, 135);
		Button pov180 = new JoystickPOVButton(joy, 180);
		Button pov225 = new JoystickPOVButton(joy, 225);
		Button pov270 = new JoystickPOVButton(joy, 270);
		Button pov315 = new JoystickPOVButton(joy, 315);

		// Button Mapping for codriver joy-stick
		Button ca = new JoystickButton(cojoy, 1);
		Button cb = new JoystickButton(cojoy, 2);
		Button cx = new JoystickButton(cojoy, 3);
		Button cy = new JoystickButton(cojoy, 4);
		Button clb = new JoystickButton(cojoy, 5);
		Button crb = new JoystickButton(cojoy, 6);
		Button cback = new JoystickButton(cojoy, 7);
		Button cstart = new JoystickButton(cojoy, 8);
		Button cls = new JoystickButton(cojoy, 9);
		Button crs = new JoystickButton(cojoy, 10);
		Button clt = new JoystickTriggerButton(cojoy, false, .5);
		Button crt = new JoystickTriggerButton(cojoy, true, .5);

		Button cpov0 = new JoystickPOVButton(cojoy, 0);
		Button cpov45 = new JoystickPOVButton(cojoy, 45);
		Button cpov90 = new JoystickPOVButton(cojoy, 90);
		Button cpov135 = new JoystickPOVButton(cojoy, 135);
		Button cpov180 = new JoystickPOVButton(cojoy, 180);
		Button cpov225 = new JoystickPOVButton(cojoy, 225);
		Button cpov270 = new JoystickPOVButton(cojoy, 270);
		Button cpov315 = new JoystickPOVButton(cojoy, 315);

		// layout for single driver
		// y.toggleWhenPressed(new BallIntakeRollerIn());
		// rb.toggleWhenPressed(new GearIntakeToggleOpenClose());
		// a.whileHeld(new GearIntakeFeeding());
		// b.whenPressed(new GearIntakeDropOff());
		// rb.toggleWhenPressed(new ScalerUp());
		// x.whenPressed(new ShooterSwitchEnabled());
		// lb.whileHeld(new DriveBrake());
		// start.whenPressed(new DriveSwitchDirection());
		// pov0.whenPressed(new DriveTurnToAngle(0));
		// pov45.whenPressed(new DriveTurnToAngle(45));
		// pov90.whenPressed(new DriveTurnToAngle(90));
		// pov135.whenPressed(new DriveTurnToAngle(135));
		// pov180.whenPressed(new DriveTurnToAngle(180));
		// pov225.whenPressed(new DriveTurnToAngle(-135));
		// pov270.whenPressed(new DriveTurnToAngle(-90));
		// pov315.whenPressed(new DriveTurnToAngle(-45));

		
		
		// layout for two drivers
		
		//pov0.whileHeld(new DriveAddVisionAssist(Pipes.Peg));
		pov0.whenPressed(new DriveTurnToAnglePIDCtrlVision());
		//pov180.whileHeld(new DriveAddVisionAssist(Pipes.Gear));
		
		lb.whileHeld(new DriveChangeBrakeMode());
		
		b.whenPressed(new GearIntakeDropOff());
		a.whileHeld(new GearIntakeFeeding());
		x.whileHeld(new DriveAddSensitiveLeft());
		y.whileHeld(new DriveAddSensitiveRight());

		start.whenPressed(new DriveChangeToGearDirection());
		back.whenPressed(new DriveChangeToBallDirection());
		
		
//		pov0.whenPressed(new DriveTurnToAnglePIDCtrl(0));
//		pov45.whenPressed(new DriveTurnToAnglePIDCtrl(60));
//		pov90.whenPressed(new DriveTurnToAnglePIDCtrl(90));
//		pov135.whenPressed(new DriveTurnToAnglePIDCtrl(150));
//		pov180.whenPressed(new DriveTurnToAnglePIDCtrl(180));
//		pov225.whenPressed(new DriveTurnToAnglePIDCtrl(-150));
//		pov270.whenPressed(new DriveTurnToAnglePIDCtrl(-90));
//		pov315.whenPressed(new DriveTurnToAnglePIDCtrl(-60));

//		pov0.whenPressed(new DriveMoveDistanceTwist(0));
//		pov45.whenPressed(new DriveMoveDistanceTwist(60));
//		pov90.whenPressed(new DriveMoveDistanceTwist(90));
//		pov135.whenPressed(new DriveMoveDistanceTwist(120));
//		pov180.whenPressed(new DriveMoveDistanceTwist(180));
//		pov225.whenPressed(new DriveMoveDistanceTwist(-120));
//		pov270.whenPressed(new DriveMoveDistanceTwist(-90));
//		pov315.whenPressed(new DriveMoveDistanceTwist(-60));
		
//		pov0.whenPressed(new DriveTurnToAngle(0));
//		pov45.whenPressed(new DriveTurnToAngle(60));
//		pov90.whenPressed(new DriveTurnToAngle(90));
//		pov135.whenPressed(new DriveTurnToAngle(150));
//		pov180.whenPressed(new DriveTurnToAngle(180));
//		pov225.whenPressed(new DriveTurnToAngle(-150));
//		pov270.whenPressed(new DriveTurnToAngle(-90));
//		pov315.whenPressed(new DriveTurnToAngle(-60));
		
//		pov0.whenPressed(new DriveTurnToAngleHyperbola(0));
//		pov45.whenPressed(new DriveTurnToAngleHyperbola(60));
//		pov90.whenPressed(new DriveTurnToAngleHyperbola(90));
//		pov135.whenPressed(new DriveTurnToAngleHyperbola(150));
//		pov180.whenPressed(new DriveTurnToAngleHyperbola(180));
//		pov225.whenPressed(new DriveTurnToAngleHyperbola(-150));
//		pov270.whenPressed(new DriveTurnToAngleHyperbola(-90));
//		pov315.whenPressed(new DriveTurnToAngleHyperbola(-60));

		
		cy.whenPressed(new ScalerUp(1));
		//crb.whenPressed(new ScalerUp(1));
		cx.whenPressed(new ScalerOff());
		ca.whileHeld(new GearIntakeRollersIn());
		cb.whileHeld(new GearIntakeRollersOut());

		cpov0.whenPressed(new ShooterOnAgitatorHigh());
		cpov90.whenPressed(new ShooterOnAgitatorSmartDash());
		cpov180.whenPressed(new ShooterOnAgitatorLow());
		cpov270.whenPressed(new ShooterOff());
		
		clt.whenPressed(new BallIntakeRollerOff());
		clb.whenPressed(new BallIntakeRollerIn());
		
		cstart.whenPressed(new InterruptOperations());
		
		
		
	}
	
	public boolean isOverriddenByDrive(){
		return Math.abs(getMainLeftX()) > .1 || getMainLeftTrigger() > .1 || getMainRightTrigger() > .1;
	}

	@Override
	public double getMainLeftX() {
		return joy.getX();
	}

	@Override
	public double getMainLeftY() {
		return joy.getY() * -1;
	}

	@Override
	public double getMainRightX() {
		return joy.getRawAxis(4);
	}

	@Override
	public double getMainRightY() {
		return joy.getRawAxis(5) * -1;
	}

	@Override
	public double getMainLeftTrigger() {
		return joy.getZ();
	}

	@Override
	public double getMainRightTrigger() {
		return joy.getThrottle();
	}

	public double getMainRightMagnitude() {
		return Math.hypot(getMainRightX(), getMainRightY());
	}

	// returns the angle of the right main joystick in degrees in the range
	// (-180, 180]
	public double getMainRightAngle() {
		return Math.toDegrees(Math.atan2(getMainRightX(), getMainRightY()));
	}

	@Override
	public double getDriveMove() {
		return (getMainRightTrigger() - getMainLeftTrigger());
	}

	@Override
	public double getDriveTurn() {
		return getMainLeftX();
	}

	public double getCoLeftX() {
		return cojoy.getX();
	}

	public double getCoLeftY() {
		return cojoy.getY() * -1;
	}

	public double getCoRightX() {
		return cojoy.getRawAxis(4);
	}

	public double getCoRightY() {
		return cojoy.getRawAxis(5) * -1;
	}

	public double getCoLeftTrigger() {
		return cojoy.getZ();
	}

	public double getCoRightTrigger() {
		return cojoy.getThrottle();
	}

	@Override
	public void log() {
//		SmartDashboard.putNumber("right joystick angle", getMainRightAngle());
//		SmartDashboard.putNumber("right joystick magnitude",
//				getMainRightMagnitude());

	}

}
