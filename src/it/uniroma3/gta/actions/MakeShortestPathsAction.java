package it.uniroma3.gta.actions;

import it.uniroma3.gta.Console;
import it.uniroma3.gta.utils.MakeShortestPathsUtil;

import java.awt.Color;

public class MakeShortestPathsAction extends Action {

	/*
	 * It's recommended to make shortest paths file before call 
	 * other commands...
	 * (this may take several times)  
	 */
	
	public String execute(String string) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (string.equals("run")) {
			MakeShortestPathsUtil util = new MakeShortestPathsUtil();
			/*Add eventual exceptions in case util.makeShortestPaths() fails (surround it with a try catch)*/
			util.makeShortestPaths();
		}
		Console.getMSbutton().setEnabled(true);
		Console.getSPbutton().setBackground(Color.GREEN);
		Console.getSPbutton().setEnabled(false);
		return "COMPLETED!";
	}

}