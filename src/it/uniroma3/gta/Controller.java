//Controller prende i comandi e le associa alle azioni

package it.uniroma3.gta;



import it.uniroma3.gta.actions.Action;
import it.uniroma3.gta.events.*;
import it.uniroma3.gta.utils.CommandHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Controller extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Map<String, String> commandsList;
    private final static String newline = "\n";
    private String text=null;
    private static String columnWeight=null;
	
    //Set up an actionPerformed to control single events
	public void actionPerformed(ActionEvent event) {
	    	
		//Set up commandsList Map -> key(commandName) - value(commandNameClass)
		init();
			
	    //BUTTON AND TEXTFIELD input controller
	    	
	/*1*/	if (event.getSource() == Console.getSTARTbutton()) {
			this.setColumnWeight(Console.getSelectAttribute().getSelectedItem().toString());
			text=" Initialize|yes";
	    	}
	/*2*/ 	else if (event.getSource() == Console.getSPbutton()) {
		    	text = new String ("Shortest Paths|run");
		    }
	/*3*/ 	else if (event.getSource() == Console.getMSbutton()){				  
		    	text = new String ("Save results|run");
		   	}
		   	
	/*4*/ 	else if (event.getSource() == Console.getJTextNode()){
			   		
	   			String text1= Console.getSelectAlghoritm().getSelectedItem().toString();
			   		
			   	//TODO: gestici quando ancora non hanno selezionato l'algoritmo quindi stampa un messaggio di errore 
		   		String text2 = Console.getJTextNode().getText();
		   		text=text1+"|"+text2;
	   			
	/*5*/  	}else if (event.getSource() == Console.getSaveJMenuItem()) {
		    	SaveEvent save = new SaveEvent();
		    	save.createGUI();
		    }
	/*6*/ 	//Open About window event
		    else if (event.getSource() == Console.getAbout()) {
		    	AboutWindowEvent about = new AboutWindowEvent();
		    	about.createGUI();
		    }
	        		
	    //if command structure contains a pipe
		//COMMAND HELPER********************************************************************************************************
	    CommandHelper helper = new CommandHelper();
	    
	    //Splitto il comando in base alla pipe
	    HashMap<Integer,String> paramMap = helper.splitText(text);
	    
	    //Controllo se nella lista dei comandi e' contenuto il comando in posizione 0 della lista derivata dalla stringa paramMap
	    if (this.commandsList.containsKey(paramMap.get(0))){
	    	Action action = null;
	    	try {
	    		//Il metodo init invoca l'azione dal comando trovato
	    		String className = this.commandsList.get(paramMap.get(0));
	    		action = (Action)Class.forName(className).newInstance();
	   			System.out.println(action);

	    		//Call execute action method with parameter to return command result
	    		String param = paramMap.get(1);
	    		for (int i=2; i<paramMap.size(); i++)
	    			param = param + "|" + paramMap.get(i);
	    		
	    		//Eseguo il metodo execute dell'azione invocata
	    		String result = (String)action.execute(param);
	    		
    			//Scrivo nella console la stringa result risultato dell'algoritmo richiamato dall'azione
	    		if (result == null)
	    			Console.getJTextArea().append(text + " = " + result + newline + "are you sure that you have written well?" + newline + newline);	
	    		else 
	    			Console.getJTextArea().append( paramMap.get(0)+" = " + result + newline + newline);
	    	} catch (InstantiationException e) {
	    	} catch (IllegalAccessException e) {
	    	} catch (ClassNotFoundException e) {
	    	}
	    }
	}
	//ACTIONS*************************************************************************************************************
	//init method to initialize commandsList Map
	private void init() {
		this.commandsList = new HashMap<String, String>();
		this.commandsList.put(" Degree", "it.uniroma3.gta.actions.DegreeAction");
		this.commandsList.put(" Initialize", "it.uniroma3.gta.actions.InitAction");
		this.commandsList.put(" Closeness", "it.uniroma3.gta.actions.ClosenessAction");
		this.commandsList.put(" Betweenness", "it.uniroma3.gta.actions.BetweennessAction");
		this.commandsList.put(" Stress", "it.uniroma3.gta.actions.StressAction");
		this.commandsList.put(" Save results", "it.uniroma3.gta.actions.MakeStatisticsAction");
		this.commandsList.put(" Shortest Paths", "it.uniroma3.gta.actions.MakeShortestPathsAction");
	}

	public static String getColumnWeight() {
		return columnWeight;
	}

	@SuppressWarnings("static-access")
	public void setColumnWeight(String columnWeight) {
		this.columnWeight = columnWeight;
	}

}
