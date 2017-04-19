package org.usfirst.frc.team3735.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.util.MultiSpeedController;
import org.usfirst.frc.team3735.robot.util.PIDCtrl;
import org.usfirst.frc.team3735.robot.util.Setting;
import org.usfirst.frc.team3735.robot.util.VortxMath;


/***********************************************
 *
 ***********************************************/

public class Navigation extends Subsystem implements PIDSource, PIDOutput {
	private static final int BUMP_THRESHOLD = 1;

	private AHRS ahrs;
	
	private PIDCtrl controller;
	
    public static Setting iZone = new Setting("Turning IZone", 10);
    public static Setting actingI = new Setting("Acting I Value", 0.004);
    
	public static Setting coefficient = new Setting("Navx Drive Coeffecient", 5);
	private static Setting outputExponent = new Setting("Nav Output Exponent", 1);
	private static Setting inputExponent = new Setting("Nav Input Exponent", 1);
	
	public Navigation(){
		ahrs = new AHRS(SPI.Port.kMXP);
		controller = new PIDCtrl(.016,0.0,0.061,this,this);
    	controller.setOutputRange(-.5, .5);
    	controller.setInputRange(-180, 180);
    	controller.setContinuous();
    	controller.setIsUsingIZone(true);
    	controller.setIZone(10);
    	controller.setAbsoluteTolerance(1);
    	
    	SmartDashboard.putData("Navigation Turning Controller", controller);
    	
	}

	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
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
    public double getRate(){
    	return ahrs.getRate();
    }
    
    public AHRS getAHRS(){
    	return ahrs;
    }
    public void log(){
    	SmartDashboard.putNumber("Navigation Gyro Yaw", ahrs.getYaw());
//    	SmartDashboard.putNumber("Gyro Acceleration X", ahrs.getWorldLinearAccelX());
//    	SmartDashboard.putNumber("Gyro Acceleration Y", ahrs.getWorldLinearAccelY());
//    	SmartDashboard.putNumber("Gyro Accel XY Vector", getXYAcceleration());

 //     displayDebugGyroData();
    }
    
    
    
    public void displayDebugGyroData(){
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
        
    }


	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		
	}


	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}


	@Override
	public double pidGet() {
		//return ahrs.getYaw();
		return VortxMath.curveAround(ahrs.getYaw(), inputExponent.getValue(), 180);
	}


	public PIDCtrl getController() {
		return controller;
	}


	@Override
	public void pidWrite(double output) {
		output = VortxMath.curve(output, outputExponent.getValue());
		Robot.drive.setLeftRightOutputs(output, -output);
	}


	public boolean isBumped() {
		return getXYAcceleration() > BUMP_THRESHOLD;
	}
	
	public double getXYAcceleration(){
		return Math.hypot(ahrs.getWorldLinearAccelY(), ahrs.getWorldLinearAccelX());
	}
	
	
    
}

