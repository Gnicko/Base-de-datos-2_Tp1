package ar.unrn.tp.modelo;

import java.time.LocalDate;

public class PromocionCompra extends Promocion<TipoTarjeta> {
	private double porcentaje;

	PromocionCompra(TipoTarjeta tarjeta, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
		super(tarjeta, fechaDesde, fechaHasta);
		this.porcentaje = porcentaje;
	}

	@Override
	public double obtenerDescuento(TipoTarjeta tarjeta, double monto) {
		if (estaActiva() && comparar(tarjeta)) {
			return (porcentaje * monto);

		}
		return 0;
	}

}
