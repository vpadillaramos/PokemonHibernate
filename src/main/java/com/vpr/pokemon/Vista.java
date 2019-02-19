package com.vpr.pokemon;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.vpr.pokemon.beans.JEstado;
import com.vpr.pokemon.beans.JPanelArmas;
import com.vpr.pokemon.beans.JPanelPokemon;
import javax.swing.JMenu;

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
		estado.setMensajeConfirmacion("Pokemon Hibernate 1.0");
		getContentPane().add(estado, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panelPokemon = new JPanelPokemon();
		panelPokemon.botonesCrud.setLocation(222, 97);
		tabbedPane.addTab("Pokemon", null, panelPokemon, null);
		
		panelArmas = new JPanelArmas();
		panelArmas.btBorrarTodo.setLocation(328, 346);
		tabbedPane.addTab("Arma", null, panelArmas, null);
		
		modelPokemon = new DefaultListModel();
		modelArma = new DefaultListModel();
		setResizable(false);
		setLocationRelativeTo(null);
		
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex() == 0) {
					panelPokemon.panelAnadirArma.refrescar();
					panelPokemon.panelBusqueda.refrescarLista();
				}
			}
			
		});
		
		repaint();
	}
	
	//Metodos
	public void hacerVisible(boolean b) {
		setVisible(b);
	}
}
