package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Scaler extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon motor;
	//private final double voltageChangeSpeed = 5;

	
	private boolean isOverLoaded = false;
	private double rampRate = .01;
	private double percent = 0;
	
	public Scaler(){
		motor = new CANTalon(RobotMap.Scaler.motor);
		motor.setCloseLoopRampRate(.2);
		//motor.changeControlMode(CANTalon.TalonControlMode.Voltage);
		//motor.setVoltageCompensationRampRate(voltageChangeSpeed);
		SmartDashboard.putNumber("Scaler motor power", getPower());
		SmartDashboard.putBoolean("Scaler overLoaded", isOverLoaded);

	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public void log(){
		SmartDashboard.putNumber("Scaler motor power", getPower());
		//SmartDashboard.putNumber("Scaler motor joystick value", Robot.oi.getMainRightY());
		SmartDashboard.putBoolean("Scaler overLoaded", isOverLoaded);
	}
	
	public void setCurrent(double current){
		if(Math.abs(getPower()) > Constants.Scaler.powerMax){
			isOverLoaded = true;
			percent = 0;
			motor.set(percent);
		}else{
			isOverLoaded = false;
			if(current < percent){
				percent -= rampRate;
			}else if(current > percent){
				percent += rampRate;
			}
			motor.set(percent);
		}
	}
	
	public void setPercent(double p){
		percent = 0;
	}
	
	public double getPower(){
		return motor.getOutputCurrent() * motor.getOutputVoltage();
	}
	
	public boolean getOverloaded(){
		return isOverLoaded;
	}
}

