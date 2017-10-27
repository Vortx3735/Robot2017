package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;

import edu.wpi.first.wpilibj.command.Command;

public class TimeOut extends ComTrigger{
	
	private Double timeout;
	private Command parent;

	public TimeOut(Double timeout, Command p){
		this.timeout = timeout;
		parent = p;
	}
	
	public TimeOut(double acc, Command p){
		this(new Double(acc), p);
	}


	@Override
	public boolean get() {
		return parent.timeSinceInitialized() > timeout;
	}

	@Override
	public String getHaltMessage() {
		return "timed out by " + timeout + "seconds";
	}
	
	
	
	
}
