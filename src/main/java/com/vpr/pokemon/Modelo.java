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
	//Variables
	private ArrayList<Pokemon> listPokemon;
	private ArrayList<Arma> listArmas;
	private Query query;
	private static Pokemon ultimoPokemonBorrado;
	private static Arma ultimaArmaBorrada;
	
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
		Session sesion = HibernateUtil.getCurrentSession();
		Query query = sesion.createSQLQuery("SELECT usuario FROM usuarios u WHERE u.usuario = :usuario and u.contrasena = :contrasena");
		query.setParameter("usuario", usuario);
		query.setParameter("contrasena", contrasena);
		Object o = query.uniqueResult();
		sesion.close();
		
		//Si lo ha encontrado
		if(o != null)
			return true;
		
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
	
	public synchronized void modificarArma(Arma arma) {
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
		//Guardo el ultimo borrado
		ultimoPokemonBorrado = pokemon.clone();
		ultimoPokemonBorrado.getArmas().clear();
		System.out.println(pokemon.getArmas().size());
		
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(pokemon);
		
		//Pongo sus armas a null
		for(Arma arma : pokemon.getArmas()) {
			arma.setPokemon(null);
			sesion.update(arma);
		}
		
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void eliminarArma(Arma arma) {
		//Guardo la ultima borrada
		ultimaArmaBorrada = arma.clone();
		
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(arma);
		sesion.getTransaction().commit();
		sesion.close();
		
		System.out.println(ultimaArmaBorrada);
	}
	
	public boolean deshacerPokemon() {
		if(ultimoPokemonBorrado != null) {
			guardar(ultimoPokemonBorrado);
			ultimoPokemonBorrado = null;
			return true;
		}
		
		return false;
	}
	
	public boolean deshacerArma() {
		if(ultimaArmaBorrada != null) {
			guardar(ultimaArmaBorrada);
			ultimaArmaBorrada = null;
			return true;
		}
		
		return false;
	}
	
	public void borrarTodoPokemon() {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		
		for(Arma arma : getArmas()) {
			arma.setPokemon(null);
			sesion.update(arma);
		}
		
		sesion.createSQLQuery("TRUNCATE TABLE pokemon").executeUpdate();
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void borrarTodoArma() {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		
		for(Pokemon pokemon : getPokemones()) {
			pokemon.getArmas().clear();
			sesion.update(pokemon);
		}
		
		sesion.createSQLQuery("TRUNCATE TABLE armas").executeUpdate();
		sesion.getTransaction().commit();
		sesion.close();
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
