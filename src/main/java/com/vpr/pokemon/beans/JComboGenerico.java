package com.vpr.pokemon.beans;

import java.util.List;

import javax.swing.JComboBox;

public class JComboGenerico<T> extends JComboBox<T>{
	
	//Atributos
	private List<T> datos;
	
	//Constructor
	public JComboGenerico() {
		super(); //heredo todo
	}
	
	//Metodos
	public void inicializar(List<T> datos) {
		this.datos = datos;
		listar();
	}
	
	public void refrescar() {
		limpiar();
		listar();
	}
	
	public void listar() {
		if(datos == null)
			return;
		for(T dato : datos)
			addItem(dato);
	}
	
	public void limpiar() {
		removeAllItems();
	}
	
	public T getDatoSeleccionada() {
		T dato = (T) getSelectedItem();
		return dato;
	}
}
