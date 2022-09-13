package ar.unrn.tp.jpa.servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;

public class ProductoManager implements ProductoService {
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	public ProductoManager(String servicio) {
		emf = Persistence.createEntityManagerFactory(servicio);

	}

	@Override
	public void crearProducto(String codigo, String descripcion, float precio, Long idCategoria, Long idMarca) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {
			tx.begin();

			TypedQuery<Producto> qp = em.createQuery("select p from Producto p where p.codigo=:codigo", Producto.class);
			qp.setParameter("codigo", codigo);
			if (!qp.getResultList().isEmpty()) {
				throw new RuntimeException("El codigo del producto ya existe");
			}

			TypedQuery<Marca> q = em.createQuery("select m from Marca m where m.id=:id", Marca.class);
			q.setParameter("id", idMarca);
			Marca marca = q.getSingleResult();
			if (marca == null) {
				throw new RuntimeException("La marca no existe");
			}

			TypedQuery<Categoria> q1 = em.createQuery("select c from Categoria c where c.id=:id", Categoria.class);
			q1.setParameter("id", idCategoria);
			Categoria categoria = q1.getSingleResult();
			if (categoria == null) {
				throw new RuntimeException("La categoria no existe");
			}
			Producto p = new Producto(codigo, descripcion, categoria, precio, marca);
			em.persist(p);

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
	public void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long idCategoria,
			Long idMarca) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {
			tx.begin();

			Producto p = em.find(Producto.class, idProducto);

			TypedQuery<Producto> qp = em.createQuery("select p from Producto p where p.codigo=:codigo", Producto.class);
			qp.setParameter("codigo", codigo);

			if (!qp.getResultList().isEmpty() && !p.getCodigo().equals(codigo)) {
				throw new RuntimeException("Ya Existe este codigo de producto");
			}

			Marca marca = em.find(Marca.class, idMarca);
//			TypedQuery<Marca> q = em.createQuery("select m from Marca m where m.id=:id", Marca.class);
//			q.setParameter("id", marca.getId());
//			Marca marcaAux = q.getSingleResult();
//			if (marcaAux == null) {
//				throw new RuntimeException("La marca no existe");
//			}

//			TypedQuery<Categoria> q1 = em.createQuery("select c from Categoria c where c.id=:id", Categoria.class);
//			q1.setParameter("id", categoria.getId());
//			Categoria categoriaAux = q1.getSingleResult();
//			if (categoriaAux == null) {
//				throw new RuntimeException("La categoria no existe");
//			}
			Categoria categoria = em.find(Categoria.class, idCategoria);

			p.setCategoria(categoria);
			p.setCodigo(codigo);
			p.setDescripcion(descripcion);
			p.setMarca(marca);
			p.setPrecio(precio);

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
	public List listarProductos() {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {
			tx.begin();
			TypedQuery<Producto> q = em.createQuery("select p from Producto p", Producto.class);

			List<Producto> productos = q.getResultList();
			return productos;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	public void crearCategoria(String nombre) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {

			tx.begin();
			Categoria c = new Categoria(nombre);
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

	public void crearMarca(String nombre) {
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {

			tx.begin();
			Marca m = new Marca(nombre);
			em.persist(m);
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
