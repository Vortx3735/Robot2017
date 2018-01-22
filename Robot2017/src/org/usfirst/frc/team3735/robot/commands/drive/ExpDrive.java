package org.usfirst.frc.team3735.robot.commands.drive;


import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.util.calc.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.VortxCommand;
import org.usfirst.frc.team3735.robot.util.settings.Setting;



import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ExpDrive extends Command {

	private double moveSetValue;
	private double turnSetValue;
		
	private double moveMotor;
	private double turnMotor;
	
	private double moveMotorPrev;
	private double turnMotorPrev;
	
	private double fodAngle;
	private double fodMove;
	private double fodTurn;
	
	private static Setting navxCo = new Setting("FOD Navx Coefficient", 2.5);
	private static Setting navxPow = new Setting("FOD Navx Exponent", 1);
	private static Setting fodMoveCo = new Setting("FOD Move Exponent", 1);
	
private static Setting moveReactivity = new Setting("Move Reactivity", Constants.Drive.moveReactivity);
	private static Setting turnReactivity = new Setting("Turn Reactivity", Constants.Drive.turnReactivity);

	
    public ExpDrive() {
    	requires(Robot.drive);
    }

    protected void initialize() {
    	super.initialize();

    	Robot.drive.setupDriveForSpeedControl();
    	moveSetValue	= Robot.drive.getCurrentPercent();
    	turnSetValue	= 0.0;
    	
		moveMotor		= 0.0;
		turnMotor		= 0.0;
		
		moveMotorPrev 	= moveSetValue;
		turnMotorPrev 	= 0.0;

    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	super.execute();
		moveSetValue = Robot.oi.getDriveMove();
		turnSetValue = Robot.oi.getDriveTurn();
		if(Robot.oi.getFODMag() > .1){
			fodMove = Math.pow(Robot.oi.getFODMag(), fodMoveCo.getValue());
			fodAngle = Robot.oi.getFODAngle();
			Robot.navigation.getController().setSetpoint(fodAngle);
			
			fodTurn = fodMove * (Math.pow(Robot.navigation.getController().getError()/180.0, navxPow.getValue())) * navxCo.getValue();
			
		}else{
			fodMove = 0;
			fodTurn = 0;
		}

		//moveSetValue = moveSetValue + fodMove;
		turnSetValue = turnSetValue + fodTurn;

		moveMotor = (moveSetValue-moveMotorPrev)*moveReactivity.getValue() + moveMotorPrev;
		turnMotor = (turnSetValue-turnMotorPrev)*turnReactivity.getValue() + turnMotorPrev;

		moveMotorPrev = moveMotor;
		turnMotorPrev = turnMotor;
					
		moveMotor = moveMotor * Math.pow(Math.abs(moveMotor), Drive.moveExponent.getValue() - 1);
		turnMotor = turnMotor * Math.pow(Math.abs(turnMotor), Drive.turnExponent.getValue() - 1);
		
		moveMotor = moveMotor * Drive.scaledMaxMove.getValue();
//		if(!Robot.oi.main.ls.get()){
//			turnMotor = turnMotor * Drive.scaledMaxTurn.getValue();		
//		}
		Robot.drive.normalDrive(moveMotor, turnMotor);
		log();
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    public void end() {
    	super.end();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    public void interrupted() {
    	super.interrupted();
    	end();
    }
    
    private void log(){

    }
}
