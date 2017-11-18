package org.usfirst.frc.team3735.robot.commands.drive.movedistance;

import org.usfirst.frc.team3735.robot.assists.NavxAssist;
import org.usfirst.frc.team3735.robot.triggers.HasMoved;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	this is a shortcut class for simple moving a distance
 */
public class Move extends DriveExp {

    public Move(double dist) {
    	this(dist, .7);
    }
    
    public Move(double dist, double spd) {
    	super(Math.signum(dist) * spd,0);
    	addA(new NavxAssist());
    	addT(new HasMoved(dist));
    }
}
