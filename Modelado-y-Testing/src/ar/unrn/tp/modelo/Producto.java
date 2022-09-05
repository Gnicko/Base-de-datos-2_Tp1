package ar.unrn.tp.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Producto {
	@Id
	@GeneratedValue
	private Long id;

	private String codigo;
	private String descripcion;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Categoria categoria;
	private double precio;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Marca marca;

	protected Producto() {

	}

	public Producto(String codigo, String descripcion, Categoria categoria, double precio, Marca marca)
			throws RuntimeException {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		if (categoria == null)
			throw new RuntimeException("categoria no puede ser null");
		this.categoria = categoria;
		this.precio = precio;
		if (marca == null)
			throw new RuntimeException("marca no puede ser null");
		this.marca = marca;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public double getPrecio() {
		return precio;
	}

	public Marca getMarca() {
		return marca;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "codigo=" + codigo + ", descripcion=" + descripcion + ", " + categoria + ", precio=" + precio + ", "
				+ marca;
	}

}
