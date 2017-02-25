package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */

public class BallIntake extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon motor;
	
	
	public BallIntake(){
		motor = new CANTalon(RobotMap.BallIntake.roller);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setRollerCurrent(double speed){
    	motor.set(speed);
    }
    
    public double getPower(){
    	return motor.getOutputVoltage() * motor.getOutputCurrent();
    }
    
    public void log(){
    	SmartDashboard.putNumber("Ball Intake getPower", getPower());
    }
}

