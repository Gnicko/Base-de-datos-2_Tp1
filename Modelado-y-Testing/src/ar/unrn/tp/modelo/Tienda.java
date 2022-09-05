package ar.unrn.tp.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tienda {
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany(cascade = CascadeType.PERSIST)
	private Set<Promocion> promocionesCompra;
	@OneToMany(cascade = CascadeType.PERSIST)
	private Set<Promocion> promocionesMarca;
	@OneToMany(cascade = CascadeType.PERSIST)
	private Set<Venta> ventas;

	public Tienda() {
		this.promocionesCompra = new HashSet<>();
		this.promocionesMarca = new HashSet<>();
		this.ventas = new HashSet<>();
	}

	public void agregarPromocionCompra(Promocion<TipoTarjeta> promocionCompra) {
		if (!promocionesCompra.isEmpty()) {
			for (Promocion prom : this.promocionesCompra) {
				if ((prom.seSuperpone(promocionCompra.getFechaDesde())
						&& (prom.seSuperpone(promocionCompra.getFechaDesde())
								&& ((TipoTarjeta) prom.getTipo()).equals(promocionCompra.getTipo())))) {
					throw new RuntimeException("Ya existe un promocion activa de este tipo ");
				}
			}
		}
		this.promocionesCompra.add(promocionCompra);

	}

	public void agregarPromocionMarca(Promocion<Marca> promocionMarca) {
		for (Promocion prom : this.promocionesMarca) {
			if ((prom.estaActiva() && ((Marca) prom.getTipo()).equals(promocionMarca.getTipo()))) {
				throw new RuntimeException("Ya existe un promocion activa de este tipo");
			}
		}

		this.promocionesMarca.add(promocionMarca);
	}

	public void agregarVenta(Venta venta) {
		this.ventas.add(venta);
	}

	public Set<Promocion> getPromocionesCompra() {
		return promocionesCompra;
	}

	public Set<Promocion> getPromocionesMarca() {
		return promocionesMarca;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private void setPromocionesCompra(Set<Promocion> promocionesCompra) {
		this.promocionesCompra = promocionesCompra;
	}

	private void setPromocionesMarca(Set<Promocion> promocionesMarca) {
		this.promocionesMarca = promocionesMarca;
	}

	public Set<Venta> getVentas() {
		return ventas;
	}

	private void setVentas(Set<Venta> ventas) {
		this.ventas = ventas;
	}

}
