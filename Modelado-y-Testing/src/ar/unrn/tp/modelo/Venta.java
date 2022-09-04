package ar.unrn.tp.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Venta {
	private ArrayList<Producto> productos;
	private double montoTotal;
	private Date fecha;
	private Cliente cliente;
	private double montoConDescuento;

	public Venta(Cliente cliente, ArrayList<Producto> productos, double montoConDescuento, double montoTotal) {
		this.cliente = cliente;
		this.fecha = new Date();
		this.montoTotal = montoTotal;
		this.montoConDescuento = montoConDescuento;
		this.productos = new ArrayList<>();
		for (Producto p : productos) {
			this.productos.add(new Producto(p.getCodigo(), p.getDescripcion(),
					new Categoria(p.getCategoria().getNombre()), p.getPrecio(), new Marca(p.getMarca().getNombre())));
		}
	}

	private String mostrarProductos() {
		String imprimir = "\n";
		for (Producto p : productos) {
			imprimir += p.toString() + "\n";
		}
		return imprimir;
	}

	private String formatoFecha(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

		return formatter.format(fecha);
	}

	@Override
	public String toString() {
		return "Venta:\nFecha:" + formatoFecha(this.fecha) + "\nCliente: " + cliente.getNombre() + "\nProductos: "
				+ mostrarProductos() + "\nMonto total: " + this.montoTotal + "\nMonto con descuento: "
				+ this.montoConDescuento;
	}

}
