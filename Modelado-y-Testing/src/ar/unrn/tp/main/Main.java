package ar.unrn.tp.main;

import ar.unrn.tp.jpa.servicios.ClienteManager;
import ar.unrn.tp.jpa.servicios.ProductoManager;
import ar.unrn.tp.jpa.servicios.VentaManager;
import ar.unrn.tp.ui.VentanaCompra;

public class Main {

	public static void main(String[] args) {
//		ClienteManager cli = new ClienteManager("jpa-mysql");
//		cli.crearCliente("nico", "gomez", "40778051", "nico@gmail.com");
//		cli.agregarTarjeta(1L, "4004895487", "master_card");
//		cli.agregarTarjeta(1L, "5404895487", "Visa");

		// Productos service
//		ProductoManager prod = new ProductoManager("jpa-mysql");
//		prod.crearCategoria("Comida");
//		prod.crearCategoria("Bebida");
//		prod.crearMarca("Arcor");
//		prod.crearMarca("Oreo");
//		prod.crearMarca("Coca-Cola");
//		prod.crearProducto("12222", "arroz", 100, 4L, 6L);
//		prod.crearProducto("12224", "fideos", 100, 4L, 6L);
//		prod.crearProducto("12225", "galletita", 100, 4L, 7L);
//		prod.crearProducto("12223", "Gaseosa", 200, 5L, 8L);
//		prod.modificarProducto(9L, "1", "arroz", 200, 4L, 6L);
//		prod.modificarProducto(9L, "12222", "arroz", 200, 4L, 6L);

		// Descueto service
//		DescuentoManager desc = new DescuentoManager("jpa-mysql");
//		desc.crearTienda();
//		desc.crearDescuentoSobreTotal("visa", LocalDate.now(), LocalDate.now().plusDays(1), 0.08f);
//		desc.crearDescuento("Arcor", LocalDate.now(), LocalDate.now().plusDays(1), 0.05f);
//
//		// Venta service
		VentaManager venta = new VentaManager("jpa-mysql");
//		List<Long> productos = new ArrayList<>();
//		productos.add(20L);
//		productos.add(46L);
//		venta.realizarVenta(1L, productos, red28L);
//		System.out.println(venta.calcularMonto(productos, 28L));
//		System.out.println(venta.ventas());

		VentanaCompra ventana = new VentanaCompra(new ProductoManager("jpa-mysql"), new VentaManager("jpa-mysql"),
				new ClienteManager("jpa-mysql"), 1L, 13L);
		ventana.setVisible(true);

//		VentanaModificarProducto frame = new VentanaModificarProducto(new ProductoManager("jpa-mysql"), 9L);
//		frame.setVisible(true);

//		JedisPool pool = new JedisPool("localhost", 6379);
//		Jedis jedis = pool.getResource();
//		jedis.hset("user", "nombre", "nico");
//		System.out.println(jedis.get("edad"));
//		List<Venta> v = venta.ultimasVentas(1L);
//		System.out.println(v);

	}

}
