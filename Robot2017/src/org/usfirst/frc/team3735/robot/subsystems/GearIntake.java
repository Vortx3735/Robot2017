package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class GearIntake extends Subsystem {
	CANTalon topRoller;
	CANTalon bottomRoller;
	Solenoid liftSolenoid;
	Solenoid topFeederSolenoid;


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public GearIntake(){
		topRoller = new CANTalon(RobotMap.GearIntake.topRoller);
		bottomRoller = new CANTalon(RobotMap.GearIntake.bottomRoller);
		
		liftSolenoid = new Solenoid(RobotMap.GearIntake.liftSolenoid);
		topFeederSolenoid = new Solenoid(RobotMap.GearIntake.topFeedSolenoid);
	}

    public void initDefaultCommand() {
        //setDefaultCommand(new GearIntakeRollersOff());
    }
    
    //positive is out, negative is in
    public void setRollerSpeed(double speed){
    	topRoller.set(speed);
    	bottomRoller.set(speed);
    }
    
    public void feedOpen(){
    	topFeederSolenoid.set(true);
    }
    public void feedClose(){
    	topFeederSolenoid.set(false);
    }
    public void liftDown(){
    	liftSolenoid.set(true);
    }
    public void liftUp(){
    	liftSolenoid.set(false);
    }
}

