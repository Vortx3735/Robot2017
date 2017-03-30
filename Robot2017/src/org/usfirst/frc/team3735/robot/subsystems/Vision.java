package org.usfirst.frc.team3735.robot.subsystems;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3735.robot.pipelines.GearPipeline;
import org.usfirst.frc.team3735.robot.pipelines.PegPipeline;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class Vision extends Subsystem {

	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private VisionThread visionThread;
	private UsbCamera camera;
	private final Object imgLock = new Object();
	
	private double centerX = 0.0;
	private double centerY = 0.0;	
	private double area = 0.0;
	private double height = 0.0;
	private double width = 0.0;
	
	private VisionPipeline gearPipe;
	private VisionPipeline pegPipe;
	
	private VisionThread gearThread;
	private VisionThread pegThread;
	
	public Vision(){
		camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    initThreads();
	    visionThread = pegThread;
	}
	
	private void initThreads(){
		gearThread = new VisionThread(camera, new GearPipeline(), pipeline -> {
            synchronized (imgLock) {
                if(pipeline.getCenterX() != -1){
                    centerX = pipeline.getCenterX();
                }
                //centerY = pipeline.getCenterY();
                if(pipeline.getArea() != -1){
                    area = pipeline.getArea();
                }
                ///height = pipeline.getHeight();
                //width = pipeline.getWidth();
            }
	    });
	    gearThread.start();
//	    try {
//			gearThread.wait();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	    
	    pegThread = new VisionThread(camera, new PegPipeline(), pipeline -> {
            synchronized (imgLock) {
            	if(pipeline.getCenterX() != -1){
                    centerX = pipeline.getCenterX();
                }
                //centerY = pipeline.getCenterY();
                if(pipeline.getArea() != -1){
                    area = pipeline.getArea();
                }
            }
	    });
	    pegThread.start();
//	    try {
//			pegThread.wait();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	    
	}
	
	public enum Pipes{
		Gear,
		Peg
	}
	
	private void setThread(VisionThread p){
		visionThread = p;
	}
	
	public void setPipeline(Pipes p){
		switch(p){
			case Gear:
				setThread(gearThread);
				break;
			case Peg:
				setThread(pegThread);
				break;
		}
	}
	
	public void pause(){
		try {
			visionThread.wait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void resume(){
		visionThread.notify();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void log(){
		//SmartDashboard.putNumber("CenterX", getCenterX());
		//SmartDashboard.putNumber("CenterY", getCenterY());
		SmartDashboard.putNumber("Relative CX", getRelativeCX());
		SmartDashboard.putNumber("height", getHeight());
		SmartDashboard.putNumber("width", getWidth());
		SmartDashboard.putNumber("area", getArea());
    }

    
    
//    public double getCenterX(){
//    	double centerX;
//		synchronized (imgLock) {
//			centerX = this.centerX;
//		}
//		return centerX;
//    }
    public double getRelativeCX(){
    	double centerX;
		synchronized (imgLock) {
			centerX = this.centerX;
		}
		return centerX - (IMG_WIDTH / 2);
    }
    public double getCenterY(){
    	double centerY;
		synchronized (imgLock) {
			centerY = this.centerY;
		}
		return centerY;
    }
    public double getArea(){
    	double area;
		synchronized (imgLock) {
			area = this.area;
		}
		return area;
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

    
    
}

