package ar.unrn.tp.modelo;

import java.time.LocalDate;
import java.util.Date;

public abstract class Promocion<T> {

	private Date fechaDesde;
	private Date fechaHasta;

	public enum Estados {
		ACTIVO, INACTIVO
	}

	private Estados estado;
	private T tipo;

	Promocion(T tipo, LocalDate fechaDesde, LocalDate fechaHasta) {
		if (!validarFechas(fechaDesde, fechaHasta)) {
			throw new RuntimeException("La fecha de fin debe ser despues de la fecha de inicio");
		}
		this.fechaDesde = ConversorFechas.convertirADate(fechaDesde);
		this.fechaHasta = ConversorFechas.convertirADate(fechaHasta);
		this.estado = Estados.ACTIVO;
		this.tipo = tipo;

	}

	protected boolean comparar(T tipo) {
		return this.tipo.equals(tipo);
	}

	public T getTipo() {
		return this.tipo;
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
			System.out.println("activa");
			return true;
		}
		System.out.println("inactiva");
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

	public abstract double obtenerDescuento(T tipo, double monto);
}
