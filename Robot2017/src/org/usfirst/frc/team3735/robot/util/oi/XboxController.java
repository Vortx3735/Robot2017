package org.usfirst.frc.team3735.robot.util.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/*
 * A standard interface for interacting with a controller that is programmed as shown
 */
public class XboxController extends Joystick{
	public Button a,b,x,y,lb,rb,back,start,ls,rs,lt,rt;
	public Button pov0,pov45,pov90,pov135,pov180,pov225,pov270,pov315;
	
	public XboxController(int p){
		super(p);
		a = new JoystickButton(this, 1);
		b = new JoystickButton(this, 2);
		x = new JoystickButton(this, 3);
		y = new JoystickButton(this, 4);
		lb = new JoystickButton(this, 5);
		rb = new JoystickButton(this, 6);
		back = new JoystickButton(this, 7);
		start = new JoystickButton(this, 8);
		ls = new JoystickButton(this, 9);
		rs = new JoystickButton(this, 10);
		lt = new JoystickTriggerButton(this, false, .3);
		rt = new JoystickTriggerButton(this, true, .3);

		pov0 = new JoystickPOVButton(this, 0);
		pov45 = new JoystickPOVButton(this, 45);
		pov90 = new JoystickPOVButton(this, 90);
		pov135 = new JoystickPOVButton(this, 135);
		pov180 = new JoystickPOVButton(this, 180);
		pov225 = new JoystickPOVButton(this, 225);
		pov270 = new JoystickPOVButton(this, 270);
		pov315 = new JoystickPOVButton(this, 315);
	}
	
	public double getLeftX() {
		return getX();
	}
	public double getLeftY() {
		return getY() * -1;
	}
	public double getLeftMagnitude() {
		return Math.hypot(getLeftX(), getLeftY());
	}
	public double getLeftAngle() {
		return Math.toDegrees(Math.atan2(getLeftX(), getLeftY()));
	}
	
	

	public double getRightX() {
		return getRawAxis(4);
	}
	public double getRightY() {
		return getRawAxis(5) * -1;
	}
	public double getRightMagnitude() {
		return Math.hypot(getRightX(), getRightY());
	}
	public double getRightAngle() {
		return Math.toDegrees(Math.atan2(getRightX(), getRightY()));
	}

	
	
	public double getLeftTrigger() {
		return getZ();
	}
	public double getRightTrigger() {
		return getThrottle();
	}
	
	
	
	public void setLRRumble(double left, double right){
		super.setRumble(RumbleType.kLeftRumble, left);
		super.setRumble(RumbleType.kRightRumble, right);
	}
	
	
	
}
