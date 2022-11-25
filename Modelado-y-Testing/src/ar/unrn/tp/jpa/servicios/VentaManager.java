package ar.unrn.tp.jpa.servicios;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Carrito;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.IdVenta;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.modelo.Tienda;
import ar.unrn.tp.modelo.Venta;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class VentaManager implements VentaService {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	public VentaManager(String servicio) {
		this.emf = Persistence.createEntityManagerFactory(servicio);

	}

	@Override
	public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
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
			Tienda tienda = em.find(Tienda.class, 13L);
			Venta venta = carrito.pagar(tienda.getPromociones(), tarjeta);
			int anio = LocalDate.now().getYear();
			TypedQuery<IdVenta> q = em.createQuery("from IdVenta where anio=:anio", IdVenta.class);
			q.setParameter("anio", anio);
			q.setLockMode(LockModeType.PESSIMISTIC_WRITE);

			List<IdVenta> idVentas = q.getResultList();
			int idNumero = 0;

			if (idVentas.isEmpty()) {
				IdVenta idVenta = new IdVenta(1, anio);
				idNumero = 1;
				em.persist(idVenta);
			} else {
				idNumero = idVentas.get(0).siguienteNumero();
			}

			String id = idNumero + "-" + anio;

			venta.setId(id);
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
		JedisPool pool = new JedisPool("localhost", 6379);
		Jedis jedis = pool.getResource();
		jedis.hdel("user:" + idCliente, "ventas");
	}

	@Override
	public float calcularMonto(List<Long> productos, Long idTarjeta) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
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
			Tienda tienda = em.find(Tienda.class, 13L);

			return (float) carrito.calcularMontoTotalConDescuentos(tienda.getPromociones(), tarjeta);

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
		em = emf.createEntityManager();
		tx = em.getTransaction();
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

	@Override
	public List<Venta> ultimasVentas(Long id) {
		em = emf.createEntityManager();
		tx = em.getTransaction();

		List<Venta> ventas = new ArrayList<>();
		JedisPool pool = new JedisPool("localhost", 6379);
		Jedis jedis = pool.getResource();
		String cache = jedis.hget("user:" + id, "ventas");
		Gson gson = new Gson();
		if (cache == null) {

			try {
				tx.begin();
				TypedQuery<Venta> q = em.createQuery(
						" select v from Venta v where v.cliente.id=:id ORDER BY v.fecha DESC", Venta.class);
				q.setParameter("id", id);
				List<Venta> v = q.getResultList();
				if (v.isEmpty()) {
					throw new RuntimeException("No hay ventas para el cliente");
				}
				// iterar mientras tenga elementos y obtener los primeros 3
				Iterator<Venta> i = v.iterator();
				int cont = 1;
				while (i.hasNext() && cont < 4) {
					ventas.add(i.next());
					cont++;
				}

				String json = gson.toJson(ventas);
				jedis.hset("user:" + id, "ventas", json);

			} catch (Exception e) {
				tx.rollback();
				throw new RuntimeException(e);
			} finally {
				if (em != null && em.isOpen())
					em.close();
			}
		} else {
			Type tipoVenta = new TypeToken<List<Venta>>() {
			}.getType();
			ventas = gson.fromJson(cache, tipoVenta);
		}
		return ventas;
	}

}
