package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterSmartDashboard;

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
	CANTalon agitator = new CANTalon(RobotMap.Shooter.agitator);
//	Encoder drumEncoder = new Encoder(RobotMap.Shooter.encoder1, RobotMap.Shooter.encoder2);
	double targetSpeed = 0;
	private boolean isEnabled = false;
	
//	public PIDController controller = new PIDController(1.0, 0.0, 0.0, drumEncoder, drum);
	
	public Shooter(){
		drum.changeControlMode(TalonControlMode.Voltage);
		agitator.changeControlMode(TalonControlMode.Voltage);
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
		SmartDashboard.putNumber("Shooter motor get", drum.get());
		SmartDashboard.putBoolean("shooter enabled", isEnabled);
	}


  	public void initDefaultCommand() {
  		setDefaultCommand(new ShooterSmartDashboard());
  	}
  
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setSpeed(double d){
    	if(isEnabled){
    		drum.set(d);
    	}else{
    		drum.set(0);
    	}
    }
    
    public void setAgitatorVoltage(double voltage){

    	if(isEnabled){
    		agitator.set(voltage);
    	}else{
    		agitator.set(0);
    	}
    }
    
    public void toggleEnabled(){
    	isEnabled = !isEnabled;
    }
}

