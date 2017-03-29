package org.usfirst.frc.team3735.robot.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class DataRecorder {
	
	private static boolean isRecording = false;
	private static boolean isOutOfData = false;
	private static String fileName = "DefaultName";
	private static ArrayList<RecordableDevice> devices
			= new ArrayList<RecordableDevice>();
	private static int deviceLen;
	private static Formatter formatter;
	private static Scanner sc;
	
	public static void addDevice(RecordableDevice d){
		devices.add(d);
		deviceLen = devices.size();
	}
	
	public static void startRecording(String name){
		fileName = name;
		try{
			formatter = new Formatter(fileName + ".txt");
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
			formatter.close();
			return true;
		}else{
			return false;
		}
	}
	
	public static void recordData(){
		for(RecordableDevice d : devices){
			formatter.format("%s", d.getData() + " ");
		}
		formatter.format("%s", System.getProperty("line.separator"));
	}
	
	public static void startSending(String name){
		try{
			sc = new Scanner(new File(name + ".txt"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void sendData(){
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
	
	

	

}
