package org.usfirst.frc.team3735.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	Joystick joy = new Joystick(0);
	Joystick coJoy = new Joystick(1);	
	public OI(){
		
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
		
		//Button Mapping for codriver joy-stick
		Button cX = new JoystickButton(coJoy,1);
		Button cA = new JoystickButton(coJoy,2);
		Button cB = new JoystickButton(coJoy,3);
		Button cY = new JoystickButton(coJoy,4);
		Button cLB = new JoystickButton(coJoy,5);
		Button cRB = new JoystickButton(coJoy,6);
		Button cLT = new JoystickButton(coJoy,7);
		Button cRT = new JoystickButton(coJoy,8);
		Button cBack = new JoystickButton(coJoy,9);
		Button cStart = new JoystickButton(coJoy,10);
		Button cLS = new JoystickButton(coJoy,11);
		Button cRS = new JoystickButton(coJoy,12);
	}
	
	public double getMainLeftX(){
		return joy.getX();
	}
	public double getMainLeftY(){
		return joy.getY();
	}
	public double getMainRightX(){
		return joy.getTwist();
	}
	public double getMainRightY(){
		return joy.getThrottle();
	}
	
	public double getCoLeftX(){
		return joy.getX();
	}
	public double getCoLeftY(){
		return joy.getY();
	}
	public double getCoRightX(){
		return joy.getTwist();
	}
	public double getCoRightY(){
		return joy.getThrottle();
	}
}
