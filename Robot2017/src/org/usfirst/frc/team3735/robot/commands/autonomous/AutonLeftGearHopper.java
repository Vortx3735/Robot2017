package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.Move;

import org.usfirst.frc.team3735.robot.triggers.Bumped;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGearHopper extends CommandGroup {

    public AutonLeftGearHopper() {
     	addSequential(new AutonLeftGear());
    	addSequential(new TurnTo(90));
    	addSequential(new Move(-90).addT(new Bumped(.8)));
    	addSequential(new Wait(2));
    	addSequential(new Move(10));
    	addSequential(new TurnTo(-170),2);

    }
}
