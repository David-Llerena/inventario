package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.krakedev.inventarios.entidades.CabeceraVentas;
import com.krakedev.inventarios.entidades.DetalleVentas;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasBDD {  
	public void insertar(CabeceraVentas cabeceraVentas) throws KrakeDevException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    PreparedStatement psDet = null;
	    PreparedStatement psHs = null;
	    ResultSet rsClave = null;
	    int codigo = 0;
	    Date fechaActual = new Date();
		java.sql.Timestamp fechaSQL = new java.sql.Timestamp(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConexion();
			//ingresa los datos en 0 para cabecera de ventas
			ps = con.prepareStatement("INSERT INTO cabecera_ventas (fecha, total_sin_iva, iva, total) "
			+"VALUES (?, ?, ?, ?) ",Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, fechaSQL);
			ps.setBigDecimal(2, BigDecimal.ZERO);
			ps.setBigDecimal(3, BigDecimal.ZERO);
			ps.setBigDecimal(4, BigDecimal.ZERO);
			ps.executeUpdate();
			
			rsClave = ps.getGeneratedKeys();
			if(rsClave.next()) {
				codigo=rsClave.getInt(1);
			}
			BigDecimal Subtotal_con_iva = new BigDecimal(0);
			BigDecimal Subtotal_sin_iva = new BigDecimal(0);
			for (int i = 0; i < cabeceraVentas.getDetalles().size(); i++) {
			    DetalleVentas ventDet = cabeceraVentas.getDetalles().get(i);
			    //ingresa los datos de detalles de ventas en base al cabecera ventas que se creo arriba en 0
			    psDet = con.prepareStatement("INSERT INTO detalle_ventas (cabecera_ventas, producto, cantidad, precio_venta, subtotal,subtotal_iva) "
			            + "	VALUES ( ?, ?, ?, ?, ?,?);");
			    psDet.setInt(1, codigo);
			    psDet.setInt(2, ventDet.getProducto().getCodigo());
			    psDet.setInt(3, ventDet.getCantidad());
			    psDet.setBigDecimal(4, ventDet.getProducto().getPrecioVenta());
			    
			    BigDecimal subtotal = ventDet.getProducto().getPrecioVenta().multiply(new BigDecimal(ventDet.getCantidad()));
			    psDet.setBigDecimal(5, subtotal);
	       
			    BigDecimal valorIva = new BigDecimal(1.12);
			    BigDecimal subtotalIva=subtotal.multiply(valorIva);
			    
		        if(ventDet.getProducto().isTieneIva()) {
		        	psDet.setBigDecimal(6, subtotalIva);
		        	Subtotal_con_iva=subtotalIva;
		        }else {
		        	psDet.setBigDecimal(6, subtotal);
		        	Subtotal_sin_iva=subtotal;
		        }

			    psDet.execute();   
			}
			//Actualiza la cabecera_ventas con los valores obtenidos de detalles de ventas
			ps = con.prepareStatement("UPDATE cabecera_ventas " 
					+ "	SET  total_sin_iva=?, iva=?, total=? "
					+ "	WHERE codigo =?;");
			ps.setBigDecimal(1, Subtotal_sin_iva);
			ps.setBigDecimal(2, Subtotal_con_iva );
			ps.setBigDecimal(3, Subtotal_sin_iva.add(Subtotal_con_iva ));
			ps.setInt(4, codigo);
			ps.execute();
			
			for (int i = 0; i < cabeceraVentas.getDetalles().size(); i++) {
				   DetalleVentas ventDet = cabeceraVentas.getDetalles().get(i);
				   //actualiza los valores de historial stock
				   psHs= con.prepareStatement("INSERT INTO historial_stock (fecha, producto, referencia, cantidad ) " 
				   + "VALUES (?, ?, ?, ?);");
				   psHs.setTimestamp(1, fechaSQL);
				   psHs.setString(2, "VENTA " + codigo);
				   psHs.setInt(3, ventDet.getProducto().getCodigo());
				   psHs.setInt(4, -1*ventDet.getCantidad());
				   psHs.execute();
				}
			

		} catch (KrakeDevException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new KrakeDevException("Error al insertar detalle y cabecera de ventas: " + e.getMessage());
	    }
	}
}
//	    Connection con = null;
//	    PreparedStatement psHeaderInsert = null;
//	    PreparedStatement psDetailInsert = null;
//	    PreparedStatement psHistorialStock = null;
//	    ResultSet rsClave = null;
//	    Date fechaActual = new Date();
//	    java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

