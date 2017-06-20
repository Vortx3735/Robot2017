package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.commands.drive.DriveAddNavxAssist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceProfileNavx extends CommandGroup {
	private boolean done = false;
	
    @Override
	protected void initialize() {
		done = false;
	}

	public DriveMoveDistanceProfileNavx(double d, double v, double a, double exitV) {
    	done = false;
		addParallel(new DriveAddNavxAssist(){
			@Override
			protected boolean isFinished() {
				return done;
			}
    	});
    	addSequential(new DriveMoveDistanceProfile2(d, v, a, exitV){
    		@Override
    		protected void end() {
    			super.end();
    			done = true;
    		}

    		@Override
    		protected void interrupted() {
    			super.interrupted();
    			done = true;
    		}
    	});
    }


    
    
}