package org.usfirst.frc.team3735.robot.util.oi;

//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
//import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Chinese extends ChineseLeft {
	
	private ChineseRight right;
	
	public Button mJoyButton, rJoyButton, k,l,m;
	
	
	public Chinese() {
		super(1);
		right = new ChineseRight(0);
		
		mJoyButton = right.lJoyButton;
		rJoyButton = right.rJoyButton;
		k = right.left;
		l = right.middle;
		m = right.right;
	}
	
	public double getMiddleX() {
		return right.getLeftX();
	}
	public double getMiddleY() {
		return right.getLeftY();
	}
	public double getMiddleMagnitude() {
		return right.getLeftMagnitude();
	}
	public double getMiddleAngle() {
		return right.getLeftAngle();
	}
	public double getMiddleZ() {
		return right.getLeftZ();
	}
	
	

	public double getRightX() {
		return right.getRightX();
	}
	public double getRightY() {
		return right.getRightY();
	}
	public double getRightMagnitude() {
		return right.getRightMagnitude();
	}
	public double getRightAngle() {
		return right.getRightAngle();
	}
	public double getRightZ() {
		return right.getRightZ();
	}
}
