package org.usfirst.frc.team3735.robot.util.recording;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class RecordableCantalon extends WPI_TalonSRX implements RecordableDevice{
	
	ControlMode prevMode;
	private boolean isSending;
	private double voltage;

	public RecordableCantalon(int port){
		
		super(port);
		DataRecorder.addDevice(this);
		voltage = 0;
		TalonSRX n = new WPI_TalonSRX(0);

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
		isSending = true;
	}

	@Override
	public void resestForNormal() {
		isSending = false;
	}


}
