package org.usfirst.frc.team3735.robot.util;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/*
 * Implements a SpeedController with an underlying PID controller
 */
public class MultiSpeedController implements SpeedController, LiveWindowSendable {

	private boolean _isInverted = false;
	private SpeedController[] _controllers;
	private double _setpoint;
	private ITable m_table;
	private ITableListener m_table_listener;

	public MultiSpeedController(SpeedController[] controllers, String subsystem, String controllerName) {
		_controllers = controllers;
		LiveWindow.addActuator(subsystem, controllerName, this);
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return _setpoint;
	}

	public void set(double setpoint, byte syncGroup) {
		set(setpoint);
	}

	@Override
	public void set(double setpoint) {
		_setpoint = (setpoint < -1.0 ? -1.0 : (setpoint > 1.0 ? 1.0 : setpoint));

		for (SpeedController sc : _controllers) {
			sc.set(_setpoint);
		}
	}

	@Override
	public void disable() {
		for (SpeedController sc : _controllers) {
			sc.disable();
		}
	}

	@Override
	public void setInverted(boolean isInverted) {
		_isInverted = isInverted;

		for (SpeedController sc : _controllers) {
			sc.setInverted(_isInverted);
			
		}
	}

	@Override
	public boolean getInverted() {
		return _isInverted;
	}

	public void stopMotor() {
		for (SpeedController sc : _controllers) {
			sc.set(0);
		}
		_setpoint = 0;
	}

	/*
	 * Live Window code, only does anything if live window is activated.
	 */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSmartDashboardType() {
		return "Speed Controller";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("Value", get());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITable getTable() {
		return m_table;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startLiveWindowMode() {
		stopMotor(); // Stop for safety
		m_table_listener = new ITableListener() {
			@Override
			public void valueChanged(ITable itable, String key, Object value, boolean bln) {
				set(((Double) value).doubleValue());
			}
		};
		m_table.addTableListener("Value", m_table_listener, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopLiveWindowMode() {
		stopMotor(); // Stop for safety
		// TODO: Broken, should only remove the listener from "Value" only.
		m_table.removeTableListener(m_table_listener);
	}
}
