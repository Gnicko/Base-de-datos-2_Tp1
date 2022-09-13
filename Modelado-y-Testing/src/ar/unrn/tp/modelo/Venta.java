package ar.unrn.tp.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Venta {
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany(cascade = CascadeType.PERSIST)
	private ArrayList<ProductoVendido> productos;
	private double montoTotal;
	private Date fecha;

	private Cliente cliente;
	private double montoConDescuento;

	protected Venta() {

	}

	public Venta(Cliente cliente, ArrayList<Producto> productos, double montoConDescuento, double montoTotal) {
		this.cliente = cliente;
		this.fecha = new Date();
		this.montoTotal = montoTotal;
		this.montoConDescuento = montoConDescuento;
		this.productos = new ArrayList<>();
		for (Producto p : productos) {
			this.productos.add(new ProductoVendido(p.getId(), p.getCodigo(), p.getDescripcion(), p.getPrecio()));
		}
	}

	private String mostrarProductos() {
		String imprimir = "\n";
		for (ProductoVendido p : productos) {
			imprimir += p.toString() + "\n";
		}
		return imprimir;
	}

	private String formatoFecha(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

		return formatter.format(fecha);
	}

	private ArrayList<ProductoVendido> getProductos() {
		return productos;
	}

	private void setProductos(ArrayList<ProductoVendido> productos) {
		this.productos = productos;
	}

	private double getMontoTotal() {
		return montoTotal;
	}

	private void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	private Date getFecha() {
		return fecha;
	}

	private void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	private Cliente getCliente() {
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	private double getMontoConDescuento() {
		return montoConDescuento;
	}

	private void setMontoConDescuento(double montoConDescuento) {
		this.montoConDescuento = montoConDescuento;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Venta:\nFecha:" + formatoFecha(this.fecha) + "\nCliente: " + cliente.getNombre() + "\nProductos: "
				+ mostrarProductos() + "\nMonto total: " + this.montoTotal + "\nMonto con descuento: "
				+ this.montoConDescuento;
	}

}
