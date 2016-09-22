//Interfaccia grafica

package it.uniroma3.gta;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.LineBorder;

import cytoscape.Cytoscape;

public class Console extends javax.swing.JFrame{

	private static final long serialVersionUID = 1L;


	//Create Console JComponents

	private static JButton buttonstart;
	private static JButton  buttonsp;
	private static JButton buttonms;
	private static JComboBox<?> select_algorithm;
	private static JComboBox<?> select_attribute;
	private static JMenu menu_file;
	private static JMenu menu_about;
	private static JMenuBar jMenuBar;
	private static JMenuItem item_about;
	private static JScrollPane ScrollPane_output;
	private static JTextArea output_area;
	private static JTextField input_node;
	private static JMenuItem save;


	


	//Console constructor
	//Set Console JComponents
	public Console() {
		init();
		initComponents();   
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {

		
		//JTextField.setBorder(new LineBorder(Color.BLUE, 2, true))
		
		select_attribute = new javax.swing.JComboBox();
		input_node = new javax.swing.JTextField();
		buttonstart = new javax.swing.JButton();
		buttonsp = new javax.swing.JButton();
		buttonms = new javax.swing.JButton();
		ScrollPane_output = new javax.swing.JScrollPane();
		output_area = new javax.swing.JTextArea();
		select_algorithm = new javax.swing.JComboBox();
		jMenuBar = new javax.swing.JMenuBar();
		menu_file = new javax.swing.JMenu();
		menu_about = new javax.swing.JMenu();
		item_about = new javax.swing.JMenuItem();
		save = new javax.swing.JMenuItem();
		
		//
		input_node.setBorder(new LineBorder(Color.BLACK, 1, false));
		//output_area.setBorder(new LineBorder(Color.BLACK, 1, false));
		//output_area.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1, true), "Output Area"));
		//
		
		//setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);

		buttonsp.setEnabled(false);
		buttonms.setEnabled(false);
		
		//Assign edge attribute names (and default ones) to select attribute option
		String[] edgeAtts = Cytoscape.getEdgeAttributes().getAttributeNames();
		String[] selectFromAttributeNames = new String[ 2 + edgeAtts.length ];
	    selectFromAttributeNames[0] = " Select edge weight attribute";
		selectFromAttributeNames[1] = "Default weight (1)";
		for (int i = 2; i<selectFromAttributeNames.length; i++){
			selectFromAttributeNames[i] = edgeAtts[i-2];
		}
		select_attribute.setModel(new javax.swing.DefaultComboBoxModel(selectFromAttributeNames));
		
		//Text field where user has to write the node name which he wants to apply an algorithm
		input_node.setText(" Node name");
		input_node.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
		input_node.setDragEnabled(true);
		
		// Delete text in the input_node area?
		/*input_node.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				input_node.setText("");
			}
		});*/

		buttonstart.setText("Initialize");

		buttonsp.setText("Calculate shortest paths");

		buttonms.setText("Run and Save Results");

		output_area.setColumns(20);
		output_area.setRows(5);
		output_area.setEditable(false);

		ScrollPane_output.setViewportView(output_area);
		ScrollPane_output.setBackground(Color.white);
		ScrollPane_output.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1, true), "Output Area"));
		
		select_algorithm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " Select algorithm", " Degree", " Stress", " Betweenness", " Closeness" }));

		menu_file.setText("File");
		jMenuBar.add(menu_file);

		save = new JMenuItem("Save as ...");
		//Set up a hot key
		//CTRL+S to save JTextArea content
		save.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		menu_file.add(save);

		menu_about.setText("About");
		item_about.setText("GTA v1.0");
		menu_about.add(item_about);
		jMenuBar.add(menu_about);
		
		
		setJMenuBar(jMenuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(buttonms, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(buttonstart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(buttonsp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(select_attribute, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(input_node, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(select_algorithm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(3, 3, 3))
																.addGroup(layout.createSequentialGroup()
																		.addGap(10, 10, 10)
																		.addComponent(ScrollPane_output)))
																		.addGap(10, 10, 10))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(buttonstart)
								.addComponent(select_attribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(buttonsp)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(buttonms))
												.addGroup(layout.createSequentialGroup()
														.addGap(13, 13, 13)
														.addComponent(select_algorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(12, 12, 12)
														.addComponent(input_node, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGap(13, 13, 13)
														.addComponent(ScrollPane_output, javax.swing.GroupLayout.DEFAULT_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addContainerGap())
				);

		getContentPane().setBackground(Color.white);
		jMenuBar.setBackground(Color.white);


		//CONTROLLER********************************************************************************************************
		save.addActionListener(new Controller());
		item_about.addActionListener(new Controller());
		         
		buttonstart.addActionListener(new Controller());
		buttonsp.addActionListener(new Controller());
		buttonms.addActionListener(new Controller());
		input_node.addActionListener(new Controller());

		pack();
	}// </editor-fold>                        

	
	public void init() {
		try {
			
			/*
			
			Il look & feel di un sistema a finestre e' caratterizzato da due proprieta' fondamentali: la semantica e' 
			la sintassi. La prima riguarda l'aspetto dei componenti grafici, la seconda invece definisce la maniera 
			in cui essi reagiscono alle azioni degli utenti. Quindi ogni sistema grafico ha un suo look & feel specifico.
			
			1) settaggio del look & feel di un'applicazione grafica avviene tramite la classeUIManage
			2) className e' una stringa che contiene il nome della classe con il relativo package, che si occupa di 
			   installare il look & feel specifico.Java Swing implementa nativamente due look & feel
			
			*/
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			//Questo metodo se lanciato sulla finestra principale (di solito JFrame), forza tutti 
			//"updateComponentTreeUI" i componenti della finestra ad aggiornare il proprio look & feel
			SwingUtilities.updateComponentTreeUI(getRootPane());
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}


	}

	public static JTextField getJTextNode() {
		return input_node;
	}

	public static JTextArea getJTextArea() {
		return output_area;
	}

	public static JButton getSPbutton() {
		return buttonsp;
	}

	public static void setSPbutton(JButton jButton2) {
		Console.buttonsp = jButton2;
	}

	public static JButton getMSbutton() {
		return buttonms;
	}

	public static void setMSbutton(JButton jButton3) {
		Console.buttonms = jButton3;
	}

	public static JButton getSTARTbutton() {
		return buttonstart;
	}

	public static void setSTARTbutton(JButton jButton1) {
		Console.buttonstart = jButton1;
	}

	public javax.swing.JMenu getjMenu2() {
		return menu_about;
	}

	public void setjMenu2(javax.swing.JMenu jMenu2) {
		Console.menu_about = jMenu2;
	}

	public static JComboBox<?> getSelectAlghoritm() {
		return select_algorithm;
	}
	
	public static JComboBox<?> getSelectAttribute() {
		return select_attribute;
	}

	public void setjComboBoxSelectAttribute(JComboBox<?> jComboBox1) {
		Console.select_algorithm = jComboBox1;
	}

	public JMenu getjMenu() {
		return menu_file;
	}

	public void setMenu(javax.swing.JMenu jMenu1) {
		Console.menu_file = jMenu1;
	}

	public JMenuBar getjMenuBar() {
		return jMenuBar;
	}

	public void setjMenuBar(JMenuBar jMenuBar1) {
		Console.jMenuBar = jMenuBar1;
	}

	public javax.swing.JScrollPane getjScrollPane() {
		return ScrollPane_output;
	}

	public void setjScrollPane1(JScrollPane jScrollPane1) {
		Console.ScrollPane_output = jScrollPane1;
	}

	public static JMenuItem getAbout() {
		return item_about;
	}

	public void setAbout(JMenuItem jMenuItem1) {
		Console.item_about = jMenuItem1;
	}

	public static JTextArea getOutputArea() {
		return output_area;
	}

	public void setOutputArea(JTextArea jTextArea1) {
		Console.output_area = jTextArea1;
	}
	
	public static JTextField getInputNode() {
		return input_node;
	}
	
	public void setInputText(JTextField jTextNode) {
		Console.input_node = jTextNode;
	}

	public static JMenuItem getSaveJMenuItem() {
		return save;
	}
	
}

