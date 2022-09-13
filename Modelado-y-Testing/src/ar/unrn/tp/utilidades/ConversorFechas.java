package ar.unrn.tp.utilidades;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ConversorFechas {
	
	public static LocalDate convertirALocalDate(Date date) {
		return date.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
	}

	public static Date convertirADate(LocalDate fecha) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		return Date.from(fecha.atStartOfDay(defaultZoneId).toInstant());
	}
}
