package ar.unrn.tp.modelo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tarjeta {
	@Id
	@GeneratedValue
	private Long id;

	private String numero;

	private TipoTarjeta tipoTarjeta;

	protected Tarjeta() {

	}

	public Tarjeta(String numero, TipoTarjeta tipoTarjeta) {
		this.numero = numero;

		this.tipoTarjeta = tipoTarjeta;
	}

	public TipoTarjeta getTipoTarjeta() {
		return tipoTarjeta;
	}

	private String getNumero() {
		return numero;
	}

	private void setNumero(String numero) {
		this.numero = numero;
	}

	private void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
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
		return Objects.equals(numero, other.numero);
	}

	@Override
	public String toString() {
		return "Tarjeta [numero=" + numero + ", tipoTarjeta=" + tipoTarjeta + "]";
	}

}
