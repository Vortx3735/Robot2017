package org.usfirst.frc.team3735.robot.commands.autonomous.newstuff;

import org.usfirst.frc.team3735.robot.commands.drive.TurnTo;
import org.usfirst.frc.team3735.robot.commands.drive.positions.BeginAtPathY;
import org.usfirst.frc.team3735.robot.commands.drive.positions.FollowPathPredicted;
import org.usfirst.frc.team3735.robot.commands.drive.positions.FollowPathTight;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.settings.Waypoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonTopGearHopper extends CommandGroup {

    public AutonTopGearHopper() {
    	addSequential(new AutonTopGear());
    	//addSequential(new FollowPathTight(Waypoints.topGearHopper, true));
    	addSequential(new FollowPathPredicted(Waypoints.topGearHopper, true));
    	addSequential(new TurnTo(-180,false));
    }
}
