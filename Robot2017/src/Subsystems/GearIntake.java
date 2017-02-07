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
	
	double intakeSpeed = 1;
	double outtakeSpeed = -1;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public GearIntake(){
		topRoller = new CANTalon(RobotMap.GearIntake.topRoller);
		bottomRoller = new CANTalon(RobotMap.GearIntake.bottomRoller);
		//topRoller.setInverted();
	}

    public void initDefaultCommand() {
        //setDefaultCommand(new GearIntakeRollersOff());
    }
    
    public void turnRollersIn(){
    	topRoller.set(intakeSpeed);
    	bottomRoller.set(intakeSpeed);
    }
    
    public void turnRollersOut(){
    	topRoller.set(outtakeSpeed);
    	bottomRoller.set(outtakeSpeed);
    }
    
    public void turnRollersOff(){
    	topRoller.set(0);
    	bottomRoller.set(0);
    }
    
}

