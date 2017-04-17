package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class GearIntake extends Subsystem {
	CANTalon roller;
	//CANTalon bottomRoller;
	Solenoid liftSolenoid;
	Solenoid topFeederSolenoid;

	private boolean isDown = false;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public GearIntake(){
		roller = new CANTalon(RobotMap.GearIntake.topRoller);
		
		roller.changeControlMode(TalonControlMode.Voltage);
		
		roller.setInverted(RobotMap.GearIntake.topRollerInverted);
		
		liftSolenoid = new Solenoid(RobotMap.GearIntake.liftSolenoid);
		liftUp();
	}

    public void initDefaultCommand() {
    }
    
    //positive is out, negative is in
    //actually flip that
    public void setRollerVoltage(double speed){
    	roller.set(speed);
    }
    
    public double getRollerPower(){
    	return Math.abs(roller.getOutputVoltage() * roller.getOutputCurrent());
    }

    public void liftDown(){
    	liftSolenoid.set(true);
    	isDown = true;
    }
    public void liftUp(){
    	liftSolenoid.set(false);
    	isDown = false;
    }
    
    public void switchLift(){
    	if(isDown){
    		liftUp();
    	}else{
    		liftDown();
    	}
    }
    
    public void log(){
    	SmartDashboard.putNumber("Gear Roller getPower", getRollerPower());
    }
}

