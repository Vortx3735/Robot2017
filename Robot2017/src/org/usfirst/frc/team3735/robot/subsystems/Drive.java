package org.usfirst.frc.team3735.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3735.robot.commands.drive.DDxDrive;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.settings.Dms;
import org.usfirst.frc.team3735.robot.settings.RobotMap;
import org.usfirst.frc.team3735.robot.util.settings.Setting;


/**
 *
 */

public class Drive extends Subsystem {

	private CANTalon l1;
	private CANTalon l2;
	private CANTalon l3;
	
	private CANTalon r1;
	private CANTalon r2;
	private CANTalon r3;
		
	private static double dP = 1.0;
	private static double dI = 0.0;
	private static double dD = 0.0;
	private static double dF = 0.0;

	//for speed profiling
	public static final double slope = 0.00113174;
	public static final double minPct = 0.0944854;
	public static final double maxSpeed = (1-minPct)/slope; //about 800.11 rpm, 173 in/s
	
	
	
	private double leftAddTurn = 0;
	private double rightAddTurn = 0;
	private double visionAssist = 0;
	private double navxAssist = 0;

	public static Setting moveExponent = new Setting("Move Exponent", Constants.Drive.moveExponent);
	public static Setting turnExponent = new Setting("Turn Exponent", Constants.Drive.turnExponent);
	public static Setting scaledMaxMove = new Setting("Scaled Max Move", Constants.Drive.scaledMaxMove);
	public static Setting scaledMaxTurn = new Setting("Scaled Max Turn", Constants.Drive.scaledMaxTurn);
	
	
	

	public Drive() {
		l1 = new CANTalon(RobotMap.Drive.leftMotor1);
		l2 = new CANTalon(RobotMap.Drive.leftMotor2);
		l3 = new CANTalon(RobotMap.Drive.leftMotor3);

		r1 = new CANTalon(RobotMap.Drive.rightMotor1);
		r2 = new CANTalon(RobotMap.Drive.rightMotor2);
		r3 = new CANTalon(RobotMap.Drive.rightMotor3);

		initSensors();
		setupSlaves();
		setEnableBrake(true);
	}

	/*******************************
	 * Default Command For Driving
	 *******************************/
	public void initDefaultCommand() {
		setDefaultCommand(new ExpDrive());
	}

	/*******************************
	 * Setups for Position and Speed
	 *******************************/
	public void setupForPositionControl() {
		l1.setAllowableClosedLoopErr(0);
		l1.setProfile(0);
		l1.setF(dF);
		l1.setP(dP);
		l1.setI(dI);
		l1.setD(dD);
		l1.changeControlMode(TalonControlMode.Position);
		l1.setIZone(2);
	
		r1.setAllowableClosedLoopErr(0);
		r1.setProfile(0);
		r1.setF(dF);
		r1.setP(dP);
		r1.setI(dI);
		r1.setD(dD);
		r1.changeControlMode(TalonControlMode.Position);
		r1.setIZone(2);
		
		setEnableBrake(true);		
	}

	/*******************************
	 * Speed Control Setup
	 *******************************/
	public void setupDriveForSpeedControl() {
		setEnableBrake(true);
		l1.changeControlMode(TalonControlMode.PercentVbus);
		r1.changeControlMode(TalonControlMode.PercentVbus);
		this.setNavxAssist(0);
		this.setVisionAssist(0);
	}

	/*******************************
	 * Slaves Setup
	 *******************************/
	public void setupSlaves() {
		l2.changeControlMode(CANTalon.TalonControlMode.Follower);
		l3.changeControlMode(CANTalon.TalonControlMode.Follower);
		r2.changeControlMode(CANTalon.TalonControlMode.Follower);
		r3.changeControlMode(CANTalon.TalonControlMode.Follower);
	
		l2.set(l1.getDeviceID());
		l3.set(l1.getDeviceID());
		r2.set(r1.getDeviceID());
		r3.set(r1.getDeviceID());
	}

	public void initSensors() {
		
		int absolutePosition = l1.getPulseWidthPosition() & 0xFFF;

		l1.reverseOutput(false);
		l1.setEncPosition(absolutePosition);
		l1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		l1.reverseSensor(true);
		l1.configNominalOutputVoltage(0.0, -0.0);
		l1.configPeakOutputVoltage(5, -5);
		l1.setPosition(0);

		absolutePosition = r1.getPulseWidthPosition() & 0xFFF;

		r1.reverseOutput(true);
		r1.setEncPosition(absolutePosition);
		r1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		r1.reverseSensor(false);
		r1.configNominalOutputVoltage(0.0f, -0.0);
		r1.configPeakOutputVoltage(5, -5);
		r1.setPosition(0);

	}
	
