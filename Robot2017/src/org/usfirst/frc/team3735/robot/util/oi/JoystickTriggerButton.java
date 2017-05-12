package org.usfirst.frc.team3735.robot.util.oi;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class JoystickTriggerButton extends Button {

	  private final Joystick m_joystick;
	  private final double m_threshold;
	  private final boolean m_right;
	
	  /**
	   * Create a joystick button for triggering commands.
	   *
	   * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
	   *                     etc)
	   * @param right		 true if right trigger, false if left
	   * 
	   * @param threshold    the threshold for the trigger (0,1]
	   */
	  public JoystickTriggerButton(Joystick joystick, boolean right, double threshold) {
	    m_joystick = joystick;
	    m_threshold = threshold;
	    m_right = right;
	    
	  }
	
	  /**
	   * Gets the value of the joystick button.
	   *
	   * @return The value of the joystick button
	   */
	  public boolean get() {
	    if(m_right){
	    	return m_joystick.getThrottle() > m_threshold;
	    }else{
	    	return m_joystick.getZ() > m_threshold;
	    }
	  }
}
