package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductoVendido {
	@Id
	private Long id;
	private String codigo;
	private String descripcion;
	private double precio;

	protected ProductoVendido() {

	}

	public ProductoVendido(Long id, String codigo, String descripcion, double precio) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.precio = precio;
	}

	private double getPrecio() {
		return precio;
	}

	private void setPrecio(double precio) {
		this.precio = precio;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private String getCodigo() {
		return codigo;
	}

	private void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	private String getDescripcion() {
		return descripcion;
	}

	private void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "ProductoVendido [id=" + id + ", codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}

}
