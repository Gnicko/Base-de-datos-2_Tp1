package ar.unrn.tp.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.jpa.servicios.ClienteManager;
import ar.unrn.tp.jpa.servicios.DescuentoManager;
import ar.unrn.tp.jpa.servicios.ProductoManager;
import ar.unrn.tp.jpa.servicios.VentaManager;

public class Main {

	public static void main(String[] args) {
		ClienteManager cli = new ClienteManager();
		try {
			cli.agregarTarjeta(1L, "12", "master_card");
			System.out.println(cli.listarTarjetas(1L));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		// Productos service
		ProductoManager prod = new ProductoManager();
		prod.crearProducto("12222", "arroz", 100, 6L, 5L);

		prod.modificarProducto(20L, "1322", "Fideos", 200, 6L, 5L);
		System.out.println(prod.listarProductos());

		// Descueto service
		DescuentoManager desc = new DescuentoManager();
		desc.crearTienda();
		desc.crearDescuentoSobreTotal("visa", LocalDate.now(), LocalDate.now().plusDays(1), 0.08f);
		desc.crearDescuento("Acme", LocalDate.now(), LocalDate.now().plusDays(1), 0.05f);

		// Venta service
		VentaManager venta = new VentaManager();
		List<Long> productos = new ArrayList<>();
		productos.add(20L);
		productos.add(46L);
		venta.realizarVenta(1L, productos, 28L);
		System.out.println(venta.calcularMonto(productos, 28L));
		System.out.println(venta.ventas());
	}

}