package Commands;


import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExpDrive extends Command {
	/************************************/
	/* Constants						*/
	/************************************/	
	//Range is (0,1] , 1 is no filter, .333 or .167, .125 is recommended 
	private static final double K_FILTERCOEF_Y 		= .125;  //this is for the move variable
	private static final double K_FILTERCOEF_Z 		= .250;	 //this is for the turning
	
	
	/************************************/
	/* Variables						*/
	/************************************/	
	
	private double YDriveStick;
	private double ZDriveStick;
		
	private double YDriveMotor;
	private double ZDriveMotor;
	
	private double YDriveMotorPrevious;
	private double ZDriveMotorPrevious;
	
	/************************************/
	/* Code								*/
	/************************************/
    public ExpDrive() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		YDriveStick			= 0.0;
		ZDriveStick			= 0.0;
		YDriveMotor			= 0.0;
		ZDriveMotor			= 0.0;
		YDriveMotorPrevious = 0.0;
		ZDriveMotorPrevious = 0.0;
	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/************************************/
		/* Lets Get the New Joy Stick Values*/
		/************************************/
		YDriveStick = Robot.oi.getMainLeftY();
		ZDriveStick = Robot.oi.getMainRightX();
	
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
		Robot.drive.arcadeDrive(YDriveMotor, ZDriveMotor);	
	
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
    }
}
