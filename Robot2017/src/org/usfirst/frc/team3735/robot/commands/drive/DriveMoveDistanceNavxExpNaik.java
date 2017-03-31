package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.VortxMath;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class DriveMoveDistanceNavxExpNaik extends Command {

	/**************** MAKE ADJUSTMENTS TO THESE CONSTANTS AND ENABLE DISABLES */
	public static final boolean ISCONSOLEDEBUG_ENABLED = false;
	public static final boolean ISSMARTDASHDEBUG_ENABLED = true;
	public static final boolean ENABLE_EXPRAMP = true;
	public static final boolean ENABLE_YAWMODE = true;

	final double STATEPERIODINTERVAL = 0.05;
	final double REACHEDTOLERANCE = 1.0; // Inches
	final double BEGINZONEINCHES = 2.0; // Inches
	final double ENDZONEINCHES = 5.0; // Inches
	final short  POSTREACHED_TRYCNT =6;
	final double NORMALMOVESPEED = 0.33;//

	final short EXPRAMP_NFACTOR = 4; // Small is Fast Large is Slow 2 fast, 8,
										// 32 slow are good numbers

	final static double KP = 0.005;
	final static double KI = 0.000;// 0.00010;
	final static double KD = 0.001;
	final static double KF = 0.000;
	final static int       KZ = 0;
	final static double KR = 0.0;
	
	

	final static double KPYAW = 0.015;

	/****************************************/

	/****************************************/
	/* STATE MACHINE */
	/****************************************/

	public enum STATE {
		IDLE, /* This Idles Nothing Going On */
		INITTRAVEL, /* This is Start of SM */
		ZERODISTANCE, /* Reset Encoders to to Zero Position */
		SETSTARTPOS, /* Save the Start Position as we Need to Calculate End */
		STARTPID, /* Init PID For Travel */
		TRAVELUNTILREACHED, /*
							 * This is a State That Runs the POSITION Mode Until
							 * Reached
							 */
		REACHED, /* We Reached End so Just Do some Fine tuning of Position */
		CLEANUPEND /* House Keeping Clean up in prep to Idle State. */
	};

	private double displacementInches;
	private double startPositionLeftInches;
	private double startPositionRightInches;
	private double endPositionLeftInches;
	private double endPositionRightInches;
	private double filtspeed;
	private double movespeed;

	private double yawtwistcorrection;

	private STATE currentstate;
	private STATE nextstate;
	private double stateDoWorkTime = 0;
	private boolean typeistwist = false;
	private boolean done = false;
	private short postreachcntr = 0;
	private double targetYaw;

	/*
	 * @param distance	the distance to move in inches
	 */
	public DriveMoveDistanceNavxExpNaik(double distance) {
		requires(Robot.drive);
		this.currentstate = STATE.IDLE;
		this.nextstate = STATE.IDLE;
		this.displacementInches = distance;
		this.typeistwist = false;
		this.filtspeed = 0;
		this.movespeed = 0;
		this.yawtwistcorrection = 0.0;
		this.setTimeout(10.0);

	}

	/*
	 * @param distance	the distance to move in inches
	 * @param typeistwist	if true, it will move the left forward 
	 * 						and the right back, turning the robot
	 */
	public DriveMoveDistanceNavxExpNaik(double distance, boolean typeistwist) {
		requires(Robot.drive);
		this.currentstate = STATE.IDLE;
		this.nextstate = STATE.IDLE;
		this.displacementInches = distance;
		this.typeistwist = typeistwist;
		this.filtspeed = 0;
		this.yawtwistcorrection = 0.0;
		this.setTimeout(15.0);

	}

	protected void initialize() {
		done = false;
		currentstate = STATE.INITTRAVEL;
		//Robot.navigation.resetHeading();
		targetYaw = Robot.drive.getYaw();
		Robot.drive.setSetpoint(targetYaw);
		yawtwistcorrection = 0.0;
		stateDoWorkTime = Timer.getFPGATimestamp() + STATEPERIODINTERVAL;
	}

	protected void stateMachine() {

		switch (currentstate) {

		case IDLE:
			this.nextstate = STATE.IDLE; /* Just Index to Next State */
			break;

		case INITTRAVEL:
			this.nextstate = STATE.ZERODISTANCE; /* Just Index to Next State */
			Robot.drive.resetEncodersPositions();
			//Robot.navigation.resetHeading();
			Robot.drive.zeroYaw();
			postreachcntr = 0;
			break;

		case ZERODISTANCE:
			Robot.drive.resetEncodersPositions();
			//Robot.navigation.resetHeading();
			Robot.drive.zeroYaw();
			this.nextstate = STATE.SETSTARTPOS; /* Just Index to Next State */
			break;

		case SETSTARTPOS:
			/*******************************
			 * Set Start & End Position
			 *******************************/
			startPositionLeftInches = Robot.drive.getLeftPositionInches();
			startPositionRightInches = Robot.drive.getRightPositionInches();

			/********************/
			if (typeistwist == false) {
				endPositionLeftInches = startPositionLeftInches + displacementInches;
				endPositionRightInches = startPositionRightInches + displacementInches;
			} else {
				endPositionLeftInches = startPositionLeftInches + displacementInches;
				endPositionRightInches = startPositionRightInches - displacementInches;
			}

			
			this.nextstate = STATE.STARTPID; /* Just Index to Next State */
			break;

		case STARTPID:
			Robot.drive.setPIDSettings(KP, KI, KD,KF,KZ,KR);
			Robot.drive.setEnableBrake(true);
			Robot.drive.setUpDriveForSpeedControl();
			
			filtspeed = 0;
			this.nextstate = STATE.TRAVELUNTILREACHED;
			break;

		case TRAVELUNTILREACHED:
			/**************************************
			 * Update the Drive Outputs with New Positions
			 */

			/**** Drive Output is done in main execute state outputs ***/

			/*******************************
			 * If Ramping we do Ramping other wise just straight speed to end
			 * Position
			 *******************************/

			if (DriveMoveDistanceNavxExpNaik.ENABLE_EXPRAMP) {

				if (isPositionEndZone() == true)
					filtspeed = NORMALMOVESPEED/-8;
				else
					filtspeed = ((EXPRAMP_NFACTOR - 1) * filtspeed + NORMALMOVESPEED) / EXPRAMP_NFACTOR;
		
			
				movespeed = filtspeed;
			} else {
				filtspeed = NORMALMOVESPEED;
				movespeed = NORMALMOVESPEED;
			}

			/**************************************
			 * When we Reach End Position we Are Done we are reached!
			 */
			if (isTravelReachedEndPosition()) {
				this.nextstate = STATE.REACHED;
			}
			break;

		case REACHED:
			/************
			 *  We have a After Reach Try Time.
			 */
			movespeed = 0;
			if (++postreachcntr > POSTREACHED_TRYCNT) {
				this.nextstate = STATE.CLEANUPEND; 
			}
			break;

		case CLEANUPEND:
			Robot.drive.setUpDriveForSpeedControl();
			Robot.drive.setEnableBrake(true);
			done = true; /** WE ARE DONE SO LET DONE TERMINATE TO CND **/
			this.nextstate = STATE.IDLE; /* Just Index to Idle */
			break;

		default:
			Robot.drive.setUpDriveForSpeedControl();
			done = true; /** WE ARE DONE SO LET DONE TERMINATE TO CND **/
			this.nextstate = STATE.IDLE; /* Just Index to Idle */
			break;
		}

		this.currentstate = this.nextstate;

	}

	protected boolean isTravelReachedEndPosition() {
		boolean rval = false;
		double ErrLeft = endPositionLeftInches-Robot.drive.getLeftPositionInches();
		double ErrRight = endPositionRightInches-  Robot.drive.getRightPositionInches();

		if (DriveMoveDistanceNavxExpNaik.ISCONSOLEDEBUG_ENABLED) {
			System.out.format("[%08.3f]: <Lerr=%08.3f Rerr=%08.3f", stateDoWorkTime, ErrLeft, ErrRight);
			System.out.println("<");
		}

		if (ErrLeft < REACHEDTOLERANCE && ErrRight < REACHEDTOLERANCE) {
			rval = true;
		}
		return rval;
	}

	protected boolean isPositionBeginZone() {
		boolean rval = false;
		double ErrLeft = Robot.drive.getLeftPositionInches()- startPositionLeftInches;
		double ErrRight =  Robot.drive.getRightPositionInches()-startPositionRightInches;

		if (ErrLeft< BEGINZONEINCHES
				&& ErrRight < BEGINZONEINCHES) {
			rval = true;
		}
		return rval;
	}

	protected boolean isPositionEndZone() {
		boolean rval = false;
		double ErrLeft = endPositionLeftInches - Robot.drive.getLeftPositionInches()- startPositionLeftInches;
		double ErrRight = endPositionRightInches - Robot.drive.getRightPositionInches()-startPositionRightInches;
		
		if (ErrLeft < ENDZONEINCHES
				&& ErrRight< ENDZONEINCHES) {
			rval = true;
		}
		return rval;
	}

	// Called repeatedly when this Command is scheduled to run
	@SuppressWarnings("unused")
	protected void execute() {
		/*****************************************
		 * Call the State Machine at a Periodic Rate
		 *****************************************/
		if (Timer.getFPGATimestamp() > stateDoWorkTime) {
			if (DriveMoveDistanceNavxExpNaik.ISCONSOLEDEBUG_ENABLED) {
				System.out.format("[%08.3f]:", stateDoWorkTime);
				System.out.println(currentstate);
			}
			stateDoWorkTime = Timer.getFPGATimestamp() + STATEPERIODINTERVAL;
			stateMachine();
		}

		/*****************************************
		 * Calculate Yaw Correction States
		 *****************************************/
		if (ENABLE_YAWMODE && !typeistwist){
			yawtwistcorrection = calculateYawCorrection();
		}else{
			yawtwistcorrection = 0;
		}
		
		/*****************************************
		 * Drive Only During Travel States
		 *****************************************/
		if (this.currentstate == STATE.REACHED) {
			Robot.drive.arcadeDrive((endPositionLeftInches- Robot.drive.getLeftPositionInches())/100, yawtwistcorrection, false);
		}else if(this.currentstate == STATE.TRAVELUNTILREACHED){
				Robot.drive.arcadeDrive(movespeed, yawtwistcorrection, false);
		}else{
			Robot.drive.arcadeDrive(0, 0, false);
			Robot.drive.setEnableBrake(true);
		}
		
		
		

		//System.out.println(endPositionLeftInches- Robot.drive.getInchesPositionLeftInches());
		
		/*****************************************
		 * Time Out Abort
		 *****************************************/
		if (this.isTimedOut()){
			done = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		done = true;
		this.currentstate = STATE.IDLE;
		Robot.drive.setUpDriveForSpeedControl();

	}

	protected double calculateYawCorrection() {
		/*****************************************
		 * Calculate Yaw Correction States
		 *****************************************/
		/*
		 * If Angle Error is Positive 0 to 180 we need to Turn Opposite
		 * 
		 */
		double reval = Robot.drive.getPIDController().getError()/ -180.0;
		reval = VortxMath.limit(reval, -.5, .5);
		return reval * 4.0;

	}
	protected void showDashTestInfo() {
		// SmartDashboard.putNumber("Cmd get rotations left",
		// Robot.drive.getRotationsLeft());
		// SmartDashboard.putNumber("Cmd get rotations right",
		// Robot.drive.getRotationsRight());
		//
		// SmartDashboard.putNumber("CmdGetRInches",
		// Robot.drive.getInchesPositionRightInches());
		// SmartDashboard.putNumber("CmdGetLInches",
		// Robot.drive.getInchesPositionLeftInches());
		//
		// SmartDashboard.putNumber("CmdSPLEnd", endPositionLeftInches);
		// SmartDashboard.putNumber("CmdSPREnd", endPositionRightInches);
		// System.out.println("Working");
	}

}
