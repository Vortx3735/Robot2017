package Commands;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnToAngle extends Command implements PIDOutput, PIDSource {

	PIDController controller;
	PIDSourceType type = PIDSourceType.kDisplacement;
	
	private double P = 1;
	private double I = 0;
	private double D = 0;
	private double F = 0;

    public DriveTurnToAngle(double angle){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	controller = new PIDController(P, I, D, F, this, this);
    	controller.setContinuous();
    	controller.setInputRange(0, 360);
    	controller.setOutputRange(-1, 1);
    	controller.setSetpoint(angle);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	controller.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return controller.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	controller.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		type = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGet() {
		return Robot.drive.getYaw();
	}

	@Override
	public void pidWrite(double output) {
		Robot.drive.turn(output);
	}
}
