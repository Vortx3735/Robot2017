package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveMoveTwistAngle extends Command {
	
	static double twistamount;
	static double startPositionLeftInches;
	static double startPositionRightInches;
	static double endPositionLeftInches;
	static double endPositionRightInches;
	static double adjustment;
	static double endadjustment;
	
	
	
	static boolean done = false;
//	private static double P = 1;
//	private static double I = 0;
//	private static double D = 0;
//	private static double F = 0;

    public DriveMoveTwistAngle(double angle){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.twistamount = angle;
    	
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	startPositionLeftInches = Robot.drive.getInchesPositionLeftInches();
    	startPositionRightInches = Robot.drive.getInchesPositionRightInches();

    	endPositionLeftInches = startPositionLeftInches + Math.abs(55.0f * twistamount/180);
    	endPositionRightInches = startPositionRightInches - Math.abs(55.0f * twistamount/180);
    	
    	adjustment = 0;
    	endadjustment = Math.abs(55.0f * twistamount/180) ; // FIXME MOVE CONST TO BETTER PLACE
    	this.setTimeout(5.0);
    	Robot.drive.setPIDSettings(0.07,0.003,0);
        Timer.delay(0.02);
    	Robot.drive.setupDriveForPositionControl();
    	done = false;
    
        
    	//Robot.drive.setLeftRightDistance(endPositionLeft, endPositionRight);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if  ( 		adjustment	<	endadjustment )
    	{
    		adjustment+=0.25;
    		if (twistamount>0.0f)
        		Robot.drive.setLeftRightDistance(startPositionLeftInches + adjustment, startPositionRightInches-adjustment);
        	else
        		Robot.drive.setLeftRightDistance(startPositionLeftInches - adjustment, startPositionRightInches+adjustment);
        
    		
    	}
    	else
    	{
    		if (       Math.abs(Robot.drive.getInchesPositionLeftInches() - endPositionLeftInches )<0.5
    				&& Math.abs(Robot.drive.getInchesPositionRightInches() - endPositionRightInches )<0.5    )
        		done = true;
    		Robot.drive.setLeftRightDistance(endPositionLeftInches,endPositionRightInches);
    		
        
    	}
    	
    	    		
    	
   	  		 
		SmartDashboard.putNumber("CmdActLPos",Robot.drive.getRotationsLeft());
		SmartDashboard.putNumber("CmdActRPos",Robot.drive.getRotationsRight());
		
		SmartDashboard.putNumber("CmdGetRInches",Robot.drive.getInchesPositionRightInches());
		SmartDashboard.putNumber("CmdGetLInches",Robot.drive.getInchesPositionLeftInches());
		
		SmartDashboard.putNumber("CmdSPLEnd",endPositionLeftInches);
		SmartDashboard.putNumber("CmdSPREnd",endPositionRightInches);

		
		SmartDashboard.putNumber("CmdSPLStart",startPositionLeftInches);
		SmartDashboard.putNumber("CmdSPRStart",startPositionRightInches);    	
    	
    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		return done || this.isTimedOut();

    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setUpDriveForSpeedControl();
    	//Robot.drive.arcadeDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}

