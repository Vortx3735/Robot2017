package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.commands.ScalerDown;
import org.usfirst.frc.team3735.robot.commands.ScalerUp;
import org.usfirst.frc.team3735.robot.commands.ShooterMotorTest;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeRollersIn;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
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
		
		a.toggleWhenPressed(new GearIntakeRollersIn());
		rb.whileHeld(new ScalerUp());
		rt.whileHeld(new ScalerDown());
		b.whenPressed(new ShooterMotorTest());
	
		
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
