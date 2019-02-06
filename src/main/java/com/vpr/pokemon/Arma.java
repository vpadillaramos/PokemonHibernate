package com.vpr.pokemon;

import javax.persistence.*;

@Entity
@Table(name="armas")
public class Arma {
	//Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="nombre")
	private String nombre;
	@Column(name="ataque")
	private int ataque;
	@Column(name="duracion")
	private int duracion;
	
	//Relacion con la tabla Pokemon
	@ManyToOne
	@JoinColumn(name="id_pokemon")
	private Pokemon pokemon;
	
	//Constructor
	public Arma() {}
	
	//Metodos
	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getAtaque() {
		return ataque;
	}

	public int getDuracion() {
		return duracion;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Arma))
			return false;
		
		Arma arma = (Arma) o;
		if(nombre.equals(arma.getNombre()))
			return true;
		
		return false;
	}
}
