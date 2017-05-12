package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterAgitatorOff;
import org.usfirst.frc.team3735.robot.util.settings.Setting;

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

	CANTalon drum;
	CANTalon drum2;

	CANTalon agitator;
//	Encoder drumEncoder = new Encoder(RobotMap.Shooter.encoder1, RobotMap.Shooter.encoder2);
	
	double drumVoltage;
	double agitatorVoltage;
	
//	String drumKey = "Shooter setVoltage";
//	String agitatorKey = "Agitator setVoltage";
	
	Setting drumSet;
	Setting agitatorSet;
	
	Setting F;
	Setting P;
	Setting I;
	Setting D;
	
//	public PIDController controller = new PIDController(1.0, 0.0, 0.0, drumEncoder, drum);
	
	public Shooter(){
		drum = new CANTalon(RobotMap.Shooter.drum);
		drum.setInverted(RobotMap.Shooter.drumInverted);
		drum.changeControlMode(TalonControlMode.Speed);
		//drum.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		drum.setCloseLoopRampRate(6);
		
		drum2 = new CANTalon(RobotMap.Shooter.drum2);
		drum2.changeControlMode(TalonControlMode.Follower);
		drum2.set(drum.getDeviceID());
		
		agitator = new CANTalon(RobotMap.Shooter.agitator);
		agitator.setInverted(RobotMap.Shooter.agitatorInverted);
		agitator.changeControlMode(TalonControlMode.Voltage);
		
		
		drumSet = new Setting("Shooter set", Constants.Shooter.shootSpeed);
		agitatorSet = new Setting("Agitator set", Constants.Shooter.agitatorVoltage);

		
//		F = new Setting("Shooter F", .022);
//		P = new Setting("Shooter P", .01);
//		I = new Setting("Shooter I", .0);
//		D = new Setting("Shooter D", .0005);

		F = new Setting("Shooter F", .028);
		P = new Setting("Shooter P", .03);
		I = new Setting("Shooter I", .0);
		D = new Setting("Shooter D", 1);
        drum.configNominalOutputVoltage(0.0, -0.0); 
        drum.configPeakOutputVoltage(12.0, -12.0); 

        drum.setProfile(0); 
        drum.setF(F.getValue()); 
        drum.setP(P.getValue()); 
        drum.setI(I.getValue());  
        drum.setD(D.getValue()); 
//        
//        try{
//    	SmartDashboard.putData(new ShooterSmartDashboard());
//        }catch(Exception e){
//        	System.out.println(e);
//        }
	} 
	
	public void log() {
		SmartDashboard.putNumber("Shooter Motor getSpeed", drum.getSpeed());
		SmartDashboard.putNumber("Shooter Motor get", drum.get());
		drum.setF(F.getValue()); 
        drum.setP(P.getValue()); 
        drum.setI(I.getValue());  
        drum.setD(D.getValue()); 
        
		//SmartDashboard.putNumber("Agitator getPower", getAgitatorPower());
		//SmartDashboard.putNumber("Shooter getPower", getShooterPower());
	}


  	public void initDefaultCommand() {
  		setDefaultCommand(new ShooterAgitatorOff());
  	}
  
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setDrumSpeed(double d){
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
    	//drumVoltage = SmartDashboard.getNumber(drumKey, drumVoltage);
    	drum.set(drumSet.getValue());
    }
    
    public void setAgitatorSmartDashboard(){
    	agitator.set(agitatorSet.getValue());
    }
    
    public void setDrumSmartDashboard(double speed){
    	drumSet.setValue(speed);
    	drum.set(speed);
    }
    
    public void setAgitatorSmartDashboard(double voltage){
    	agitatorSet.setValue(voltage);
    	agitator.set(voltage);
    }
    

   
    
    public double getAgitatorPower(){
    	return Math.abs(agitator.getOutputCurrent() * agitator.getOutputVoltage());
    }
    
    public double getShooterPower(){
    	return Math.abs(drum.getOutputCurrent() * drum.getOutputVoltage());
    }
}

