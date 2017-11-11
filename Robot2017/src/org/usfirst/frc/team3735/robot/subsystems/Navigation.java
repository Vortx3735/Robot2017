package org.usfirst.frc.team3735.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.Robot.Side;
//import org.usfirst.frc.team3735.robot.Robot.Side;
import org.usfirst.frc.team3735.robot.settings.Dms;
import org.usfirst.frc.team3735.robot.settings.Waypoints;
import org.usfirst.frc.team3735.robot.util.PIDCtrl;
import org.usfirst.frc.team3735.robot.util.VortxMath;
import org.usfirst.frc.team3735.robot.util.profiling.Location;
import org.usfirst.frc.team3735.robot.util.profiling.Position;
import org.usfirst.frc.team3735.robot.util.settings.Setting;


public class Navigation extends Subsystem implements PIDSource, PIDOutput {
	private static final int BUMP_THRESHOLD = 1;

	private AHRS ahrs;
	
	private PIDCtrl controller;
	//PID Controller stuff
	private static Setting outputExponent = new Setting("Nav Output Exponent", 1);
    public static Setting iZone = new Setting("Turning IZone", 10);
    public static Setting actingI = new Setting("Acting I Value", 0.004);
    
    public static Setting verticalOffset = new Setting("Vertical Offset", 0);
    
	public static Setting navCo = new Setting("Navx Assist Coeffecient", 5);
	public static Setting navVisCo = new Setting("Navx Vision Assist Coeffecient", 5);

	
	Position pos = new Position(0,0,0);
	private Object posLock = new Object();
	
	NetworkTable table;

	private double prevLeft = 0;
	private double prevRight = 0;
	private double curLeft;
	private double curRight;
	
	public Navigation(){
		table = NetworkTable.getTable("MAP");
		ahrs = new AHRS(SPI.Port.kMXP);
		controller = new PIDCtrl(.016,0.0,0.061,this,this);
    	controller.setOutputRange(-.5, .5);
    	controller.setInputRange(-180, 180);
    	controller.setContinuous();
    	controller.setIsUsingIZone(true);
    	controller.setIZone(10);
    	controller.setAbsoluteTolerance(1);
    	
    	SmartDashboard.putData("Navigation Turning Controller", controller);
    	
		curLeft = Robot.drive.getLeftPositionInches();
    	curRight = Robot.drive.getRightPositionInches();
	}

	public synchronized void setPosition(Position p){
		synchronized(posLock){
			pos = p;
		}
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    }
    
    public synchronized void integrate(){
    	synchronized(posLock){
    		curLeft = Robot.drive.getLeftPositionInches();
        	curRight = Robot.drive.getRightPositionInches();
        	
        	double dd = ((curLeft-prevLeft) + (curRight-prevRight)) * .5;
        	double angle = getYaw();
        	if(Robot.side.equals(Side.Left)){ //simplified version of getFieldYaw without limits
        		angle *= -1;
        	}else{
        		angle = (angle + 180) * -1;
        	}
    		pos.x += Math.cos(Math.toRadians(angle)) * dd;
    		pos.y += Math.sin(Math.toRadians(angle)) * dd;
    		pos.yaw = angle * -1;
    		
        	prevLeft = curLeft;
        	prevRight = curRight;
    	}
    	
    	
    }
    
    public double getYaw(){
    	return ahrs.getYaw();
    }
    
    public double getFieldYaw() {
    	if(Robot.side.equals(Side.Left)) {
    		return getYaw();
    	}else {
    		return VortxMath.continuousLimit(getYaw() + 180, -180, 180);

    	}
   
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
    
    public void displayPosition(){
    	table.putNumberArray("centerX", new double[]{pos.x});
		table.putNumberArray("centerY", new double[]{pos.y});
		table.putNumberArray("angle", new double[]{pos.yaw});
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
		return ahrs.getYaw();
	}


	public PIDCtrl getController() {
		return controller;
	}


	@Override
	public void pidWrite(double output) {
		output = VortxMath.curve(output, outputExponent.getValue());
		Robot.drive.setLeftRight(output, -output);
	}


	public boolean isBumped() {
		return getXYAcceleration() > BUMP_THRESHOLD;
	}
	
	public double getXYAcceleration(){
		return Math.hypot(ahrs.getWorldLinearAccelY(), ahrs.getWorldLinearAccelX());
	}
	
	public Position getStartingPosition() {
		if(Robot.side.equals(Side.Left)){
			return new Position(Dms.Bot.HALFLENGTH, verticalOffset.getValue(), 0);
		}else{
			return new Position(Dms.Field.LENGTH - Dms.Bot.HALFLENGTH, verticalOffset.getValue(), 180);
		}
	}

	public void debugLog() {
		// TODO Auto-generated method stub
		
	}

	public void resetPosition() {
		zeroYaw();
		Robot.retrieveSide();
		setPosition(getStartingPosition());
		Location.changeSide(Robot.side);
		System.out.println("Reseting Position...");
	}
	
	public synchronized Position getPosition() {
		return pos;
	}
	
	/**
	 * 
	 * @return	the quadrant the robot is in
	 * 			follows the quadrant naming system of algebra, looking at the field from Field Map.PNG
	 */
	public int getQuadrant() {
		double xdif = pos.x - Waypoints.center.x;
		double ydif = pos.y - Waypoints.center.y;
		if(xdif > 0) {
			if(ydif > 0) {
				return 1;
			}else {
				return 4;
			}
		}else {
			if(ydif > 0) {
				return 2;
			}else {
				return 3;
			}
		}

	}
	public Location getClosestLocation(Location[] locs) {
		if(locs != null && locs.length > 0) {
			Location curPos = getPosition();
			double least = curPos.distanceFrom(locs[0]);
			int best = 0;
			for(int i = 1; i < locs.length; i++) {
				double dist = curPos.distanceFrom(locs[i]);
				if(dist < least) {
					least = dist;
					best = i;
				}
			}
			return locs[best];
		}else {
			System.out.println("Error in getting closest location");
			return new Location(0,0);
		}
	}
	
	public double getAngleToLocation(Location loc) {
		return Math.toDegrees(-Math.atan2(loc.y - pos.y, loc.x - pos.x));
	}
	
	/**
	 * 
	 * @param loc	the Location to reference
	 * @return		the Angle to the location, meant for using with the TurnTo Command, 
	 * 				where the angle from getYaw is used for turning
	 */
	public double getAngleToLocationCorrected(Location loc) {
		double ans = Math.toDegrees(-Math.atan2(loc.y - pos.y, loc.x - pos.x));
		if(Robot.side.equals(Side.Right)){
			return VortxMath.continuousLimit(ans + 180, -180, 180);
		}else {
			return ans;
		}
		
	}
    
}

