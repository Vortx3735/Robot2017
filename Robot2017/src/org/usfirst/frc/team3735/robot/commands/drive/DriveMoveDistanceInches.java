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

	static double displacementInches;
	static double startPositionLeftInches;
	static double startPositionRightInches;
	static double endPositionLeftInches;
	static double endPositionRightInches;
	static double spleft;
	static double spright;

	static double adjustment;

	static boolean done = false;

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
    	Robot.drive.setPIDSettings(0.1,0.00015,0);
    	
        Timer.delay(0.02);
    	
    	Robot.drive.setupDriveForPositionControl();
    	done = false;

		if (displacementInches>0)
        	adjustment=0.33;
        else 
        	adjustment=-0.33;
    }

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Math.abs(Robot.drive.getInchesPositionLeftInches()-startPositionLeftInches) < 4.0f 
			&&  Math.abs(Robot.drive.getInchesPositionRightInches()-startPositionRightInches) < 4.0f){
			spleft += adjustment;
			spright += adjustment;
			Robot.drive.setLeftRightDistance(spleft, spright);
			//Robot.drive.setLeftRightDistance(endPositionLeftInches,endPositionRightInches);
			System.out.println("Stepping");

		} else {
			if(Math.abs(Robot.drive.getInchesPositionLeftInches()-endPositionLeftInches) < 2.0
			&& 	Math.abs(Robot.drive.getInchesPositionRightInches()-endPositionRightInches) < 2.0){
				done = true;
			}
			System.out.println("Waiting to Reach");
			Robot.drive.setLeftRightDistance(endPositionLeftInches,endPositionRightInches);

		}
		
//		if (this.isTimedOut()){
//			done = true;
//
//		}
	
		SmartDashboard.putNumber("CmdActLPos", Robot.drive.getRotationsLeft());
		SmartDashboard.putNumber("CmdActRPos", Robot.drive.getRotationsRight());

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
		Robot.drive.setUpDriveForSpeedControl();
		Robot.drive.arcadeDrive(-0.5*adjustment, 0, false);
		Timer.delay(0.04);
//		Robot.drive.arcadeDrive(-0.5*adjustment, 0, false);
//     	Timer.delay(0.02);
		Robot.drive.arcadeDrive(0, 0, false);

		// Robot.drive.arcadeDrive(0, 0, false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
