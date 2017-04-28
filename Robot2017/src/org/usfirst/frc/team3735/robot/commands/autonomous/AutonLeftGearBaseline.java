package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDNavx;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePID;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistancePIDBroken;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpVision;
import org.usfirst.frc.team3735.robot.commands.drive.movedistance.DriveMoveDistanceExpNavxVision;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePID;
import org.usfirst.frc.team3735.robot.commands.drive.turntoangle.DriveTurnToAnglePIDCtrl;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.subsystems.Vision.Pipes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLeftGearBaseline extends CommandGroup {

    public AutonLeftGearBaseline(){
    	addSequential(new AutonLeftGear());
    	addSequential(new Wait(2));
    	addSequential(new DriveMoveDistanceExpNavx(-10,1),1);
    	addSequential(new DriveBrake(),.4);
    	addSequential(new DriveTurnToAnglePIDCtrl(0),2);
    	addSequential(new DriveMoveDistanceExpNavx(150,1),4);
     }
}
