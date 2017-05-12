package org.usfirst.frc.team3735.robot.commands.recorder;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.recording.DataRecorder;
import org.usfirst.frc.team3735.robot.util.settings.Setting;
import org.usfirst.frc.team3735.robot.util.settings.StringSetting;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RecordSmartDashboardFile extends Command {

	StringSetting fileName = new StringSetting("Recording File", "default file");
	
    public RecordSmartDashboardFile() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot.drive);
//    	requires(Robot.ballIntake);
//    	requires(Robot.gearIntake);
//    	requires(Robot.navigation);
//    	requires(Robot.scaler);
//    	requires(Robot.shooter);
//    	requires(Robot.ultra);
//    	requires(Robot.vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DataRecorder.startRecording(fileName.getValue());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	DataRecorder.recordData();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DataRecorder.endRecording();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}