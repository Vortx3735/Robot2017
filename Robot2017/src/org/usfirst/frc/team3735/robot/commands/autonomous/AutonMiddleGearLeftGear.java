package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.sequences.DriveAcquireGear;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVisionBumped;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutonMiddleGearLeftGear extends CommandGroup {

	private Double distanceToMove = new Double(0);
	private double initialDistance;
	private double finalDistance;
	
    public AutonMiddleGearLeftGear(){
    	addSequential(new AutonMiddleGear());
    	addSequential(new DriveTurnToAnglePIDCtrl(-135),1);
    	addSequential(new DriveTurnToAnglePIDCtrl(Pipes.Gear));
    	addSequential(new RecordInitialDistance());
    	addSequential(new DriveAcquireGear());
    	addSequential(new RecordFinalDistance());
    	addSequential(new DriveMoveDistanceExpNavx(distanceToMove, .4));
    	addSequential(new DriveTurnToAnglePIDCtrl(0),1);
    	addSequential(new DriveMoveDistanceExpVisionBumped(100, .5, Pipes.Peg));
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
