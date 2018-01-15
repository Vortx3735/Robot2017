package org.usfirst.frc.team3735.robot.subsystems;



import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.DDxDrive;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.settings.Dms;
import org.usfirst.frc.team3735.robot.settings.RobotMap;
import org.usfirst.frc.team3735.robot.util.settings.BooleanSetting;
import org.usfirst.frc.team3735.robot.util.settings.Setting;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/**
 *
 */

public class Drive extends Subsystem {

	private WPI_TalonSRX l1;
	private WPI_TalonSRX l2;
	private WPI_TalonSRX l3;
	
	private WPI_TalonSRX r1;
	private WPI_TalonSRX r2;
	private WPI_TalonSRX r3;
		
	private static double dP = 1.0;
	private static double dI = 0.0;
	private static double dD = 0.0;
	private static double dF = 0.0;
	private static int iZone = 2;

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
	
	public static BooleanSetting brakeEnabled = new BooleanSetting("Brake Mode On", false) {

		@Override
		public void valueChanged(boolean val) {
			if(Robot.drive != null) {
				Robot.drive.setEnableBrake(val);

			}
		}
		
	};
	

	public Drive() {
		l1 = new WPI_TalonSRX(RobotMap.Drive.leftMotor1);
		l2 = new WPI_TalonSRX(RobotMap.Drive.leftMotor2);
		l3 = new WPI_TalonSRX(RobotMap.Drive.leftMotor3);

		r1 = new WPI_TalonSRX(RobotMap.Drive.rightMotor1);
		r2 = new WPI_TalonSRX(RobotMap.Drive.rightMotor2);
		r3 = new WPI_TalonSRX(RobotMap.Drive.rightMotor3);

		//initSensors();
		setupSlaves();
		//setEnableBrake(false);
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
		l1.configAllowableClosedloopError(0, 0, 0);
		setLeftPIDF(dP,dI,dD,dF);
		l1.config_IntegralZone(0, iZone, 0);
		
		//slot, value, timeout
		l1.configAllowableClosedloopError(0, 0, 0);
		setLeftPIDF(dP,dI,dD,dF);
		l1.config_IntegralZone(0, iZone, 0);
		
		setEnableBrake(true);		
	}

	/*******************************
	 * Speed Control Setup
	 *******************************/
	public void setupDriveForSpeedControl() {
		setEnableBrake(false);

		this.setNavxAssist(0);
		this.setVisionAssist(0);
	}

	/*******************************
	 * Slaves Setup
	 *******************************/
	public void setupSlaves() {
		l2.follow(l1);
		l3.follow(l1);
		
		r2.follow(r1);
		r3.follow(r1);
	}

	public void initSensors() {
		
		//int absolutePosition = l1.getPulseWidthPosition() & 0xFFF;
		int absolutePosition = l1.getSelectedSensorPosition(0) & 0xFFF;

		//l1.reverseOutput(false); <--- setinverted does this instead
		
		//l1.setEncPosition(absolutePosition);
		l1.setSelectedSensorPosition(absolutePosition, 0, 0);
		
		//l1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		l1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		
		//l1.reverseSensor(true);
		l1.setSensorPhase(true);
		
		//l1.configNominalOutputVoltage(0.0, -0.0);
		l1.configNominalOutputForward(0, 0);
		l1.configNominalOutputReverse(0, 0);
		l1.configPeakOutputForward(.5, 0);
		l1.configPeakOutputReverse(.5, 0);
		//l1.setPosition(0);
		
		
//		absolutePosition = r1.getPulseWidthPosition() & 0xFFF;
//		
//		r1.reverseOutput(true);
//		r1.setEncPosition(absolutePosition);
//		r1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
//		r1.reverseSensor(false);
//		r1.configNominalOutputVoltage(0.0f, -0.0);
//		r1.configPeakOutputVoltage(5, -5);
//		r1.setPosition(0);
		
		//int absolutePosition = l1.getPulseWidthPosition() & 0xFFF;
		absolutePosition = l1.getSelectedSensorPosition(0) & 0xFFF;

		//l1.reverseOutput(false); <--- setinverted does this instead
		
		//l1.setEncPosition(absolutePosition);
		l1.setSelectedSensorPosition(absolutePosition, 0, 0);
		
		//l1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		l1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		
		//l1.reverseSensor(true);
		l1.setSensorPhase(false);
		
		//l1.configNominalOutputVoltage(0.0, -0.0);
		l1.configNominalOutputForward(0, 0);
		l1.configNominalOutputReverse(0, 0);
		l1.configPeakOutputForward(.5, 0);
		l1.configPeakOutputReverse(.5, 0);
		//l1.setPosition(0);

	}
	
	public void setPIDSettings(double kp, double ki, double kd){
		setLeftPID(kp, ki, kd);
		setRightPID(kp, ki, kd);
	}
	
	public void setPIDFSettings(double kp, double ki, double kd, double kf){
		setLeftPIDF(kp, ki, kd, kf);
		setRightPIDF(kp, ki, kd, kf);
	}
	
	public void setLeftPIDF(double kp, double ki, double kd, double kf) {
		setLeftPID(kp,ki,kd);
		l1.config_kF(0, kf, 0);
	}
	
