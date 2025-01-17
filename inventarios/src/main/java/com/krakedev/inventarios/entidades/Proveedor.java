package com.krakedev.inventarios.entidades;

public class Proveedor {
	private String identificador;
	private TiposDocumento tipoDocumento;
	private String nombre;
	private String telefono;
	private String correo;
	private String direccion;
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public TiposDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TiposDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Proveedor(String identificador, TiposDocumento tipoDocumento, String nombre, String telefono, String correo,
			String direccion) {
		super();
		this.identificador = identificador;
		this.tipoDocumento = tipoDocumento;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
	}
	public Proveedor() {
		super();
	}
	
	
}
