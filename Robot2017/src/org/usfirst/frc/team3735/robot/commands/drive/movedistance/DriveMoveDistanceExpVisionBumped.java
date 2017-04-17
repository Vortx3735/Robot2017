package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceExpVisionBumped extends CommandGroup {

    public DriveMoveDistanceExpVisionBumped(double distance, double power, Pipes p) {
        addSequential(new DriveMoveDistanceExpVision(distance, power, p){
        	@Override
        	public boolean isFinished(){
        		return super.isFinished() || Robot.navigation.isBumped();
        	}
        });
    }
    
    
}
