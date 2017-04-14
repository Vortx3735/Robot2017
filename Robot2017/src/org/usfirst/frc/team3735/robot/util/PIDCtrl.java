package org.usfirst.frc.team3735.robot.util;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class PIDCtrl extends PIDController{

	private double iZone = 0;
	private double actingI;
	private boolean isUsingIZone = false;
	
	public PIDCtrl(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, double period) {
		super(Kp, 0, Kd, source, output, period);
		actingI = Ki;
	}
	public PIDCtrl(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output) {
		super(Kp, 0, Kd, source, output);
		actingI = Ki;
	}
	public PIDCtrl(double Kp, double Ki, double Kd, double Kf, PIDSource source, PIDOutput output, double period) {
		super(Kp, 0, Kd, Kf, source, output, period);		
		actingI = Ki;
	}
	public PIDCtrl(double Kp, double Ki, double Kd, double Kf, PIDSource source, PIDOutput output) {
		super(Kp, 0, Kd, Kf, source, output);
		actingI = Ki;
	}
	
	/*
	 * these methods set and update the I-zone for the controller

	 */
	
	public synchronized void setIZone(double izone){
		iZone = izone;
	}
	
	public synchronized void setIsUsingIZone(boolean b){
		isUsingIZone = b;
	}
	
	public synchronized void updateI(double i){
		actingI = i;
		if(isUsingIZone){
			if(Math.abs(super.getError()) < iZone){
				super.setPID(super.getP(), actingI, super.getD());
			}else{
				super.setPID(super.getP(), 0, super.getD());
			}
		}else{
			super.setPID(super.getP(), actingI, super.getD());
		}
		
	}
	
	
	/*
	 * these methods route to the controller, manipulating instead the acting I
	 */
	
	public synchronized void setPID(double p, double i, double d) {
		actingI = i;
		super.setPID(p, 0, d);	
	}

	
	public synchronized void setPID(double p, double i, double d, double f) {
		actingI = i;
		super.setPID(p, 0, d, f);
	}



	
	
	
	

}
