package org.usfirst.frc.team3735.robot.commands.drive.spinnyspin;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveSpinMove extends CommandGroup {

    protected static final double yawThreshold = 30;
	private double targetAngle = 0;
    private double move = 1;

	public DriveSpinMove(double turn) {
        addSequential(new ExpDrive(move, turn){
        	@Override
        	public boolean isFinished(){
        		return VortxMath.isWithinThreshold(Robot.navigation.getYaw(), targetAngle, yawThreshold);
        	}
        });
        addSequential(new DriveMoveDistanceExpNavx(100, 1, null, .7));
    }

	@Override
	protected void initialize() {
		targetAngle  = VortxMath.continuousLimit(Robot.navigation.getYaw() + 180, -180, 180);
		move = move * Math.signum(Robot.drive.getLeftSpeed() + Robot.drive.getRightSpeed());
	}
	
	@Override
	protected boolean isFinished() {
		return super.isFinished() || Robot.oi.isOverriddenByDrive();
	}
    
    
}
