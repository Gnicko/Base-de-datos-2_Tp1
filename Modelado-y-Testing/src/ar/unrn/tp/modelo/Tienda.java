package ar.unrn.tp.modelo;

import java.util.HashSet;
import java.util.Set;

public class Tienda {
	private Set<Promocion> promocionesCompra;
	private Set<Promocion> promocionesMarca;

	public Tienda() {
		this.promocionesCompra = new HashSet<>();
		this.promocionesMarca = new HashSet<>();
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

	public Set<Promocion> getPromocionesCompra() {
		return promocionesCompra;
	}

	public Set<Promocion> getPromocionesMarca() {
		return promocionesMarca;
	}

}
