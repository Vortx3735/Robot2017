package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.triggers.HasPassedWaypoint;
import org.usfirst.frc.team3735.robot.util.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.VortxCommand;
import org.usfirst.frc.team3735.robot.util.profiling.Location;
import org.usfirst.frc.team3735.robot.util.profiling.Position;

public class HitWaypoint extends VortxCommand{
	
	public Location target;
	private double speed;
	private boolean isReversed;
	
	public HitWaypoint(Location target, boolean rev) {
		this(target, null, rev);
		
	}
	
	
	public HitWaypoint(Location target, Location from, boolean rev) {
		requires(Robot.drive);
		requires(Robot.navigation);
		this.target = target;
		if(from == null)
		addTrigger(new HasPassedWaypoint(target, from));
		isReversed = rev;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void execute() {
		super.execute();
		Position p = Robot.navigation.getPosition();
		
		//moving
		
		
		//turning
		double targetAngle = Math.toDegrees(-Math.atan2(target.y - p.y, target.x - p.x));
		if(isReversed) {
			targetAngle = VortxMath.continuousLimit(targetAngle + 180, -180, 180);
		}
		Robot.navigation.getController().setSetpoint(targetAngle);
		
		Robot.drive.setNavxAssist(Robot.navigation.getController().getError());
		Robot.drive.limitedDrive(1,0);
		
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
