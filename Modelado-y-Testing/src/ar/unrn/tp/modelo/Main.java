package ar.unrn.tp.modelo;

import java.time.LocalDate;

import ar.unrn.tp.modelo.Tarjeta.EstadosTarjeta;

public class Main {

	public static void main(String[] args) {
		Cliente cliente1 = new Cliente("nicolas", "Gomez", "40000000", "nico@gmail.com");
		Tarjeta tarjeta = new Tarjeta(2222, 222, LocalDate.now(), LocalDate.now().plusDays(1), EstadosTarjeta.ACTIVO,
				TipoTarjeta.MASTER_CARD);
		cliente1.agregarTarjeta(tarjeta);

		Marca acme = new Marca("Acme");
		Marca mm = new Marca("MM");

		Categoria categoria = new Categoria("Deporte");
		Categoria categoria2 = new Categoria("Herramientas");

		Producto producto1 = new Producto("1212", "Pelota", categoria, 100, acme);
		Producto producto2 = new Producto("1211", "Martillo", categoria2, 100, acme);

		Carrito carrito = new Carrito(cliente1);
		carrito.agregarProducto(producto1);
		carrito.agregarProducto(producto2);

		Promocion promMarca = new PromocionMarca(acme, LocalDate.now(), LocalDate.now().plusDays(1), 0.05);
		Promocion promMarca2 = new PromocionMarca(mm, LocalDate.now(), LocalDate.now().plusDays(1), 0.05);
		Promocion promCompra = new PromocionCompra(TipoTarjeta.MASTER_CARD, LocalDate.now(),
				LocalDate.now().plusDays(1), 0.08);

		Tienda tienda = new Tienda();
		tienda.agregarPromocionMarca(promMarca);
		tienda.agregarPromocionMarca(promMarca2);
		tienda.agregarPromocionCompra(promCompra);

		Venta venta = carrito.pagar(tienda.getPromocionesCompra(), tienda.getPromocionesMarca(), tarjeta);
		System.out.println(venta.toString());
	}

}
