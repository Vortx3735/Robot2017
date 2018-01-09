package org.usfirst.frc.team3735.robot.ois;

import org.usfirst.frc.team3735.robot.commands.InterruptOperations;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveLeft;
import org.usfirst.frc.team3735.robot.commands.drive.simple.DriveAddSensitiveRight;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeFeeding;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeRollersIn;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeRollersOut;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerOff;
import org.usfirst.frc.team3735.robot.commands.scaler.ScalerUp;
import org.usfirst.frc.team3735.robot.commands.sequences.DriveGoToPeg;
import org.usfirst.frc.team3735.robot.commands.sequences.GearIntakeDropOff;
//import org.usfirst.frc.team3735.robot.util.oi.DriveOI;
import org.usfirst.frc.team3735.robot.util.oi.ChineseBoard;
import org.usfirst.frc.team3735.robot.util.oi.DriveOI;

public class ChineseOI implements DriveOI{
	
	ChineseBoard board;
	
	public ChineseOI() {
		board = new ChineseBoard();
		board.l.whenPressed(new DriveGoToPeg());
		board.mJoyButton.whenPressed(new GearIntakeDropOff());
		board.rJoyButton.whileHeld(new GearIntakeFeeding());
		//main.x.whileHeld(new DriveAddSensitiveLeft());
		//main.y.whileHeld(new DriveAddSensitiveRight());
		//Co-Driver
		board.lWhiteButton.whenPressed(new ScalerUp(1));
		board.rWhiteButton.whenPressed(new ScalerOff());
		board.a.whileHeld(new GearIntakeRollersIn());
		board.b.whileHeld(new GearIntakeRollersOut());

		board.j.whenPressed(new InterruptOperations());
	}

	public double getDriveMove() {
		return board.getMiddleY();
	}

	 
	public double getDriveTurn() {
		return board.getRightX() * .7 + board.getMiddleX() * .3 + board.getRightZ() * .2;
	}
	
	@Override
	public double getFODMag() {
		return board.getLeftMagnitude();
	}

	@Override
	public double getFODAngle() {
		return board.getLeftAngle();
	}
	
	public boolean isOverriddenByDrive(){
		return Math.abs(getDriveTurn()) > .1 || Math.abs(getDriveMove()) > .1;
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub
		
	}

}
