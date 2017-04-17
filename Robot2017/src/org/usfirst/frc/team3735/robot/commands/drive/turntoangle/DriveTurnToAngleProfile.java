package org.usfirst.frc.team3735.robot.commands.drive.turntoangle;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.TrapProfile;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnToAngleProfile extends Command {

    private static final double V = 1;
	private static final double A = 1;
	private TrapProfile profile;
	private double targetAngle;
	private double startAngle;
	private int cont = 0;
	private double contAdd = 360;
	
	PIDController p;
	public DriveTurnToAngleProfile(double angle) {
    	targetAngle = angle;
    	requires(Robot.drive);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startAngle = Robot.navigation.getYaw();
    	if(targetAngle - startAngle > 180){
    		cont  = -1;
    	}else if(targetAngle - startAngle < -180){
    		cont = 1;
    	}
    	profile = new TrapProfile(startAngle, targetAngle + cont * contAdd, V, A){
    		@Override
    		public double speedToOutput(double v){
    			return v;
    		}
    	};
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double yaw = Robot.navigation.getYaw();
		if(cont != 0){
			if(cont == -1 && yaw > 0){
				yaw += cont * contAdd;
			}
			if(cont == 1 && yaw < 0){
				yaw += cont * contAdd;
			}
		}
    	profile.step(yaw);
    	Robot.drive.setLeftRightOutputs(profile.getOutput(), -profile.getOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return profile.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setLeftRightOutputs(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
