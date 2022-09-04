package ar.unrn.tp.modelo;

public class Producto {
	private String codigo;
	private String descripcion;
	private Categoria categoria;
	private double precio;
	private Marca marca;

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

	@Override
	public String toString() {
		return "codigo=" + codigo + ", descripcion=" + descripcion + ", " + categoria + ", precio=" + precio + ", "
				+ marca;
	}

}
