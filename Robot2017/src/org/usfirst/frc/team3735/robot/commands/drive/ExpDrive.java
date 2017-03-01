package org.usfirst.frc.team3735.robot.commands.drive;


import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ExpDrive extends Command {
	/************************************/
	/* Constants						*/
	/************************************/	
	//Range is (0,1] , 1 is no filter, .333 or .167, .125 is recommended 
	private static double K_FILTERCOEF_Y 		=  Constants.Drive.moveReactivity;  //this is for the move variable
	private static double K_FILTERCOEF_Z 		= Constants.Drive.turnReactivity;	 //this is for the turning
	
	/************************************/
	/* Variables						*/
	/************************************/	
	
	private double YDriveStick;
	private double ZDriveStick;
		
	private double YDriveMotor;
	private double ZDriveMotor;
	
	private double YDriveMotorPrevious;
	private double ZDriveMotorPrevious;
	
	private boolean isJoystickInput;
	
	private String moveExponentKey = "Move Exponent";
	private String turnExponentKey = "Turn Exponent";
	private String scaledMaxMoveKey = "Scaled Max Move";
	private String scaledMaxTurnKey = "Scaled Max Turn";
	private String moveReactivityKey = "Move Reactivity";
	private String turnReactivityKey = "Turn Reactivity";
	
	private double moveExponent = Constants.Drive.moveExponent;
	private double turnExponent = Constants.Drive.turnExponent;
	private double scaledMaxMove = Constants.Drive.scaledMaxMove;
	private double scaledMaxTurn = Constants.Drive.scaledMaxTurn;
	private double moveReactivity = Constants.Drive.moveReactivity;
	private double turnReactivity = Constants.Drive.turnReactivity;
	
	/************************************/
	/* Code								*/
	/************************************/
    public ExpDrive() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.drive);
		isJoystickInput = true;
    }
    
    public ExpDrive(double move, double turn){
    	YDriveStick = move;
    	ZDriveStick = turn;
    	System.out.println("Exp Move no Joystick");
    	isJoystickInput = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setUpDriveForController();
    	if(isJoystickInput){
    		YDriveStick			= 0.0;
    		ZDriveStick			= 0.0;
    	}
		YDriveMotor			= 0.0;
		ZDriveMotor			= 0.0;
		YDriveMotorPrevious = 0.0;
		ZDriveMotorPrevious = 0.0;

	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		moveExponent = SmartDashboard.getNumber(moveExponentKey, moveExponent);
	    turnExponent = SmartDashboard.getNumber(turnExponentKey, turnExponent);
		scaledMaxMove = SmartDashboard.getNumber(scaledMaxMoveKey, scaledMaxMove);
		scaledMaxTurn = SmartDashboard.getNumber(scaledMaxTurnKey, scaledMaxTurn);
		moveReactivity = SmartDashboard.getNumber(moveReactivityKey, moveReactivity);
		turnReactivity = SmartDashboard.getNumber(turnReactivityKey, turnReactivity);

    	/************************************/
		/* Lets Get the New Joy Stick Values*/
		/************************************/
		if(isJoystickInput){
			YDriveStick = Robot.oi.getDriveMove();
			ZDriveStick = Robot.oi.getDriveTurn();
		}
	
		/*********************************/
		/* Lets Filter the Motor Outputs */
		/*********************************/
		/* Note We use the Saved Past Motor Drive Values to Make Calculations */
		//this is old formula- literally the worst formula for the computer
//		YDriveMotor = (YDriveStick  / K_FILTERCOEF_Y ) + (YDriveMotorPrevious*(K_FILTERCOEF_Y - 1 )/ K_FILTERCOEF_Y);
//		ZDriveMotor = (ZDriveStick  / K_FILTERCOEF_Z ) + (ZDriveMotorPrevious*(K_FILTERCOEF_Z - 1 )/ K_FILTERCOEF_Z);
		
		//these are new formulas, much better for CPU. These are the same forumlas, but the filters are inversed, and the range is 0<x<=1
		YDriveMotor = (YDriveStick-YDriveMotorPrevious)*K_FILTERCOEF_Y + YDriveMotorPrevious;
		ZDriveMotor = (ZDriveStick-ZDriveMotorPrevious)*K_FILTERCOEF_Z + ZDriveMotorPrevious;

		/****************************************/
		/* Let Save the Motor Y and Z so we     */
		/* Use the Value for future Calculations*/
		/****************************************/
		YDriveMotorPrevious = YDriveMotor; 
		ZDriveMotorPrevious = ZDriveMotor;
					
		/**************************************/
		/* Let Update the Drive Train Y and Z */
		/**************************************/
		YDriveMotor = YDriveMotor * Math.pow(Math.abs(YDriveMotor), Constants.Drive.moveExponent - 1);
		ZDriveMotor = ZDriveMotor * Math.pow(Math.abs(ZDriveMotor), Constants.Drive.turnExponent - 1);
		YDriveMotor = YDriveMotor * Constants.Drive.scaledMaxMove;
		ZDriveMotor = ZDriveMotor * Constants.Drive.scaledMaxTurn;
		Robot.drive.arcadeDrive(YDriveMotor, ZDriveMotor, false);	
		log();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	if(!isJoystickInput){
    		Robot.drive.arcadeDrive(0, 0, false);
    	}
    }
    
    private void log(){
    	SmartDashboard.putNumber(moveExponentKey, moveExponent);
    	SmartDashboard.putNumber(turnExponentKey, turnExponent);
    	SmartDashboard.putNumber(scaledMaxMoveKey, scaledMaxMove);
    	SmartDashboard.putNumber(scaledMaxTurnKey, scaledMaxTurn);
    	SmartDashboard.putNumber(moveReactivityKey, moveReactivity);
    	SmartDashboard.putNumber(turnReactivityKey, turnReactivity);
    }
}
