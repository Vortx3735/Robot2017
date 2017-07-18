package org.usfirst.frc.team3735.robot.commands.drive;


import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.util.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.VortxCommand;
import org.usfirst.frc.team3735.robot.util.settings.Setting;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveExp extends VortxCommand {

	private double moveSetValue;
	private double turnSetValue;
		
	private double moveMotor;
	private double turnMotor;
	
	private double moveMotorPrev;
	private double turnMotorPrev;
	

	
	
	/************************************/
	/* Code								*/
	/************************************/
    public DriveExp() {
    	requires(Robot.drive);
    }
    
    public DriveExp(double move, double turn){
    	moveSetValue = move;
    	turnSetValue = turn;
    	requires(Robot.drive);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	super.initialize();
    	Robot.drive.setupDriveForSpeedControl();
		moveMotor = moveMotorPrev = Robot.drive.
		turnMotor = turnMotorPrev = 0.0;

    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	super.execute();
		if(isJoystickInput){
			moveSetValue = Robot.oi.getDriveMove();
			turnSetValue = Robot.oi.getDriveTurn();
		}
		
		
		
		if(Robot.oi.getFODMag() > .05){
			fodMove = Math.pow(Robot.oi.getFODMag(), fodMoveCo.getValue());
			fodAngle = Robot.oi.getFODAngle();
			Robot.navigation.getController().setSetpoint(fodAngle);
			
			fodTurn = (Math.pow(Robot.navigation.getController().getError(), navxPow.getValue())/180.0) * navxCo.getValue();
			
		
		}else{
			fodMove = 0;
			fodTurn = 0;
		}
		
//		SmartDashboard.putNumber("FOD Move", fodMove);
//		SmartDashboard.putNumber("FOD Turn", fodTurn);

		//moveSetValue = moveSetValue + fodMove;
		turnSetValue = turnSetValue + fodTurn;
		
		
	
		moveMotor = (moveSetValue-moveMotorPrev)*moveReactivity.getValue() + moveMotorPrev;
		turnMotor = (turnSetValue-turnMotorPrev)*turnReactivity.getValue() + turnMotorPrev;

		moveMotorPrev = moveMotor;
		turnMotorPrev = turnMotor;
		
					
		moveMotor = moveMotor * Math.pow(Math.abs(moveMotor), moveExponent.getValue() - 1);
		turnMotor = turnMotor * Math.pow(Math.abs(turnMotor), turnExponent.getValue() - 1);
		
		moveMotor = moveMotor * scaledMaxMove.getValue();
		turnMotor = turnMotor * scaledMaxTurn.getValue();
		
		//turnMotor = turnMotor + Robot.oi.getCoLeftX() * .2; you suck James
		

//		SmartDashboard.putNumber("Move Motor", moveMotor);
//		SmartDashboard.putNumber("Turn Motor", turnMotor);
//		moveMotor = VortxMath.limit(moveMotor, -1, 1);
//		turnMotor = VortxMath.limit(turnMotor, -1, 1);


		Robot.drive.arcadeDrive(moveMotor, turnMotor);
		
		
		log();
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return super.isFinished();
    }

    // Called once after isFinished returns true
    public void end() {
    	super.end();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    public void interrupted() {
    	super.interrupted();
    }
    
    private void log(){

    }
}
