package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.commands.autonomous.*;
import org.usfirst.frc.team3735.robot.subsystems.BallIntake;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.subsystems.GearIntake;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Scaler;
import org.usfirst.frc.team3735.robot.subsystems.Shooter;
import org.usfirst.frc.team3735.robot.subsystems.Ultrasonic;
import org.usfirst.frc.team3735.robot.util.DriveOI;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String autonomousTest = "AutonomousTest";
	String autoSelected;
	SendableChooser autonomousChooser;
	Command autonomousCommand;
	public static BallIntake ballIntake;
	public static Drive drive;
	public static GearIntake gearIntake;
	public static Scaler scaler;
	public static Shooter shooter;
	public static Navigation navigation;
	CameraServer server;
	public static Ultrasonic ultra;
	
	public static DriveOI oi;
	public RobotMap robotmap;
	public CoordinateHandler cords;
	
	//SendableChooser lrChooser;
	boolean rightSide = false;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		robotmap = new RobotMap();
		//cords = new CoordinateHandler();
		
		gearIntake = new GearIntake();
		shooter = new Shooter();
		scaler = new Scaler();
		drive = new Drive();
		ballIntake = new BallIntake();
		//navigation = new Navigation();
		oi = new GTAOI();
		ultra = new Ultrasonic();
		
		
		autonomousChooser = new SendableChooser();
		autonomousChooser.addDefault ("Do Nothing", new AutonomousDoNothing());
		autonomousChooser.addObject("DriveBaseStraightOnlyToBase", new AutonTimedDriveTimedDriveStraightToBase());
		//autonomousChooser.addObject("DriveBaseLeftOfAirShip", new AutonTimedDriveTimedStepsToLeft());
		//autonomousChooser.addObject("DriveBaseRightOfAirShip", new AutonTimedDriveTimedStepsToRight());
		//autonomousChooser.addObject("DriveStrightDropGear", new AutonTimedDriveTimedDropGear());
		autonomousChooser.addObject("DriveEncoderFwd", new AutonForwardDrivePosition());
		autonomousChooser.addObject("DriveEncoderFwdWithDropGear", new  AutonForwardDrivePositionWithGearDrop());
		
		
		//autonomousChooser.addObject("DriveEncoderSquare", new AutonForwardDriveSquare());
		//autonomousChooser.addObject("Drive Encoder Position Andrew", new AutonForwardDrivePositionAndrew());
		

//	THIS IS COMMENTED BY MR NAIK OUT SINCE NOT TESTED 	
//		autonomousChooser.addObject("Top-Cross-Gear-Loader", new AutonomousTopCrossGearLoader());
//		autonomousChooser.addObject("Top-Cross-Gear-Shoot", new AutonomousTopCrossGearShoot());
//		autonomousChooser.addObject("Middle-Gear-Top-Loader", new AutonomousMiddleGearTopLoader());
//		autonomousChooser.addObject("Middle-Gear-Bottom-Shoot", new AutonomousMiddleGearBottomShoot());
//		autonomousChooser.addObject("Bottom-Cross-Gear-Loader", new AutonomousBottomCrossGearLoader());
//		autonomousChooser.addObject("Bottom-Cross-Gear-Shoot", new AutonomousBottomCrossGearShoot());
		//lrChooser.addDefault("Left Side Coords", false);
		//lrChooser.addObject("Right Side Coords", true);
		//chooser.addObject("Autonomous Test", autonomousTest);
		//************************************************************************
		
		SmartDashboard.putData("AUTONOMOUS SELECTION", autonomousChooser);
		/* Lets Start the WEB CAMERA */

//    	try {
//			server = CameraServer.getInstance();
//			server.startAutomaticCapture();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/*****************************/
        
        //experimental code to test on 2/24
        //SmartDashboard.putData(drive);
        
		oi.log();
		scaler.log();
		drive.log();
		shooter.log();
		ballIntake.log();
		gearIntake.log();
	}
	
	@Override
	public void robotPeriodic() {
	}
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
//		THIS IS COMMENTED BY MR NAIK OUT SINCE NOT TESTED 	
//		Robot.drive.zeroYaw();
//		rightSide = (boolean) lrChooser.getSelected();
//		if(rightSide){
//			cords.switchToRightSide();
//		}else{
//			cords.switchToLeftSide();
//		}
        autonomousCommand = (Command) autonomousChooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	
    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }
    
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
        Scheduler.getInstance().run();
        log();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void log(){
		oi.log();

		scaler.log();
		drive.log();
		shooter.log();
		ballIntake.log();
		gearIntake.log();
		ultra.log();
	}
	
	
	/**
	 * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
}

