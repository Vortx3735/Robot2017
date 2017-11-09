package org.usfirst.frc.team3735.robot.util.cmds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Subsystem;

/*
 * A class that can be added to a VortxCommand.
 * when get returns true, the command will halt.
 * For example, a trigger for moving a certain distance would record the initial
 * distances on initialize, and get() will return true when it is finished
 */
public abstract class ComTrigger {
	
	public ArrayList<Subsystem> requirements = new ArrayList<Subsystem>();

	
	public abstract boolean get();

	public void initialize(){
		
	}
	
	public void execute() {
		
	}
	
	public void requires(Subsystem s){
		requirements.add(s);
	}
	
	public String getHaltMessage() {
		return "";
	}
	
}
