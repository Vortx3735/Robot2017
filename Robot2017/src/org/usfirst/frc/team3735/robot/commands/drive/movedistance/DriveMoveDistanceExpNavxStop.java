package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.commands.drive.DriveBrakeUntilStopped;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceExpNavxStop extends CommandGroup {

    public DriveMoveDistanceExpNavxStop(double distance, double power) {
    	addSequential(new DriveMoveDistanceExpNavx(distance, power));
    	addSequential(new DriveBrakeUntilStopped(),1.5);
    }
    public DriveMoveDistanceExpNavxStop(double distance, double power, double angle) {
    	addSequential(new DriveMoveDistanceExpNavx(distance, power, angle));
    	addSequential(new DriveBrakeUntilStopped(),1.5);
    }
    public DriveMoveDistanceExpNavxStop(double distance, double power, Double angle, double acc) {
    	addSequential(new DriveMoveDistanceExpNavx(distance, power, angle, acc));
    	addSequential(new DriveBrakeUntilStopped(),1.5);
    }
}
