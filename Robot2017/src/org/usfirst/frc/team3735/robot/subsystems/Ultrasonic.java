package org.usfirst.frc.team3735.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Ultrasonic extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	SerialPort serialPort;
	
    public void initDefaultCommand() {
    }
    
    public Ultrasonic() {
    	try{
    		serialPort = new SerialPort(115200, SerialPort.Port.kUSB);
    		System.out.println("Created 0");
    	}catch(Exception e){
    		System.out.println(e);
    		try {
    			serialPort = new SerialPort(115200, SerialPort.Port.kUSB2);
    		} catch (Exception e2) {
    			System.out.println(e2);
    			System.out.println("No untrasonic sensor found");
    		}
    	}
    }
    
    public double getInchesDistance() {
    	double ret =-1;
    	if(serialPort != null){
	    	String distanceString = serialPort.readString(); 
	    	//System.out.println("Distance string is " + distanceString);
	        if(distanceString.length() >1){
		        int indexOfComma = distanceString.indexOf(',');
		        int indexOfI = distanceString.indexOf('I');       
		        double distanceInches = Double.parseDouble(distanceString.substring(indexOfComma+1, indexOfI));    
		        ret =  distanceInches;
	        } else {
	        	//System.out.println("Sensor string length is 0");
	        }
        } else {
        	//System.out.println("Ultra is null");
        	
        }
    	return ret;	
    }
    
    public void log() {
//    	SmartDashboard.putNumber("Ultrasonic distance in Inches", 
//    			Double.valueOf(String.format("%.1f",getInchesDistance())));
    }

	public void debugLog() {
		// TODO Auto-generated method stub
		
	}

}

