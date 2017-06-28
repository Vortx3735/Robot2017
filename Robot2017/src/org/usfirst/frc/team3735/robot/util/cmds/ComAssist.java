package org.usfirst.frc.team3735.robot.util.cmds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ComAssist {
	
	public ArrayList<Subsystem> requirements;

	public void initialize(){
		
	}
	public void execute(){
		
	}
	
	public void requires(Subsystem s){
		requirements.add(s);
	}
}
