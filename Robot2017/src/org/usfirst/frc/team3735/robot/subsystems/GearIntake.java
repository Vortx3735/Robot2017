package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.settings.Constants;
import org.usfirst.frc.team3735.robot.settings.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class GearIntake extends Subsystem {
	WPI_TalonSRX roller;
	//WPI_TalonSRX bottomRoller;
	Solenoid liftSolenoid;

	private boolean isDown = false;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public GearIntake(){
		roller = new WPI_TalonSRX(RobotMap.GearIntake.topRoller);
		roller.setInverted(RobotMap.GearIntake.topRollerInverted);
		liftSolenoid = new Solenoid(RobotMap.GearIntake.liftSolenoid);
		liftUp();
	}

    public void initDefaultCommand() {
    }
    
    //positive is out, negative is in
    //actually flip that
    public void setRollerSpeed(double speed){
    	roller.set(speed);
    }
    


    public void liftDown(){
    	liftSolenoid.set(true);
    	isDown = true;
    }
    public void liftUp(){
    	liftSolenoid.set(false);
    	isDown = false;
    }
    
    public void setIsDown(boolean down) {
    	liftSolenoid.set(down);
    	isDown = down;
    }
    
    public void switchLift(){
    	if(isDown){
    		liftUp();
    	}else{
    		liftDown();
    	}
    }
    
    public void log(){
    }

	public void debugLog() {

		
	}
}

