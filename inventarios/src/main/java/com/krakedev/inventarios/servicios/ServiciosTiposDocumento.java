package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.krakedev.inventarios.bdd.TiposDocumentoBDD;
import com.krakedev.inventarios.entidades.TiposDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path ("tiposdocumento")
public class ServiciosTiposDocumento {
	
	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public Response recuperar(){
		TiposDocumentoBDD tiposDocBDD = new TiposDocumentoBDD();
		ArrayList<TiposDocumento> tiposdocumentos = null;
		try {
			tiposdocumentos =  tiposDocBDD.recuperar();
			return Response.ok(tiposdocumentos).build();
		} catch (KrakeDevException e){
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
}
