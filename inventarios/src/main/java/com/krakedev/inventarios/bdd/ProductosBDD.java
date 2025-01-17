package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {
	public ArrayList<Producto> buscar(String subcadena) throws KrakeDevException{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;	
		Producto producto = null;
		
		try {
			con=ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select prod.codigo_prod, prod.nombre as nombre_producto, "
					+ "udm.codigo_udm as nombre_udm, udm.descripcion as descripcion_udm, "
					+ "cast(prod.precio_venta as decimal(6,2)),prod.iva,cast(prod.coste as decimal(5,4)),prod.categoria, "
					+ "cat.nombre as nombre_categoria,stock "
					+ "from producto prod,unidad_medida udm, categorias cat "
					+ "where prod.udm = udm.codigo_udm and prod.categoria = cat.codigo_cat "
					+ "and upper(prod.nombre) like ? ");
			ps.setString(1,"%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				int codigoProducto=rs.getInt("codigo_prod");
				String nombreProducto=rs.getString("nombre_producto");
				String nombreUnidadMedida=rs.getString("nombre_udm");
				String descripcionUnidadMedida=rs.getString("descripcion_udm");
				BigDecimal precioVenta=rs.getBigDecimal("precio_venta");
				boolean tieneIva=rs.getBoolean("iva");
				BigDecimal coste=rs.getBigDecimal("coste");
				int codigoCategoria=rs.getInt("categoria");
				String nombreCategoria=rs.getString("nombre_categoria");
				int stock=rs.getInt("stock");
				
				UnidadDeMedida udm=new UnidadDeMedida();
				udm.setNombre(nombreUnidadMedida);
				udm.setDescripcion(descripcionUnidadMedida);
				
				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategoria);
				
				producto=new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecioVenta(precioVenta);
				producto.setTieneIva(tieneIva);
				producto.setCoste(coste);
				producto.setCategoria(categoria);
				producto.setStock(stock);
				
				productos.add(producto);
			}
		}catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar: Detalle "+e.getMessage());
		}
		return productos;
	}
	
	public void crear(Producto producto) throws KrakeDevException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    
	    try {
	        con = ConexionBDD.obtenerConexion();
	        ps = con.prepareStatement("INSERT INTO producto "
	            + "(codigo_prod, nombre, udm, precio_venta, iva, coste, categoria, stock) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	        
	        // Establecer los valores de los parámetros
	        ps.setInt(1, producto.getCodigo());
	        ps.setString(2, producto.getNombre());
	        ps.setString(3, producto.getUnidadMedida().getNombre());
	        ps.setBigDecimal(4, producto.getPrecioVenta());
	        ps.setBoolean(5, producto.isTieneIva());
	        ps.setBigDecimal(6, producto.getCoste());
	        ps.setInt(7, producto.getCategoria().getCodigo());
	        ps.setInt(8, producto.getStock());
	        
	        // Ejecutar la inserción
	        ps.executeUpdate();
	    } catch (KrakeDevException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new KrakeDevException("Error al insertar: Detalle " + e.getMessage());
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
