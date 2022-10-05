package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class IdVenta {
	@Id
	@GeneratedValue
	int id;
	int numero;
	int anio;

	public IdVenta() {

	}

	public IdVenta(int numero, int anio) {
		this.anio = anio;
		this.numero = numero;
	}

	public int getNumero() {
		return numero;
	}

	private void setNumero(int numero) {
		this.numero = numero;
	}

	public int getAnio() {
		return anio;
	}

	private void setAnio(int anio) {
		this.anio = anio;
	}

	public int siguienteNumero() {
		this.numero += 1;
		return numero;
	}

}
