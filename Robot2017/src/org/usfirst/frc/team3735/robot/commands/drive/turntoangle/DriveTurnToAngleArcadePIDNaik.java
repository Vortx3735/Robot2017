package org.usfirst.frc.team3735.robot.commands.drive.turntoangle;

import org.usfirst.frc.team3735.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class DriveTurnToAngleArcadePIDNaik extends Command  implements PIDOutput{

	/**************** MAKE ADJUSTMENTS TO THESE CONSTANTS AND ENABLE DISABLES */
	public static final boolean ISCONSOLEDEBUG_ENABLED = true;
	public static final boolean ISSMARTDASHDEBUG_ENABLED = true;
	
	final static double kP = 0.0150;
	final static double kI  = 0.0; //0.0005;
	final static double kD = 0.005;
	final static double kF  = 0.005;
	static final double kToleranceDegrees = 1.0;

	PIDController turnController;
	private double setpointangle;
    double rotateToAngleRate;           // Current rotation rate
    short timeOnTarget=0;

	
	public DriveTurnToAngleArcadePIDNaik(double angle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		turnController = new PIDController(kP, kI, kD, kF, Robot.navigation, this);
		turnController.setInputRange(-180.0f,  180.0f);
	    turnController.setOutputRange(-0.25, 0.25);
	    turnController.setAbsoluteTolerance(kToleranceDegrees);
	    turnController.setContinuous(true);
	    setpointangle = angle;
	}

	

	// Called just before this Command runs the first time
	protected void initialize() {
		//Robot.navigation.zeroYaw();
		turnController.reset();
		turnController.setSetpoint(setpointangle);
		turnController.enable();
		timeOnTarget=0;
		 
	}


	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.arcadeDrive(0, rotateToAngleRate, false);
		showDashTestInfo();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		boolean done = false;
		if (Math.abs(Robot.navigation.getRate())< 5 && Math.abs(rotateToAngleRate)<0.2 ){
			timeOnTarget+= .02;
			if (timeOnTarget > 50){
				done = true;
			}
		}
		else{
			timeOnTarget=0;
		}
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		turnController.disable();
		Robot.drive.arcadeDrive(0, 0, false);	
		Robot.drive.setEnableBrake(true);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	protected void showDashTestInfo() {
		if (ISSMARTDASHDEBUG_ENABLED) {
			SmartDashboard.putNumber("PID SP", turnController.getSetpoint());
		    SmartDashboard.putNumber("PID RotataToAngle", rotateToAngleRate);
     		SmartDashboard.putData("PID Turn Ctn", turnController);
		}
			
		// SmartDashboard.putNumber("Cmd get rotations left",
		// Robot.drive.getRotationsLeft());
		// SmartDashboard.putNumber("Cmd get rotations right",
		// Robot.drive.getRotationsRight());
		//
		// SmartDashboard.putNumber("CmdGetRInches",
		// Robot.drive.getInchesPositionRightInches());
		// SmartDashboard.putNumber("CmdGetLInches",
		// Robot.drive.getInchesPositionLeftInches());
		//
		// SmartDashboard.putNumber("CmdSPLEnd", endPositionLeftInches);
		// SmartDashboard.putNumber("CmdSPREnd", endPositionRightInches);
		// System.out.println("Working");
	}

	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}

}
