package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.Set;

public class Carrito {
	private ArrayList<Producto> productos;
	private Cliente cliente;

	public Carrito() {
		this.productos = new ArrayList<Producto>();
	}

	public Carrito(Cliente clietne) {
		this.cliente = clietne;
		productos = new ArrayList<>();
	}

	public void agregarProducto(Producto producto) {
		Producto p = new Producto(producto.getId(), producto.getCodigo(), producto.getDescripcion(),
				producto.getCategoria(), producto.getPrecio(), producto.getMarca());
		productos.add(p);
	}

	private double calcularMontoTotal() {
		return productos.stream().mapToDouble(Producto::getPrecio).sum();
	}

	public double calcularMontoTotalConDescuentos(Set<Promocion> promociones, Tarjeta tarjeta) {
		double descuento = 0;

		double montoConDescuento = calcularMontoTotal();

		// validar tarjeta con algun servicio para continuar//

		if (promociones != null && productos != null) {
			for (Producto p : productos) {

				for (Promocion prom : promociones) {
					descuento += prom.obtenerDescuento(p.getMarca().getNombre(), p.getPrecio());

				}
			}
		}
		montoConDescuento -= descuento;
		descuento = 0;
		if (promociones != null) {
			for (Promocion prom : promociones) {

				descuento += prom.obtenerDescuento(tarjeta.getTipoTarjeta().toString(), montoConDescuento);

			}
		}

		montoConDescuento -= descuento;
		return montoConDescuento;
	}

	public Venta pagar(Set<Promocion> promociones, Tarjeta tarjeta) {

		return new Venta(this.cliente, this.productos, calcularMontoTotalConDescuentos(promociones, tarjeta),
				calcularMontoTotal());

	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	@Override
	public String toString() {
		String imprimir = "\n";
		for (Producto p : productos) {
			imprimir += p.toString() + "\n";
		}
		return imprimir;
	}

	private Cliente getCliente() {
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	private void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

}
