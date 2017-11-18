package org.usfirst.frc.team3735.robot.commands.drive.positions;

import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveExp;
import org.usfirst.frc.team3735.robot.commands.sequences.DriveAcquireGear;
import org.usfirst.frc.team3735.robot.commands.sequences.DrivePlaceGear;
import org.usfirst.frc.team3735.robot.settings.Waypoints;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;
import org.usfirst.frc.team3735.robot.util.profiling.Location;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearCycle extends CommandGroup {

    public GearCycle() {
    	//start a distance away from the airship
        addSequential(new GoToCenterField());
        addSequential(new FollowPath(Waypoints.toKeyLeft));
        addSequential(new DriveAcquireGear());
        addSequential(new DriveExp(-1,0).addT(new HasMoved(-60)),1);
        addSequential(new GoToCenterField());
        addSequential(new FollowPath(Waypoints.toTopGear));
        addSequential(new DrivePlaceGear());
        //end where started
    }
}