//	    try {
//	        con = ConexionBDD.obtenerConexion();
//
//	        psHeaderInsert = con.prepareStatement("INSERT INTO detalle_ventas (producto, cantidad, precio_venta, subtotal,subtotal_iva) VALUES (?, ?, ?, ?,?) ", Statement.RETURN_GENERATED_KEYS);
//	        psHeaderInsert.setInt(1, detalleVentas.getProducto().getCodigo());
//	        psHeaderInsert.setInt(2, detalleVentas.getCantidad());
//	        psHeaderInsert.setBigDecimal(3, detalleVentas.getProducto().getCoste());
//	        BigDecimal cantidad = new BigDecimal(detalleVentas.getCantidad());
//	        BigDecimal subtotal= detalleVentas.getSubtotal().multiply(cantidad);
//	        psHeaderInsert.setBigDecimal(4, subtotal);
//	        boolean iva=detalleVentas.getProducto().isTieneIva();
//	        BigDecimal valorIva = new BigDecimal(1.12);
//	        BigDecimal subtotalIva=subtotal.multiply(valorIva);
//	        if(iva==true) {
//	        	psHeaderInsert.setBigDecimal(5, subtotalIva);
//	        }else {
//	        	psHeaderInsert.setBigDecimal(5, subtotal);
//	        }
//	        
//	         
//	        psHeaderInsert.executeUpdate();
//
//	        rsClave = psHeaderInsert.getGeneratedKeys();
//	        if (rsClave.next()) {
//	            int codigoDetalle = rsClave.getInt(1);
//	            detalleVentas.setCodigo(codigoDetalle);
//
//	            psDetailInsert = con.prepareStatement("INSERT INTO cabecera_ventas (fecha, totalSinIva, iva, total) VALUES (?, ?, ?, ?)");
//	            psHistorialStock = con.prepareStatement("INSERT INTO historial_stock (fecha, producto, referencia, cantidad) VALUES (?, ?, ?, ?)");
//
//	            CabeceraVentas cabeceraVentas = detalleVentas.getCabeceraVentas();
//
//	            psDetailInsert.setDate(1, fechaSQL);
//	            psDetailInsert.setBigDecimal(2, cabeceraVentas.getTotalSinIva());
//	            BigDecimal ivaC = new BigDecimal(0.12);
//	            BigDecimal valorIvaC = cabeceraVentas.getTotalSinIva().multiply(ivaC);
//	            psDetailInsert.setBigDecimal(3, valorIvaC);
//	            psDetailInsert.setBigDecimal(4, cabeceraVentas.getTotalSinIva().add(valorIvaC));
//	            psDetailInsert.executeUpdate();
//
//	            psHistorialStock.setDate(1, fechaSQL);
//	            psHistorialStock.setInt(2, detalleVentas.getProducto().getCodigo());
//	            psHistorialStock.setString(3, "VENTA " + codigoDetalle);
//	            psHistorialStock.setInt(4, detalleVentas.getCantidad());
//	            psHistorialStock.executeUpdate();
//	        }
//	    } catch (KrakeDevException e) {
//	        e.printStackTrace();
//	        throw e;
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	        throw new KrakeDevException("Error al insertar detalle y cabecera de ventas: " + e.getMessage());
//	    }
//	}  
	
