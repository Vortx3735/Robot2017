package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerJoystickMovement;

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
	
	public Scaler(){
		motor = new CANTalon(RobotMap.Scaler.motor);
		//motor.changeControlMode(CANTalon.TalonControlMode.Voltage);
		//motor.setVoltageCompensationRampRate(voltageChangeSpeed);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ScalerJoystickMovement());
    }
	
	public void log(){
		SmartDashboard.putNumber("Scaler motor power", getPower());
		//SmartDashboard.putNumber("Scaler motor joystick value", Robot.oi.getMainRightY());
		SmartDashboard.putBoolean("Scaler overLoaded", isOverLoaded);
	}
	
	public void setCurrent(double current){
		if(getPower() >= Constants.Scaler.powerMax){
			isOverLoaded = true;
			setCurrent(0);
		}else{
			isOverLoaded = false;
			setCurrent(current);
		}
	}
	
	public double getPower(){
		return motor.getOutputCurrent() * motor.getOutputVoltage();
	}
}

