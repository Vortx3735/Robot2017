package org.usfirst.frc.team3735.robot.commands.autonomous.newstuff;

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
public class AutonBottomGear extends CommandGroup {

    public AutonBottomGear() {
    	addSequential(new BeginAtPathY(Waypoints.bottomGear));
    	//addSequential(new FollowPathTight(Waypoints.bottomGear));
    	addSequential(new FollowPathPredicted(Waypoints.bottomGear));
    	addSequential(new DriveBrake(),.5);
    	addSequential(new GearIntakeDropOff());
    }
}
