package org.usfirst.frc.team3735.robot.util.oi;

	import edu.wpi.first.wpilibj.Joystick;
	import edu.wpi.first.wpilibj.buttons.Button;
	import edu.wpi.first.wpilibj.buttons.JoystickButton;

	/*
	 * A standard interface for interacting with a controller that is programmed as shown
	 */
	public class ChineseRight extends Joystick{
		public Button lJoyButton, left, middle, right, rJoyButton;
		
		public ChineseRight(int p){
			super(p);
			lJoyButton = new JoystickButton(this, 1);
			rJoyButton = new JoystickButton(this, 2);
			left = new JoystickButton(this, 3);
			middle = new JoystickButton(this, 4);
			right = new JoystickButton(this, 5);
			
		}
		
		public double getLeftX() {
			return getRawAxis(0)*1.25;
		}
		public double getLeftY() {
			return getRawAxis(1) * 1.25;
		}
		public double getLeftMagnitude() {
			return Math.hypot(getLeftX(), getLeftY());
		}
		public double getLeftAngle() {
			return Math.toDegrees(Math.atan2(getLeftX(), getLeftY()));
		}
		public double getLeftZ() {
			return getRawAxis(2);
		}
		
		

		public double getRightX() {
			return getRawAxis(3);
		}
		public double getRightY() {
			return getRawAxis(4);
		}
		public double getRightMagnitude() {
			return Math.hypot(getRightX(), getRightY());
		}
		public double getRightAngle() {
			return Math.toDegrees(Math.atan2(getRightX(), getRightY()));
		}
		public double getRightZ() {
			return getRawAxis(5);
		}
		
	}