package Commands;

import team3735.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExpDrive extends Command {
	/************************************/
	/* Constants						*/
	/************************************/	
	private static final double K_FILTERCOEF_Y 		= 8.0;  /* Range is 1 to 50 , 1 is like old code, 3 or 6, 8 is recommended */ 
	private static final double K_FILTERCOEF_Z 		= 4.0; 
	
	private static final double K_MAX_MTR_Y 		= 1.00;  /* This is Max Motor Speed when Jy Stick Max */
	private static final double K_MAX_MTR_Z 		= 1.00; 
	
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
    	requires(Robot.drivetrain);
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
		YDriveStick = -Robot.oi.driverStick.getY();
		ZDriveStick = -Robot.oi.driverStick.getZ();
    	if(Robot.isTrueSpeed()) {
			/*********************************/
			/* Lets Filter the Motor Outputs */
			/*********************************/
			/* Note We use the Saved Past Motor Drive Values to Make Calculations */
			YDriveMotor = (YDriveStick  / K_FILTERCOEF_Y ) + (YDriveMotorPrevious*(K_FILTERCOEF_Y - 1 )/ K_FILTERCOEF_Y);
			ZDriveMotor = (ZDriveStick  / K_FILTERCOEF_Z ) + (ZDriveMotorPrevious*(K_FILTERCOEF_Z - 1 )/ K_FILTERCOEF_Z);
			
			/****************************************/
			/* Let Save the Motor Y and Z so we     */
			/* Use the Value for future Calculations*/
			/****************************************/
			YDriveMotorPrevious = YDriveMotor; 
			ZDriveMotorPrevious = ZDriveMotor;
						
			/**************************************/
			/* Let Update the Drive Train Y and Z */
			/**************************************/
			Robot.drivetrain.move(YDriveMotor * K_MAX_MTR_Y , ZDriveMotor * K_MAX_MTR_Z);	
    	}
    	else {
    		Robot.drivetrain.move(YDriveStick, ZDriveStick);
    	}
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
