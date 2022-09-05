package ar.unrn.tp.modelo;

import java.time.LocalDate;

import javax.persistence.Embeddable;

@Embeddable
public class PromocionCompra extends Promocion<TipoTarjeta> {

	protected PromocionCompra() {

	}

	public PromocionCompra(TipoTarjeta tarjeta, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
		super(tarjeta, fechaDesde, fechaHasta, porcentaje);

	}

	@Override
	public double obtenerDescuento(TipoTarjeta tarjeta, double monto) {
		if (estaActiva() && comparar(tarjeta)) {
			return (getPorcentaje() * monto);

		}
		return 0;
	}

}
