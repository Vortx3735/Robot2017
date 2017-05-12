package org.usfirst.frc.team3735.robot.util.cmds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;

/*
 * A class that allows users to make triggers, that one can add to a command, that
 * modify when a command should halt. This cycles through all of the added triggers,
 * and halts the command when one of their "get" methods returns true.
 * This requires the subclass to call super.initialize() and super.isFinished() in
 * the corresponding subclass methods.
 */
public class VortxCommand extends Command{

	ArrayList<ComTrigger> triggers = new ArrayList<ComTrigger>();
	
	@Override
	protected void initialize() {
		for(ComTrigger t : triggers){
			t.initialize();
		}
	}

	@Override
	protected boolean isFinished() {
		for(ComTrigger t : triggers){
			if(t.get()){
				return true;
			}
		}
		return false;
	}
	
	public VortxCommand addTrigger(ComTrigger t){
		triggers.add(t);
		return this;
	}
	
}
