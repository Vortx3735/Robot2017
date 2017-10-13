package org.usfirst.frc.team3735.robot.commands.autonomous.newstuff;

import org.usfirst.frc.team3735.robot.commands.drive.positions.BeginAtPathY;
import org.usfirst.frc.team3735.robot.commands.drive.positions.FollowPathTight;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.settings.Waypoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonCrossField extends CommandGroup {

    public AutonCrossField() {
    	addSequential(new BeginAtPathY(Waypoints.crossField));
    	addSequential(new FollowPathTight(Waypoints.crossField));
    	
    }
}
