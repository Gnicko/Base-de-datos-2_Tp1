package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;

public interface ProductoService {
	// validar que sea una categoría existente y que codigo no se repita
	void crearProducto(String codigo, String descripcion, float precio, Long IdCategoria, Long IdMarca);

	// validar que sea un producto existente
	void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long idCategoria,
			Long idMarca, Long version);

	// Devuelve todos los productos
	List<Producto> listarProductos();

	Producto buscarProducto(Long id);

	void crearMarca(String nombre);

	void crearCategoria(String nombre);

	List<Categoria> listarCategorias();

	List<Marca> listarMarcas();
}
