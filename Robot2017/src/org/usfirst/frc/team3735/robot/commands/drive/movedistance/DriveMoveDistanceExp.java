package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceExp extends CommandGroup {

    private double deltaDistance;
	private double leftTargetDistance;
	private double rightTargetDistance;

	public DriveMoveDistanceExp(double distance, double power) {
		
    	deltaDistance = distance;
    	power = Math.signum(distance) * Math.abs(power);
    	
    	addSequential(new ExpDrive(power,0){
    		@Override
    		public boolean isFinished(){
    			return(VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(), leftTargetDistance, 2) ||
    				   VortxMath.isWithinThreshold(Robot.drive.getRightPositionInches(), rightTargetDistance, 2));
    		}
    	});
    }

	@Override
	protected void initialize() {
		leftTargetDistance = Robot.drive.getLeftPositionInches() + deltaDistance;
		rightTargetDistance = Robot.drive.getRightPositionInches() + deltaDistance;
	}
    
    
}
