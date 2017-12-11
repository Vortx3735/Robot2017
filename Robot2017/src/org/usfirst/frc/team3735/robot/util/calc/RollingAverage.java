package org.usfirst.frc.team3735.robot.util.calc;

public class RollingAverage {
	
	private static final double FRAMERATE = 50;
	private Double[] numbers;
	int i;
	int size;
	double average;
	double sum;
	
	public RollingAverage(){
		this(1);
	}
	
	public RollingAverage(double time){
		size = (int)Math.round(FRAMERATE * time);
		numbers = new Double[size];
		i = 0;
		sum = 0;
	}
	
	
	public void compute(){
		if(numbers[i] != null){
			sum-= numbers[i];
		}
		numbers[i] = get();
		sum += numbers[i];
		
		i++;
		if(i >= size){
			i = 0;
		}
	}
	
	public double getAverage(){
		return sum / (double)size;
	}
	
	public void reset(){
		numbers = new Double[size];
		sum = 0;
	}
	
	public double get(){
		return 0;
	}
	
	
}
