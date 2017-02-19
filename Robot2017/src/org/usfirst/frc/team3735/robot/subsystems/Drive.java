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
import org.usfirst.frc.team3735.robot.commands.drive.GTAExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.DriveNavxResest;
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
	private double direction = 1;
	
	//values for rotation
	private static double P = 1.0;
	private static double I = 0.0;
	private static double D = 0.0;
	private static double F = 0.0;
	private static double accel = 100;
	private static double velocity = 0.0;
	

	public Drive(){
		super("Drive",P,I,D,F);
		//drivetrain
			l1 = new CANTalon(RobotMap.Drive.leftMotor1); 
			l2 = new CANTalon(RobotMap.Drive.leftMotor2); 
			l3 = new CANTalon(RobotMap.Drive.leftMotor3); 
			r1 = new CANTalon(RobotMap.Drive.rightMotor1); 
			r2 = new CANTalon(RobotMap.Drive.rightMotor2); 
			r3 = new CANTalon(RobotMap.Drive.rightMotor3); 
			driveTrain = new RobotDrive(l1, r1);
			driveTrain.setSensitivity(Constants.Drive.sensitivity);
			driveTrain.setMaxOutput(Constants.Drive.scaledMaxOutput);
			setupSlaves();
		
		//sensors
			ahrs = new AHRS(SPI.Port.kMXP);
			
		
		//turn pid
			getPIDController().setContinuous();
			getPIDController().setAbsoluteTolerance(5);
			getPIDController().setInputRange(-180, 180);
			getPIDController().setOutputRange(-1, 1);
	        LiveWindow.addActuator("Drive", "turn Controller", getPIDController());
	}

	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        setDefaultCommand(new GTAExpDrive());
    }
    
    public void changeDirection(){
    	direction *= -1;
    }
    
    public void arcadeDrive(double move, double rotate, boolean squareValues){
    	driveTrain.arcadeDrive(move * direction, rotate * -1, squareValues);    	
    }
    
    public void tankDrive(double left, double right){
    	driveTrain.tankDrive(left, right);
    }
    
    public void normalDrive(double move, double curve){
    	driveTrain.drive(move, curve);
    }
    
    public void turn(double power){
    	driveTrain.tankDrive(power, -power);
    }
    
    public void setControlMode(TalonControlMode mode){
    	l1.changeControlMode(mode);
    	r1.changeControlMode(mode);
    }
    
    public void setLeftRight(double left, double right){
    	l1.set(left);
    	r1.set(right);
    }
    
    public void setupDriveForDistance() {
    	int absolutePosition = l1.getPulseWidthPosition() & 0xFFF; /* mask out the bottom12 bits, we don't care about the wrap arounds */
        l1.setEncPosition(absolutePosition);
        l1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        l1.reverseSensor(false);
        l1.configNominalOutputVoltage(+0f, -0f);
        l1.configPeakOutputVoltage(+12f, -12f);
        l1.setAllowableClosedLoopErr(0); 
        l1.setProfile(0);
        l1.setF(F);
        l1.setP(P);
        l1.setI(I); 
        l1.setD(D); 
        l1.changeControlMode(TalonControlMode.MotionMagic);
        l1.setMotionMagicCruiseVelocity(velocity);
		l1.setMotionMagicAcceleration(accel);
        
        r1.setEncPosition(absolutePosition);
        r1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        r1.reverseSensor(false);
        r1.configNominalOutputVoltage(+0f, -0f);
        r1.configPeakOutputVoltage(+12f, -12f);
        r1.setAllowableClosedLoopErr(0); 
        r1.setProfile(0);
        r1.setF(F);
        r1.setP(P);
        r1.setI(I); 
        r1.setD(D); 
        r1.changeControlMode(TalonControlMode.MotionMagic);
        r1.setMotionMagicCruiseVelocity(velocity);
		r1.setMotionMagicAcceleration(accel);
    }
    
    public void getAverageDisplacement(){
    	
    }
    
    public double getPosistionLeft() {
    	return l1.getPosition();
    }
    
    public double getPosistionRight() {
    	return r1.getPosition();
    }
    
    public double getYaw(){
    	return ahrs.getYaw();
    }
    public void zeroYaw(){
    	ahrs.zeroYaw();
    }
    public void resetAhrs(){
    	ahrs.reset();
    }
    
    private void setupSlaves(){
        
		l2.changeControlMode(CANTalon.TalonControlMode.Follower);
		l3.changeControlMode(CANTalon.TalonControlMode.Follower);
		r2.changeControlMode(CANTalon.TalonControlMode.Follower);
		r3.changeControlMode(CANTalon.TalonControlMode.Follower);
		
		l2.set(l1.getDeviceID());
		l3.set(l1.getDeviceID());
		r2.set(r1.getDeviceID());
		r3.set(r1.getDeviceID());
    }
    
	@Override
	protected double returnPIDInput() {
		return ahrs.getYaw();
	}


	@Override
	protected void usePIDOutput(double output) {
		turn(output);
	}
    
    
    public void log(){
    	//displayGyroData();
//    	SmartDashboard.putNumber("left Position", l1.getPosition());
//    	SmartDashboard.putNumber("right Position", r1.getPosition());
    	SmartDashboard.putNumber("gyro yaw", ahrs.getYaw());

    }
    
    
    
    public void displayGyroData(){
    	SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
        
        /* Display tilt-corrected, Magnetometer-based heading (requires             */
        /* magnetometer calibration to be useful)                                   */
        
        SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());
        
        /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
        SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

        /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
        /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
        
        SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
        SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());

        /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
        
        SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
        SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());
        

        /* Display estimates of velocity/displacement.  Note that these values are  */
        /* not expected to be accurate enough for estimating robot position on a    */
        /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
        /* of these errors due to single (velocity) integration and especially      */
        /* double (displacement) integration.                                       */
        
        SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
        SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
        SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
        SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());
        
        SmartDashboard.putNumber("Displacement Total", 
        		Math.sqrt(Math.pow(ahrs.getDisplacementX(),2) + Math.pow(ahrs.getDisplacementY(),2)));
        
        /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
        /* NOTE:  These values are not normally necessary, but are made available   */
        /* for advanced users.  Before using this data, please consider whether     */
        /* the processed data (see above) will suit your needs.                     */
        
        SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());
        SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());
        SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());
        SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());
        SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());
        SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());
        SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());
        SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());
        SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());
        SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());
        SmartDashboard.putNumber(   "IMU_Timestamp",        ahrs.getLastSensorTimestamp());
        
        /* Omnimount Yaw Axis Information                                           */
        /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
        AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
        SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
        SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
        
        /* Sensor Board Information                                                 */
        SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());
        
        /* Quaternion Data                                                          */
        /* Quaternions are fascinating, and are the most compact representation of  */
        /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
        /* from the Quaternions.  If interested in motion processing, knowledge of  */
        /* Quaternions is highly recommended.                                       */
        SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());
        SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());
        SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());
        SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());
        
        
        
        /* Connectivity Debugging Support                                           */
        SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());
        SmartDashboard.putNumber(   "IMU_Update_Count",     ahrs.getUpdateCount());
        
        SmartDashboard.putData("Reset", new DriveNavxResest());
    }



}

