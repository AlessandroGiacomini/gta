package it.uniroma3.gta.actions;

import java.awt.Color;

import it.uniroma3.gta.Console;
import it.uniroma3.gta.utils.MakeStatisticsUtil;

public class MakeStatisticsAction extends Action {

	/*
	 * This class returns a stats.xlsx file with statistical data about loaded graph
	 * and their nodes 
	 */
	
	public String execute(String string) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (string.equals("run")) {
			MakeStatisticsUtil util = new MakeStatisticsUtil();
			if (util.makeStatistics()) {
				Console.getMSbutton().setBackground(Color.GREEN);
				Console.getMSbutton().setEnabled(false);
				
				return "COMPLETED!";
			}
			else {
				return "An error has occurred!";
			}
		}
		return "Invalid parameter!";
	}

}

