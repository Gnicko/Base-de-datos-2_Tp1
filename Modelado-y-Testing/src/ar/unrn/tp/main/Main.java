package ar.unrn.tp.main;

import ar.unrn.tp.jpa.servicios.ClienteManager;
import ar.unrn.tp.jpa.servicios.ProductoManager;
import ar.unrn.tp.jpa.servicios.VentaManager;
import ar.unrn.tp.ui.VentanaCompra;

public class Main {

	public static void main(String[] args) {
//		ClienteManager cli = new ClienteManager("jpa-objectdb");
//		try {
//			cli.agregarTarjeta(1L, "12", "master_card");
//			System.out.println(cli.listarTarjetas(1L));
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		}
//		// Productos service
//		ProductoManager prod = new ProductoManager("jpa-objectdb");
//		prod.crearProducto("12222", "arroz", 100, 6L, 5L);
//
//		prod.modificarProducto(20L, "1322", "Fideos", 200, 6L, 5L);
//		System.out.println(prod.listarProductos());
//
//		// Descueto service
//		DescuentoManager desc = new DescuentoManager("jpa-objectdb");
//		desc.crearTienda();
//		desc.crearDescuentoSobreTotal("visa", LocalDate.now(), LocalDate.now().plusDays(1), 0.08f);
//		desc.crearDescuento("Acme", LocalDate.now(), LocalDate.now().plusDays(1), 0.05f);
//
//		// Venta service
//		VentaManager venta = new VentaManager("jpa-objectdb");
//		List<Long> productos = new ArrayList<>();
//		productos.add(20L);
//		productos.add(46L);
//		venta.realizarVenta(1L, productos, 28L);
//		System.out.println(venta.calcularMonto(productos, 28L));
//		System.out.println(venta.ventas());
		VentanaCompra ventana = new VentanaCompra(new ProductoManager("jpa-objectdb"), new VentaManager("jpa-objectdb"),
				new ClienteManager("jpa-objectdb"), 1L);
		ventana.setVisible(true);
	}

}
