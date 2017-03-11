package org.usfirst.frc.team3735.robot.commands.autonomous;

//import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Constants.PointsLeft;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAngle;
import org.usfirst.frc.team3735.robot.util.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousMiddleGearBottomShoot extends CommandGroup {
	
    public AutonomousMiddleGearBottomShoot() {
    	Point point0 = PointsLeft.startMiddle;
    	Point point1 = PointsLeft.middleLift;
    	Point point2 = PointsLeft.midBackUp;
    	Point point3 = PointsLeft.lineUpWithBoiler;
    	Point point4 = PointsLeft.boiler;
    	Point point5 = PointsLeft.boilerExact;
    	
    	addSequential(new DriveTurnToAngle(Point.findAngle(point0, point1)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point0, point1)));
    	//addSequential(new GearDropOff());
    	addSequential(new DriveTurnToAngle(Point.findAngle(point1, point2)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point1, point2)));
    	addSequential(new DriveTurnToAngle(Point.findAngle(point2, point3)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point2, point3)));
    	addSequential(new DriveTurnToAngle(Point.findAngle(point3, point4)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point3, point4)));
    	addSequential(new DriveTurnToAngle(Point.findAngle(point4, point5)));
    	//addSequential(new ShooterShoot());
    }
}
