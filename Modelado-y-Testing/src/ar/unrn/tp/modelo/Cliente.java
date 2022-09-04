package ar.unrn.tp.modelo;

import java.util.HashSet;
import java.util.Set;

public class Cliente {
	private String dni;
	private String nombre;
	private String Apellido;
	private String email;
	private Set<Tarjeta> tarjetas;

	public Cliente(String nombre, String apellido, String dni, String email, Set<Tarjeta> tarjetas) {
		if (dni == null) {
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
		this.tarjetas = tarjetas;
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

}
