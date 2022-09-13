package ar.unrn.tp.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Unique;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	private Long id;
	@Unique
	private String dni;
	private String nombre;
	private String Apellido;
	private String email;
	@OneToMany(cascade = CascadeType.PERSIST)
	private Set<Tarjeta> tarjetas;

	protected Cliente() {

	}

	public Cliente(String nombre, String apellido, String dni, String email) {

		if (esDatoNulo(dni) || esDatoVacio(dni)) {
			throw new RuntimeException("Se requiere DNI valido");
		}
		this.dni = dni;
		if (esDatoNulo(nombre) || esDatoVacio(nombre)) {
			throw new RuntimeException("Se requiere nombre valido");
		}
		this.nombre = nombre;
		if (esDatoNulo(apellido) || esDatoVacio(apellido)) {
			throw new RuntimeException("Se requiere apellido valido");
		}
		this.Apellido = apellido;
		if (!validarEmail(email)) {
			throw new RuntimeException("Se requiere email valido");
		}
		this.email = email;
		this.tarjetas = new HashSet<>();

	}

	public boolean tieneTarjeta(Tarjeta t) {
		return this.tarjetas.contains(t);
	}

	public void agregarTarjeta(Tarjeta tarjeta) throws RuntimeException {
		if (tarjetas.contains(tarjeta))
			throw new RuntimeException("La tarjeta ya esta cargada");
		tarjetas.add(tarjeta);
	}

	public String getDni() {
		return dni;
	}

	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}

	private boolean esDatoNulo(Object dato) {
		return dato == null;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean validarEmail(String email) {
		return email.matches(
				"^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

	}

	public String getEmail() {
		return email;
	}

	public Set<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	private String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEmail(String email) {
		if (!validarEmail(email)) {
			throw new RuntimeException("Se requiere email valido");
		}
		this.email = email;
	}

	private void setTarjetas(Set<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", Apellido=" + Apellido + ", email="
				+ email + ", tarjetas=" + tarjetas + "]";
	}

}
