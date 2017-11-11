package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;

public class Bumped extends ComTrigger{
	
	private Double acc = new Double(1);

	public Bumped(Double acc){
		this.acc = acc;
	}
	
	public Bumped(double acc){
		this(new Double(acc));
	}
	
	public Bumped(){
	}

	@Override
	public boolean get() {
		return Math.abs(Robot.navigation.getXYAcceleration()) > acc;
	}

	@Override
	public String getHaltMessage() {
		return "bumped " + acc + " acc";
	}
	
	
	
	
}
