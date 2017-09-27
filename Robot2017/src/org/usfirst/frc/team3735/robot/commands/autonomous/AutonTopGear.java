package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.positions.BeginAtPathY;
import org.usfirst.frc.team3735.robot.commands.drive.positions.FollowPathTight;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.settings.Waypoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonTopGear extends CommandGroup {

    public AutonTopGear() {
    	addSequential(new BeginAtPathY(Waypoints.topGear));
    	addSequential(new FollowPathTight(Waypoints.topGear));
    	addSequential(new DriveBrake(),.5);
    	addSequential(new GearIntakeDropOff());
    }
}
