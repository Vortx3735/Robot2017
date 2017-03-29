package org.usfirst.frc.team3735.robot.util;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class PIDCtrl extends PIDController{

	public PIDCtrl(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, double period) {
		super(Kp, Ki, Kd, source, output, period);
	}
	public PIDCtrl(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output) {
		super(Kp, Ki, Kd, source, output);
	}
	public PIDCtrl(double Kp, double Ki, double Kd, double Kf, PIDSource source, PIDOutput output, double period) {
		super(Kp, Ki, Kd, Kf, source, output, period);
	}
	public PIDCtrl(double Kp, double Ki, double Kd, double Kf, PIDSource source, PIDOutput output) {
		super(Kp, Ki, Kd, Kf, source, output);
	}
	
	

}
