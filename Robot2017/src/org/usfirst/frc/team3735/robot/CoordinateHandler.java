package org.usfirst.frc.team3735.robot;

import java.util.ArrayList;

import org.usfirst.frc.team3735.robot.Constants.Field;
import org.usfirst.frc.team3735.robot.Constants.RobotDimensions;
import org.usfirst.frc.team3735.robot.util.Point;

public class CoordinateHandler {

	public double length = 33.65;
	public double width = 40;
	public double height = 23.5;
	public double halfLength = RobotDimensions.length/2;
	
	public Point startTop = new Point(halfLength, 80.7);
	public Point startMiddle = new Point(halfLength, 0);
	public Point startBottom = new Point(halfLength, -72.9);
	
	public Point topLift = new Point(123.9425, 45.059);
	public Point middleLift = new Point(Field.fromStartToGearLiftMiddle-halfLength, 0);
	public Point bottomLift = new Point(123.9425, 45.059);
	
	public Point retrivalAndBaseLine = new Point(93.25, 126.6);
	public Point opSideRetrivalAndBaseLine = new Point(420, 126.6);
	
	public Point midBackUp = new Point(12+halfLength, Field.midHeight);
	public Point crossBottomBaseLine = new Point(140, -117.45);
	
	public Point lineUpWithBoiler = new Point(46.65, -117.45);
	public Point boiler = new Point(40.373, 38.555);
	public Point boilerExact = new Point(0, -162);
	
	public Point lineUpWithTopLift = new Point(93.25, 98.34);
	public Point lineUpWithBottomLift = new Point(93.25, -98.34);
	
	private ArrayList<Point> points;
	private boolean isRightSide = false;
	
	public CoordinateHandler(){
		points.add(startTop);
		points.add(startMiddle);
		points.add(startBottom);
		
		points.add(topLift);
		points.add(middleLift);
		points.add(bottomLift);
		
		points.add(retrivalAndBaseLine);
		points.add(opSideRetrivalAndBaseLine);
		
		points.add(midBackUp);
		points.add(crossBottomBaseLine);
		
		points.add(lineUpWithBoiler);
		points.add(boiler);
		points.add(boilerExact);
		
		points.add(lineUpWithTopLift);
		points.add(lineUpWithBottomLift);
	}
	
	public void switchToRightSide(){
		if(!isRightSide){
			switchPoints();
			isRightSide = true;
		}else{
			System.out.println("right side is already chosen");
		}
	}
	
	public void switchToLeftSide(){
		if(isRightSide){
			switchPoints();
			isRightSide = false;
		}else{
			System.out.println("left side is already chosen");
		}
	}
	
	private void switchPoints(){
		for(Point p : points){
			double y = p.getY();
			p.setY( y * -1);
		}
	}
	
	
}
