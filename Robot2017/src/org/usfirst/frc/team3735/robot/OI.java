package org.usfirst.frc.team3735.robot;

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
import org.usfirst.frc.team3735.robot.util.DriveOI;
import org.usfirst.frc.team3735.robot.util.JoystickPOVButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI implements DriveOI{
	
	Joystick joy;
	Joystick cojoy;
	
	public OI(){
		
		//joystick port mapping
		joy = new Joystick(0);
		cojoy = new Joystick(1);	
		
		//Button Mapping for driver joy-stick
		Button x = new JoystickButton(joy,1);
		Button a = new JoystickButton(joy,2);
		Button b = new JoystickButton(joy,3);
		Button y = new JoystickButton(joy,4);
		Button lb = new JoystickButton(joy,5);
		Button rb = new JoystickButton(joy,6);
		Button lt = new JoystickButton(joy,7);
		Button rt = new JoystickButton(joy,8);
		Button back = new JoystickButton(joy,9);
		Button start = new JoystickButton(joy,10);
		Button ls = new JoystickButton(joy,11);
		Button rs = new JoystickButton(joy,12);
		
		Button pov0 = new JoystickPOVButton(joy,0);
		Button pov45 = new JoystickPOVButton(joy,45);
		Button pov90 = new JoystickPOVButton(joy,90);
		Button pov135 = new JoystickPOVButton(joy,135);
		Button pov180 = new JoystickPOVButton(joy,180);
		Button pov225 = new JoystickPOVButton(joy,225);
		Button pov270 = new JoystickPOVButton(joy,270);
		Button pov315 = new JoystickPOVButton(joy,315);
		
		//Button Mapping for codriver joy-stick
		Button cX = new JoystickButton(cojoy,1);
		Button cA = new JoystickButton(cojoy,2);
		Button cB = new JoystickButton(cojoy,3);
		Button cY = new JoystickButton(cojoy,4);
		Button cLB = new JoystickButton(cojoy,5);
		Button cRB = new JoystickButton(cojoy,6);
		Button cLT = new JoystickButton(cojoy,7);
		Button cRT = new JoystickButton(cojoy,8);
		Button cBack = new JoystickButton(cojoy,9);
		Button cStart = new JoystickButton(cojoy,10);
		Button cLS = new JoystickButton(cojoy,11);
		Button cRS = new JoystickButton(cojoy,12);
		
		
		b.toggleWhenPressed(new BallIntakeRollerIn());
		
		rb.toggleWhenPressed(new GearIntakeToggleOpenClose());
		rt.whileHeld(new GearIntakeFeeding());
		lt.whenPressed(new GearIntakeDropOff());
		
		a.toggleWhenPressed(new ScalerUp());
		
		y.whenPressed(new ShooterSwitchEnabled());
		
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
	
	
	
//	public double getMainLeftX(){
//		return joy.getX();
//	}
//	public double getMainLeftY(){
//		return joy.getY();
//	}
//	public double getMainRightX(){
//		return joy.getTwist();
//	}
//	public double getMainRightY(){
//		return joy.getThrottle();
//	}
//	
//	public double getCoLeftX(){
//		return joy.getX();
//	}
//	public double getCoLeftY(){
//		return joy.getY();
//	}
//	public double getCoRightX(){
//		return joy.getTwist();
//	}
//	public double getCoRightY(){
//		return joy.getThrottle();
//	}


	@Override
	public double getDriveMove() {
		return getMainLeftY() * -1;
	}
	@Override
	public double getDriveTurn() {
		return getMainRightX();
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
		return joy.getTwist();
	}
	@Override
	public double getMainRightY() {
		return joy.getThrottle();
	}
	@Override
	public double getMainLeftTrigger() {
		return 0;
	}
	@Override
	public double getMainRightTrigger() {
		return 0;
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
		return cojoy.getTwist();
	}
	@Override
	public double getCoRightY() {
		return cojoy.getThrottle();
	}
	@Override
	public double getCoLeftTrigger() {
		return 0;
	}
	@Override
	public double getCoRightTrigger() {
		return 0;
	}

	@Override
	public void log() {
		
	}
	
}
