package ar.unrn.tp.modelo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ClienteTest {

	private String nombre;
	private String apellido;
	private String dni;
	private String email;

	public static Object[] argumentos() {
		return new Object[][] { { "", "Gomez", "40000000", "nico@gmail.com" },
				{ "Nico", "", "400000", "nico@gmail.com" }, { "Nico", "Gomez", "", "nico@gmail.com" },
				{ "Nico", "Gomez", "400000", "nico@gmail" } };
	}

	@ParameterizedTest
	@MethodSource("argumentos")
	void cliente(String nombre, String apellido, String dni, String email) {
		assertThrows(RuntimeException.class, () -> new Cliente(nombre, apellido, dni, email));
	}

}
