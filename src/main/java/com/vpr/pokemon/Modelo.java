package com.vpr.pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.vpr.pokemon.util.HibernateUtil;


public class Modelo {
	//Constantes
	private final String IP = "192.168.34.5"; //192.168.34.5 casa:127.0.0.1
	private final String BD = "vpadilla";
	private final String USUARIO = "vpadilla";
	private final String CONTRASENA = "42ha3h";
	
	//Variables
	private static Connection conexion;
	private PreparedStatement sentencia = null;
	private ResultSet resultado = null;
	private ArrayList<Pokemon> listPokemon;
	private ArrayList<Arma> listArmas;
	private Query query;
	
	public Modelo() {
		try {
			conectarDb();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Metodos
	public void conectarDb() throws ClassNotFoundException, SQLException {
		HibernateUtil.buildSessionFactory();
	}
	
	public void desconectarDb() throws SQLException {
		HibernateUtil.closeSessionFactory();
	}
	
	/*//Este metodo se invoca automaticamente al cerrar la aplicacion
	@Override
	public void finalize() throws SQLException {
		desconectarDb();
	}*/
	
	
	public boolean iniciarSesion(String usuario, String contrasena) {
		query = HibernateUtil.getCurrentSession().createQuery("");
		return false;
	}
	
	//Metodos de la ventana de pokemons
	public void guardar(Pokemon pokemon) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.save(pokemon);
		
		for(Arma arma : pokemon.getArmas()) {
			arma.setPokemon(pokemon);
			sesion.update(arma);
		}
		
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void guardar(Arma arma) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.save(arma);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void modificarPokemon(Pokemon pokemon) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.update(pokemon);
		
		for(Arma arma : pokemon.getArmas()) {
			arma.setPokemon(pokemon);
			sesion.update(arma);
		}
		
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void modificarArma(Arma arma) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.update(arma);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public boolean existePersonaje(String nombre) {
		Session sesion = HibernateUtil.getCurrentSession();
		Query<Pokemon> query = sesion.createQuery("FROM Pokemon WHERE nombre = :nombre");
		query.setParameter("nombre", nombre);
		Pokemon pokemon = query.uniqueResult();
		return pokemon != null;
	}
	
	public void eliminarPokemon(Pokemon pokemon) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(pokemon);
		
		for(Arma arma : pokemon.getArmas()) {
			arma.setPokemon(null);
			sesion.update(arma);
		}
		
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void eliminarArma(Arma arma) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(arma);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void borrarTodoPokemon() {
		Session sesion = HibernateUtil.getCurrentSession();
		Transaction tx = sesion.beginTransaction();
		sesion.createSQLQuery("").executeUpdate();
	}
	
	public void borrarTodoArma() {
		Session sesion = HibernateUtil.getCurrentSession();
		Transaction tx = sesion.beginTransaction();
		String hqDelete = "delete Arma";
	}
	
	public ArrayList<Pokemon> getPokemones() {
		query = HibernateUtil.getCurrentSession().createQuery("FROM Pokemon");
		listPokemon = (ArrayList<Pokemon>) query.getResultList();
		
		return listPokemon;
	}
	
	public ArrayList<Arma> getArmas(){
		query = HibernateUtil.getCurrentSession().createQuery("FROM Arma");
		listArmas = (ArrayList<Arma>) query.list();
		
		return listArmas;
	}
	
	public List<Arma> getArmasLibres(){
		Session sesion = HibernateUtil.getCurrentSession();
		List<Arma> armas = sesion.createQuery("FROM Arma a WHERE a.pokemon IS NULL").list();
		sesion.close();
		return armas;
	}
	
	/***
	 * Copmprueba si una cadena es un entero
	 * @param cadena
	 * @return
	 */
	public boolean isInt(String cadena) {
		boolean resultado = false;
		if(cadena.matches("\\d*"))	//esto es una expresion regular que comprueba si son numeros
			resultado = true;
		return resultado;
	}
	
	/***
	 * Comprueba si una cadena es float (detecta el punto)
	 * @param cadena
	 * @return
	 */
	public boolean isNumeric(String cadena) {
		boolean resultado = false;
		if(cadena.matches("\\d*\\.\\d*") || cadena.matches("\\d*\\,\\d*") || cadena.matches("\\d*"))	//esto es una expresion regular que comprueba si son numeros
			resultado = true;
		cadena = cadena.replaceAll(",", ".");
		return resultado;
	}
}
