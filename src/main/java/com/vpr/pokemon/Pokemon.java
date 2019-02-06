package com.vpr.pokemon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="pokemon")
public class Pokemon {
	//Atributos
	public enum Tipo{
		Normal, Planta, Hada, Fuego, Agua, Fantasma, Siniestro,
		Psíquico, Acero, Dragón, Tierra, Eléctrico, Veneno,
		Bicho, Volador, Hielo, Lucha, Roca
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="nombre")
	private String nombre;
	@Column(name="tipo")
	private Tipo tipo;
	@Column(name="nivel")
	private int nivel;
	@Column(name="peso")
	private float peso;
	@Column(name="imagen")
	private byte[] imagen;
	
	//Relacion con la tabla Arma
	@OneToMany(mappedBy="pokemon", fetch = FetchType.EAGER)
	private List<Arma> armas; //un pokemon puede tener de 0 a N armas
	
	//Constructores
	public Pokemon() {
		armas = new ArrayList<>();
	}
	
	public Pokemon(String nombre, Tipo tipo, int nivel, float peso) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.nivel = nivel;
		this.peso = peso;
	}
	
	public Pokemon(int id, String nombre, Tipo tipo, int nivel, float peso) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.nivel = nivel;
		this.peso = peso;
	}
	
	//Metodos
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public float getPeso() {
		return peso;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public void setPeso(float peso) {
		this.peso = peso;
	}
	
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public List<Arma> getArmas() {
		return armas;
	}

	public void setArmas(List<Arma> armas) {
		this.armas.addAll(armas);
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(!(o instanceof Pokemon))
			return false;
		
		Pokemon pokemon = (Pokemon) o;
		if(nombre.equals(pokemon.getNombre()))
			return true;
		
		return false;
	}
}
