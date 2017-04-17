package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.DriveChangeToGearDirection;
import org.usfirst.frc.team3735.robot.commands.vision.DriveAddVisionAssist;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceExpVision extends CommandGroup {

	private boolean done = false;
	
    public DriveMoveDistanceExpVision(double distance, double power, Pipes p) {
    	addParallel(new DriveAddVisionAssist(p));
    	addSequential(new DriveChangeToGearDirection());
    	addSequential(new DriveMoveDistanceExp(distance, power));
    	//addSequential(new ExpDrive(-1,0),.5);
    	addSequential(new DriveBrake(){
    		@Override
    		public void end(){
    			super.end();
    			done = true;
    		}
    	},.4);
    	
    }

	@Override
	protected boolean isFinished() {
		return super.isFinished() || done;
	}
    
}
