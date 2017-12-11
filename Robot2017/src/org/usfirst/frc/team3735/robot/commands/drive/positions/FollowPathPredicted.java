package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Dms;
import org.usfirst.frc.team3735.robot.settings.Waypoints;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.triggers.HasPassedWaypoint;
import org.usfirst.frc.team3735.robot.util.Range;
import org.usfirst.frc.team3735.robot.util.calc.DDxLimiter;
import org.usfirst.frc.team3735.robot.util.calc.VortxMath;
import org.usfirst.frc.team3735.robot.util.profiling.Line;
import org.usfirst.frc.team3735.robot.util.profiling.Location;
import org.usfirst.frc.team3735.robot.util.profiling.Position;
import org.usfirst.frc.team3735.robot.util.settings.Setting;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FollowPathPredicted extends Command {

	Location[] locs;
	boolean rev;
	double maxP = .6;
	
	DDxLimiter dxdt;
	DDxLimiter dadt;
	
	double[] angleChanges;
	private static Setting coeff = new Setting("Path Prediction coeff", .01);
	HasPassedWaypoint cutoffLine;
	
	double wantedDadx;
	double wantedDxdt;
	Line toFollow;
	int targetIndex;
	private boolean isDone;
	
	public FollowPathPredicted(Location[] locs) {
		this(locs, false);
	}
	
    public FollowPathPredicted(Location[] locs, boolean rev) {
    	this.locs = locs;
    	this.rev = rev;
    	
    	dxdt = new DDxLimiter(0, new Range(-10, 10), new Range(-10, 10));
    	dadt = new DDxLimiter(0, new Range(-10, 10), new Range(-10, 10));
    	
    	angleChanges = new double[locs.length];
    	
    	//since bezier curves have dadt of 0 at endpoints, assign this to endpoints
    	angleChanges[0] = 0;
    	angleChanges[angleChanges.length-1] = 0;
    	
    	//compute the rest
    	for(int i = 1; i < locs.length-1; i++) {
    		Location prev = locs[i-1];
    		Location next = locs[i+1];
    		Location current = locs[i];
    		
    		angleChanges[i] = Math.atan2(next.y - current.y, next.x - current.x)  -  
    					   Math.atan2(current.y - prev.y, current.x - prev.x);
    		
    		
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	dxdt.reset(Robot.drive.getAverageSpeedInches());
    	dadt.reset();
    	
    	wantedDadx = computeTurn(0);
    	targetIndex = 1;
    	cutoffLine = new HasPassedWaypoint(locs[1], locs[0]);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(cutoffLine.get()) {
    		if(!nextTarget()) {
    			isDone = true;
    			return;
    		}
    		
    	}
    	
    	setControllerAngle();
    	double error = coeff.getValue() * toFollow.distanceFrom(Robot.navigation.getPosition());
    	error *= Math.signum(Robot.navigation.getController().getError());
    	double currDxdt = dxdt.feed(wantedDxdt);
    	move(currDxdt, dadt.feed(currDxdt * wantedDadx + error));
    }
    
    public boolean nextTarget(){
    	targetIndex++;
    	if(targetIndex >= locs.length) {
    		return false;
    	}
		wantedDadx = computeTurn(targetIndex);
		wantedDxdt = Drive.percentToSpeed(maxP / (1 + Math.abs(wantedDadx)*Dms.Bot.DriveBase.HALFWIDTH));
    	cutoffLine = new HasPassedWaypoint(locs[targetIndex], locs[targetIndex-1]);
    	toFollow = new Line(locs[targetIndex], locs[targetIndex-1]);
    	return true;
    }
    
    //the index of the waypoint we're targeting
    public double computeTurn(int index) {
    	double da = .5 * (angleChanges[index] + angleChanges[index-1]);
    	double dx = locs[index].distanceFrom(locs[index-1]);
    	return da/dx;
    }
    
	public void setControllerAngle() {
		Position p = Robot.navigation.getPosition();
		double targetAngle = Math.toDegrees(-Math.atan2(locs[targetIndex].y - p.y, locs[targetIndex].x - p.x));
		if(rev) {
			targetAngle = VortxMath.navLimit(targetAngle + 180);
		}
		Robot.navigation.getController().setSetpoint(targetAngle);
	}
	
    //moves from dx/dt and da/dt
    public void move(double dx, double da) {
    	double left = dx + (da * Dms.Bot.DriveBase.HALFWIDTH);
    	double right = dx - (da * Dms.Bot.DriveBase.HALFWIDTH);
    	Robot.drive.setLeftRight(Drive.speedToPercent(left), Drive.speedToPercent(right));
    }
	
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    

}
