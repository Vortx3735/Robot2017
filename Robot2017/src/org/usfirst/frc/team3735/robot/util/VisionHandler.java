package org.usfirst.frc.team3735.robot.util;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3735.robot.pipelines.GearPipeline;

import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class VisionHandler {

	private VisionThread thread;
	private Object imgLock = new Object();
	
	private double centerX = 0.0;
	private double centerY = 0.0;
	private double height = 0.0;
	private double width = 0.0;
	
	double[] centerXs;
	double[] centerYs;
	double[] widths;
	double[] heights;
	
	private NetworkTable mainTargetTable;
	private NetworkTable allSquaresTable;

	private ArrayList<MatOfPoint> output;
	
	public VisionHandler(ContoursOutputPipeline pipe, VideoSource source, int numTargets, String pubAddress){
		mainTargetTable = NetworkTable.getTable(pubAddress);
		allSquaresTable = NetworkTable.getTable(pubAddress + "All");
		thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
            synchronized (imgLock) {
//            	for(MatOfPoint m : filterContoursOutput() ){
//        			Rect w = Imgproc.boundingRect(m);
//        			System.out.print(w.x + "  ");
//        		}
//        		System.out.println();
            	if(pipe.filterContoursOutput().isEmpty()){
            		centerX = -1;
            		centerY = -1;
            		width = -1;
            		height = -1;
            	}else{
            		output = pipe.filterContoursOutput();
            		ArrayList<MatOfPoint> targets = new ArrayList<MatOfPoint>();
            		for(int i = 0; i < output.size() && i < numTargets; i++){
            			targets.add(output.get(i));
            		}
            		Rect r = boundRects(targets);
            		centerX = r.x + r.width/2;
            		centerY = r.y + r.height/2;
            		width = r.width;
            		height = r.height;
            		
            	}
            	
            }
	    });
		
	}
	
    
    public double getCenterX(){
    	double centerX;
		synchronized (imgLock) {
			centerX = this.centerX;
		}
		return centerX;
    }

    public double getCenterY(){
    	double centerY;
		synchronized (imgLock) {
			centerY = this.centerY;
		}
		return centerY;
    }

    public double getWidth(){
    	double width;
		synchronized (imgLock) {
			width = this.width;
		}
		return width;
    }
    public double getHeight(){
    	double height;
		synchronized (imgLock) {
			height = this.height;
		}
		return height;
    }
	
	public void startThread(){
		thread.start();
	}
	
	
	private Rect boundRects(ArrayList<MatOfPoint> targets) {
		ArrayList<Rect> rects = new ArrayList<Rect>();
		for(MatOfPoint n : targets){
			rects.add(Imgproc.boundingRect(n));
		}
		Point tl = rects.get(0).tl();
		Point br = rects.get(0).br();
		
		for(Rect r : rects){
			tl.x = Math.min(tl.x, r.x);
			tl.y = Math.min(tl.y, r.y);
			br.x = Math.max(br.x, r.br().x);
			br.y = Math.max(br.y, r.br().y);
		}
		return new Rect(tl,br);
		
	}
	
	public void publishTarget(){
		if(mainTargetTable != null){
			mainTargetTable.putNumberArray("centerX", new double[]{getCenterX()});
			mainTargetTable.putNumberArray("centerY", new double[]{getCenterY()});
			mainTargetTable.putNumberArray("height", new double[]{getHeight()});
			mainTargetTable.putNumberArray("width", new double[]{getWidth()});
		}
		
	}
	
	public void publishAll(){
		if(allSquaresTable != null && output != null){
			centerXs = new double[output.size()];
			centerYs = new double[output.size()];
			widths = new double[output.size()];
			heights = new double[output.size()];
			
			for(int i = 0; i < output.size(); i++){
				Rect r = Imgproc.boundingRect(output.get(i));
				centerXs[i] = r.x + r.width/2;
				centerYs[i] = r.y + r.height/2;
				widths[i] = r.width;
				heights[i] = r.height;
			}
		}

	}




}
