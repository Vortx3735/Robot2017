package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceExp extends CommandGroup {

    private double deltaDistance;
	private double leftTargetDistance;
	private double rightTargetDistance;

	public DriveMoveDistanceExp(double power, double distance) {
		
    	deltaDistance = distance;
    	power = Math.abs(power);
    	if(distance < 0){
    		 power *= -1;
    	}
    	addSequential(new ExpDrive(power,0){
    		@Override
    		public boolean isFinished(){
    			return(VortxMath.isWithinThreshold(Robot.drive.getLeftPositionInches(), leftTargetDistance, 2) ||
    				   VortxMath.isWithinThreshold(Robot.drive.getRightPositionInches(), rightTargetDistance, 2));
    		}
    	});
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }

	@Override
	protected void initialize() {
		leftTargetDistance = Robot.drive.getLeftPositionInches() + deltaDistance;
		rightTargetDistance = Robot.drive.getRightPositionInches() + deltaDistance;
	}
    
    
}
