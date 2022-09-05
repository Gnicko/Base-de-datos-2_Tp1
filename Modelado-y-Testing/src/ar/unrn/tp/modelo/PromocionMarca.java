package ar.unrn.tp.modelo;

import java.time.LocalDate;

import javax.persistence.Embeddable;

@Embeddable
public class PromocionMarca extends Promocion<Marca> {

	public PromocionMarca(Marca marca, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
		super(marca, fechaDesde, fechaHasta, porcentaje);

	}

	@Override
	public double obtenerDescuento(Marca marca, double monto) {
		if (estaActiva() && comparar(marca)) {

			return (getPorcentaje() * monto);
		}
		return 0;
	}

}
