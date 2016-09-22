//Crea la finestra about

package it.uniroma3.gta.events;

import it.uniroma3.gta.utils.ImageAbout;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

//About Window Event
public class AboutWindowEvent {
	
	private JFrame window;
	
	public AboutWindowEvent() {}
	
	//Create a new about plugin window
	public void createGUI() {
        URL imageURL = this.getClass().getClassLoader().getResource("images/about.png"); 
        ImageAbout panel = new ImageAbout(new ImageIcon(imageURL).getImage());
		//Create and set up the window.
	    window = new JFrame("About");
	    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    window.setSize(new Dimension(500,360));
	    window.getContentPane().add(panel);
	    window.pack();
	    //Display the window.
	    window.setVisible(true);
    }
}