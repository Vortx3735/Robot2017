package org.usfirst.frc.team3735.robot.util.calc;

import edu.wpi.first.wpilibj.Timer;

public class Integrator {
	public double total = 0;
	public double lastTime;
	public double lastVal;
	
	
	public Integrator(double start) {
		total = start;
	}
	
	public Integrator() {
		this(0);
	}
	
	public void init(double val) {
		lastVal = val;
		lastTime = Timer.getFPGATimestamp();
	}
	
	/**
	 * Uses right sum integration currently. LastVal is in there if we wish to add triangles
	 * to integration pattern later.
	 * @param value		the value to integrate
	 */
	public void feed(double value) {
		double curTime = Timer.getFPGATimestamp();
		total += value * (curTime - lastTime);
		
		lastVal = value;
		lastTime = curTime;
	}
}
