package org.usfirst.frc.team3735.robot.util;

public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}
	
	public static double findAngle(Point start, Point end) {
		double angle = -Math.toDegrees(Math.atan2(end.getY()-start.getY(), end.getX()-start.getX()));
		System.out.println("angle is " + angle);
		return angle;
	}
	public static double findDistance(Point start, Point end){
		return Math.hypot(end.getX()-start.getX(), end.getY()-start.getY());
	}
}