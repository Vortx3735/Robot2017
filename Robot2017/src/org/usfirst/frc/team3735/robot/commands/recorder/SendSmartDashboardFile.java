package org.usfirst.frc.team3735.robot.commands.recorder;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.recording.DataRecorder;
import org.usfirst.frc.team3735.robot.util.settings.Setting;
import org.usfirst.frc.team3735.robot.util.settings.StringSetting;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SendSmartDashboardFile extends Command {

	StringSetting fileName = new StringSetting("Sending File", "default file");
	
    public SendSmartDashboardFile() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	requires(Robot.gearIntake);
    	requires(Robot.navigation);
    	requires(Robot.scaler);
    	requires(Robot.ultra);
    	requires(Robot.vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DataRecorder.startSending(fileName.getValue());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!DataRecorder.outOfData()){
    		DataRecorder.sendData();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return DataRecorder.outOfData();
    }

    // Called once after isFinished returns true
    protected void end() {
    	DataRecorder.endSending();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
