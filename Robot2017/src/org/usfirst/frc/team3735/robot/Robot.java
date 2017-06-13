package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.commands.RecordVoltageData;
import org.usfirst.frc.team3735.robot.commands.SendSDVoltage;
import org.usfirst.frc.team3735.robot.commands.autonomous.*;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceProfile2;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerUp;
import org.usfirst.frc.team3735.robot.commands.sequences.DriveAcquireGear;
import org.usfirst.frc.team3735.robot.commands.sequences.DrivePlaceGear;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.BallIntake;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.subsystems.GearIntake;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Scaler;
import org.usfirst.frc.team3735.robot.subsystems.Shooter;
import org.usfirst.frc.team3735.robot.subsystems.Ultrasonic;
import org.usfirst.frc.team3735.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
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

	SendableChooser<Command> autonomousChooser;
	Command autonomousCommand;
	
	public static BallIntake ballIntake;
	public static Drive drive;
	public static GearIntake gearIntake;
	public static Scaler scaler;
	public static Shooter shooter;
	public static Navigation navigation;
	public static Ultrasonic ultra;
	public static Vision vision;
	
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
		vision = new Vision();
		
		oi = new GTAOI(); //MUST be instantiated after the subsystems
			
		autonomousChooser = new SendableChooser<Command>();
		
		autonomousChooser.addDefault ("Do Nothing", new AutonDoNothing());
		autonomousChooser.addObject("Base Line", new AutonBaseline());
		autonomousChooser.addObject("Left Gear Hopper", new  AutonLeftGearHopper());
		autonomousChooser.addObject("Left Gear", new  AutonLeftGear());
		autonomousChooser.addObject("Left Gear Baseline", new  AutonLeftGearBaseline());
		autonomousChooser.addObject("Left Gear Balls", new  AutonLeftGearBalls());
		autonomousChooser.addObject("Middle Gear Left Gear", new  AutonMiddleGearLeftGear());
		autonomousChooser.addObject("Middle Gear Left Balls", new  AutonMiddleGearLeftBalls());
		autonomousChooser.addObject("Middle Gear", new  AutonMiddleGear());
		autonomousChooser.addObject("Middle Gear Right Balls", new  AutonMiddleGearRightBalls());
		autonomousChooser.addObject("Middle Gear Right Gear", new  AutonMiddleGearRightGear());
		autonomousChooser.addObject("Right Gear", new  AutonRightGear());
		autonomousChooser.addObject("Right Gear Baseline", new  AutonRightGearBaseline());
		autonomousChooser.addObject("Right Gear Balls", new  AutonRightGearBalls());
		autonomousChooser.addObject("Testing", new  AutonDriveForwardTest());

		SmartDashboard.putData("AUTONOMOUS SELECTION", autonomousChooser);
		
		//SmartDashboard.putData("Start Sending Turn Voltages", new RecordTrapTurnData());
		//SmartDashboard.putData("Start Sending Turn Voltages", new RecordAverageRate());

//		SmartDashboard.putData("Record Data", new RecordSmartDashboardFile());
//		SmartDashboard.putData("Send Data", new SendSmartDashboardFile());
		SmartDashboard.putData("Gear Dropoff", new GearIntakeDropOff());
		SmartDashboard.putData("Scaler Start", new ScalerUp(1));
		
		SmartDashboard.putData("Resume Thread", new InstantCommand(){
			@Override
			public void initialize(){
				Robot.vision.resume();
			}
		});
		
		SmartDashboard.putData("Pause Thread", new InstantCommand(){
			@Override
			public void initialize(){
				Robot.vision.pause();
			}
		});
		
		SmartDashboard.putData("Acquire Gear", new  DriveAcquireGear());
		SmartDashboard.putData("Place Gear", new  DrivePlaceGear());
		SmartDashboard.putData("Zero Yaw", new InstantCommand(){
			@Override
			public void initialize(){
				Robot.navigation.zeroYaw();
			}
		});
		
		SmartDashboard.putData(new RecordVoltageData());
		SmartDashboard.putData(new SendSDVoltage());
		SmartDashboard.putData(new DriveMoveDistanceProfile2(100, 80, 20, 0));
		
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
		//oi.log();
		scaler.log();
		drive.log();
		shooter.log();
		ballIntake.log();
		gearIntake.log();
		navigation.log();
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

