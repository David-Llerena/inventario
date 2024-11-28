package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVentas {
	private int codigo ;
	private int cabecera;
	private Producto producto;
	private int cantidad;
	private BigDecimal precio_venta;
	private BigDecimal subtotal;
	private BigDecimal total_sin_iva;
	
	public DetalleVentas(int codigo, int cabecera, Producto producto, int cantidad, BigDecimal precio_venta,
			BigDecimal subtotal, BigDecimal total_sin_iva) {
		super();
		this.codigo = codigo;
		this.cabecera = cabecera;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio_venta = precio_venta;
		this.subtotal = subtotal;
		this.total_sin_iva = total_sin_iva;
	}

	public DetalleVentas() {
		super();
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCabecera() {
		return cabecera;
	}

	public void setCabecera(int cabecera) {
		this.cabecera = cabecera;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(BigDecimal precio_venta) {
		this.precio_venta = precio_venta;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal_sin_iva() {
		return total_sin_iva;
	}

	public void setTotal_sin_iva(BigDecimal total_sin_iva) {
		this.total_sin_iva = total_sin_iva;
	}
	
	
}
