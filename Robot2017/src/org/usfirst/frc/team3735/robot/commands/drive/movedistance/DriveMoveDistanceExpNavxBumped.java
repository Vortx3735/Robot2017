package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceExpNavxBumped extends CommandGroup {

    public DriveMoveDistanceExpNavxBumped(double distance, double power) {
        addSequential(new DriveMoveDistanceExpNavx(distance, power){
        	@Override
        	public boolean isFinished(){
        		return super.isFinished() || Robot.navigation.isBumped();
        	}
        });
    }
    public DriveMoveDistanceExpNavxBumped(double distance, double power, double angle) {
        addSequential(new DriveMoveDistanceExpNavx(distance, power, angle){
        	@Override
        	public boolean isFinished(){
        		return super.isFinished() || Robot.navigation.isBumped();
        	}
        });
    }
    public DriveMoveDistanceExpNavxBumped(double distance, double power, Double angle, double acc) {
        addSequential(new DriveMoveDistanceExpNavx(distance, power, angle, acc){
        	@Override
        	public boolean isFinished(){
        		return super.isFinished() || Robot.navigation.isBumped();
        	}
        });
    }
}
