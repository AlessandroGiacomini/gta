//Save nel menu

package it.uniroma3.gta.events;

import it.uniroma3.gta.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SaveEvent extends JPanel {

	private static final long serialVersionUID = 1L;

	public SaveEvent() {}
	
	public void createGUI() {
		JFileChooser chooser = new JFileChooser();
    	int code  = chooser.showSaveDialog(Console.getOutputArea());
    	if (code == JFileChooser.APPROVE_OPTION) {
            try {
            	File selectedFile = chooser.getSelectedFile();
            	if (selectedFile.exists()){
            		int result = JOptionPane.showConfirmDialog(this, "The file already exists, overwrite it?", "Existing file", JOptionPane.YES_NO_OPTION);
            	    switch(result){
            	    	case JOptionPane.YES_OPTION:
            	    		FileOutputStream fos = new FileOutputStream(selectedFile);
                            OutputStreamWriter out = new OutputStreamWriter(fos, Charset.forName("UTF-8")); 
                            out.write(Console.getOutputArea().getText());
            				out.close();
            	    	case JOptionPane.NO_OPTION:
            	    		chooser.cancelSelection();
            	            return;
            	    }
            	}
            	else {
            		FileOutputStream fos = new FileOutputStream(selectedFile);
            		OutputStreamWriter out = new OutputStreamWriter(fos, Charset.forName("UTF-8")); 
            		out.write(Console.getOutputArea().getText());
            		out.close();
            		fos.close();
            	}
            } catch (IOException e) {}
    	}
	}
	
}
