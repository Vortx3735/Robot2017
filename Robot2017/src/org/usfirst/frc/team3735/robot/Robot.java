package org.usfirst.frc.team3735.robot;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3735.robot.commands.RecordAverageRate;
import org.usfirst.frc.team3735.robot.commands.RecordSmartDashboardFile;
import org.usfirst.frc.team3735.robot.commands.RecordTrapTurnData;
import org.usfirst.frc.team3735.robot.commands.SendSmartDashboardFile;
import org.usfirst.frc.team3735.robot.commands.autonomous.*;
import org.usfirst.frc.team3735.robot.pipelines.GearPipeline;
import org.usfirst.frc.team3735.robot.pipelines.StickyNotePipeline;
import org.usfirst.frc.team3735.robot.subsystems.BallIntake;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.subsystems.GearIntake;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Scaler;
import org.usfirst.frc.team3735.robot.subsystems.Shooter;
import org.usfirst.frc.team3735.robot.subsystems.Ultrasonic;
import org.usfirst.frc.team3735.robot.subsystems.Vision;
import org.usfirst.frc.team3735.robot.subsystems.Vision2;
import org.usfirst.frc.team3735.robot.util.DriveOI;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	SendableChooser<Command> autonomousChooser;
	Command autonomousCommand;
	
	public static BallIntake ballIntake;
	public static Drive drive;
	public static GearIntake gearIntake;
	public static Scaler scaler;
	public static Shooter shooter;
	public static Navigation navigation;
	public static Ultrasonic ultra;
	public static Vision2 vision;
	
	public static GTAOI oi;
	public RobotMap robotmap;
	
	boolean rightSide = false;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		robotmap = new RobotMap();
		gearIntake = new GearIntake();
		shooter = new Shooter();
		scaler = new Scaler();
		drive = new Drive();
		ballIntake = new BallIntake();
		navigation = new Navigation();
		ultra = new Ultrasonic();
		vision = new Vision2();
		
		oi = new GTAOI(); //MUST be instantiated after the subsystems
			
		autonomousChooser = new SendableChooser<Command>();
		
		autonomousChooser.addDefault ("Do Nothing", new AutonDoNothing());
		autonomousChooser.addObject("Base Line", new AutonBaseline());
		autonomousChooser.addObject("Middle Gear", new  AutonMiddleGear());
		autonomousChooser.addObject("Left Gear", new  AutonLeftGear());
		autonomousChooser.addObject("Right Gear", new  AutonRightGear());
		autonomousChooser.addObject("Naiks middle", new  AutonMiddleGearNaik());
		autonomousChooser.addObject("Naiks left gear", new  AutonLeftGearNaik());
		autonomousChooser.addObject("Left Gear Balls", new  AutonLeftGearBalls());
		autonomousChooser.addObject("Right Gear Balls", new  AutonRightGearBalls());
		autonomousChooser.addObject("Testing", new  AutonDriveForwardTest());

		SmartDashboard.putData("AUTONOMOUS SELECTION", autonomousChooser);
		
		//SmartDashboard.putData("Start Sending Turn Voltages", new RecordTrapTurnData());
		//SmartDashboard.putData("Start Sending Turn Voltages", new RecordAverageRate());

		SmartDashboard.putData("Record Data", new RecordSmartDashboardFile());
		SmartDashboard.putData("Send Data", new SendSmartDashboardFile());

		//old camera code
//    	try {
//			server = CameraServer.getInstance();
//			server.startAutomaticCapture();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		log();
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
		drive.zeroYaw();
		navigation.zeroYaw();
        autonomousCommand = autonomousChooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
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
		vision.log();
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

