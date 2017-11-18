package org.usfirst.frc.team3735.robot.util.vision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3735.robot.util.calc.VortxMath;

import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class VisionHandler {

	protected static final int IMG_WIDTH = 320;
	protected static final int IMG_HEIGHT = 240;
			
	private VisionThread thread;
	private Object imgLock = new Object();

	private NetworkTable mainTargetTable;
	private NetworkTable allSquaresTable;

	private ArrayList<MatOfPoint> contOutput;
	ArrayList<Rect> rects = new ArrayList<Rect>();

	public Rect target = new Rect();
	
	public static Rect nullTarget = new Rect(-1,-1,-1,-1);
	
	public enum VisionType{
		Normal,
		NeedsNumTargets,
		RemoveXOutliers,
		GetCenterMostSingle, 
		getCenterMost, 
		getCorrectRatio, 
		getSimilarHeights
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
		            		target = new Rect(-1,-1,-1,-1);
		            	}else{
		            		updateRects(pipe);
		            		rects = firstNTargets(rects, numTargets);
		            		target = boundRects(rects);
		            	}
		            }
				});
				break;
			case NeedsNumTargets:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty() || pipe.filterContoursOutput().size() < numTargets){
		            		target = new Rect(-1,-1,-1,-1);
		            	}else{
		            		updateRects(pipe);
		            		rects = firstNTargets(rects, numTargets);
		            		target = boundRects(rects);
		            	}
		            }
				});
				break;
			case RemoveXOutliers:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		target = new Rect(-1,-1,-1,-1);
		            		
		            	}else{
		            		updateRects(pipe);
		            		rects = removeOutliers(rects);
		            		rects = firstNTargets(rects, numTargets);
		            		target = boundRects(rects);
		            	}
		            }
				});
				break;
			case GetCenterMostSingle:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		target = new Rect(-1,-1,-1,-1);
		            	}else{
		            		updateRects(pipe);
		            		target = getCenterMostSquare(rects);
		            	}
		            }
				});
				break;
			case getCenterMost:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		target = new Rect(-1,-1,-1,-1);
		            	}else{
		            		updateRects(pipe);
		            		sortByCenterMost(rects);
		            		rects = firstNTargets(rects, numTargets);
		            		target = boundRects(rects);
		            	}
		            }
				});
			case getCorrectRatio:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		target = new Rect(-1,-1,-1,-1);
		            	}else{
		            		updateRects(pipe);
		            		rects = removeOutliers(rects);
		            		if(rects.size() == 1){
		            			target = rects.get(0);
		            		}else{
		            			rects = getClosestHeightRatio(rects);
			            		Rect r1 = rects.get(0);
			            		Rect r2 = rects.get(1);
			            		double ratio = getAbsRatio(r1.height, r2.height);
			            		if(ratio < .5){
			            			target = (r1.height < r2.height)? r1 : r2;
			            		}else{
				            		target = boundRects(rects);
			            		}
		            		}
		            	}
		            }
				});
				break;
			default:
				thread = new VisionThread(source, (VisionPipeline)pipe, pipeline -> {
		            synchronized (imgLock) {
		            	if(pipe.filterContoursOutput().isEmpty()){
		            		target = new Rect(-1,-1,-1,-1);
		            	}else{
		            		updateRects(pipe);
		            		rects = firstNTargets(rects, numTargets);
		            		target = boundRects(rects);
		            	}
		            }
				});
				break;
		}
		
	}


	private void updateRects(ContoursOutputPipeline pipe) {
		contOutput = pipe.filterContoursOutput();
		rects.clear();
		for(MatOfPoint n : contOutput){
			rects.add(Imgproc.boundingRect(n));
		}
	}

	private ArrayList<Rect> getClosestHeightRatio(ArrayList<Rect> rs) {
		ArrayList<Rect> result = new ArrayList<Rect>();
		if(rs.size() == 1){
			result.add(rs.get(0));
			return result;
		}
		
		int first = 0;
		int second = 1;
		double bestRatio = getAbsRatio(rs.get(0).height,rs.get(1).height);
		
		for(int i = 0; i < rs.size(); i++){
			Rect r1 = rs.get(i);
    		for(int j = i+1; j < rs.size(); j++){
    			Rect r2 = rs.get(j);
    			double ratio = getAbsRatio(r1.height, r2.height);
    			if(ratio > bestRatio){
    				bestRatio = ratio;
    				first = i;
    				second = j;
    			}
    			//System.out.print(ratio);
    			//System.out.println(" " + bestRatio);
    		}
		}
		result.add(rs.get(first));
		result.add(rs.get(second));
		return result;
	}

	private void sortByCenterMost(ArrayList<Rect> rs) {
		Collections.sort(rs, new Comparator<Rect>(){
			public int compare(Rect r1, Rect r2){
				return (int) (Math.abs(IMG_WIDTH/2-getCX(r1)) - Math.abs(IMG_WIDTH/2-getCX(r2)));
			}
		});	
	}
	
	private ArrayList<Rect> firstNTargets(ArrayList<Rect> r, int num) {
		ArrayList<Rect> result = new ArrayList<Rect>();
		for(int i = 0; i < r.size() && i < num; i++){
			result.add(r.get(i));
		}
		return result;
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


	private Rect boundRects(ArrayList<Rect> rects) {
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

	private double getCX(Rect r){
		return r.x + (double)r.width/2;
	}

	private double getCY(Rect r){
		return r.y + (double)r.height/2;
	}

	private double getAbsRatio(double x, double y) {
		return Math.abs(Math.min(x,y)/Math.max(x, y));
	}

	public double getCenterX(){
    	double centerX;
		synchronized (imgLock) {
			centerX = target.x + (double)target.width/2;
		}
		return centerX;
    }

    public double getCenterY(){
    	double centerY;
		synchronized (imgLock) {
			centerY = target.y + (double)target.height/2;
		}
		return centerY;
    }

    public double getWidth(){
    	double width;
		synchronized (imgLock) {
			width = target.width;
		}
		return width;
    }
    public double getHeight(){
    	double height;
		synchronized (imgLock) {
			height = target.height;
		}
		return height;
    }
	
	public void startThread(){
		thread.start();
	}
	
	public void pause(){
		try {
			//thread.interrupt();
		} catch (Exception e) {
			System.out.println("Error in pausing thread");
			e.printStackTrace();
		}
	}
	public void resume(){
		try {
			
		} catch (Exception e) {
			System.out.println("Error in resuming thread");
			e.printStackTrace();
		}
	}

	public void publishTarget(){
		if(mainTargetTable != null){
			mainTargetTable.putNumberArray("centerX", new double[]{getCenterX()});
			mainTargetTable.putNumberArray("centerY", new double[]{getCenterY()});
			mainTargetTable.putNumberArray("width", new double[]{getWidth()});
			mainTargetTable.putNumberArray("height", new double[]{getHeight()});
		}
		
	}
	
	double[] centerXs = {0};
	double[] centerYs = {0};
	double[] widths = {0};
	double[] heights = {0};
	
	public void publishAll(){
		synchronized(imgLock){
			if(allSquaresTable != null && rects != null){
				centerXs = new double[rects.size()];
				centerYs = new double[rects.size()];
				widths = new double[rects.size()];
				heights = new double[rects.size()];
				
				for(int i = 0; i < rects.size(); i++){
					Rect r = rects.get(i);
					if(r != null){
						centerXs[i] = getCX(r);
						centerYs[i] = getCY(r);
						widths[i] = r.width;
						heights[i] = r.height;
					}
					
				}
			}
			
			allSquaresTable.putNumberArray("centerX", centerXs);
			allSquaresTable.putNumberArray("centerY", centerYs);
			allSquaresTable.putNumberArray("width", widths);
			allSquaresTable.putNumberArray("height", heights);

		}
		
	}




}
