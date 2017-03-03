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
	CANTalon topRoller;
	CANTalon bottomRoller;
	Solenoid liftSolenoid;
	Solenoid topFeederSolenoid;

	private boolean isDown = false;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public GearIntake(){
		topRoller = new CANTalon(RobotMap.GearIntake.topRoller);
		bottomRoller = new CANTalon(RobotMap.GearIntake.bottomRoller);
		
		topRoller.changeControlMode(TalonControlMode.Voltage);
		bottomRoller.changeControlMode(TalonControlMode.Voltage);
		
		topRoller.setInverted(Constants.GearIntake.topRollerInverted);
		bottomRoller.setInverted(Constants.GearIntake.bottomRollerInverted);
		
		liftSolenoid = new Solenoid(RobotMap.GearIntake.liftSolenoid);
		topFeederSolenoid = new Solenoid(RobotMap.GearIntake.topFeedSolenoid);
		liftUp();
	}

    public void initDefaultCommand() {
    }
    
    //positive is out, negative is in
    //actually flip that
    public void setRollerVoltage(double speed){
    	topRoller.set(speed);
    	bottomRoller.set(speed);
    }
    
    public double getTopRollerPower(){
    	return Math.abs(topRoller.getOutputVoltage() * topRoller.getOutputCurrent());
    }
    public double getBottomRollerPower(){
    	return Math.abs(bottomRoller.getOutputVoltage() * bottomRoller.getOutputCurrent());
    }
    
    public void feedOpen(){
    	topFeederSolenoid.set(true);
    }
    public void feedClose(){
    	topFeederSolenoid.set(false);
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
    	SmartDashboard.putNumber("Top Roller getPower", getTopRollerPower());
    	SmartDashboard.putNumber("Bottom Roller getPower", getBottomRollerPower());

    }
}

