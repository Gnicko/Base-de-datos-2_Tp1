package ar.unrn.tp.modelo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Categoria {
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;

	protected Categoria() {

	}

	public Categoria(String nombre) {
		this.nombre = nombre;
	}

	public Categoria(Long id, String nombre) {
		this.nombre = nombre;
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	public String getNombre() {
		return this.nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Categoria : " + nombre;
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

}
