package com.vpr.pokemon.beans;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JScrollPane;

import com.vpr.pokemon.Arma;
import com.vpr.pokemon.Modelo;

import javax.swing.JList;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class JPanelAnadirArma extends JPanel implements ActionListener{
	public JPanel panel;
	public JComboGenerico<Arma> cbArmas;
	public JButton btAnadir;
	public JScrollPane scrollPane;
	public JList<Arma> lista;
	public DefaultListModel modelLista;
	public JButton btEliminar;

	public JPanelAnadirArma() {
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		cbArmas = new JComboGenerico<>();
		cbArmas.setPreferredSize(new Dimension(100, 20));
		cbArmas.setMinimumSize(new Dimension(100, 20));
		panel.add(cbArmas);
		
		btAnadir = new JButton("+");
		panel.add(btAnadir);
		
		btEliminar = new JButton("-");
		panel.add(btEliminar);
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		lista = new JList<>();
		modelLista = new DefaultListModel();
		lista.setModel(modelLista);
		scrollPane.setViewportView(lista);
		
		
		inicializar();
	}
	
	//Metodos
	private void inicializar() {
		Modelo modelo = new Modelo();
		List<Arma> armas = modelo.getArmas();
		cbArmas.inicializar(armas);
		cbArmas.refrescar();
		
		btAnadir.addActionListener(this);
		btEliminar.addActionListener(this);
	}
	
	public void modoEdicion(boolean b) {
		if(b) {
			lista.setEnabled(b);
			cbArmas.setEnabled(b);
			btAnadir.setEnabled(b);
			btEliminar.setEnabled(b);
		}
		else {
			lista.setEnabled(b);
			cbArmas.setEnabled(b);
			btAnadir.setEnabled(b);
			btEliminar.setEnabled(b);
		}
	}
	
	public List<Arma> getListaArmas() {
		List<Arma> armas = new ArrayList<>();
		for(int i = 0; i < modelLista.size(); i++) {
			armas.add((Arma) modelLista.getElementAt(i));
		}
		
		return armas;
	}
	
	public void anadirArmas(List<Arma> armas) {
		modelLista.removeAllElements();
		for(Arma a: armas)
			modelLista.addElement(a);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		case "+":
			Arma armaSeleccionada = cbArmas.getDatoSeleccionada();
			if(armaSeleccionada == null)
				return;
			if(modelLista.contains(armaSeleccionada))
				return;
			
			modelLista.addElement(armaSeleccionada);
			cbArmas.removeItem(armaSeleccionada);
			break;
		case "-":
			if(lista.getSelectedIndex() == -1)
				return;
			
			cbArmas.addItem((Arma) modelLista.remove(lista.getSelectedIndex()));
			
			break;
		}
	}
}
