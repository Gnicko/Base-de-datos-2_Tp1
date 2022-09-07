package ar.unrn.tp.jpa.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Carrito;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.modelo.Tienda;
import ar.unrn.tp.modelo.Venta;

public class VentaManager implements VentaService {

	@Override
	public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();
			Cliente cliente = em.find(Cliente.class, idCliente);
			if (cliente == null) {
				throw new RuntimeException("El cliente no existe");
			}
			Tarjeta tarjeta = em.find(Tarjeta.class, idTarjeta);
			if (!cliente.tieneTarjeta(tarjeta)) {
				throw new RuntimeException("La tarjeta no pertenece al cliente");
			}

			List<Producto> productosLista = new ArrayList<>();
			if (productos.isEmpty()) {
				throw new RuntimeException("La lista de productos no puede ser vacia");
			}
			for (Long p : productos) {
				Producto prod = em.find(Producto.class, p);
				if (prod == null) {
					throw new RuntimeException("el producto de id: " + p + " no existe");
				}
				productosLista.add(prod);
			}

			Carrito carrito = new Carrito(cliente);
			for (Producto p : productosLista) {
				carrito.agregarProducto(p);
			}
			Tienda tienda = em.find(Tienda.class, 27L);
			Venta venta = carrito.pagar(tienda.getPromocionesCompra(), tienda.getPromocionesMarca(), tarjeta);

			tienda.agregarVenta(venta);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@Override
	public float calcularMonto(List<Long> productos, Long idTarjeta) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();

			Tarjeta tarjeta = em.find(Tarjeta.class, idTarjeta);
			if (tarjeta == null) {
				throw new RuntimeException("La tarjeta no existe");
			}

			List<Producto> productosLista = new ArrayList<>();
			if (productos.isEmpty()) {
				throw new RuntimeException("La lista de productos no puede ser vacia");
			}
			for (Long p : productos) {
				Producto prod = em.find(Producto.class, p);
				if (prod == null) {
					throw new RuntimeException("el producto de id: " + p + " no existe");
				}
				productosLista.add(prod);
			}

			Carrito carrito = new Carrito();
			for (Producto p : productosLista) {
				carrito.agregarProducto(p);
			}
			Tienda tienda = em.find(Tienda.class, 27L);

			return (float) carrito.calcularMontoTotalConDescuentos(tienda.getPromocionesCompra(),
					tienda.getPromocionesMarca(), tarjeta);

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@Override
	public List ventas() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			TypedQuery<Venta> q = em.createQuery("select v from Tienda t join t.ventas v", Venta.class);

			List<Venta> ventas = q.getResultList();
			return ventas;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

	}

}
