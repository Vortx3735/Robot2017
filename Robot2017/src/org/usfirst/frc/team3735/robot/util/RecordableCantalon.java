package org.usfirst.frc.team3735.robot.util;

import java.util.ArrayList;
import java.util.Formatter;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class RecordableCantalon extends CANTalon implements RecordableDevice{
	
	TalonControlMode prevMode;
	
	public RecordableCantalon(int port){
		super(port);
		DataRecorder.addDevice(this);
	}
	
	@Override
	public String getData(){
		return String.format("%.4f", getOutputVoltage());
	}
	@Override
	public void sendData(String data) {
		set(Double.parseDouble(data));
	}

	@Override
	public void setUpForRecording() {
		
	}

	@Override
	public void setUpForSending() {
		prevMode = getControlMode();
		changeControlMode(TalonControlMode.Voltage);
	}

	@Override
	public void resestForNormal() {
		changeControlMode(prevMode);
	}


}