	public void setRightPIDF(double kp, double ki, double kd, double kf) {
		setRightPID(kp,ki,kd);
		r1.config_kF(0, kf, 0);
	}

	public void setLeftPID(double kp, double ki, double kd){
		l1.config_kP(0, kp, 0);
		l1.config_kI(0, ki, 0);
		l1.config_kD(0, kd, 0);
	}
	public void setRightPID(double kp, double ki, double kd){
		r1.config_kP(0, kp, 0);
		r1.config_kI(0, ki, 0);
		r1.config_kD(0, kd, 0);
	}
//	/******************************
//	 * changing the Talon control mode
//	 */
//	public void changeControlMode(TalonControlMode t){
//		l1.changeControlMode(t);
//		r1.changeControlMode(t);
//	}

	/*********************************
	 * Configuring left and right PID Peak Voltages
	 */
	public void setLeftPeakVoltage(double vol){
		//l1.configPeakOutputVoltage(vol, -vol);
		l1.configPeakOutputForward(vol, 0);
		l1.configPeakOutputReverse(-vol, 0);

	}

	public void setRightPeakVoltage(double vol){
		//r1.configPeakOutputVoltage(vol, -vol);
		r1.configPeakOutputForward(vol, 0);
		r1.configPeakOutputReverse(-vol, 0);
	}

	public void resetEncodersPositions(){
		int absolutePosition = l1.getSelectedSensorPosition(0) & 0xFFF;
		l1.setSelectedSensorPosition(absolutePosition, 0, 0);
		
		absolutePosition = r1.getSelectedSensorPosition(0) & 0xFFF;
		r1.setSelectedSensorPosition(absolutePosition, 0, 0);

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
		if(b) {
			l1.setNeutralMode(NeutralMode.Brake);
			l2.setNeutralMode(NeutralMode.Brake);
			l3.setNeutralMode(NeutralMode.Brake);
			r1.setNeutralMode(NeutralMode.Brake);
			r2.setNeutralMode(NeutralMode.Brake);
			r3.setNeutralMode(NeutralMode.Brake);
		}else {
			l1.setNeutralMode(NeutralMode.Coast);
			l2.setNeutralMode(NeutralMode.Coast);
			l3.setNeutralMode(NeutralMode.Coast);
			r1.setNeutralMode(NeutralMode.Coast);
			r2.setNeutralMode(NeutralMode.Coast);
			r3.setNeutralMode(NeutralMode.Coast);
		}

	}
	
	public double getLeftErrorInches(){
		return l1.getClosedLoopError(0) * Constants.Drive.InchesPerRotation ;
	}

	public double getRightErrorInches(){
		return r1.getClosedLoopError(0)* Constants.Drive.InchesPerRotation ;
	}
	/*********************************
	 * Left and Right position getters
	 *********************************/
	public double getLeftPosition() {
		return l1.getSelectedSensorPosition(0); //Multiply by (1/SensorUnitsPerRotation) to convert into rotations.
	}

	public double getRightPosition() {
		return r1.getSelectedSensorPosition(0); //Multiply by (1/SensorUnitsPerRotation) to convert into rotations.
	}

	public double getLeftPositionInches() {
		return getLeftPosition() * (Constants.Drive.InchesPerRotation);
	}

	public double getRightPositionInches() {
		return getRightPosition() * (Constants.Drive.InchesPerRotation);
	}
	
	/*
	 * 	The return value is in units per 100ms for all sensor types. 
	 * Sensor must be selected using 
	 * configSelectedFeedbackSensor()/ Multiply by (600/SensorUnitsPerRotation) to convert into RPM.
	 *
	 */

	public double getLeftSpeed() {
		return l1.getSelectedSensorVelocity(0);
	}
	
	public double getRightSpeed() {
		return l1.getSelectedSensorVelocity(0);
	}

	public double getAverageSpeed() {
		return .5 * (getLeftSpeed() + getRightSpeed());
	}
	
	public double getLeftSpeedInches() {
		return (getLeftSpeed() * Constants.Drive.InchesPerRotation) /60.0;
	}
	
	public double getRightSpeedInches() {
		return (getRightSpeed() * Constants.Drive.InchesPerRotation) /60.0;
	}
	
	public double getAverageSpeedInches() {
		return (getAverageSpeed() * Constants.Drive.InchesPerRotation)/60.0;
	}

	public void setLeftRight(double left, double right) {
		System.out.println("Left: " + left + " Right:" + right);
		//l1.set(left); 
		l1.set(ControlMode.PercentOutput, left *100);
		
		
		//r1.set(-1 * right);
		r1.set(ControlMode.PercentOutput, right * -100);
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
		SmartDashboard.putNumber("Drive Left Position", getLeftPosition());
		SmartDashboard.putNumber("Drive Right Position", getRightPosition());

		SmartDashboard.putNumber("Drive Left Speed", getLeftSpeed());
		SmartDashboard.putNumber("Drive Right Speed", getRightSpeed());

		SmartDashboard.putNumber("Drive Left Get", l1.get());
		SmartDashboard.putNumber("Drive Right Get", r1.get());
		
		SmartDashboard.putNumber("Drive avg speed inches", getAverageSpeedInches());
	}



}

