package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.PedidosBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeDevException;
@Path("pedidos")
public class ServiciosPedidos {
	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Pedido pedido) {
		PedidosBDD pedidosBDD=new PedidosBDD();
		try {
			pedidosBDD.insertar(pedido);
			return Response.ok().build();
		} catch (KrakeDevException e){
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("recibir")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recibir(Pedido pedido) {
		PedidosBDD pedidosBDD=new PedidosBDD();
		try {
			pedidosBDD.actualizar(pedido);
			return Response.ok().build();
		} catch (KrakeDevException e){
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
}
