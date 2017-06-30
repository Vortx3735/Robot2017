package org.usfirst.frc.team3735.robot.assists;

import java.util.ArrayList;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Vision;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.util.cmds.ComAssist;

import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionAssist extends ComAssist{
	
	Pipes pipe;
	private double prevWorking = 0;

	public VisionAssist(Pipes p){
		requires(Robot.vision);
		this.pipe = p;
	}

	@Override
	public void initialize() {
    	Robot.vision.setMainHandler(pipe);
	}

	@Override
	public void execute() {
		double in = Robot.vision.getRelativeCX();
    	if(in == Vision.nullValue){
    		Robot.drive.setVisionAssist(0);
    	}else{
    		prevWorking = in;
        	Robot.drive.setVisionAssist(in * .0025);
    	}
	}
	
	
}
