package ar.unrn.tp.modelo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TiendaTest {

	@Test
	void validarAgregarPromociones() {

		Promocion promCompra1 = new PromocionCompra(TipoTarjeta.MASTER_CARD, LocalDate.now(),
				LocalDate.now().plusDays(1), 0.08);
		Promocion promCompra2 = new PromocionCompra(TipoTarjeta.MASTER_CARD, LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(3), 0.08);
		

		Tienda tienda = new Tienda();

		tienda.agregarPromocionCompra(promCompra1);
		tienda.agregarPromocionCompra(promCompra2);
		
		assertThrows(RuntimeException.class, () -> tienda.agregarPromocionCompra(promCompra2));
	}

}
