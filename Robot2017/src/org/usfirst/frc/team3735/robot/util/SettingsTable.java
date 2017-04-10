package org.usfirst.frc.team3735.robot.util;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class SettingsTable implements Sendable{

	ArrayList<String> keys = new ArrayList<String>();
	ArrayList<Double> values = new ArrayList<Double>();
	
	
	public SettingsTable(){
		
	}
	
	public void addSetting(String name, double obj){
		if(keys.indexOf(name) == -1){
			System.out.println("SettingsTable Error: attempt to add an already existing key");
			return;
		}
		keys.add(name);
		values.add(obj);
	}
	
	public void setValue(String key, double value){
		int index = keys.indexOf(key);
		if(index != -1){
			values.set(index, value);
			m_table.putNumber(key, value);
		}else{
			System.out.println("SettingsTable Error: attempt to set value for invalid key");
		}
	}
	
	public double getValue(String key, double value){
		int index = keys.indexOf(key);
		if(index != -1){
			return values.get(index);
		}else{
			System.out.println("SettingsTable Error: attempt to get value from invalid key");
			return 0;
		}
	}
	
	private ITable m_table;
	private final ITableListener m_listener = (table, key, value, isNew) -> {
		for(int i = 0; i < keys.size(); i++){
			values.set(i, table.getNumber(keys.get(i), values.get(i)));
		}
	};
	
	
	@Override
	public void initTable(ITable table) {
		if (this.m_table != null) {
			m_table.removeTableListener(m_listener);
	    }
	    m_table = table;
	    if (table != null) {
	      for(int i = 0; i < keys.size(); i++){
	    	  table.putNumber(keys.get(i), values.get(i));
	      }
	      table.addTableListener(m_listener, false);
	    }		
	}

	@Override
	public ITable getTable() {
	    return m_table;
	}

	@Override
	public String getSmartDashboardType() {
		return "SettingsTable";
	}
	
}
