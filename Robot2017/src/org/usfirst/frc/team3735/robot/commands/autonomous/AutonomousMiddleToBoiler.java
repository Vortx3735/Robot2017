package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousMiddleToBoiler extends CommandGroup {
	
    public AutonomousMiddleToBoiler() {
    	addSequential(new DriveMoveDistance(114.775-Constants.RobotDimensions.length));
    	//addSequential(new GearPlaceGear());
    	//addSequential(new )
    }
}
