package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveSine extends CommandGroup {
	private double maxv = 170;
    public MoveSine(double dist) {
    	double d = dist/2;
    	double vf;
    	vf = (d/250) * maxv;
    	if(vf > maxv) vf = maxv;
    	addSequential(new SinusoidalProfile(0,vf, d));
    	addSequential(new SinusoidalProfile(vf,0, d));

    	
    }
}
