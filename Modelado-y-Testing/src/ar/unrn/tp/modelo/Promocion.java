package ar.unrn.tp.modelo;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import ar.unrn.tp.utilidades.ConversorFechas;

@Entity
@Inheritance
@DiscriminatorColumn(name = "tipo_promocion")
public abstract class Promocion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date fechaDesde;
	private Date fechaHasta;
	private double porcentaje;
	private String promocion;

	protected Promocion() {

	}

	Promocion(LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje, String promocion) {
		if (!validarFechas(fechaDesde, fechaHasta)) {
			throw new RuntimeException("La fecha de fin debe ser despues de la fecha de inicio");
		}
		this.fechaDesde = ConversorFechas.convertirADate(fechaDesde);
		this.fechaHasta = ConversorFechas.convertirADate(fechaHasta);

		this.porcentaje = porcentaje;
		this.promocion = promocion.toUpperCase();

	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getPromocion() {
		return promocion;
	}

	private void setPromocion(String promocion) {
		this.promocion = promocion.toUpperCase();
	}

	public LocalDate getFechaDesde() {
		return ConversorFechas.convertirALocalDate(fechaDesde);
	}

	public LocalDate getFechaHasta() {
		return ConversorFechas.convertirALocalDate(fechaHasta);
	}

	public boolean estaActiva() {
		LocalDate actual = LocalDate.now();
		LocalDate desde = ConversorFechas.convertirALocalDate(fechaDesde);
		LocalDate hasta = ConversorFechas.convertirALocalDate(fechaHasta);

		if (desde.isBefore(actual) && hasta.isAfter(actual) || desde.equals(actual) || hasta.equals(actual)) {

			return true;
		}

		return false;
	}

	public boolean seSuperpone(LocalDate fecha) {
		LocalDate desde = ConversorFechas.convertirALocalDate(fechaDesde);
		LocalDate hasta = ConversorFechas.convertirALocalDate(fechaHasta);
		if (fecha.isBefore(desde) || fecha.isAfter(hasta)) {
			return false;
		}

		return true;
	}

	private boolean validarFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		if (fechaDesde.isAfter(fechaHasta) || fechaDesde.isEqual(fechaHasta)) {

			return false;
		}
		return true;
	}

	protected double getPorcentaje() {
		return porcentaje;
	}

	private void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	private void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	private void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaDesde, fechaHasta, id, porcentaje, promocion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promocion other = (Promocion) obj;
		return Objects.equals(fechaDesde, other.fechaDesde) && Objects.equals(fechaHasta, other.fechaHasta)
				&& Objects.equals(id, other.id)
				&& Double.doubleToLongBits(porcentaje) == Double.doubleToLongBits(other.porcentaje)
				&& Objects.equals(promocion, other.promocion);
	}

	@Override
	public String toString() {
		return "Promocion [id=" + id + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", porcentaje="
				+ porcentaje + ", promocion=" + promocion + "]";
	}

	public abstract double obtenerDescuento(String tipo, double monto);
}
