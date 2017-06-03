package org.usfirst.frc.team3735.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Dimensions;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.util.MultiSpeedController;
import org.usfirst.frc.team3735.robot.util.recording.RecordableCantalon;


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


	RobotDrive driveTrain;
	
	private boolean reversed = false;
	
	
	private static double dP = 1.0;
	private static double dI = 0.0;
	private static double dD = 0.0;
	private static double dF = 0.0;

	//unused motion magic variables
	private static double accel = 30;			//rpm/s
	private static double cruiseVelocity = 120;	//rpm
	
	private double leftAddTurn = 0;
	private double rightAddTurn = 0;
	private double visionAssist = 0;
	private double voltageAssist;

	

	public Drive() {
		l1 = new CANTalon(RobotMap.Drive.leftMotor1);
		l2 = new CANTalon(RobotMap.Drive.leftMotor2);
		l3 = new CANTalon(RobotMap.Drive.leftMotor3);

		r1 = new CANTalon(RobotMap.Drive.rightMotor1);
		r2 = new CANTalon(RobotMap.Drive.rightMotor2);
		r3 = new CANTalon(RobotMap.Drive.rightMotor3);

		initSensors();
		setupSlaves();
		setEnableBrake(false);
		
		driveTrain = new RobotDrive(l1, r1);
		reversed = false;
		setUpDriveTrain();
						
//		SmartDashboard.putNumber("left Voltage", 5.4);
//		SmartDashboard.putNumber("right Voltage", 5);
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
		l1.setP(kp);
		l1.setI(ki);
		l1.setD(kd);
		
		r1.setP(kp);
		r1.setI(ki);
		r1.setD(kd);
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
	public void resetEncodersPositions(){
		l1.setPosition(0);
		r1.setPosition(0);
	}
	
	/*******************************
	 * Default Command For Drive
	 *******************************/
	public void initDefaultCommand() {
		setDefaultCommand(new ExpDrive());
	}

	/*******************************
	 * Drive Functions
	 *******************************/
	public void arcadeDrive(double move, double rotate, boolean squareValues) {
		driveTrain.arcadeDrive(move, (rotate + leftAddTurn + rightAddTurn + visionAssist) * -1, squareValues);
	}
	public void normalDrive(double move, double curve) {
		driveTrain.drive(move, curve);
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

	/*******************************
	 * Value setups
	 *******************************/
	public void setUpDriveTrain() {
		driveTrain.setSensitivity(Constants.Drive.sensitivity);
	}

	/*******************************
	 * Direction Changes
	 *******************************/
	public void changeDirection() {
		if (reversed) {
			changeToForward();
		} else {
			changeToReverse();
		}
	}
	public void changeToForward() {
		if (reversed) {
			driveTrain = new RobotDrive(l1, r1);
			reversed = false;
			setUpDriveTrain();
		}
	}
	public void changeToReverse() {
		if (!reversed) {
			driveTrain = new RobotDrive(r1, l1);
			reversed = true;
			setUpDriveTrain();
		}
	}
	
	/*******************************
	 * Left and Right Setters
	 *******************************/
	public void setLeftRightRotations(double left, double right) {
		l1.set(left);
		r1.set(right);
	}

	public void setLeftRightDistance(double left, double right) {
		l1.set(left / (Constants.Drive.InchesPerRotation )); //OneRotationInches
		r1.set(right / (Constants.Drive.InchesPerRotation ));
	}

	public void setLeftRightOutputs(double leftOutput, double rightOutput) {
		driveTrain.setLeftRightMotorOutputs(leftOutput, rightOutput);
	}
	
	public void voltageDrive(double voltage, double turn){
		double moveValue = voltage;
		double rotateValue = turn + voltageAssist;
		double leftMotorSpeed = 0;
		double rightMotorSpeed = 0;
		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
		    }else {
		    	leftMotorSpeed = Math.max(moveValue, -rotateValue);
		    	rightMotorSpeed = moveValue + rotateValue;
		    }
		  }else{
		    if (rotateValue > 0.0) {
		      leftMotorSpeed = -Math.max(-moveValue, rotateValue);
		      rightMotorSpeed = moveValue + rotateValue;
		    } else {
		      leftMotorSpeed = moveValue - rotateValue;
		      rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
		    }
		}
		setLeftRight(leftMotorSpeed, rightMotorSpeed);

	}
	
	public void radialDrive(double radius, double voltage){
		double left;
		double right;
		if(radius > 0){
			radius = Math.abs(radius);
			left = voltage;
			right = voltage * (radius - Dimensions.HALFWIDTH)/
							  (radius + Dimensions.HALFWIDTH);
		}else{
			radius = Math.abs(radius);
			right = voltage;
			left = voltage * (radius - Dimensions.HALFWIDTH)/
				  			 (radius + Dimensions.HALFWIDTH);
		}
		setLeftRight(left, right);
	}

	
	/*********************************
	 * Left and Right position getters
	 *********************************/
	public double getRotationsLeft() {
		return l1.getPosition();
	}
	public double getRotationsRight() {
		return r1.getPosition();
	}
	public double getLeftPositionInches() {
		return getRotationsLeft() * (Constants.Drive.InchesPerRotation);
	}
	public double getRightPositionInches() {
		return getRotationsRight() * (Constants.Drive.InchesPerRotation);
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
	
	/***************************
	 * Basic left and right setters
	 ***************************/
	public void setLeft(double output) {
		l1.set(output);
	}
	public void setRight(double output) {
		r1.set(output);
	}
	
	/******************************
	 * changing the talon control mode
	 */
	public void changeControlMode(TalonControlMode t){
		l1.changeControlMode(t);
		r1.changeControlMode(t);
	}

	/*******************************
	 * Setups for Position and Speed
	 *******************************/
	public void setupDriveForPositionControl() {
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
	public void setUpDriveForSpeedControl() {
		setEnableBrake(true);
		l1.changeControlMode(TalonControlMode.PercentVbus);
		r1.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	/*******************************
	 * Speed Enable Brake
	 *******************************/
	public void setEnableBrake(boolean b) {
		l1.enableBrakeMode(b);
		l2.enableBrakeMode(b);
		l3.enableBrakeMode(b);
		
		r1.enableBrakeMode(b);
		r2.enableBrakeMode(b);
		r3.enableBrakeMode(b);
	}
	
	public double getRightError(){
		return r1.getClosedLoopError() * Constants.Drive.InchesPerRotation ;
	}
	public double getLeftError(){
		return l1.getClosedLoopError() * Constants.Drive.InchesPerRotation ;
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

	/******************************************
	 * Dashboard Update Display Variables
	 ******************************************/
	public void dashBoardUpdateDisplays() {
		SmartDashboard.putNumber("Drive Left Position", l1.getPosition());
		SmartDashboard.putNumber("Drive Right Position", r1.getPosition());

		SmartDashboard.putNumber("Drive Left Speed", l1.getSpeed());
		SmartDashboard.putNumber("Drive Right Speed", r1.getSpeed());

		SmartDashboard.putNumber("Drive Left Get", l1.get());
		SmartDashboard.putNumber("Drive Right Get", r1.get());

	}

	/******************************************
	 * Dashboard Update Setting Variables
	 ******************************************/
	public void dashBoardUpdateControls() {
		//scaledMaxOutput = SmartDashboard.getNumber("DriveParamMaxOut", defaultMaxOutput);
		
		//sendLeftVoltage((SmartDashboard.getNumber("left Voltage", 5.4)));
		//sendRightVoltage((SmartDashboard.getNumber("right Voltage", 5)));

	}

	/******************************************
	 * The Logs
	 ******************************************/
	public void log() {
		dashBoardUpdateDisplays();
		dashBoardUpdateControls();
		SmartDashboard.putNumber("Inches per Second", getAverageSpeedInches());
		//changeScaledMaxOutput(scaledMaxOutput);
	}


	public void setPIDSettings(double kp, double ki, double kd, double kf, int kz, double kramp) {
		l1.setPID(kp, ki, kd, kf, kz, kramp, 0);
		r1.setPID(kp, ki, kd, kf, kz, kramp, 0);		
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
		return l1.getSpeed() * Constants.Drive.InchesPerRotation;
	}
	
	public double getRightSpeedInches() {
		return r1.getSpeed() * Constants.Drive.InchesPerRotation;
	}
	
	public double getAverageSpeedInches() {
		return (.5 * ((getLeftSpeed() + getRightSpeed())) * Constants.Drive.InchesPerRotation)/60;
	}

	public void setLeftRight(double left, double right) {
		l1.set(left); 
		r1.set(right);
	}



}

