package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAngle;
import org.usfirst.frc.team3735.robot.util.Point;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousTopCrossGearLoader extends CommandGroup {

    public AutonomousTopCrossGearLoader() {
       Point point0 = Constants.PointsLeft.startTop;
       Point point1 = Constants.PointsLeft.lineUpWithTopLift;
       Point point2 = Constants.PointsLeft.topLift;
       Point point3 = Constants.PointsLeft.lineUpWithTopLift;
       Point point4 = Constants.PointsLeft.opSideRetrivalAndBaseLine;
       
       addSequential(new DriveTurnToAngle(Point.findAngle(point0, point1)));
       addSequential(new DriveMoveDistanceInches(Point.findDistance(point0, point1)));	
       addSequential(new DriveTurnToAngle(Point.findAngle(point1, point2)));
   	   addSequential(new DriveMoveDistanceInches(Point.findDistance(point1, point2)));
   	   //addSequential(new GearDropOff());
   	   addSequential(new DriveTurnToAngle(Point.findAngle(point2, point3)));
   	   addSequential(new DriveMoveDistanceInches(Point.findDistance(point2, point3)));
   	   addSequential(new DriveTurnToAngle(Point.findAngle(point3, point4)));
   	   addSequential(new DriveMoveDistanceInches(Point.findDistance(point3, point4)));
    }
}
