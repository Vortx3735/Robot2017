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
		double startX = start.getX();
		double startY = start.getY();
		double endX = end.getX();
		double endY = end.getY();

		
		double changeX = endX - startX;
		double changeY = endY - startY;
		double angleA = 0;

		angleA = -1*Math.atan((changeY / changeX));
		angleA = Math.toDegrees(angleA);
		
		if(endX < startX){
			if(startY >= endY){
				angleA += 180;
			}else if(startY < endY){
				angleA -=180;
			}
		}
		
		System.out.println("angle is " + angleA);
		return angleA;
	}
	public static double findDistance(Point start, Point end){
		return Math.sqrt(Math.pow(end.getX()-start.getX(), 2) 
		               + Math.pow(end.getY()-start.getY(), 2)); 
	}
}