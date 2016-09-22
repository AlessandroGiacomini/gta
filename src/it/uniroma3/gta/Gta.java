//Main di gta

package it.uniroma3.gta;

import cytoscape.Cytoscape;
import cytoscape.plugin.CytoscapePlugin;
import cytoscape.util.CytoscapeAction;
import it.uniroma3.gta.utils.io.GtaInit;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Gta extends CytoscapePlugin {
	
	private static final String pluginName = "GTA - Graph Tool Analyzer";
	private static final String pluginVersion = "v1.0";
	private static final String pluginAuthor = "Alessandro Giacomini, Fabio Ganci, Eder Monaco, Alessandro Rastelli";
	private static final String cyMenu = "Plugins";
	
	public Gta() {
		gtaPluginMenuAction menuAction = new gtaPluginMenuAction();
		//Set Plugins Menu
		menuAction.setPreferredMenu(cyMenu);
		Cytoscape.getDesktop().getCyMenus().addAction(menuAction);
	}
	
	public class gtaPluginMenuAction extends CytoscapeAction {

		private static final long serialVersionUID = 1L;

		
		public gtaPluginMenuAction() {
			//Set Cytoscape Menu Entry Name
			super(pluginName + " " + pluginVersion);
		}

		public void actionPerformed(ActionEvent event) {

			//se piu thread devono interagire su piu componenti Swing anche se non sono condivisi, 
			//bisogna usare il metodo invokeLater per evitare eventuali problemi di gestione	        
	        java.awt.EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            Console Gui=new Console();
		            Gui.setVisible(true);
		        }
		    });
	        
	        try {
	        	GtaInit.setup(); //Creates folders in cytoscape for operations
			} catch (IOException e) {
			}
		}
	}
	
	// main for testing
	public static void main(String[] args) {
		try {
			
			//CONSOLE**************************************************************************************************
			Console Gui=new Console();
            Gui.setVisible(true);
			GtaInit.setup();
		} catch (IOException e) {
					
		}
		
	}

	
	public static String getPluginInfo() {
		return "Cytoscape Plugin: " + pluginName + " " + pluginVersion + "\nAuthor: " + pluginAuthor;
	}
	
}