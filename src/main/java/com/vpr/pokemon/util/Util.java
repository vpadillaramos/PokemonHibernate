package com.vpr.pokemon.util;

import javax.swing.JOptionPane;

public class Util {
	
	/***
	 * Metodo que muestra una ventana de mensaje informativo
	 * @param titulo Titulo de la ventana
	 * @param mensaje Mensaje que se mostrara en la ventana
	 */
	public static void mensajeInformacion(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/***
	 * Metodo que muestra una ventana de mensaje de error
	 * @param titulo Titulo de la ventana
	 * @param mensaje Mensaje de error
	 */
	public static void mensajeError(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
	}
}
