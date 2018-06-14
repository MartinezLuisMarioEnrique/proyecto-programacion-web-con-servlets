package io.bitbucket.martinezluismarioenrique.jee002.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Console {

	private static String marcaDeTiempo() {
		LocalDateTime lDT = LocalDateTime.now();
		DateTimeFormatter oP = DateTimeFormatter.ofPattern("H:m:s:S");
		return lDT.format(oP);
	}

	public static void println(String sujeto, String mensaje) {
		System.out.println("["+ marcaDeTiempo() +"] " + sujeto + " : " + mensaje);
	}

	public static void println(String sujeto, int mensaje) {
		System.out.println("["+ marcaDeTiempo() +"] " + sujeto + " : " + mensaje);
	}

	public static void println(String sujeto, boolean mensaje) {
		System.out.println("["+ marcaDeTiempo() +"] " + sujeto + " : " + mensaje);
	}

}
