package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.util.calc.Integrator;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;
import org.usfirst.frc.team3735.robot.util.settings.Func;


public class HasMoved extends ComTrigger{
	
	private Func deltaDistance;
	private Double rsd;
	private Double lsd;
	
	private boolean isIntegrating = false;
	private Integrator integrator;
	
	public HasMoved(Func distance){
		this.deltaDistance = distance;
	}
	
	public HasMoved(double distance){
		this(new Func() {
			public double getValue() {
				return distance;
			}
		});
	}

	@Override
	public void initialize() {
		if(Constants.Drive.isUsingLeftEncoders) {
			lsd = Robot.drive.getLeftPositionInches();
		}
		if(Constants.Drive.isUsingRightEncoders) {
			rsd = Robot.drive.getRightPositionInches();
		}
		if(lsd == null && rsd == null) {
			isIntegrating = true;
			integrator = new Integrator(0);
			integrator.init(Robot.drive.getSpeedInchesFromCurrent());
		}

	}
	
	@Override
	public void execute() {
		if(isIntegrating) {
			integrator.feed(Robot.drive.getSpeedInchesFromCurrent());
		}
	}
	
	@Override
	public boolean get() {
		if(deltaDistance.getValue() > 0){
			return distanceTraveled() > deltaDistance.getValue();
		}else{
			return distanceTraveled() < deltaDistance.getValue();
		}
	}


	
	public double distanceTraveled(){
		if((rsd != null) && (lsd != null)) {
			return .5 * ((Robot.drive.getLeftPositionInches() - lsd) + (Robot.drive.getRightPositionInches() - rsd));
		}
		if(lsd != null) {
			return Robot.drive.getLeftPositionInches() - lsd;
		}
		if(rsd != null) {
			return Robot.drive.getRightPositionInches() - lsd;
		}
		if(integrator != null) {
			return integrator.total;
		}
		System.out.println("Has Moved Error: distanceTraveled() called but not initialized");
		return 0;
		
	}
	
	public double distanceToGo(){
		return deltaDistance.getValue() - distanceTraveled();
	}
	
	public double distance(){
		return deltaDistance.getValue();
	}
	
	@Override
	public String getHaltMessage() {
		return "Moved " + deltaDistance.getValue() + " Inches";
	}
	
	
}
