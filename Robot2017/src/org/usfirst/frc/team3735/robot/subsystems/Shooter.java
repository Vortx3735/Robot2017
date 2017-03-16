package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOff;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {

	CANTalon drum = new CANTalon(RobotMap.Shooter.drum);
	CANTalon drum2 = new CANTalon(RobotMap.Shooter.drum2);

	CANTalon agitator = new CANTalon(RobotMap.Shooter.agitator);
//	Encoder drumEncoder = new Encoder(RobotMap.Shooter.encoder1, RobotMap.Shooter.encoder2);
	
	double drumVoltage;
	double agitatorVoltage;
	
	String drumKey = "Shooter setVoltage";
	String agitatorKey = "Agitator setVoltage";
	
//	public PIDController controller = new PIDController(1.0, 0.0, 0.0, drumEncoder, drum);
	
	public Shooter(){
		drum.changeControlMode(TalonControlMode.Voltage);
		
		drum2.changeControlMode(TalonControlMode.Follower);
		drum2.set(drum.getDeviceID());
		
		agitator.changeControlMode(TalonControlMode.Voltage);
		
		drum.setInverted(RobotMap.Shooter.drumInverted);
		agitator.setInverted(RobotMap.Shooter.agitatorInverted);
		
		drumVoltage = Constants.Shooter.shootVoltage;
		agitatorVoltage = Constants.Shooter.agitatorVoltage;
		
		SmartDashboard.putNumber(drumKey, drumVoltage);
		SmartDashboard.putNumber(agitatorKey, agitatorVoltage);
		
		//drum.changeControlMode(TalonControlMode.Speed);
		//drum.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
//        drum.configNominalOutputVoltage(+0.0f, -0.0f); 
//        drum.configPeakOutputVoltage(+12.0f, -12.0f); 
//
//        drum.setProfile(0); 
//        drum.setF(0.1097); 
//        drum.setP(0.22); 
//        drum.setI(0);  
//        drum.setD(0); 
//        
//        try{
//    	SmartDashboard.putData(new ShooterSmartDashboard());
//        }catch(Exception e){
//        	System.out.println(e);
//        }
	} 
	
	public void log() {
//		SmartDashboard.putNumber("Raw Motor Speed (native ticks/100ms)", drum.getSpeed());
//		SmartDashboard.putNumber("Motor Speed (RPM?)", drum.get());
		SmartDashboard.putNumber("Agitator getPower", getAgitatorPower());
		SmartDashboard.putNumber("Shooter getPower", getShooterPower());
	}


  	public void initDefaultCommand() {
  		setDefaultCommand(new ShooterOff());
  	}
  
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setDrumVoltage(double d){
//    	if(isEnabled){
//    		drum.set(d);
//    	}else{
//    		drum.set(0);
//    	}
    	drum.set(d);
    }    
    public void setAgitatorVoltage(double voltage){
//    	if(isEnabled){
//    		agitator.set(voltage);
//    	}else{
//    		agitator.set(0);
//    	}
    	agitator.set(voltage);
    }
    
    public void setDrumSmartDashboard(){
    	drumVoltage = SmartDashboard.getNumber(drumKey, drumVoltage);
    	setDrumVoltage(drumVoltage);
    }
    
    public void setAgitatorSmartDashboard(){
    	agitatorVoltage = SmartDashboard.getNumber(agitatorKey, agitatorVoltage);
    	setAgitatorVoltage(agitatorVoltage);
    }
    

    
//    public void toggleEnabled(){
//    	isEnabled = !isEnabled;
//    }
    
    public double getAgitatorPower(){
    	return Math.abs(agitator.getOutputCurrent() * agitator.getOutputVoltage());
    }
    
    public double getShooterPower(){
    	return Math.abs(drum.getOutputCurrent() * drum.getOutputVoltage());
    }
}

