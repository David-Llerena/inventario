package com.krakedev.inventarios.entidades;

import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.bdd.EstadoPedido;

public class Pedido {
	private int codigo;
	private Proveedor proveedor;
	private Date fecha;
	public ArrayList<DetallePedido> getDetalles() {
		return detalles;
	}

	public void setDetalles(ArrayList<DetallePedido> detalles) {
		this.detalles = detalles;
	}

	private EstadoPedido estado;
	
	private ArrayList<DetallePedido> detalles;
	
	public Pedido(int codigo, Proveedor proveedor, Date fecha, EstadoPedido estado) {
		super();
		this.codigo = codigo;
		this.proveedor = proveedor;
		this.fecha = fecha;
		this.estado = estado;
	}

	public Pedido() {
		super();
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	
}
