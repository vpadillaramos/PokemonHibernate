package com.vpr.pokemon;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import com.vpr.pokemon.beans.JEstado;
import com.vpr.pokemon.beans.JPanelArmas;
import com.vpr.pokemon.beans.JPanelPokemon;
import javax.swing.JMenu;
import com.vpr.pokemon.beans.JComboGenerico;

public class Vista extends JFrame {
	public DefaultListModel<Pokemon> modelPokemon;
	public DefaultListModel<Arma> modelArma;
	public JPanel panel;
	public JMenuBar menuBar;
	public JToolBar toolBar;
	public JEstado estado;
	public JTabbedPane tabbedPane;
	public JPanelArmas panelArmas;
	public JPanelPokemon panelPokemon;
	public JMenu mnNewMenu;
	public JMenu mnOpciones;

	public Vista() {
		setSize(548,498);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		menuBar = new JMenuBar();
		panel.add(menuBar);
		
		mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.SOUTH);
		
		estado = new JEstado();
		getContentPane().add(estado, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panelPokemon = new JPanelPokemon();
		panelPokemon.botonesCrud.setLocation(222, 97);
		tabbedPane.addTab("Pokemon", null, panelPokemon, null);
		
		panelArmas = new JPanelArmas();
		tabbedPane.addTab("Arma", null, panelArmas, null);
		
		modelPokemon = new DefaultListModel();
		modelArma = new DefaultListModel();
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		
		repaint();
	}
	
	//Metodos
	public void hacerVisible(boolean b) {
		setVisible(b);
	}
}
