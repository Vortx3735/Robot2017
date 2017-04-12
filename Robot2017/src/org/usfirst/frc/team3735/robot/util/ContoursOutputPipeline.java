package org.usfirst.frc.team3735.robot.util;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;

public interface ContoursOutputPipeline {
	
	public ArrayList<MatOfPoint> filterContoursOutput();

}
