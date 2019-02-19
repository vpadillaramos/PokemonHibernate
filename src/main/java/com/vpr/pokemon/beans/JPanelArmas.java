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
import javax.swing.JButton;

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
		NUEVO, MODIFICAR, DESHACER
	}
	private Accion accion;
	private Arma armaActual;
	public JButton btBorrarTodo;
	public JButton btDeshacer;

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
		
		btBorrarTodo = new JButton("Borrar todo");
		btBorrarTodo.setBounds(328, 266, 102, 23);
		add(btBorrarTodo);
		
		btDeshacer = new JButton("Deshacer borrado");
		btDeshacer.setActionCommand("DESHACER");
		btDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btDeshacer.setBounds(269, 232, 161, 23);
		add(btDeshacer);
		
		iniciar();
		
	}
	
	public void iniciar() {
		refrescarLista();
		modoEdicion(false);
		botonesCrud.addListeners(this);
		btBorrarTodo.addActionListener(this);
		btDeshacer.addActionListener(this);
		listArmas.addListSelectionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btBorrarTodo) {
			borrarTodo();
			return;
		}
		
		if(e.getSource() == btDeshacer) {
			
		}
		
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
			btBorrarTodo.setEnabled(!b);
			btDeshacer.setEnabled(!b);
			
			tfNombre.setEditable(b);
			tfAtaque.setEditable(b);
			tfDuracion.setEditable(b);
		}
		else {
			botonesCrud.modoEdicion(b);
			btBorrarTodo.setEnabled(!b);
			btDeshacer.setEnabled(!b);
			
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
	
	private void deshacer() {
		Modelo modelo = new Modelo();
		if(modelo.deshacerArma()) {
			Util.mensajeInformacion("Hecho", "Arma recuperada");
			refrescarLista();
		}
	}
	
	private void borrarTodo() {
		Modelo modelo = new Modelo();
		
		if(!Util.mensajeConfirmacion("¡ATENCIÓN!", "¿Quieres borrar todas las armas?"))
			return;
		modelo.borrarTodoArma();
		refrescarLista();
		
		Util.mensajeInformacion("Hecho", "Todas las armas han sido borradas correctamente");
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
		Modelo modelo = new Modelo();
		if(tfNombre.getText().equals("")) {
			Util.mensajeError("Error", "El nombre es obligatorio");
			tfNombre.selectAll();
			tfNombre.requestFocus();
			return;
		}
		
		if(tfAtaque.getText().trim().equals(""))
			tfAtaque.setText("0");
		if(!modelo.isInt(tfAtaque.getText().trim())) {
			Util.mensajeError("Error", "El ataque debe ser entero");
			tfAtaque.selectAll();
			tfAtaque.requestFocus();
			return;
		}
		
		if(tfDuracion.getText().trim().equals(""))
			tfDuracion.setText("0");
		if(!modelo.isInt(tfDuracion.getText().trim())) {
			Util.mensajeError("Error", "La duración debe ser entera");
			tfDuracion.selectAll();
			tfDuracion.requestFocus();
			return;
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
		arma.setNombre(tfNombre.getText().trim());
		arma.setAtaque(Integer.parseInt(tfAtaque.getText().trim()));
		arma.setDuracion(Integer.parseInt(tfDuracion.getText().trim()));
		
		
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
