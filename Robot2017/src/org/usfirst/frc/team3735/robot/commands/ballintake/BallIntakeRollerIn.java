package org.usfirst.frc.team3735.robot.commands.ballintake;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeRollerIn extends InstantCommand {

    public BallIntakeRollerIn() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.ballIntake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.ballIntake.setRollerCurrent(Constants.BallIntake.rollerInSpeed);
    }

}
