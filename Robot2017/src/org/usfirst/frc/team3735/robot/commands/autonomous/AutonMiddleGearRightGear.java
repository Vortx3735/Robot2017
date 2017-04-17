package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveAcquireGear;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExp;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDNavx;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVision;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVisionBumped;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOn;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutonMiddleGearRightGear extends CommandGroup {

	private Double distanceToMove;
	private double initialDistance;
	private double finalDistance;
	
    public AutonMiddleGearRightGear(){
    	addSequential(new AutonMiddleGear());
    	addSequential(new DriveTurnToAnglePIDCtrl(135));
    	addSequential(new DriveTurnToAnglePIDCtrl(Pipes.Gear));
    	addSequential(new RecordInitialDistance());
    	addSequential(new DriveAcquireGear());
    	addSequential(new RecordFinalDistance());
    	addSequential(new DriveMoveDistanceExpNavx(distanceToMove, .3));
    	addSequential(new DriveTurnToAnglePIDCtrl(0));
    	addSequential(new DriveMoveDistanceExpVisionBumped(100, .3, Pipes.Peg));
    	addSequential(new GearIntakeDropOff());
     }
    
    private class RecordInitialDistance extends InstantCommand{
    	@Override
    	public void initialize(){
    		initialDistance = Robot.drive.getLeftPositionInches();
    	}
    }
    
    private class RecordFinalDistance extends InstantCommand{
    	@Override
    	public void initialize(){
    		finalDistance = Robot.drive.getLeftPositionInches();
    		distanceToMove = new Double(initialDistance - finalDistance);
    	}
    }
}
