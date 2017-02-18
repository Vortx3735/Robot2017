package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMoveDistance extends Command {
	
	private double targetRotations;
	private double startPosistion;
	private double endPosistion;
//	private static double P = 1;
//	private static double I = 0;
//	private static double D = 0;
//	private static double F = 0;

    public DriveMoveDistance(double distance){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.targetRotations=distance/(Constants.Drive.wheelDiameter*Math.PI); //4 is diameter of wheels
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	startPosistion = Robot.drive.getPosistionLeft();
    	endPosistion = startPosistion+targetRotations;
    	Robot.drive.setupDriveForDistance();
    	Robot.drive.setControlMode(TalonControlMode.MotionMagic);
    	Robot.drive.setLeftRight(endPosistion, endPosistion);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double howFarLeft = Math.abs(endPosistion-Robot.drive.getPosistionLeft());
    	double howFarRight = Math.abs((endPosistion-Robot.drive.getPosistionRight()));
        if((howFarLeft<1)&&(howFarRight<1)) {
        	return true;
        } else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }

}

