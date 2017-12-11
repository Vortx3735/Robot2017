package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.assists.NavxAssist;
import org.usfirst.frc.team3735.robot.assists.NavxVisionAssist;
import org.usfirst.frc.team3735.robot.commands.autonomous.*;
import org.usfirst.frc.team3735.robot.commands.autonomous.newstuff.AutonBottomGear;
import org.usfirst.frc.team3735.robot.commands.autonomous.newstuff.AutonTopGear;
import org.usfirst.frc.team3735.robot.commands.autonomous.newstuff.AutonTopGearHopper;
import org.usfirst.frc.team3735.robot.commands.drive.RecordAverageSpeed;
import org.usfirst.frc.team3735.robot.commands.drive.SendSDSpeed;
import org.usfirst.frc.team3735.robot.commands.drive.arcing.DriveMoveInCircleProfile;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveExp;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceProfile;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.MoveSine;
import org.usfirst.frc.team3735.robot.commands.drive.positions.ResetPosition;
import org.usfirst.frc.team3735.robot.commands.drive.positions.ZeroYaw;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerUp;
import org.usfirst.frc.team3735.robot.commands.sequences.DriveAcquireGear;
import org.usfirst.frc.team3735.robot.commands.sequences.DrivePlaceGear;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.ois.DemoOI;
import org.usfirst.frc.team3735.robot.ois.GTAOI;
import org.usfirst.frc.team3735.robot.ois.NormieOI;
import org.usfirst.frc.team3735.robot.settings.Dms;
import org.usfirst.frc.team3735.robot.subsystems.BallIntake;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.subsystems.GearIntake;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Scaler;
import org.usfirst.frc.team3735.robot.subsystems.Shooter;
import org.usfirst.frc.team3735.robot.subsystems.Ultrasonic;
import org.usfirst.frc.team3735.robot.subsystems.Vision;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.triggers.Bumped;
import org.usfirst.frc.team3735.robot.util.bases.VortxIterative;
import org.usfirst.frc.team3735.robot.util.bases.VortxSim;
import org.usfirst.frc.team3735.robot.util.oi.DriveOI;
import org.usfirst.frc.team3735.robot.util.profiling.Location;
import org.usfirst.frc.team3735.robot.util.profiling.Position;
import org.usfirst.frc.team3735.robot.util.settings.Setting;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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
public class Robot extends VortxIterative {

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
	
	private double dt;
	private double prevTime = 0;


	public static SendableChooser<Side> sideChooser;
	public static Side side = Side.Left;
	
	
	
	@Override
	public void robotInit() {
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
			autonomousChooser.addDefault("Do Nothing", new AutonDoNothing());
			autonomousChooser.addObject("Base Line", new AutonBaseline());
			
			autonomousChooser.addObject("Left Gear Hopper", new  AutonLeftGearHopper());
			autonomousChooser.addObject("Left Gear", new  AutonLeftGear());
			autonomousChooser.addObject("Left Gear Baseline", new  AutonLeftGearBaseline());

			autonomousChooser.addObject("Middle Gear", new  AutonMiddleGear());

			autonomousChooser.addObject("Right Gear", new  AutonRightGear());
			autonomousChooser.addObject("Right Gear Baseline", new  AutonRightGearBaseline());
			autonomousChooser.addObject("Right Gear Hopper", new  AutonRightGearHopper());

			autonomousChooser.addObject("Testing", new  AutonTest());
			
			autonomousChooser.addObject("Top Gear", new AutonTopGear());
			autonomousChooser.addObject("Top Gear Hopper", new AutonTopGearHopper());

			autonomousChooser.addObject("Bottom Gear", new AutonBottomGear());

		SmartDashboard.putData("AUTONOMOUS SELECTION", autonomousChooser);
		
		sideChooser = new SendableChooser<Side>();
			sideChooser.addDefault("Red", Side.Left);
			sideChooser.addObject("Blue", Side.Right);
		SmartDashboard.putData("Side Selection", sideChooser);	
		
		SmartDashboard.putData("Reset Position", new ResetPosition());
		SmartDashboard.putData("Gear Dropoff", new GearIntakeDropOff());
		SmartDashboard.putData("Scaler Start", new ScalerUp(1));
		SmartDashboard.putData("Acquire Gear", new  DriveAcquireGear());
		SmartDashboard.putData("Place Gear", new  DrivePlaceGear());
		SmartDashboard.putData("Zero Yaw", new ZeroYaw());
		
//		SmartDashboard.putData(new RecordVoltageData());
//		SmartDashboard.putData(new SendSDVoltage());
		SmartDashboard.putData(new DriveMoveDistanceProfile(100.0, 30, 30, 0).addA(new NavxAssist()));
		SmartDashboard.putData(new DriveMoveInCircleProfile(90, 60, true, 30, 30, 30));
		SmartDashboard.putData("Drive Test", new DriveExp(.5,0).addA(new NavxVisionAssist(Pipes.Peg)));
		SmartDashboard.putData("Sine Profile 100", new MoveSine(100));
		side = Side.Left;
		prevTime = Timer.getFPGATimestamp();
	}
	@Override
	public void robotPeriodic() {		
		Setting.fetchAround();
		
        vision.debugLog();
        //navigation.integrate();
        navigation.displayPosition();
        //drive.debugLog();
        log();       
	}
	@Override
	public void robotContinuous() {
//		dt = Timer.getFPGATimestamp() - prevTime;
//		prevTime += dt;
//		SmartDashboard.putNumber("dt", dt);
		navigation.integrate();
	}
	
	

	@Override
	public void autonomousInit() {
		navigation.resetPosition();
		retrieveSide();
		Location.changeSide(side, Dms.Field.LENGTH);
        autonomousCommand = autonomousChooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
	}
	@Override
	public void autonomousPeriodic() {
		 Scheduler.getInstance().run();
	}
	@Override
	public void autonomousContinuous() {
		
	}
	
	

	@Override
    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
    }
	@Override
	public void teleopPeriodic() {
        Scheduler.getInstance().run();
	}
	@Override
	public void teleopContinuous() {

	}
	
	

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	@Override
	public void disabledInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

	}
	@Override
	public void disabledContinuous() {
		
	}
	
	
	
	public void log(){
		scaler.log();
		drive.log();
		//shooter.log();
		//ballIntake.log();
		gearIntake.log();
		navigation.log();
		//ultra.log();
		vision.log();
	}
	
	public void debugLog(){
		scaler.debugLog();
		drive.debugLog();
		//shooter.debugLog();
		//ballIntake.debugLog();
		gearIntake.debugLog();
		navigation.debugLog();
		//ultra.debugLog();
		vision.debugLog();
	}
	
	
	

	
	
	public static void retrieveSide(){
		if(sideChooser.getSelected() != null){
			side = sideChooser.getSelected();
		}else{
			System.out.println("Error : sideChooser was found null when retrieving side.");
		};
	}


}

