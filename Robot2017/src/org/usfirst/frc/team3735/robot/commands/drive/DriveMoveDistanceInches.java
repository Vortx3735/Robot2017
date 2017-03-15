package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMoveDistanceInches extends Command {

	private double displacementInches;
	private double startPositionLeftInches;
	private double startPositionRightInches;
	private double endPositionLeftInches;
	private double endPositionRightInches;
	private double spleft;
	private double spright;

	private double adjustment;

	private boolean done = false;
	
	private short donectr =0;

	// private static double P = 1;
	// private static double I = 0;
	// private static double D = 0;
	// private static double F = 0;

	public DriveMoveDistanceInches(double distance) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		this.displacementInches = distance;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
    	startPositionLeftInches = Robot.drive.getInchesPositionLeftInches();
    	startPositionRightInches = Robot.drive.getInchesPositionRightInches();

    	endPositionLeftInches = startPositionLeftInches + displacementInches;
    	endPositionRightInches = startPositionRightInches + displacementInches;
    	
    	spleft = startPositionLeftInches;
    	spright = startPositionRightInches;
    	//this.setTimeout(Math.abs(displacementInches/10));
    	//Robot.drive.setPIDSettings(0.1,0.00025,0);
    	Robot.drive.setPIDSettings(0.02,0.0000,0);
    	
        Timer.delay(0.02);
    	
    	Robot.drive.setupDriveForPositionControl();
    	done = false;
    	donectr = 0;

		if (displacementInches>0)
        	adjustment=0.125;
        else 
        	adjustment=-0.125;
    }

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Math.abs(Robot.drive.getInchesPositionLeftInches()-startPositionLeftInches) < 1.0f 
			&&  Math.abs(Robot.drive.getInchesPositionRightInches()-startPositionRightInches) < 1.0f){
			spleft += adjustment;
			spright += adjustment;
			Robot.drive.setLeftRightDistance(spleft, spright);
			//Robot.drive.setLeftRightDistance(endPositionLeftInches,endPositionRightInches);
			System.out.println("Stepping");

		} else {
			if(Math.abs(Robot.drive.getInchesPositionLeftInches()-endPositionLeftInches) < 10.0
			&& 	Math.abs(Robot.drive.getInchesPositionRightInches()-endPositionRightInches) < 10.0){
				donectr++;
			
			}
			System.out.println("Waiting to Reach");
			
			if (donectr<7)
			{
		    	Robot.drive.setPIDSettings(0.05,0.00000,0);
				Robot.drive.setLeftRightDistance(endPositionLeftInches,endPositionRightInches);
			}
			
			if (donectr>8)
			{
				Robot.drive.arcadeDrive(-0.2*adjustment, 0, false);
			}
			if (donectr>10)
			{
				Robot.drive.arcadeDrive(0, 0, false);
				done = true;
			}
		}
		
//		if (this.isTimedOut()){
//			done = true;
//
//		}
	
		SmartDashboard.putNumber("Cmd get rotations left", Robot.drive.getRotationsLeft());
		SmartDashboard.putNumber("Cmd get rotations right", Robot.drive.getRotationsRight());

		SmartDashboard.putNumber("CmdSPLeft", spleft);
		SmartDashboard.putNumber("CmdSPRight", spright);

		SmartDashboard.putNumber("CmdGetRInches",
				Robot.drive.getInchesPositionRightInches());
		SmartDashboard.putNumber("CmdGetLInches",
				Robot.drive.getInchesPositionLeftInches());

		SmartDashboard.putNumber("CmdSPLEnd", endPositionLeftInches);
		SmartDashboard.putNumber("CmdSPREnd", endPositionRightInches);
		System.out.println("Working");
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done ;
	}

	// Called once after isFinished returns true
	protected void end() {
		/* This is to E Brake we add little power to stop motor coasting*/
		Robot.drive.arcadeDrive(0, 0, false);

		// Robot.drive.arcadeDrive(0, 0, false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
