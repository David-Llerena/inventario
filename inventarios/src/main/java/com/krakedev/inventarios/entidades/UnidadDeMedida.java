package com.krakedev.inventarios.entidades;

public class UnidadDeMedida {
	private String nombre;
	private String descripcion;
	private CategoriaUDM categoriaUnidadDeMedida;
	public UnidadDeMedida(String nombre, String descripcion, CategoriaUDM categoriaUnidadDeMedida) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoriaUnidadDeMedida = categoriaUnidadDeMedida;
	}
	public UnidadDeMedida() {
		super();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CategoriaUDM getCategoriaUnidadDeMedida() {
		return categoriaUnidadDeMedida;
	}
	public void setCategoriaUnidadDeMedida(CategoriaUDM categoriaUnidadDeMedida) {
		this.categoriaUnidadDeMedida = categoriaUnidadDeMedida;
	}
	
}
