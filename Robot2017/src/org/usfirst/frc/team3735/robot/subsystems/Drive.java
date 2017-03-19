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
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.DriveNavxReset;
import org.usfirst.frc.team3735.robot.util.MultiSpeedController;


/**
 *
 */

public class Drive extends PIDSubsystem {
	
	private CANTalon l1,l2,l3,r1,r2,r3;
//	
//	MultiSpeedController leftMotors = 
//			new MultiSpeedController(new CANTalon[] {l1, l2, l3}, "Drive", "left Motors");
//	MultiSpeedController rightMotors = 
//			new MultiSpeedController(new CANTalon[] {r1, r2, r3}, "Drive", "left Motors");
//	
	RobotDrive driveTrain;
	
	private AHRS ahrs;
	private boolean reversed = false;
	
	//values for rotation
	private static double P = .0045;//this = max magnitude/180
	private static double I = 0.00019;
	private static double D = 0.009;
	private static double F = 0.0;
	
	private static double dP = 1.0;
	private static double dI = 0.0;
	private static double dD = 0.0;
	private static double dF = 0.0;

	private static double accel = 30;			//rpm/s
	private static double cruiseVelocity = 120;	//rpm
	
	private double leftAddTurn = 0;
	private double rightAddTurn = 0;
	
	private String turnCorrectionKey = "Turn Correction";
	private static double defaultTurnCorrection = Constants.Drive.turnCorrection;
	private double turnCorrection;
	
	private String maxOutputKey = "Drive Scaled Max Output";
	private static double defaultMaxOutput = Constants.Drive.scaledMaxOutput;
	private double scaledMaxOutput;

	public Drive() {
		super("Drive",P,I,D,F);
		/******************
		 * Drivetrain Left
		 ******************/
		l1 = new CANTalon(RobotMap.Drive.leftMotor1);
		l2 = new CANTalon(RobotMap.Drive.leftMotor2);
		l3 = new CANTalon(RobotMap.Drive.leftMotor3);

		/******************
		 * Drivetrain Right
		 ******************/
		r1 = new CANTalon(RobotMap.Drive.rightMotor1);
		r2 = new CANTalon(RobotMap.Drive.rightMotor2);
		r3 = new CANTalon(RobotMap.Drive.rightMotor3);
		/* Setup Encoders and Controls Scaling */
		initLeftRightSensorsAndControls();
		setupSlaves();
		setEnableBrake(false);
		
		driveTrain = new RobotDrive(l1, r1);
		reversed = false;
		setUpDriveTrain();
		
		ahrs = new AHRS(SPI.Port.kMXP);
		getPIDController().setAbsoluteTolerance(Constants.Drive.turnTolerance);
		getPIDController().setInputRange(-180, 180);
		getPIDController().setContinuous();
		getPIDController().setOutputRange(-1, 1);
        //LiveWindow.addActuator("Drive", "turn Controller", getPIDController());
		SmartDashboard.putData("Drive PID Controller",getPIDController());
	}

