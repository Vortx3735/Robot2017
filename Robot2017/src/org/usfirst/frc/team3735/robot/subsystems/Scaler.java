package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerOff;
import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.settings.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Scaler extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	WPI_TalonSRX motor;
	WPI_TalonSRX motor2;

	//private final double voltageChangeSpeed = 5;

	
	private boolean isOverLoaded = false;
	private double rampRate = Constants.Scaler.rampRate;
	private double percent = 0;
	
	public Scaler(){
		motor = new WPI_TalonSRX(RobotMap.Scaler.motor);
		//motor.setCloseLoopRampRate(.2);
		motor.setInverted(RobotMap.Scaler.scalerInverted);
		motor2 = new WPI_TalonSRX(RobotMap.Scaler.motor2);
		//motor2.setCloseLoopRampRate(.2);
		motor2.setInverted(RobotMap.Scaler.scalerInverted);
		//motor.changeControlMode(WPI_TalonSRX.TalonControlMode.Voltage);
		//motor.setVoltageCompensationRampRate(voltageChangeSpeed);
		log();

	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ScalerOff());
    }
	
	public void log(){
		
	}
	
	public void setCurrent(double current){
		if(getPower() > Constants.Scaler.powerMax){
			isOverLoaded = true;
			percent = 0;
			motor.set(percent);
			motor2.set(-percent);
		}else{
			isOverLoaded = false;
			if(current < percent){
				percent -= rampRate;
			}else if(current > percent){
				percent += rampRate;
			}
			motor.set(percent);
			motor2.set(-percent);

		}
	}
	
	public void setPercent(double p){
		percent = 0;
	}
	
	public double getPower(){
		//return Math.abs(motor.getOutputCurrent() * motor.getOutputVoltage());
		return 0;
	}
	
	public boolean getOverloaded(){
		return isOverLoaded;
	}

	public void debugLog() {
		SmartDashboard.putNumber("Scaler motor getPower", getPower());
		//SmartDashboard.putNumber("Scaler motor joystick value", Robot.oi.getMainRightY());
		SmartDashboard.putBoolean("Scaler overLoaded", isOverLoaded);
	}
}

