package ar.unrn.tp.modelo;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PM")
public class PromocionMarca extends Promocion {

	protected PromocionMarca() {

	}

	public PromocionMarca(String marca, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
		super(fechaDesde, fechaHasta, porcentaje, marca);

	}

	@Override
	public double obtenerDescuento(String marca, double monto) {
		if (estaActiva() && getPromocion().equals(marca)) {

			return (getPorcentaje() * monto);
		}
		return 0;
	}

}
