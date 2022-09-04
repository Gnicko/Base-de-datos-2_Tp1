package ar.unrn.tp.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unrn.tp.modelo.Tarjeta.EstadosTarjeta;

class CarritoTest {
	private Tienda tienda;

	Tarjeta tarjeta;

	private Marca acme;
	private Marca mm;
	private Marca arcor;
	private Marca comarca;

	private Cliente cliente;

	private Carrito carrito;

	private Categoria categoria;
	private Categoria categoria2;
	private Categoria categoria3;

	private Producto producto1;
	private Producto producto2;
	private Producto producto3;
	private Producto producto4;

	@BeforeEach
	public void before() {

		tienda = new Tienda();
		acme = new Marca("Acme");
		mm = new Marca("MM");
		arcor = new Marca("Arcor");
		comarca = new Marca("Comarca");

		cliente = new Cliente("nicolas", "Gomez", "40000000", "nico@gmail.com");

		carrito = new Carrito(cliente);

		categoria = new Categoria("Deporte");
		categoria2 = new Categoria("Herramientas");
		categoria3 = new Categoria("Comida");

		producto1 = new Producto("1212", "Pelota", categoria, 100, acme);
		producto2 = new Producto("1211", "Martillo", categoria2, 100, acme);
		producto3 = new Producto("1213", "fideos", categoria3, 200, arcor);
		producto4 = new Producto("1214", "fideos", categoria3, 250, comarca);

		tarjeta = new Tarjeta(2222, 222, LocalDate.now(), LocalDate.now().plusDays(1), EstadosTarjeta.ACTIVO,
				TipoTarjeta.MASTER_CARD);
	}

	@Test
	void calcularMontoTotalConDescuento() {

		Promocion promMarca = new PromocionMarca(acme, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1),
				0.05);
		Promocion promMarca2 = new PromocionMarca(mm, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), 0.05);
		Promocion promCompra = new PromocionCompra(TipoTarjeta.MASTER_CARD, LocalDate.now().minusDays(2),
				LocalDate.now().minusDays(1), 0.08);

		tienda.agregarPromocionCompra(promCompra);
		tienda.agregarPromocionMarca(promMarca);
		tienda.agregarPromocionMarca(promMarca2);

		carrito.agregarProducto(producto1);
		carrito.agregarProducto(producto2);
		assertEquals(200, carrito.calcularMontoTotalConDescuentos(tienda.getPromocionesCompra(),
				tienda.getPromocionesMarca(), tarjeta));

	}

	@Test
	void calcularDescuentoProductosAcme() {
		Promocion promMarca = new PromocionMarca(acme, LocalDate.now(), LocalDate.now().plusDays(1), 0.05);
		Promocion promMarca2 = new PromocionMarca(mm, LocalDate.now(), LocalDate.now().plusDays(1), 0.05);

		tienda.agregarPromocionMarca(promMarca);
		tienda.agregarPromocionMarca(promMarca2);

		carrito.agregarProducto(producto1);
		carrito.agregarProducto(producto2);
		carrito.agregarProducto(producto3);
		assertEquals(390, carrito.calcularMontoTotalConDescuentos(tienda.getPromocionesCompra(),
				tienda.getPromocionesMarca(), tarjeta));
	}

	@Test
	void calcularDescuentoMedioDePagoVigente() {
		Promocion promCompra = new PromocionCompra(TipoTarjeta.MASTER_CARD, LocalDate.now().minusDays(2),
				LocalDate.now().plusDays(1), 0.08);

		tienda.agregarPromocionCompra(promCompra);

		carrito.agregarProducto(producto1);
		carrito.agregarProducto(producto2);
		carrito.agregarProducto(producto3);
		assertEquals(368, carrito.calcularMontoTotalConDescuentos(tienda.getPromocionesCompra(),
				tienda.getPromocionesMarca(), tarjeta));

	}

	@Test
	void calcularDescuentoDeProductoYTarjeta() {
		Promocion promCompra = new PromocionCompra(TipoTarjeta.MASTER_CARD, LocalDate.now().minusDays(2),
				LocalDate.now().plusDays(1), 0.08);
		Promocion promMarca2 = new PromocionMarca(comarca, LocalDate.now().minusDays(2), LocalDate.now().plusDays(1),
				0.05);

		tienda.agregarPromocionMarca(promMarca2);
		tienda.agregarPromocionCompra(promCompra);

		carrito.agregarProducto(producto1);
		carrito.agregarProducto(producto2);
		carrito.agregarProducto(producto4);
		assertEquals(402.5, carrito.calcularMontoTotalConDescuentos(tienda.getPromocionesCompra(),
				tienda.getPromocionesMarca(), tarjeta));
	}

	@Test
	void validarCreacionDeVenta() {
		carrito.agregarProducto(producto1);
		carrito.agregarProducto(producto2);
		carrito.agregarProducto(producto4);

		assertEquals(Venta.class,
				carrito.pagar(tienda.getPromocionesCompra(), tienda.getPromocionesMarca(), tarjeta).getClass());
	}
}