	public void setPIDSettings(double kp, double ki, double kd){
		setLeftPID(kp, ki, kd);
		setRightPID(kp, ki, kd);
	}
	
	public void setPIDSettings(double kp, double ki, double kd, double kf, int kz, double kramp) {
		l1.setPID(kp, ki, kd, kf, kz, kramp, 0);
		r1.setPID(kp, ki, kd, kf, kz, kramp, 0);		
	}

	public void setLeftPID(double kp, double ki, double kd){
		l1.setP(kp);
		l1.setI(ki);
		l1.setD(kd);
	}
	public void setRightPID(double kp, double ki, double kd){
		r1.setP(kp);
		r1.setI(ki);
		r1.setD(kd);
	}
	/******************************
	 * changing the Talon control mode
	 */
	public void changeControlMode(TalonControlMode t){
		l1.changeControlMode(t);
		r1.changeControlMode(t);
	}

	/*********************************
	 * Configuring left and right PID Peak Voltages
	 */
	public void setLeftPeakVoltage(double vol){
		l1.configPeakOutputVoltage(vol, -vol);
	}

	public void setRightPeakVoltage(double vol){
		r1.configPeakOutputVoltage(vol, -vol);
	}

	public void resetEncodersPositions(){
		l1.setPosition(0);
		r1.setPosition(0);
	}
	
	/*******************************
	 * Drive Functions
	 *******************************/
	
	/**
	 * Standard arcade drive from wpi's RobotDrive class
	 * @param move
	 * @param rotate
	 */
	public void arcadeDrive(double move, double rotate) {
		//copied from RobotDrive class. essentially lowers the speed of one motor first, rather than increases
		//one and decreases the other at the same time.
		
		double leftMotorSpeed;
		double rightMotorSpeed;
		
		double moveValue = move;
		double rotateValue = rotate + getTurnAdditions();
	    if (moveValue > 0.0) {
	        if (rotateValue < 0.0) {
	          leftMotorSpeed = moveValue + rotateValue;
	          rightMotorSpeed = Math.max(moveValue, -rotateValue);
	        } else {
	          leftMotorSpeed = Math.max(moveValue, rotateValue);
	          rightMotorSpeed = moveValue - rotateValue;
	        }
	      } else {
	        if (rotateValue < 0.0) {
	          leftMotorSpeed = -Math.max(-moveValue, -rotateValue);
	          rightMotorSpeed = moveValue - rotateValue;
	        } else {
	          leftMotorSpeed = moveValue + rotateValue;
	          rightMotorSpeed = -Math.max(-moveValue, rotateValue);
	        }
	      }
	      setLeftRight(leftMotorSpeed, rightMotorSpeed);
	}
	
	
	public void normalDrive(double move, double rotate){
		double rotateValue = rotate + getTurnAdditions();
		setLeftRight(move + rotateValue, move - rotateValue);
	}
	
	/**
	 * Limits the left and right speeds so that rotation is consistent
	 * across all move values. Modifies speed for consistent rotation.
	 * @param move
	 * @param rotate
	 */
	public void limitedDrive(double move, double rotate) {
		double left = move + getTurnAdditions() + rotate;
		double right = move - getTurnAdditions() - rotate;
		double leftSpeed;
		double rightSpeed;
//		if(left > 1) {
//			leftSpeed = 1;
//			rightSpeed = right - left + 1;
//		}else if(right > 1) {
//			rightSpeed = 1;
//			leftSpeed = left - right + 1;
//		}else if(left < -1) {
//			leftSpeed = -1;
//			rightSpeed = right - left - 1;
//		}else if(right < -1) {
//			rightSpeed = -1;
//			leftSpeed = left - right - 1;
//		}
		
		if(Math.abs(left) > 1) {
			leftSpeed = Math.signum(left);
			rightSpeed = right - left + Math.signum(left);
		}else if(Math.abs(right) > 1) {
			leftSpeed = left - right + Math.signum(right);
			rightSpeed = Math.signum(right);
		}else {
			leftSpeed = left;
			rightSpeed = right;
		}
		setLeftRight(leftSpeed, rightSpeed);
		
	}

	/**
	 * Drives in a circle with a specified radius
	 * @param radius
	 * @param move
	 */
	public void radialDrive(double radius, double move){
		double left;
		double right;
		if(radius > 0){
			radius = Math.abs(radius);
			left = move;
			right = move * (radius - Dms.Bot.HALFWIDTH)/
							  (radius + Dms.Bot.HALFWIDTH);
		}else{
			radius = Math.abs(radius);
			right = move;
			left = move * (radius - Dms.Bot.HALFWIDTH)/
				  			 (radius + Dms.Bot.HALFWIDTH);
		}
		setLeftRight(left, right);
	}
	
