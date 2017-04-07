package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.TrapProfile;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveMoveDistanceProfile extends Command {

    private static final double V = 0;
	private static final double A = 0;
	
	private double deltaDistance;
	
	private TrapProfile lProfile;
	private TrapProfile rProfile;

	private double lStartPosition;
	private double rStartPosition;

	public DriveMoveDistanceProfile(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	deltaDistance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lStartPosition = Robot.drive.getLeftPositionInches();
    	rStartPosition = Robot.drive.getRightPositionInches();
    	Robot.drive.changeControlMode(TalonControlMode.Voltage);
    	
    	lProfile = new TrapProfile(lStartPosition, lStartPosition + deltaDistance, V, A){
    		@Override
    		public double speedToOutput(double v){
    			return v;
    		}
    	};
    	rProfile = new TrapProfile(rStartPosition, rStartPosition + deltaDistance, V, A){
    		@Override
    		public double speedToOutput(double v){
    			return v;
    		}
    	};

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lProfile.step(Robot.drive.getLeftPositionInches());
    	rProfile.step(Robot.drive.getRightPositionInches());

    	Robot.drive.setLeft(lProfile.getOutput());
    	Robot.drive.setRight(rProfile.getOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return lProfile.isFinished() && rProfile.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
