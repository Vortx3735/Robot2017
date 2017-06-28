package org.usfirst.frc.team3735.robot.assists;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComAssist;

public class NavxAssist extends ComAssist{
	
	Double angle;
	
	public NavxAssist(Double yaw){
		angle = yaw;
	}
	
	public NavxAssist(){
		
	}
	
	@Override
	public void initialize() {
		if(angle == null){
			Robot.navigation.getController().setSetpoint(Robot.navigation.getYaw());
		}else{
			Robot.navigation.getController().setSetpoint(angle.doubleValue());
		}
	}

	@Override
	public void execute() {
		Robot.drive.setNavxAssist(Robot.navigation.getController().getError());
	}
	
}
