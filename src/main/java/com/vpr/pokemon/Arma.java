package com.vpr.pokemon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="armas2")
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
	@ManyToMany(cascade = CascadeType.DETACH, mappedBy = "armas", fetch = FetchType.EAGER)
	private List<Pokemon> pokemones;
	
	//Constructor
	public Arma() {
		pokemones = new ArrayList<>();
	}
	
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
	
	public List<Pokemon> getPokemones() {
		return pokemones;
	}

	public void setPokemones(List<Pokemon> pokemones) {
		this.pokemones.clear();
		this.pokemones.addAll(pokemones);
	}

	public Arma clone() {
		Arma a = new Arma();
		a.setNombre(nombre);
		a.setAtaque(ataque);
		a.setDuracion(duracion);
		a.setPokemones(getPokemones());
		
		return a;
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