	/*******************************
	 * Default Commend For Drive
	 *******************************/
	public void initLeftRightSensorsAndControls() {
		
		int absolutePosition = l1.getPulseWidthPosition()
				& 0xFFF; /*
							 * mask out the bottom12 bits, we don't care about
							 * the wrap around
							 */

		l1.reverseOutput(false);

		l1.setEncPosition(absolutePosition);
		l1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		l1.reverseSensor(true);
		l1.configNominalOutputVoltage(+0.0f, -0.0f);
		l1.configPeakOutputVoltage(+5.4f, -5.4f);
		l1.setPosition(0);


		absolutePosition = r1.getPulseWidthPosition()
				& 0xFFF; /*
							 * mask out the bottom12 bits, we don't care about
							 * the wrap arounds
							 */

		r1.reverseOutput(true);
		r1.setEncPosition(absolutePosition);
		r1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		r1.reverseSensor(false);
		r1.configNominalOutputVoltage(+0.0f, -0.0f);
		r1.configPeakOutputVoltage(+6f, -6f);
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
	 * Default Commend For Drive
	 *******************************/
	public void initDefaultCommand() {
		setDefaultCommand(new ExpDrive());
	}

	/*******************************
	 * The Main Robot Arcade Drive
	 *******************************/
	public void arcadeDrive(double move, double rotate, boolean squareValues) {
		driveTrain.arcadeDrive(move, (rotate + leftAddTurn + rightAddTurn + turnCorrection) * -1, squareValues);
	}

	public void normalDrive(double move, double curve) {
		driveTrain.drive(move, curve);
	}
	
	public void setLeftTurn(double turn){
    	leftAddTurn = turn;
    }
    public void setRightTurn(double turn){
    	rightAddTurn = turn;
    }

	/*******************************
	 * Setup Config Items for Drive
	 *******************************/
	public void setUpDriveTrain() {
		driveTrain.setSensitivity(Constants.Drive.sensitivity);
		driveTrain.setMaxOutput(Constants.Drive.scaledMaxOutput);
	}

	/*******************************
	 * Direction Change
	 *******************************/
	public void changeDirection() {
		if (reversed) {
			changeToForward();
		} else {
			changeToReverse();
		}
	}

	/*******************************
	 * Direction Change Forward
	 *******************************/
	public void changeToForward() {
		if (reversed) {
			driveTrain = new RobotDrive(l1, r1);
			reversed = false;
			setUpDriveTrain();
		}
	}

	/*******************************
	 * Direction Change Reverse
	 *******************************/
	public void changeToReverse() {
		if (!reversed) {
			driveTrain = new RobotDrive(r1, l1);
			reversed = true;
			setUpDriveTrain();
		}
	}

	public void setLeftRightRotations(double left, double right) {
		l1.set(left);
		r1.set(right);
	}

	public void setLeftRightDistance(double left, double right) {
		l1.set(left / (Constants.Drive.OneRotationInches )); //OneRotationInches
		r1.set(right / (Constants.Drive.OneRotationInches ));
	}

	public void setLeftRightOutputs(double leftOutput, double rightOutput) {
		driveTrain.setLeftRightMotorOutputs(leftOutput, rightOutput);
	}

	public double getAverageDisplacementInches() {
		return .5 * (getInchesPositionLeftInches() + getInchesPositionRightInches());
	}

	public double getRotationsLeft() {
		return l1.getPosition();
	}

	public double getRotationsRight() {
		return r1.getPosition();
	}

	public double getInchesPositionLeftInches() {
		return getRotationsLeft() * (Constants.Drive.OneRotationInches);
	}

	public double getInchesPositionRightInches() {
		return getRotationsRight() * (Constants.Drive.OneRotationInches);
	}

	public void changeScaledMaxOutput(double output) {
		driveTrain.setMaxOutput(output);
	}
	
	public void sendLeftVoltage(double vol){
		l1.configPeakOutputVoltage(vol, -vol);
	}
	public void sendRightVoltage(double vol){
		r1.configPeakOutputVoltage(vol, -vol);
	}

	/*******************************
	 * Position Control Setup
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
		
		// l1.setMotionMagicCruiseVelocity(cruiseVelocity);
		// l1.setMotionMagicAcceleration(accel);

		r1.setAllowableClosedLoopErr(0);
		r1.setProfile(0);
		r1.setF(dF);
		r1.setP(dP);
		r1.setI(dI);
		r1.setD(dD);
		r1.changeControlMode(TalonControlMode.Position);
		r1.setIZone(2);
		setEnableBrake(true);
		// r1.setMotionMagicCruiseVelocity(cruiseVelocity);
		// r1.setMotionMagicAcceleration(accel);
	}

	/*******************************
	 * Speed Control Setup
	 *******************************/
	public void setUpDriveForSpeedControl() {
		setEnableBrake(false);
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
	
	public double getYaw(){
		return ahrs.getYaw();
	}
	
	@Override
	protected double returnPIDInput() {
		return ahrs.getYaw();
	}
	@Override
	protected void usePIDOutput(double output) {
		driveTrain.setLeftRightMotorOutputs(output, -output);
	}
	
	public double errorRightPositionInches(){
		return r1.getClosedLoopError() * Constants.Drive.OneRotationInches ;
	}
	public double errorLeftPositionInches(){
		return l1.getClosedLoopError() * Constants.Drive.OneRotationInches ;
	}



	/*******************************
	 * Slaves Setup
	 *******************************/
	private void setupSlaves() {

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
		SmartDashboard.putNumber("DriveLPosition", l1.getPosition());
		SmartDashboard.putNumber("DriveRPosition", r1.getPosition());

		SmartDashboard.putNumber("DriveLSpeed", l1.getSpeed());
		SmartDashboard.putNumber("DriveRSpeed", r1.getSpeed());

		SmartDashboard.putNumber("DriveLActual", l1.get());
		SmartDashboard.putNumber("DriveRActual", r1.get());

	}

	/******************************************
	 * Dashboard Update Setting Variables
	 ******************************************/
	public void dashBoardUpdateControls() {
		scaledMaxOutput = SmartDashboard.getNumber("DriveParamMaxOut", defaultMaxOutput);
	}

	/******************************************
	 * The Logs
	 ******************************************/
	public void log() {
		dashBoardUpdateDisplays();
		dashBoardUpdateControls();
    	SmartDashboard.putNumber("Gyro Yaw", ahrs.getYaw());

		changeScaledMaxOutput(scaledMaxOutput);
	}

}

