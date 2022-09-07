package ar.unrn.tp.api;

import java.util.List;

public interface ProductoService {
	// validar que sea una categoría existente y que codigo no se repita
	void crearProducto(String codigo, String descripcion, float precio, Long IdCategoria, Long IdMarca);

	// validar que sea un producto existente
	void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long idCategoria,
			Long idMarca);

	// Devuelve todos los productos
	List listarProductos();
}
