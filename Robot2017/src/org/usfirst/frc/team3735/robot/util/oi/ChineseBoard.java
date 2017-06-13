package org.usfirst.frc.team3735.robot.util.oi;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
//import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class ChineseBoard {

	private Joystick left;
	private Joystick right;
	
	// left
	public Button a, b, c, d, e, f, g, h, i, j;
	public Button lJoyButton, lSwitch, lWhiteButton, rWhiteButton, rSwitchMiddle, rSwitchLeft;
	
	//right buttons
	public Button mJoyButton, rJoyButton, k, l, m;




	public ChineseBoard() {
		right = new Joystick(0);
		left = new Joystick(1);
		
		//left
		lJoyButton = new JoystickButton(left, 1);
		rSwitchMiddle = new JoystickButton(left, 12);
		rSwitchLeft = new JoystickButton(left, 13);
		rWhiteButton = new JoystickButton(left, 14);
		lWhiteButton = new JoystickButton(left, 15);
		lSwitch = new JoystickButton(left, 15);
		a = new JoystickButton(left, 4);
		b = new JoystickButton(left, 3);
		c = new JoystickButton(left, 5);
		d = new JoystickButton(left, 9);
		e = new JoystickButton(left, 8);
		f = new JoystickButton(left, 2);
		g = new JoystickButton(left, 10);
		h = new JoystickButton(left, 7);
		i = new JoystickButton(left, 6);
		j = new JoystickButton(left, 11);
		
		//right
		mJoyButton = new JoystickButton(right, 1);
		rJoyButton = new JoystickButton(right, 2);
		k = new JoystickButton(right, 3);
		l = new JoystickButton(right, 4);
		m = new JoystickButton(right, 5);
	}
	
	
	public double getLeftX() {
		return left.getRawAxis(0);
	}
	public double getLeftY() {
		return left.getRawAxis(1);
	}
	public double getLeftMagnitude() {
		return Math.hypot(getLeftX(), getLeftY());
	}
	public double getLeftAngle() {
		return Math.toDegrees(Math.atan2(getLeftX(), getLeftY()));
	}
	public double getLeftZ() {
		return left.getRawAxis(2) * 50;
	}
	
	
	public double getMiddleX() {
		return right.getRawAxis(0) * 1.25;
	}
	public double getMiddleY() {
		return right.getRawAxis(1) * 1.25;
	}
	public double getMiddleMagnitude() {
		return Math.hypot(getMiddleX(), getMiddleY());
	}
	public double getMiddleAngle() {
		return Math.toDegrees(Math.atan2(getMiddleX(), getMiddleY()));
	}
	public double getMiddleZ() {
		return right.getRawAxis(2);
	}
	

	public double getRightX() {
		return right.getRawAxis(3);
	}
	public double getRightY() {
		return right.getRawAxis(4);
	}
	public double getRightMagnitude() {
		return Math.hypot(getRightX(), getRightY());
	}
	public double getRightAngle() {
		return Math.toDegrees(Math.atan2(getRightX(), getRightY()));
	}
	public double getRightZ() {
		return right.getRawAxis(5);
	}
}
