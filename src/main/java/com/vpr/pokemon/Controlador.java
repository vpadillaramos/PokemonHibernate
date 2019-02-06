package com.vpr.pokemon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vpr.pokemon.Pokemon.Tipo;
import com.vpr.pokemon.ui.Login;
import com.vpr.pokemon.util.Util;

public class Controlador {
	
	//Atributos
	private Vista vista;
	private Modelo modelo;
	private boolean modificarPokemon = false;
	private byte[] imagen;
	private Pokemon pokemonActual;
	
	private enum Accion{
		NUEVO, EDITAR, GUARDAR, CANCELAR, BORRAR, BORRAR_TODO
	}
	
	private Accion accion;
	
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		
		//Conecto con la base de datos
		try {
			modelo.conectarDb();
		} catch (ClassNotFoundException e) {
			System.out.println("Error al conectar con la base de datos");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
		
		//Inicio sesion
		//    iniciarSesion();
		
		refrescarLista();
		vista.hacerVisible(true);
	}
	
	//Metodos
	public void refrescarLista() {
		vista.modelPokemon.removeAllElements();
		
		for(Pokemon p: modelo.getPokemones())
			vista.modelPokemon.addElement(p);
	}
	
	public void iniciarSesion() {
		boolean autenticado = false;
		Login login = new Login();
		
		do {
			login.hacerVisible(true);
			String usuario = login.getUsuario();
			String contrasena = login.getContrasena();
			autenticado = modelo.iniciarSesion(usuario, contrasena);
			if(!autenticado) {
				login.mensajeError("Error en el usuario o contraseña");
				continue;
			}
			
		}while(!autenticado);
	}
}
