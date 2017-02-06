package Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */



public class Drive extends Subsystem {
	
	CANTalon l1 
	CANTalon l2
	CANTalon l3
	
	CANTalon r1;
	CANTalon r2;
	CANTalon r3;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

