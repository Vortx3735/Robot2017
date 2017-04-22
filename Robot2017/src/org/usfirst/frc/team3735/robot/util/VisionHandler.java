package org.usfirst.frc.team3735.robot.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	double[] centerXs = {0};
	double[] centerYs = {0};
	double[] widths = {0};
	double[] heights = {0};
	
	private NetworkTable mainTargetTable;
	private NetworkTable allSquaresTable;

	private ArrayList<MatOfPoint> output;
	
	public enum VisionType{
		Normal,
		NeedsNumTargets,
		RemoveXOutliers,
		GetCenterMostSingle, 
		getCenterMost, getCorrectRatio, getSimilarHeights
	}
	
//	for(MatOfPoint m : filterContoursOutput() ){
//		Rect w = Imgproc.boundingRect(m);
//		System.out.print(w.x + "  ");
//	}
//	System.out.println();
	
	public VisionHandler(ContoursOutputPipeline pipe, VideoSource source, int numTargets, String pubAddress, VisionType type){
		mainTargetTable = NetworkTable.getTable(pubAddress);
		allSquaresTable = NetworkTable.getTable(pubAddress + "All");
		
		switch(type){
			case Normal:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
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
				break;
			case NeedsNumTargets:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty() || pipe.filterContoursOutput().size() < numTargets){
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
				break;
			case RemoveXOutliers:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		centerX = -1;
		            		centerY = -1;
		            		width = -1;
		            		height = -1;
		            	}else{
		            		output = pipe.filterContoursOutput();
		            		ArrayList<Rect> rects = new ArrayList<Rect>();
		            		for(MatOfPoint n : output){
		            			rects.add(Imgproc.boundingRect(n));
		            		}
		            		rects = removeOutliers(rects);
		            		ArrayList<Rect> targets = new ArrayList<Rect>();
		            		for(int i = 0; i < rects.size() && i < numTargets; i++){
		            			targets.add(rects.get(i));
		            		}
		            		Rect r = boundRectsFromRects(targets);
		            		centerX = r.x + r.width/2;
		            		centerY = r.y + r.height/2;
		            		width = r.width;
		            		height = r.height;
		            		
		            	}
		            	
		            }
				});
				break;
			case GetCenterMostSingle:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		centerX = -1;
		            		centerY = -1;
		            		width = -1;
		            		height = -1;
		            	}else{
		            		output = pipe.filterContoursOutput();
		            		ArrayList<Rect> rects = new ArrayList<Rect>();
		            		for(MatOfPoint n : output){
		            			rects.add(Imgproc.boundingRect(n));
		            		}
		            		Rect r = getCenterMostSquare(rects);
		            		centerX = r.x + r.width/2;
		            		centerY = r.y + r.height/2;
		            		width = r.width;
		            		height = r.height;
		            		
		            	}
		            	
		            }
				});
				break;
			case getCenterMost:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		centerX = -1;
		            		centerY = -1;
		            		width = -1;
		            		height = -1;
		            	}else{
		            		output = pipe.filterContoursOutput();
		            		ArrayList<Rect> rects = new ArrayList<Rect>();
		            		for(MatOfPoint n : output){
		            			rects.add(Imgproc.boundingRect(n));
		            		}
		            		Collections.sort(rects, new Comparator<Rect>(){
		            			public int compare(Rect r1, Rect r2){
		            				return Math.abs(160-(r1.x + r1.width/2)) - Math.abs(160-(r2.x + r2.width/2));
		            			}
		            		});
		            		ArrayList<Rect> targets = new ArrayList<Rect>();
		            		for(int i = 0; i < rects.size() && i < numTargets; i++){
		            			targets.add(rects.get(i));
		            		}
		            		Rect r = boundRectsFromRects(targets);
		            		centerX = r.x + r.width/2;
		            		centerY = r.y + r.height/2;
		            		width = r.width;
		            		height = r.height;
		            		
		            	}
		            	
		            }
				});
			case getCorrectRatio:
				//DO NOT USE THIS IS UNFINISHED
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		centerX = -1;
		            		centerY = -1;
		            		width = -1;
		            		height = -1;
		            	}else{
		            		output = pipe.filterContoursOutput();
		            		ArrayList<Rect> rects = new ArrayList<Rect>();
		            		for(MatOfPoint n : output){
		            			rects.add(Imgproc.boundingRect(n));
		            		}
		            		//extra algorithm
		            		rects = removeOutliers(rects);

		            		int first = 0;
		            		int second = 0;
		            		double bestRatio = 100;
		            		if(rects.size() > 1){
		            			Rect r1 = rects.get(0);
		            			Rect r2 = rects.get(1);
		            			second = 1;
		            			bestRatio = Math.max(r1.height,r2.height)/Math.min(r1.height, r2.height);

		            		}
		            		for(int i = 0; i < rects.size(); i++){
		            			Rect r1 = rects.get(i);
			            		for(int j = i+1; j < rects.size(); j++){
			            			Rect r2 = rects.get(j);
			            			double ratio = Math.max((double)r1.height,(double)r2.height)/Math.min((double)r1.height, (double)r2.height);
			            			if(ratio < bestRatio){
			            				bestRatio = ratio;
			            				first = i;
			            				second = j;
			            			}
			            			System.out.print(ratio);
			            			System.out.println(" " + bestRatio);
			            		}
		            		}
		            		Rect r1 = rects.get(first);
		            		Rect r2 = rects.get(second);
		            		if(bestRatio > 2){
		            			Rect rr = (r1.height < r2.height)? r1 : r2;
		            			centerX = rr.x + rr.width/2;
			            		centerY = rr.y + rr.height/2;
			            		width = rr.width;
			            		height = rr.height;
		            		}else{
		            			ArrayList<Rect> goodOnes = new ArrayList<Rect>();
		            			goodOnes.add(r1);
		            			goodOnes.add(r2);
			            		Rect r = boundRectsFromRects(goodOnes);
			            		centerX = r.x + r.width/2;
			            		centerY = r.y + r.height/2;
			            		width = r.width;
			            		height = r.height;
			            		System.out.println();
		            		}
		            		
		            		
		            	}
		            	
		            }
				});
				break;
			default:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		centerX = -1;
		            		centerY = -1;
		            		width = -1;
		            		height = -1;
		            	}else{
		            		output = pipe.filterContoursOutput();
		            		ArrayList<Rect> rects = new ArrayList<Rect>();
		            		for(MatOfPoint n : output){
		            			rects.add(Imgproc.boundingRect(n));
		            		}
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
		
	}
    private Rect getCenterMostSquare(ArrayList<Rect> rects) {
    	Rect answer = new Rect();
		double closest = 162;
    	for(int i = 0; i < rects.size(); i++){
    		Rect r = rects.get(i);
    		if(VortxMath.isWithinThreshold(r.x + r.width/2, 160, closest)){
    			answer = rects.get(i);
    			closest = Math.abs(160 - (r.x + r.width/2));
    		}
    	}
    	return answer;
    	
    }

    
    private ArrayList<Rect> removeOutliers(ArrayList<Rect> rects) {
		double sum = 0;
		for(Rect r : rects){
			sum+= (r.x + r.width/2);
		}
		double mean = (double)sum/rects.size();
		double[] diffs = new double[rects.size()];
		for(int i = 0; i < diffs.length; i++){
			diffs[i] = Math.pow(mean-(double)(rects.get(i).x + rects.get(i).width/2), 2);
		}
		sum = 0;
		for(double d : diffs){
			sum+=d;
		}
		double stvar = Math.sqrt(sum/(double)diffs.length);
		ArrayList<Rect> answer = new ArrayList<Rect>();
		for(int i = 0; i < diffs.length; i++){
			if(VortxMath.isWithinThreshold(rects.get(i).x + rects.get(i).width/2, mean, stvar + 5)){
				answer.add(rects.get(i));
			}
		}
		return answer;

		
		
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
	
	public void pause(){
		try {
			thread.wait();
		} catch (Exception e) {
			System.out.println("Error in pausing thread");
			e.printStackTrace();
		}
	}
	public void resume(){
		try {
			thread.notify();
		} catch (Exception e) {
			System.out.println("Error in resuming thread");
			e.printStackTrace();
		}
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
	
	private Rect boundRectsFromRects(ArrayList<Rect> rects) {
		
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
			mainTargetTable.putNumberArray("width", new double[]{getWidth()});
			mainTargetTable.putNumberArray("height", new double[]{getHeight()});
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
		
		allSquaresTable.putNumberArray("centerX", centerXs);
		allSquaresTable.putNumberArray("centerY", centerYs);
		allSquaresTable.putNumberArray("width", widths);
		allSquaresTable.putNumberArray("height", heights);

	}




}
