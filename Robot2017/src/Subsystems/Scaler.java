package Subsystems;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.ScalerJoystickMovement;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Scaler extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon motor = new CANTalon(6);
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ScalerJoystickMovement());
		motor.changeControlMode(CANTalon.TalonControlMode.Voltage);
		motor.setVoltageCompensationRampRate(3);
    }
	
	public void log(){
		SmartDashboard.putNumber("motor voltage", getPower());
		SmartDashboard.putNumber("joystick value", Robot.oi.getMainRightY());
	}
	
	public void setVoltage(double voltage){
		motor.set(voltage*10);
	}
	
	public double getPower(){
		return motor.getOutputCurrent() * motor.getOutputVoltage();
	}
}

