package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.commands.drive.AddNavxAssist;

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
		addParallel(new AddNavxAssist(){
			@Override
			protected boolean isFinished() {
				return done;
			}
    	});
    	addSequential(new DriveMoveDistanceProfile(d, v, a, exitV){
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
