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
	
	private double maxSpeed = .8;
	private double minSpeed = .3;
	
	public HitWaypoint(Location target, boolean rev) {
		this(target, null, rev);
		
	}
	
	
	public HitWaypoint(Location target, Location from, boolean rev) {
		requires(Robot.drive);
		requires(Robot.navigation);
		this.target = target;
		addT(new HasPassedWaypoint(target, from));
		isReversed = rev;
	}
	
	@Override
	protected void initialize() {
		super.initialize();

	}

	@Override
	protected void execute() {
		super.execute();
		
		setControllerAngle();
		
		double err = Robot.navigation.getController().getError();
		speed =((maxSpeed-minSpeed)*Math.exp(-Math.abs(.05*err))) + minSpeed;
		if(isReversed) {
			speed *= -1;
		}
		Robot.drive.setNavxAssist(VortxMath.limit(err, -40, 40));
		Robot.drive.limitedDrive(speed, 0);
		
		
	}
	
	public void setControllerAngle() {
		Position p = Robot.navigation.getPosition();
		double targetAngle = Math.toDegrees(-Math.atan2(target.y - p.y, target.x - p.x));
		if(isReversed) {
			targetAngle = VortxMath.navLimit(targetAngle + 180);
		}
		Robot.navigation.getController().setSetpoint(targetAngle);
	}

	@Override
	protected boolean isFinished() {
		return super.isFinished() || Robot.navigation.getPosition().distanceFrom(target) < 10;
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
