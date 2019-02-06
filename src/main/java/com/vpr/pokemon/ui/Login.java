package com.vpr.pokemon.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login extends JDialog implements ActionListener, KeyListener{
	
	//Atributos
	private String usuario;
	private String contrasena;
	
	//Componentes
	private JPanel contentPanel = new JPanel();
	public JLabel lbUsuario;
	public JLabel lbContrasena;
	public JTextField tfUsuario;
	public JPasswordField tfContrasena;
	public JButton btLogin;
	public JButton btSalir;
	public JLabel lbError;


	public Login() {
		setTitle("Login");
		setBounds(100, 100, 328, 226);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);
		
		
		lbUsuario = new JLabel("Usuario");
		lbUsuario.setBounds(66, 61, 46, 14);
		contentPanel.add(lbUsuario);

		lbContrasena = new JLabel("Contrasena");
		lbContrasena.setBounds(49, 97, 84, 14);
		contentPanel.add(lbContrasena);

		tfUsuario = new JTextField();
		tfUsuario.setBounds(122, 58, 86, 20);
		contentPanel.add(tfUsuario);
		tfUsuario.setColumns(10);

		tfContrasena = new JPasswordField();
		tfContrasena.setBounds(121, 94, 86, 20);
		tfContrasena.addKeyListener(this);
		contentPanel.add(tfContrasena);
		
		lbError = new JLabel("");
		lbError.setBounds(40, 129, 217, 14);
		contentPanel.add(lbError);
		setLocationRelativeTo(null);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btLogin = new JButton("Login");
		buttonPane.add(btLogin);
		btLogin.setActionCommand("login");
		btLogin.addActionListener(this);
		getRootPane().setDefaultButton(btLogin);
		
		btSalir = new JButton("Salir");
		btSalir.setActionCommand("salir");
		btSalir.addActionListener(this);
		buttonPane.add(btSalir);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "login":
			usuario = tfUsuario.getText();
			contrasena = String.valueOf(tfContrasena.getPassword());
			setVisible(false);
			break;
			
		case "salir":
			System.exit(0);
			break;
		default:
			break;
		}
	}

	public void hacerVisible(boolean b) {
		setVisible(b);
	}
	
	public void mensajeError(String texto) {
		lbError.setForeground(Color.RED);
		tfContrasena.setText("");
		tfContrasena.requestFocus();
		lbError.setText(texto);
	}
	
	public String getUsuario() {return usuario;}
	public String getContrasena() {return contrasena;}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			btLogin.doClick();
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
