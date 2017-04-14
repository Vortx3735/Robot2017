package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.DriveChangeToGearDirection;
import org.usfirst.frc.team3735.robot.commands.vision.DriveAddVisionAssist;
import org.usfirst.frc.team3735.robot.subsystems.Vision2.Pipes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveMoveDistanceVisionExp extends CommandGroup {

	private Command visAssist;
	protected boolean done = false;
	
    public DriveMoveDistanceVisionExp(Pipes p, double power, double distance) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	visAssist = new DriveAddVisionAssist(p);
    	
    	addParallel(visAssist);
    	addSequential(new DriveChangeToGearDirection());
    	addSequential(new DriveMoveDistanceExp(power, distance));
    	//addSequential(new ExpDrive(-1,0),.5);
    	addSequential(new DriveBrake(){
    		@Override
    		public void end(){
    			super.end();
    			//cancelGroup();
    			done = true;
    		}
    	},1);
    	
    }
    
    private void cancelGroup(){
    	this.cancel();
    }

	@Override
	protected boolean isFinished() {
		return done;
	}
    
}
