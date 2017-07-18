package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.triggers.HasPassedWaypoint;
import org.usfirst.frc.team3735.robot.util.cmds.VortxCommand;
import org.usfirst.frc.team3735.robot.util.profiling.Location;
import org.usfirst.frc.team3735.robot.util.profiling.Position;

public class HitWaypoint extends VortxCommand{
	
	public Location target;
	private double pct;
	private double acc = 1/50;
	
	public HitWaypoint(Location point) {
		requires(Robot.drive);
		requires(Robot.navigation);
		target = point;
		addTrigger(new HasPassedWaypoint(target));
		
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		pct = Robot.drive.getCurrentPercentSpeed();
	}

	@Override
	protected void execute() {
		super.execute();
		Position p = Robot.navigation.getPosition();
		
		//moving
		if(p.distanceFrom(target) < 100) {
			pct -= acc;
		}else {
			pct += acc;
		}
		Robot.drive.normalDrive(Drive.handleDeadband(pct), 0);
		
		
		//turning
		Robot.navigation.getController().setSetpoint(Math.toDegrees(-Math.atan2(target.y - p.y, target.x - p.x)));
		Robot.drive.setNavxAssist(Robot.navigation.getController().getError());
		
	}

	@Override
	protected boolean isFinished() {
		return super.isFinished();
	}

	@Override
	protected void end() {
		super.end();
		Robot.drive.setNavxAssist(0);
	}

	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}


}
