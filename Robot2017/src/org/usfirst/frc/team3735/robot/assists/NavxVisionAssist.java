package org.usfirst.frc.team3735.robot.assists;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Vision;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.util.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.ComAssist;
import org.usfirst.frc.team3735.robot.util.settings.Setting;

public class NavxVisionAssist extends ComAssist{

    private Pipes pipe;
	private double prevWorking = 0;

	public NavxVisionAssist(Pipes p){
		this.pipe = p;
    	prevWorking = 0;
    	requires(Robot.vision);
    	requires(Robot.navigation);
	}
	
	@Override
	public void initialize() {
		Robot.vision.setMainHandler(pipe);
		prevWorking = 0;
	}

	@Override
	public void execute() {
		double input = Robot.vision.getRelativeCX();
    	if(input != Vision.nullValue){
    		if(input != prevWorking){
		    	Robot.navigation.getController().setSetpoint(
		    			VortxMath.continuousLimit(
	        				Robot.navigation.getYaw() + (input * Robot.vision.dpp.getValue()),
	        				-180, 180)
				);
				prevWorking = input;
			}
    		Robot.drive.setVisionAssist((Robot.navigation.getController().getError()/180.0) * Navigation.navVisCo.getValue());
    	}else{
    		Robot.drive.setVisionAssist(0);
    	}
	}
	
	@Override
	public void end(){
		Robot.drive.setVisionAssist(0);
	}
	
//doy mun fuh = ???
//sing chow em = hello
//em depp
	
}
