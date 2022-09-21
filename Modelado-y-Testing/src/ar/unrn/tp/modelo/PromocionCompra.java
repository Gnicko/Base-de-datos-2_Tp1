package ar.unrn.tp.modelo;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PC")
public class PromocionCompra extends Promocion {

	protected PromocionCompra() {

	}

	public PromocionCompra(String tarjeta, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
		super(fechaDesde, fechaHasta, porcentaje, tarjeta);

	}

	@Override
	public double obtenerDescuento(String tarjeta, double monto) {
		if (estaActiva() && getPromocion().equals(tarjeta)) {
			return (getPorcentaje() * monto);

		}
		return 0;
	}

}
