package org.usfirst.frc.team3735.robot.util.oi;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class JoystickPOVButton extends Button {

	  private final GenericHID m_joystick;
	  private final int m_angle;
	
	  /**
	   * Create a joystick button for triggering commands.
	   *
	   * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
	   *                     etc)
	   * @param angle		 The angle of the pov button. North is 0, then 45, 90, etc. clockwise
	   */
	  public JoystickPOVButton(GenericHID joystick, int angle) {
	    m_joystick = joystick;
	    m_angle = angle;
	  }
	
	  /**
	   * Gets the value of the joystick button.
	   *
	   * @return The value of the joystick button
	   */
	  public boolean get() {
	    return m_joystick.getPOV() == m_angle;
	  }
}