	public double getTurnAdditions() {
		return leftAddTurn + rightAddTurn + visionAssist + navxAssist;
	}

	/*******************************
	 * Additive setters
	 *******************************/
	public void setLeftTurn(double turn){
    	leftAddTurn = turn;
    }
    public void setRightTurn(double turn){
    	rightAddTurn = turn;
    }
	public void setVisionAssist(double j) {
		visionAssist = j;
	}	
	public void setNavxAssist(double error) {
		this.navxAssist = (error/180.0) * Navigation.navCo.getValue();
	}

	
	/*******************************
	 * Brake Mode
	 *******************************/
	public void setEnableBrake(boolean b) {
		l1.enableBrakeMode(b);
		l2.enableBrakeMode(b);
		l3.enableBrakeMode(b);
		
		r1.enableBrakeMode(b);
		r2.enableBrakeMode(b);
		r3.enableBrakeMode(b);
	}
	
	public double getLeftErrorInches(){
		return l1.getClosedLoopError() * Constants.Drive.InchesPerRotation ;
	}

	public double getRightErrorInches(){
		return r1.getClosedLoopError() * Constants.Drive.InchesPerRotation ;
	}
	/*********************************
	 * Left and Right position getters
	 *********************************/
	public double getLeftPosition() {
		return l1.getPosition();
	}

	public double getRightPosition() {
		return r1.getPosition();
	}

	public double getLeftPositionInches() {
		return getLeftPosition() * (Constants.Drive.InchesPerRotation);
	}

	public double getRightPositionInches() {
		return getRightPosition() * (Constants.Drive.InchesPerRotation);
	}

	public double getLeftSpeed() {
		return l1.getSpeed();
	}
	
	public double getRightSpeed() {
		return r1.getSpeed();
	}

	public double getAverageSpeed() {
		return .5 * (getLeftSpeed() + getRightSpeed());
	}
	
	public double getLeftSpeedInches() {
		return (l1.getSpeed() * Constants.Drive.InchesPerRotation) /60.0;
	}
	
	public double getRightSpeedInches() {
		return (r1.getSpeed() * Constants.Drive.InchesPerRotation) /60.0;
	}
	
	public double getAverageSpeedInches() {
		return (getAverageSpeed() * Constants.Drive.InchesPerRotation)/60.0;
	}

	public void setLeftRight(double left, double right) {
		l1.set(left); 
		r1.set(-right);
	}

	/**
     * 
     * @param spd	the target speed in inches per second
     * @return	the percent, which converts spd into normal getspeed units (rpm), and then
     * 			compensates for the deadzone using gathered data
     */
    public static double speedToPercent(double spd){
    	double speed = (Math.abs(spd) *60.0) /Constants.Drive.InchesPerRotation;
    	return Math.copySign(slope*speed + minPct, spd);
    }
    public static double percentToSpeed(double pct){
    	return Math.copySign((pct - minPct) / slope, pct) * Constants.Drive.InchesPerRotation/60.0;
    }
    /**
     * @param 	percent [0,1] of the max speed to go
     * @return	the adjusted number to send to motors
     */
    public static double handleDeadband(double percent) {
    	return percent - (minPct * percent) + minPct;
    }
    public static double invHandleDeadband(double inv) {
    	return (inv-minPct)/(1-minPct);
    }
    
    /**
     * 
     * @return the percentage of the max speed that the robot is going at
     */
    public double getCurrentPercentSpeed() {
    	return invHandleDeadband(speedToPercent(getAverageSpeed()));
    }
    
    /**
     * 
     * @return	the percentVBus to send to the motors to maintain the current speed
     */
    public double getCurrentPercent() {
    	return speedToPercent(getAverageSpeed());
    }
    
    public double getSpeedInchesFromCurrent() {
    	double pct = .5 * (l1.getOutputCurrent() + r1.getOutputCurrent());
    	return Math.abs(pct) > minPct ? (pct-Math.signum(pct)*minPct)/slope : 0;
    }

	/******************************************
	 * The Logs
	 ******************************************/
	public void log() {
		
	}

	public void debugLog() {
		SmartDashboard.putNumber("Drive Left Position", l1.getPosition());
		SmartDashboard.putNumber("Drive Right Position", r1.getPosition());

		SmartDashboard.putNumber("Drive Left Speed", l1.getSpeed());
		SmartDashboard.putNumber("Drive Right Speed", r1.getSpeed());

		SmartDashboard.putNumber("Drive Left Get", l1.get());
		SmartDashboard.putNumber("Drive Right Get", r1.get());
		
		SmartDashboard.putNumber("Drive avg speed", getAverageSpeedInches());
	}



}

