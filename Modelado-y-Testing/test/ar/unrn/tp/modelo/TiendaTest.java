package ar.unrn.tp.modelo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TiendaTest {

	@Test
	void validarAgregarPromociones() {

		PromocionCompra promCompra1 = new PromocionCompra(TipoTarjeta.MASTER_CARD.toString(), LocalDate.now(),
				LocalDate.now().plusDays(1), 0.08);
		PromocionCompra promCompra2 = new PromocionCompra(TipoTarjeta.MASTER_CARD.toString(),
				LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), 0.08);

		Tienda tienda = new Tienda();

		tienda.agregarPromocion(promCompra1);
		tienda.agregarPromocion(promCompra2);

		assertThrows(RuntimeException.class, () -> tienda.agregarPromocion(promCompra2));
	}

}
