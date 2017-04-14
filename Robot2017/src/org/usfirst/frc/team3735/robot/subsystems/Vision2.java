package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.pipelines.GearPipeline;
import org.usfirst.frc.team3735.robot.pipelines.PegPipelineLSNTest4;
import org.usfirst.frc.team3735.robot.pipelines.PegPipelineLSNTest5;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;
import org.usfirst.frc.team3735.robot.util.ContoursOutputPipeline;
import org.usfirst.frc.team3735.robot.util.VisionHandler;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class Vision2 extends Subsystem {


	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private UsbCamera camera1;
	private UsbCamera camera2;
	
	private final Object imgLock = new Object();
	//70/320) /180  *5
	private double centerX = 0.0;
	private double centerY = 0.0;	
	private double area = 0.0;
	private double height = 0.0;
	private double width = 0.0;
	
	private ContoursOutputPipeline pegPipe = new PegPipelineLSNTest5();
	
	
	private static NetworkTable table = NetworkTable.getTable("GRIP/myContoursReport");
	
	VisionHandler pegs;
	
	VisionHandler mainHandler;
	
	public Vision2(){
		camera1 = CameraServer.getInstance().startAutomaticCapture(0);
		//camera2 = CameraServer.getInstance().startAutomaticCapture(1);
		
		camera1.setFPS(16);
		//camera2.setFPS(16);
		
	    camera1.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    camera2.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    pegs = new VisionHandler(new PegPipelineLSNTest5(), camera1, 2);
	    pegs.startThread();
	    mainHandler = pegs;
	    
	}
	
	
	
	public enum Pipes{
		Gear,
		Peg
	}
	
	private void setThread(VisionHandler p){
		mainHandler = p;
	}
	
	public void setHandler(Pipes p){
		switch(p){
			case Gear:
				//setThread(gearThread);
				break;
			case Peg:
				setThread(pegs);
				break;
		}
	}
	

	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void log(){
		//SmartDashboard.putNumber("CenterX", getCenterX());
		//SmartDashboard.putNumber("CenterY", getCenterY());
		SmartDashboard.putNumber("Relative CX", getRelativeCX());
		SmartDashboard.putNumber("height", mainHandler.getHeight());
		SmartDashboard.putNumber("width", mainHandler.getWidth());
		//SmartDashboard.putNumber("area", mainHandler.getArea());
		System.out.println("logging in vision");
		table.putNumberArray("centerX", new double[]{mainHandler.getCenterX()});
		table.putNumberArray("centerY", new double[]{mainHandler.getCenterY()});
		table.putNumberArray("height", new double[]{mainHandler.getHeight()});
		table.putNumberArray("width", new double[]{mainHandler.getWidth()});
		
    }

	public double getRelativeCX() {
		return mainHandler.getCenterX() - IMG_WIDTH/2;
	}

    
    


    
    
}


