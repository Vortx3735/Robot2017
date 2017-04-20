package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.vision.DriveAddVisionAssist;
import org.usfirst.frc.team3735.robot.commands.vision.DriveAddVisionAssistNavx;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceExpNavxVision extends CommandGroup {

	protected boolean done = false;
	
    public DriveMoveDistanceExpNavxVision(double distance, double power, Pipes p) {
    	
    	addParallel(new DriveAddVisionAssistNavx(p));
    	//addSequential(new DriveChangeToGearDirection());
    	addSequential(new DriveMoveDistanceExp(distance, power));
    	//addSequential(new ExpDrive(-1,0),.5);
    	addSequential(new DriveBrake(){
    		@Override
    		public void end(){
    			super.end();
    			//cancelGroup();
    			done = true;
    		}
    	},.1);
    	
    }


	@Override
	protected void initialize() {
		done = false;
	}


	@Override
	protected boolean isFinished() {
		return done;
	}
    
}
