package ar.unrn.tp.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Tienda {
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_tienda")
	private Set<Promocion> promociones;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_tienda")
	private Set<Venta> ventas;

	public Tienda() {
		this.promociones = new HashSet<>();

		this.ventas = new HashSet<>();
	}

	public void agregarPromocion(Promocion promocion) {
		if (!promociones.isEmpty()) {
			for (Promocion prom : this.promociones) {
				if (prom.equals(promocion)
						|| ((prom.seSuperpone(promocion.getFechaDesde()) || prom.seSuperpone(promocion.getFechaDesde()))
								&& prom.getPromocion().equals(promocion.getPromocion()))) {
					throw new RuntimeException("Ya existe un promocion activa de este tipo ");
				}
			}
		}
		this.promociones.add(promocion);

	}

	public void agregarVenta(Venta venta) {
		this.ventas.add(venta);
	}

	public Set<Promocion> getPromociones() {
		return promociones;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private void setPromociones(Set<Promocion> promociones) {
		this.promociones = promociones;
	}

	public Set<Venta> getVentas() {
		return ventas;
	}

	private void setVentas(Set<Venta> ventas) {
		this.ventas = ventas;
	}

	@Override
	public String toString() {
		return "Tienda [id=" + id + ", promociones=" + promociones + ", ventas=" + ventas + "]";
	}

}
