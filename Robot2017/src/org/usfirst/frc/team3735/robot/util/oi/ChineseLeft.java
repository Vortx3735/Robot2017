package org.usfirst.frc.team3735.robot.util.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/*
 * A standard interface for interacting with a controller that is programmed as shown
 */
public class ChineseLeft extends Joystick{
	public Button a,b,c,d,e,f,g,h,i,j;
	
	public Button lJoyButton, lSwitch, lWhiteButton, rWhiteButton, rSwitchMiddle, rSwitchLeft;
	
	public ChineseLeft(int q){
		super(q);
		lJoyButton = new JoystickButton(this, 1);
		rSwitchMiddle = new JoystickButton(this, 12);
		rSwitchLeft = new JoystickButton(this, 13);
		rWhiteButton = new JoystickButton(this, 14);
		lWhiteButton = new JoystickButton(this, 15);
		lSwitch = new JoystickButton(this, 15);
		
		a = new JoystickButton(this, 4);
		b = new JoystickButton(this, 3);
		c = new JoystickButton(this, 5);
		d = new JoystickButton(this, 9);
		e = new JoystickButton(this, 8);
		f = new JoystickButton(this, 2);
		g = new JoystickButton(this, 10);
		h = new JoystickButton(this, 7);
		i = new JoystickButton(this, 6);
		j = new JoystickButton(this, 11);
		
	}
	
	public double getLeftX() {
		return getRawAxis(0);
	}
	public double getLeftY() {
		return getRawAxis(1);
	}
	public double getLeftMagnitude() {
		return Math.hypot(getLeftX(), getLeftY());
	}
	public double getLeftAngle() {
		return Math.toDegrees(Math.atan2(getLeftX(), getLeftY()));
	}
	public double getLeftZ() {
		return getRawAxis(2) * 50;
	}
	
}