package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerOff;

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
	private double rampRate = Constants.Scaler.rampRate;
	private double percent = 0;
	
	public Scaler(){
		motor = new CANTalon(RobotMap.Scaler.motor);
		motor.setCloseLoopRampRate(.2);
		
		motor.setInverted(RobotMap.Scaler.scalerInverted);
		//motor.changeControlMode(CANTalon.TalonControlMode.Voltage);
		//motor.setVoltageCompensationRampRate(voltageChangeSpeed);
		SmartDashboard.putNumber("Scaler motor power", getPower());
		SmartDashboard.putBoolean("Scaler overLoaded", isOverLoaded);

	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ScalerOff());
    }
	
	public void log(){
		SmartDashboard.putNumber("Scaler motor getPower", getPower());
		//SmartDashboard.putNumber("Scaler motor joystick value", Robot.oi.getMainRightY());
		SmartDashboard.putBoolean("Scaler overLoaded", isOverLoaded);
	}
	
	public void setCurrent(double current){
		if(getPower() > Constants.Scaler.powerMax){
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
		return Math.abs(motor.getOutputCurrent() * motor.getOutputVoltage());
	}
	
	public boolean getOverloaded(){
		return isOverLoaded;
	}
}

