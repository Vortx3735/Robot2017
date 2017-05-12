package org.usfirst.frc.team3735.robot.util.recording;

import java.util.ArrayList;
import java.util.Formatter;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class RecordableCantalon extends CANTalon implements RecordableDevice{
	
	TalonControlMode prevMode;
	private boolean isSending;
	private double voltage;

	public RecordableCantalon(int port){
		super(port);
		DataRecorder.addDevice(this);
		voltage = 0;
	}
	
	@Override
	public String getData(){
		return String.format("%.6f", this.get());
	}
	
	@Override
	public synchronized void sendData(String data) {
		try{
			voltage = Double.parseDouble(data);
		}catch(Exception e){
			System.out.println("Cantalon " + this.getDeviceID() + " failed to parse a double");
			e.printStackTrace();
		}
		super.set(voltage);
	}
	
	@Override
	public void set(double p){
		if(!isSending){
			super.set(p);
		}
	}

	@Override
	public void setUpForRecording() {
		
	}

	@Override
	public void setUpForSending() {
		prevMode = getControlMode();
		changeControlMode(TalonControlMode.PercentVbus);
		isSending = true;
	}

	@Override
	public void resestForNormal() {
		changeControlMode(prevMode);
		isSending = false;
	}


}
