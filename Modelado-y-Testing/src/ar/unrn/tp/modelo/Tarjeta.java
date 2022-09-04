package ar.unrn.tp.modelo;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Tarjeta {
	private int numero;
	private int codigo;
	private Date fechaDesde;
	private Date fechaHasta;
	private TipoTarjeta tipoTarjeta;
	public EstadosTarjeta estado = EstadosTarjeta.ACTIVO;

	public enum EstadosTarjeta {
		ACTIVO, INACTIVO
	}

	Tarjeta(int numero, int codigo, LocalDate fechaDesde, LocalDate fechaHasta, EstadosTarjeta estadoTarjeta,
			TipoTarjeta tipoTarjeta) {
		this.numero = numero;
		this.codigo = codigo;
		this.fechaDesde = ConversorFechas.convertirADate(fechaDesde);
		this.fechaHasta = ConversorFechas.convertirADate(fechaHasta);
		this.estado = estadoTarjeta;
		this.tipoTarjeta = tipoTarjeta;
	}

	public boolean validarTarjeta() {
		return estado == EstadosTarjeta.ACTIVO ? true : false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	public TipoTarjeta getTipoTarjeta() {
		return tipoTarjeta;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarjeta other = (Tarjeta) obj;
		return numero == other.numero;
	}

}
