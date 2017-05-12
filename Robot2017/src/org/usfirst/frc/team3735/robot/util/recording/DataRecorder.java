package org.usfirst.frc.team3735.robot.util.recording;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/*
 * A class for recording actuator movement and replaying it
 * The constructor of a recordable device should add itself to the devices array
 * use the various methods to record, end recording, and replay movement
 * Files can be accessed and modified on the web browser
 */
public class DataRecorder {
	
	private static boolean isRecording = false;
	private static boolean isOutOfData = false;
	private static String filePath = "DefaultName";
	private static ArrayList<RecordableDevice> devices
			= new ArrayList<RecordableDevice>();
	private static int deviceLen;
	private static Formatter formatter;
	private static Scanner sc;
	
	public static synchronized void addDevice(RecordableDevice d){
		devices.add(d);
		deviceLen = devices.size();
	}
	
	public static void startRecording(String name){
		filePath = "/home/lvuser/"  + name + ".txt";
		try{
			formatter = new Formatter(filePath);
		}catch(Exception e){
			e.printStackTrace();
		}
		isRecording = true;
		for(RecordableDevice d : devices){
			d.setUpForRecording();
		}
	}
	
	public static boolean outOfData(){
		if(!sc.hasNextLine()){
			return true;
		}else{
			return false;
		}
	}
	
	public static void recordData(){
		for(int i = 0; i < devices.size(); i++){
			formatter.format("%s", devices.get(i).getData() + " ");
		}
		formatter.format("%s", System.getProperty("line.separator"));
	}
	
	public static void startSending(String name){
		filePath = "/home/lvuser/"  + name + ".txt";
		try{
			sc = new Scanner(new File(filePath));
		}catch(Exception e){
			e.printStackTrace();
		}
		for(int i = 0; i < devices.size(); i++){
			devices.get(i).setUpForSending();
		}

	}
	
	
	public static synchronized void sendData(){
		String line = sc.nextLine();
		String[] data = line.split(" ");
		if(data.length != deviceLen){
			System.out.println("Sending error: data lengths unequal");
			return;
		}
		for(int i = 0; i < data.length; i++){
			devices.get(i).sendData(data[i]);
		}
	}
	
	public static void endRecording(){
		formatter.flush();
		formatter.close();
		System.out.println("closing the formatter");
	}
	
	public static void endSending(){
		for(int i = 0; i < devices.size(); i++){
			devices.get(i).resestForNormal();
		}
	}
	
	

	

}
