package com.vpr.pokemon.beans;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JList;

public class JPanelBusqueda<T> extends JPanel {
	public JTextField tfBuscar;
	public JScrollPane scrollPane;
	public JList<T> lista;
	public DefaultListModel modelo;
	private List<T> datos;

	public JPanelBusqueda() {
		setLayout(new BorderLayout(0, 0));
		
		tfBuscar = new JTextField();
		add(tfBuscar, BorderLayout.SOUTH);
		tfBuscar.setColumns(10);
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		lista = new JList<>();
		modelo = new DefaultListModel();
		lista.setModel(modelo);
		scrollPane.setViewportView(lista);
	}
	
	//Metodos
	public void refrescar(List<T> datos) {
		if(datos == null)
			return;
		
		modelo.removeAllElements();
		for(T dato: datos)
			modelo.addElement(dato);
	}
	
	public T getSeleccionado() {
		return lista.getSelectedValue();
	}
}
