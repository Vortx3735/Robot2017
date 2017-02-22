package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.util.DriveOI;
import org.usfirst.frc.team3735.robot.util.JoystickPOVButton;
import org.usfirst.frc.team3735.robot.commands.ballintake.BallIntakeRollerIn;
import org.usfirst.frc.team3735.robot.commands.drive.DriveChangeScaledMaxOutput;
import org.usfirst.frc.team3735.robot.commands.drive.DriveSwitchDirection;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAngle;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeFeeding;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeToggleOpenClose;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerUp;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOff;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOn;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterSwitchEnabled;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GTAOI implements DriveOI{
	
	Joystick joy;
	Joystick cojoy;
	
	public GTAOI(){
		
		
		//joystick port mapping
		joy = new Joystick(0);
		cojoy = new Joystick(1);	
		
		//Button Mapping for driver joy-stick
		//currently mapped for next level shit
		Button a = new JoystickButton(joy,1);
		Button b = new JoystickButton(joy,2);
		Button x = new JoystickButton(joy,3);
		Button y = new JoystickButton(joy,4);
		Button lb = new JoystickButton(joy,5);
		Button rb = new JoystickButton(joy,6);
		Button back = new JoystickButton(joy,7);
		Button start = new JoystickButton(joy,8);
		Button ls = new JoystickButton(joy,9);
		Button rs = new JoystickButton(joy,10);
		
		Button pov0 = new JoystickPOVButton(joy,0);
		Button pov45 = new JoystickPOVButton(joy,45);
		Button pov90 = new JoystickPOVButton(joy,90);
		Button pov135 = new JoystickPOVButton(joy,135);
		Button pov180 = new JoystickPOVButton(joy,180);
		Button pov225 = new JoystickPOVButton(joy,225);
		Button pov270 = new JoystickPOVButton(joy,270);
		Button pov315 = new JoystickPOVButton(joy,315);
		
		//Button Mapping for codriver joy-stick
		//currently mapped for normal mode
//		Button cX = new JoystickButton(cojoy,1);
//		Button cA = new JoystickButton(cojoy,2);
//		Button cB = new JoystickButton(cojoy,3);
//		Button cY = new JoystickButton(cojoy,4);
//		Button cLB = new JoystickButton(cojoy,5);
//		Button cRB = new JoystickButton(cojoy,6);
//		Button cLT = new JoystickButton(cojoy,7);
//		Button cRT = new JoystickButton(cojoy,8);
//		Button cBack = new JoystickButton(cojoy,9);
//		Button cStart = new JoystickButton(cojoy,10);
//		Button cLS = new JoystickButton(cojoy,11);
//		Button cRS = new JoystickButton(cojoy,12);
		
		//Button Mapping for codriver joy-stick
		//currently mapped for next level shit
		Button cA = new JoystickButton(cojoy,1);
		Button cB = new JoystickButton(cojoy,2);
		Button cX = new JoystickButton(cojoy,3);
		Button cY = new JoystickButton(cojoy,4);
		Button cLB = new JoystickButton(cojoy,5);
		Button cRB = new JoystickButton(cojoy,6);
		Button cBack = new JoystickButton(cojoy,7);
		Button cStart = new JoystickButton(cojoy,8);
		Button cLS = new JoystickButton(cojoy,9);
		Button cRS = new JoystickButton(cojoy,10);
		
		Button cpov0 = new JoystickPOVButton(cojoy,0);
		Button cpov45 = new JoystickPOVButton(cojoy,45);
		Button cpov90 = new JoystickPOVButton(cojoy,90);
		Button cpov135 = new JoystickPOVButton(cojoy,135);
		Button cpov180 = new JoystickPOVButton(cojoy,180);
		Button cpov225 = new JoystickPOVButton(cojoy,225);
		Button cpov270 = new JoystickPOVButton(cojoy,270);
		Button cpov315 = new JoystickPOVButton(cojoy,315);
		
		y.toggleWhenPressed(new BallIntakeRollerIn());
		
//		rb.toggleWhenPressed(new GearIntakeToggleOpenClose());
		a.whileHeld(new GearIntakeFeeding());
		b.whenPressed(new GearIntakeDropOff());
		
		rb.toggleWhenPressed(new ScalerUp());
		
		x.whenPressed(new ShooterSwitchEnabled());
		
		start.whenPressed(new DriveSwitchDirection());
		back.toggleWhenPressed(new DriveChangeScaledMaxOutput());
		
		pov0.whenPressed(new DriveTurnToAngle(0));
		pov45.whenPressed(new DriveTurnToAngle(45));
		pov90.whenPressed(new DriveTurnToAngle(90));
		pov135.whenPressed(new DriveTurnToAngle(135));
		pov180.whenPressed(new DriveTurnToAngle(180));
		pov225.whenPressed(new DriveTurnToAngle(-135));
		pov270.whenPressed(new DriveTurnToAngle(-90));
		pov315.whenPressed(new DriveTurnToAngle(-45));
		
		
	}

	@Override
	public double getMainLeftX() {
		return joy.getX();
	}
	@Override
	public double getMainLeftY() {
		return joy.getY();
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
	

	@Override
	public double getDriveMove() {
		return  (getMainRightTrigger() - getMainLeftTrigger());
	}
	@Override
	public double getDriveTurn() {
		return getMainLeftX();
	}
	
	
	
	
	@Override
	public double getCoLeftX() {
		return cojoy.getX();
	}
	@Override
	public double getCoLeftY() {
		return cojoy.getY();
	}
	@Override
	public double getCoRightX() {
		return cojoy.getRawAxis(4);
	}
	@Override
	public double getCoRightY() {
		return cojoy.getRawAxis(5) * -1;
	}
	@Override
	public double getCoLeftTrigger() {
		return cojoy.getZ();
	}
	@Override
	public double getCoRightTrigger() {
		return cojoy.getThrottle();
	}

	
	
	@Override
	public void log() {
//		SmartDashboard.putNumber("Get X:", joy.getX());
//		SmartDashboard.putNumber("Get Y:", joy.getY());
//		SmartDashboard.putNumber("Get Twist:", joy.getTwist());
//		SmartDashboard.putNumber("Get Throttle:", joy.getThrottle());
//		SmartDashboard.putNumber("Get Dicection Degrees:", joy.getDirectionDegrees());
//		SmartDashboard.putNumber("Get Magnitutde:", joy.getMagnitude());
//		SmartDashboard.putNumber("Get Z:", joy.getZ());
//		SmartDashboard.putBoolean("Get Top:", joy.getTop());
//		SmartDashboard.putNumber("Get POV:", joy.getPOV());
//		SmartDashboard.putNumber("Get Count:", joy.getAxisCount());
//		//SmartDashboard.putData("values ", AxisType.values());
//		SmartDashboard.putNumber("raw axis 4", joy.getRawAxis(4));
//		SmartDashboard.putNumber("raw axis 5", joy.getRawAxis(5));
//		SmartDashboard.putNumber("raw axis 6", joy.getRawAxis(6));

		
	}



	
}
