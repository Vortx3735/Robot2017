package Subsystems;

import org.usfirst.frc.team3735.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class GearIntake extends Subsystem {
	CANTalon topRoller;
	CANTalon bottomRoller;
	
	private final double intakeSpeed = 1;
	private final double outtakeSpeed = -1;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public GearIntake(){
		topRoller = new CANTalon(RobotMap.GearIntake.topRoller);
		bottomRoller = new CANTalon(RobotMap.GearIntake.bottomRoller);
		topRoller.setInverted();
	}

    public void initDefaultCommand() {
        //setDefaultCommand(new GearIntakeRollersOff());
    }
    
    public void turnRollersIn(){
    	setRollerSpeed(intakeSpeed);
    }
    
    public void turnRollersOut(){
    	setRollerSpeed(outtakeSpeed);
    }
    
    public void turnRollersOff(){
    	setRollerSpeed(0);
    }
    
    public void setRollerSpeed(double speed){
    	topRoller.set(speed);
    	bottomRoller.set(speed);
    }
    
}

