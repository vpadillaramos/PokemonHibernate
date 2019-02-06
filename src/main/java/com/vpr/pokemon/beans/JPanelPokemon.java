package com.vpr.pokemon.beans;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vpr.pokemon.Modelo;
import com.vpr.pokemon.Pokemon;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import com.vpr.pokemon.Pokemon.Tipo;
import com.vpr.pokemon.util.Util;

public class JPanelPokemon extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
	public JBotonesCrud botonesCrud;
	public JLabel lblNombre;
	public JLabel lblNivel;
	public JComboBox cbTipo;
	public JLabel lblTipo;
	public JLabel lblPeso;
	public JTextField tfNombre;
	public JTextField tfNivel;
	public JTextField tfPeso;
	public JButton btBorrarTodo;
	public JLabel lbImagen;
	public JPanelBusqueda panelBusqueda;
	public JPanelAnadirArma panelAnadirArma;
	public JLabel lblArma;
	
	//Atributos
	private enum Accion {
		NUEVO, MODIFICAR
	}
	private Accion accion;
	private Pokemon pokemonActual;
	private byte[] imagen;
	

	public JPanelPokemon() {
		setLayout(null);
		
		botonesCrud = new JBotonesCrud();
		botonesCrud.btModificar.setLocation(10, 145);
		botonesCrud.btCancelar.setLocation(10, 44);
		botonesCrud.btGuardar.setLocation(10, 111);
		botonesCrud.setBounds(222, 87, 104, 231);
		add(botonesCrud);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(16, 24, 51, 14);
		add(lblNombre);
		
		lblNivel = new JLabel("Nivel");
		lblNivel.setBounds(16, 92, 37, 14);
		add(lblNivel);
		
		cbTipo = new JComboBox();
		cbTipo.setBounds(101, 57, 104, 20);
		add(cbTipo);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(16, 60, 43, 14);
		add(lblTipo);
		
		lblPeso = new JLabel("Peso");
		lblPeso.setBounds(16, 126, 37, 14);
		add(lblPeso);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(101, 21, 104, 20);
		add(tfNombre);
		tfNombre.setColumns(10);
		
		tfNivel = new JTextField();
		tfNivel.setBounds(101, 92, 104, 20);
		add(tfNivel);
		tfNivel.setColumns(10);
		
		tfPeso = new JTextField();
		tfPeso.setBounds(101, 123, 104, 20);
		add(tfPeso);
		tfPeso.setColumns(10);
		
		btBorrarTodo = new JButton("Borrar todo");
		btBorrarTodo.setBounds(383, 353, 121, 23);
		add(btBorrarTodo);
		
		lbImagen = new JLabel("");
		lbImagen.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Imagen", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lbImagen.setBounds(237, 21, 71, 65);
		add(lbImagen);
		
		panelBusqueda = new JPanelBusqueda();
		panelBusqueda.setBounds(353, 23, 151, 190);
		add(panelBusqueda);
		
		panelAnadirArma = new JPanelAnadirArma();
		panelAnadirArma.setBounds(16, 186, 198, 190);
		add(panelAnadirArma);
		
		lblArma = new JLabel("Armas");
		lblArma.setBounds(94, 161, 46, 14);
		add(lblArma);
		
		iniciar();
	}

	
	//Metodos
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(JBotonesCrud.Accion.valueOf(e.getActionCommand())) {
		case NUEVO:
			nuevoPokemon();
			break;
		case MODIFICAR:
			modificarPokemon();
			break;
		case GUARDAR:
			guardarPokemon();
			break;
		case CANCELAR:
			cancelar();
			break;
		case BORRAR:
			borrarPokemon();
			break;
		default:
			
			break;
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//SI no hay nadfa seleccionado
		if(panelBusqueda.lista.getSelectedIndex() == -1)
			return;
		pokemonActual = (Pokemon) panelBusqueda.lista.getSelectedValue();
		rellenarCampos();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == lbImagen) {
			//Abro un selector de fichero
			JFileChooser fc = new JFileChooser();
	
			//si le da a cancelar se hace nada
			if(fc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
				return;
			
			File ficheroSeleccionado = fc.getSelectedFile();
			lbImagen.setIcon(new ImageIcon(ficheroSeleccionado.getAbsolutePath()));
			
			File file = new File(ficheroSeleccionado.getAbsolutePath());
			imagen = new byte[(int) file.length()];
			
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(imagen);
				fileInputStream.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void iniciar() {
		Modelo modelo = new Modelo();
		modoEdicion(false);
		poblarDesplegableTipos();
		panelBusqueda.inicializar(modelo.getPokemones());
		refrescarLista();
		
		//Listeners
		botonesCrud.addListeners(this);
		lbImagen.addMouseListener(this);
		panelBusqueda.lista.addListSelectionListener(this);
	}
	
	private void refrescarLista() {
		panelBusqueda.refrescarLista();
	}
	
	private void limpiar() {
		tfNombre.setText("");
		cbTipo.setSelectedIndex(0);
		tfNivel.setText("");
		tfPeso.setText("");
		lbImagen.setIcon(null);
	}
	
	private void modoEdicion(boolean b) {
		if(b) {
			botonesCrud.modoEdicion(b);
			panelAnadirArma.modoEdicion(b);
			
			tfNombre.setEditable(b);
			cbTipo.setEnabled(b);
			tfNivel.setEditable(b);
			tfPeso.setEditable(b);
			lbImagen.setEnabled(b);
			
			lbImagen.addMouseListener(this);
			
			panelBusqueda.lista.setEnabled(!b);
		}
		else {
			botonesCrud.modoEdicion(b);
			panelAnadirArma.modoEdicion(b);
			
			tfNombre.setEditable(b);
			cbTipo.setEnabled(b);
			tfNivel.setEditable(b);
			tfPeso.setEditable(b);
			lbImagen.setEnabled(b);
			
			lbImagen.removeMouseListener(this);
			
			panelBusqueda.lista.setEnabled(!b);
			panelBusqueda.lista.clearSelection();
		}
		
	}
	
	private void poblarDesplegableTipos() {
		for(Tipo tipo: Tipo.values())
			cbTipo.addItem(tipo);
	}
	
	private void rellenarCampos() {
		tfNombre.setText(pokemonActual.getNombre());
		cbTipo.setSelectedItem(pokemonActual.getTipo());
		tfNivel.setText(String.valueOf(pokemonActual.getNivel()));
		tfPeso.setText(String.valueOf(pokemonActual.getPeso()));
		
		if(pokemonActual.getImagen() != null)
			lbImagen.setIcon(new ImageIcon(pokemonActual.getImagen()));
		else
			lbImagen.setIcon(null);
		
		panelAnadirArma.anadirArmas(pokemonActual.getArmas());
		
		botonesCrud.btModificar.setEnabled(true);
		botonesCrud.btBorrar.setEnabled(true);
	}
	
	private void nuevoPokemon() {
		limpiar();
		modoEdicion(true);
		accion = Accion.NUEVO;
	}
	
	private void modificarPokemon() {
		rellenarCampos();
		modoEdicion(true);
		accion = Accion.MODIFICAR;
	}
	
	private void cancelar() {
		limpiar();
		modoEdicion(false);
	}
	
	private void borrarPokemon() {
		Modelo modelo = new Modelo();
		Pokemon pokemon = null;
		pokemon = (Pokemon) panelBusqueda.getSeleccionado();
		modelo.eliminarPokemon(pokemon);
		Util.mensajeInformacion("Hecho", pokemon.getNombre() + " eliminado correctamente");
		modoEdicion(false);
	}
	
	private void guardarPokemon() {
		Modelo modelo = new Modelo();
		
		//************Verificacion de datos****************
		if(tfNombre.getText().equals("")) {
			Util.mensajeError("Error", "El nombre es obligatorio");
			return;
		}
		
		if(tfNivel.getText().equals(""))
			tfNivel.setText("0");
		if(!modelo.isInt(tfNivel.getText())) {
			Util.mensajeError("Error", "El nivel debe ser un n�mero");
			tfNivel.selectAll();
			tfNivel.requestFocus();
			return;
		}
		
		if(tfPeso.getText().equals(""))
			tfPeso.setText("0.0");
		if(!modelo.isFloat(tfPeso.getText())) {
			Util.mensajeError("Error", "El peso debe ser un n�mero");
			tfPeso.selectAll();
			tfPeso.requestFocus();
			return;
		}
		
		Pokemon pokemon = null;
		switch(accion) {
		case NUEVO:
			pokemon = new Pokemon();
			break;
		case MODIFICAR:
			pokemon = pokemonActual;
			break;
		default:
			
			break;
		}
		
		//Recogida de datos
		pokemon.setNombre(tfNombre.getText());
		pokemon.setTipo((Tipo) cbTipo.getSelectedItem());
		pokemon.setNivel(Integer.parseInt(tfNivel.getText()));
		pokemon.setPeso(Float.parseFloat(tfPeso.getText()));
		pokemon.getArmas().addAll(panelAnadirArma.getListaArmas());
		
		if(imagen != null)
			pokemon.setImagen(imagen);
		else
			pokemon.setImagen(null);
		
		
		
		if(accion == Accion.MODIFICAR) {
			Util.mensajeInformacion("Hecho", "Pokemon modificada");
			modelo.modificarPokemon(pokemon);
		}
		else {
			modelo.guardar(pokemon);
			Util.mensajeInformacion("Hecho", "Pokemon guardado");
		}
		
		refrescarLista();
		limpiar();
		modoEdicion(false);
	}
	
}