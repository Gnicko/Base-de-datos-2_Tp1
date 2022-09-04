package ar.unrn.tp.modelo;

import java.time.LocalDate;

public class PromocionMarca extends Promocion<Marca> {
	private double porcentaje;

	public PromocionMarca(Marca marca, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
		super(marca, fechaDesde, fechaHasta);
		this.porcentaje = porcentaje;
	}

	@Override
	public double obtenerDescuento(Marca marca, double monto) {
		if (estaActiva() && comparar(marca)) {

			return (porcentaje * monto);
		}
		return 0;
	}

}
