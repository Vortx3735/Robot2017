package Subsystems;
import com.ctre.CANTalon;
import org.usfirst.frc.team3735.robot.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {

	CANTalon drum = new CANTalon(RobotMap.Shooter.drum);
	Encoder drumEncoder;
	double targetSpeed;
	
	public PIDController controller = new PIDController(1.0, 0.0, 0.0, drumEncoder, drum);
	
	public Shooter(){
		
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
}

