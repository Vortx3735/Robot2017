package org.usfirst.frc.team3735.robot.subsystems;

import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.ShooterMotorTest;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {

	CANTalon drum = new CANTalon(RobotMap.Shooter.drum);
	Encoder drumEncoder = new Encoder(RobotMap.Shooter.encoder1, RobotMap.Shooter.encoder2);
	double targetSpeed = 0;
	
	public PIDController controller = new PIDController(1.0, 0.0, 0.0, drumEncoder, drum);
	
	public Shooter(){
		drum.changeControlMode(TalonControlMode.Position);
		drum.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
	} 
	
	public void log() {
		
	}


  	public void initDefaultCommand() {
  	}
  
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setVoltage(double voltage){
    	drum.set(voltage);
    }
    
    public void set(double d){
    	drum.set(d);
    	System.out.println("set: " + d);
    }
}

