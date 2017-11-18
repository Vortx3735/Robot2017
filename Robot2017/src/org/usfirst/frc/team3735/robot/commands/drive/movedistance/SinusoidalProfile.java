package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;
import org.usfirst.frc.team3735.robot.triggers.HasReachedSpeed;
import org.usfirst.frc.team3735.robot.triggers.TimeOut;
import org.usfirst.frc.team3735.robot.util.cmds.VortxCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SinusoidalProfile extends VortxCommand {
	double initialV;
	double finalV;
	double vd;
	double dist;
	double acc;
	double scale;
    public SinusoidalProfile(double vi, double vf, double d) {
        requires(Robot.drive);
        vd = vf-vi;
        acc = (Math.PI/(4*d)) * (Math.pow(vd+vi, 2) - Math.pow(vi, 2));
        if(acc/vd < 0) {
        	System.out.println("Sine Profile Error: changing dist");
        	d*= -1;
            acc = (Math.PI/(4*d)) * (Math.pow(vd+vi, 2) - Math.pow(vi, 2));
        }
        initialV = vi;
        finalV = vf;
        dist = d;
        scale = 2 * acc / vd;
        
        addT(new HasMoved(d));
        addT(new HasReachedSpeed(vf));
        addT(new TimeOut(Math.PI/scale, this));
        
    }
    
    public double getV() {
    	double t = this.timeSinceInitialized();
    	return (vd/2) * (-Math.cos(t * scale) + 1) + initialV;
    }
    
    public double getDuration() {
    	return (Math.PI/2) * (vd/acc);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.normalDrive(Drive.speedToPercent(getV()), 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return super.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
