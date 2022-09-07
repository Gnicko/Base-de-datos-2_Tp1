package ar.unrn.tp.jpa.servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.modelo.TipoTarjeta;

public class ClienteManager implements ClienteService {

	@Override
	public void crearCliente(String nombre, String apellido, String dni, String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();
			Cliente c = new Cliente(nombre, apellido, dni, email);
			em.persist(c);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@Override
	public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			Cliente c = em.getReference(Cliente.class, idCliente);
			c.setNombre(nombre);
			c.setApellido(apellido);
			c.setDni(dni);
			c.setEmail(email);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

	}

	@Override
	public void agregarTarjeta(Long idCliente, String nro, String marca) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			Cliente c = em.find(Cliente.class, idCliente);
			if (c == null) {
				throw new RuntimeException("El cliente no existe");
			}
			Tarjeta t = new Tarjeta(nro, TipoTarjeta.valueOf(marca.toUpperCase()));
			if (c.tieneTarjeta(t)) {
				throw new RuntimeException("El cliente ya posee esta tarjeta");
			}
			c.agregarTarjeta(t);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

	}

	@Override
	public List listarTarjetas(Long idCliente) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			TypedQuery<Tarjeta> q = em.createQuery("select t from Cliente c join c.tarjetas t where c.id=:id",
					Tarjeta.class);
			q.setParameter("id", idCliente);
			List<Tarjeta> tarjetas = q.getResultList();
			return tarjetas;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

}
