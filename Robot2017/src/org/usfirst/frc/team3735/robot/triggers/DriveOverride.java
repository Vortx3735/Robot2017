package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;

public class DriveOverride extends ComTrigger{
	
	
	public DriveOverride(){
	}

	@Override
	public boolean get() {
		return Robot.oi.isOverriddenByDrive();
	}
	
	@Override
	public String getHaltMessage() {
		return "overriden by drive";
	}
	
	
}
