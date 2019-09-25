package com.hernandez.batch.dao.entity;

import java.io.Serializable;

public class Archivo implements Serializable {

	private String nombre; 
	private String ruta; 
	private Long peso; 
	private String tipo; 
	private String fecha; 
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

	@Override
	public String toString() {
		return "Archivo [nombre=" + nombre + ", ruta=" + ruta + ", peso=" + peso + ", tipo=" + tipo + ", fecha=" + fecha
				+ "]";
	}

	public Long getPeso() {
		return peso;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
	}



	private static final long serialVersionUID = 1L;

}
