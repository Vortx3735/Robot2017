package org.usfirst.frc.team3735.robot.util.cmds;

/*
 * A class that can be added to a VortxCommand.
 * when get returns true, the command will halt.
 * For example, a trigger for moving a certain distance would record the initial
 * distances on initialize, and get() will return true when it is finished
 */
public abstract class ComTrigger {
	

	
	public abstract boolean get();

	public void initialize(){
		
	}
	
}
