package ar.unrn.tp.jpa.servicios;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.PromocionCompra;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.Tienda;
import ar.unrn.tp.modelo.TipoTarjeta;

public class DescuentoManager implements DescuentoService {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	public DescuentoManager(String servicio) {
		emf = Persistence.createEntityManagerFactory(servicio);

	}

	@Override
	public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta,
			float porcentaje) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {

			tx.begin();
			Tienda tienda = em.find(Tienda.class, 27L);
			tienda.agregarPromocionCompra(new PromocionCompra(TipoTarjeta.valueOf(marcaTarjeta.toUpperCase()),
					fechaDesde, fechaHasta, (double) porcentaje));
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
	public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {

			tx.begin();
			Tienda tienda = em.find(Tienda.class, 27L);

			TypedQuery<Marca> q = em.createQuery("select m from Marca m where m.nombre=:nombre", Marca.class);
			q.setParameter("nombre", marcaProducto);
			Marca marca = q.getSingleResult();
			if (marca == null) {
				throw new RuntimeException("La marca no existe");
			}

			tienda.agregarPromocionMarca(new PromocionMarca(marca, fechaDesde, fechaHasta, (double) porcentaje));
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

	public void crearTienda() {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {

			tx.begin();
			Tienda t = new Tienda();
			em.persist(t);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

}
