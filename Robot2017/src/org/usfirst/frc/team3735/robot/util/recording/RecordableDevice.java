package org.usfirst.frc.team3735.robot.util.recording;

import java.util.ArrayList;
import java.util.Formatter;

public interface RecordableDevice {

	public String getData();
	public void sendData(String data);
	public void setUpForRecording();
	public void setUpForSending();
	public void resestForNormal();
}
