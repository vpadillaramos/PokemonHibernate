package com.vpr.pokemon.beans;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vpr.pokemon.Arma;
import com.vpr.pokemon.Modelo;
import com.vpr.pokemon.util.Util;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class JPanelArmas extends JPanel implements ActionListener, ListSelectionListener {
	public JBotonesCrud botonesCrud;
	public JLabel lblNombre;
	public JLabel lblAtaque;
	public JLabel lblDuracin;
	public JTextField tfNombre;
	public JTextField tfAtaque;
	public JTextField tfDuracion;
	public JScrollPane scrollPane;
	public JList<Arma> listArmas;
	public DefaultListModel<Arma> modelArma;
	
	private enum Accion{
		NUEVO, MODIFICAR
	}
	private Accion accion;
	private Arma armaActual;

	public JPanelArmas() {
		setLayout(null);
		
		botonesCrud = new JBotonesCrud();
		botonesCrud.setBounds(10, 168, 210, 121);
		add(botonesCrud);
		
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(21, 37, 58, 14);
		add(lblNombre);
		
		lblAtaque = new JLabel("Ataque");
		lblAtaque.setBounds(21, 77, 58, 14);
		add(lblAtaque);
		
		lblDuracin = new JLabel("Duraci\u00F3n");
		lblDuracin.setBounds(21, 113, 58, 14);
		add(lblDuracin);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(89, 34, 96, 20);
		add(tfNombre);
		tfNombre.setColumns(10);
		
		tfAtaque = new JTextField();
		tfAtaque.setBounds(89, 74, 96, 20);
		add(tfAtaque);
		tfAtaque.setColumns(10);
		
		tfDuracion = new JTextField();
		tfDuracion.setBounds(89, 110, 96, 20);
		add(tfDuracion);
		tfDuracion.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(269, 31, 161, 192);
		add(scrollPane);
		
		listArmas = new JList<>();
		modelArma = new DefaultListModel<>();
		listArmas.setModel(modelArma);
		scrollPane.setViewportView(listArmas);
		
		iniciar();
		
	}
	
	public void iniciar() {
		refrescarLista();
		modoEdicion(false);
		botonesCrud.addListeners(this);
		listArmas.addListSelectionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(JBotonesCrud.Accion.valueOf(e.getActionCommand())) {
		case NUEVO:
			nuevaArma();
			break;
		case MODIFICAR:
			modificarArma();
			break;
		case GUARDAR:
			guardarArma();
			break;
		case CANCELAR:
			cancelar();
			break;
		case BORRAR:
			borrar();
			break;
		default:
			
			break;
		}
	}
	
	private void limpiar() {
		tfNombre.setText("");
		tfAtaque.setText("");
		tfDuracion.setText("");
	}
	
	public void rellenarCampos() {
		tfNombre.setText(armaActual.getNombre());
		tfAtaque.setText(String.valueOf(armaActual.getAtaque()));
		tfDuracion.setText(String.valueOf(armaActual.getDuracion()));
		
		botonesCrud.btModificar.setEnabled(true);
		botonesCrud.btBorrar.setEnabled(true);
	}
	
	public void modoEdicion(boolean b) {
		if(b) {
			botonesCrud.modoEdicion(b);
			
			tfNombre.setEditable(b);
			tfAtaque.setEditable(b);
			tfDuracion.setEditable(b);
		}
		else {
			botonesCrud.modoEdicion(b);
			
			tfNombre.setEditable(b);
			tfAtaque.setEditable(b);
			tfDuracion.setEditable(b);
		}
	}
	
	private void borrar() {
		Modelo modelo = new Modelo();
		modelo.eliminarArma(listArmas.getSelectedValue());
		Util.mensajeInformacion("Hecho", listArmas.getSelectedValue().getNombre() + " eliminado correctamente");
		
		refrescarLista();
		limpiar();
		modoEdicion(false);
	}
	
	private void nuevaArma() {
		limpiar();
		modoEdicion(true);
		tfNombre.requestFocus();
		accion = Accion.NUEVO;
	}
	
	private void cancelar(){
		limpiar();
		modoEdicion(false);
	}
	
	public void guardarArma() {
		if(tfNombre.getText().equals("")) {
			Util.mensajeError("Error", "El nombre es obligatorio");
		}
		
		Arma arma = null;
		switch(accion) {
		case NUEVO:
			arma = new Arma();
			break;
		case MODIFICAR:
			arma = armaActual;
			break;
			
		default:
			
			break;
		}
		
		//recogida de datos
		arma.setNombre(tfNombre.getText());
		arma.setAtaque(Integer.parseInt(tfAtaque.getText()));
		arma.setDuracion(Integer.parseInt(tfDuracion.getText()));
		
		Modelo modelo = new Modelo();
		
		
		if(accion == Accion.MODIFICAR) {
			modelo.modificarArma(arma);
			Util.mensajeInformacion("Hecho", "Arma modificada");
		}
		else {
			modelo.guardar(arma);
			Util.mensajeInformacion("Hecho", "Arma guardada");
		}
		
		refrescarLista();
		limpiar();
		modoEdicion(false);
	}
	
	private void modificarArma() {
		rellenarCampos();
		modoEdicion(true);
		accion = Accion.MODIFICAR;
	}
	
	private void refrescarLista() {
		modelArma.removeAllElements();
		Modelo modelo = new Modelo();
		for(Arma a: modelo.getArmas())
			modelArma.addElement(a);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Si no hay nada seleccionado
		if(listArmas.getSelectedIndex() == -1)
			return;
		
		armaActual = listArmas.getSelectedValue();
		rellenarCampos();
		
	}
}
