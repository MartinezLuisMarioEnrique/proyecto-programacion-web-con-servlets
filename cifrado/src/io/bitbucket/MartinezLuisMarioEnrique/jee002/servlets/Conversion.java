package io.bitbucket.MartinezLuisMarioEnrique.jee002.servlets;

public final class Conversion {
	// String a byte
	public static byte aByte(String entrada) {
		byte salida = 0;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Byte.parseByte(entrada);
		} catch (NumberFormatException e) {
			salida = 0;
		}
		return salida;
	}

	// String a byte con valor por default
	public static byte aByte(String entrada, byte porDefault) {
		byte salida = porDefault;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Byte.parseByte(entrada);
		} catch (NumberFormatException e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a short
	public static short aShort(String entrada) {
		short salida = 0;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Short.parseShort(entrada);
		} catch (NumberFormatException e) {
			salida = 0;
		}
		return salida;
	}

	// String a short con valor por default
	public static short aShort(String entrada, short porDefault) {
		short salida = porDefault;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Short.parseShort(entrada);
		} catch (NumberFormatException e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a int
	public static int aInt(String entrada) {
		int salida = 0;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Integer.parseInt(entrada);
		} catch (NumberFormatException e) {
			salida = -1;
		}
		return salida;
	}

	public static boolean esEntero(String entrada) {
		boolean esEntero = true;
		try {
			Integer.parseInt(entrada);
			esEntero = true;
		} catch (NumberFormatException e) {
			esEntero = false;
		}
		return esEntero;
	}

	// String a int con valor por default
	public static int aInt(String entrada, int porDefault) {
		int salida = porDefault;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Integer.parseInt(entrada);
		} catch (NumberFormatException e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a long
	public static long aLong(String entrada) {
		long salida = 0;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Long.parseLong(entrada);
		} catch (NumberFormatException e) {
			salida = 0;
		}
		return salida;
	}

	// String a long con valor por default
	public static long aLong(String entrada, long porDefault) {
		long salida = porDefault;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Long.parseLong(entrada);
		} catch (NumberFormatException e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a float
	public static float aFloat(String entrada) {
		float salida = 0;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Float.parseFloat(entrada);
		} catch (NumberFormatException e) {
			salida = 0;
		}
		return salida;
	}

	// String a float con valor por default
	public static float aFloat(String entrada, float porDefault) {
		float salida = porDefault;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Float.parseFloat(entrada);
		} catch (NumberFormatException e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a double
	public static double aDouble(String entrada) {
		double salida = 0;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Double.parseDouble(entrada);
		} catch (NumberFormatException e) {
			salida = 0;
		}
		return salida;
	}

	// String a double con valor por default
	public static double aDouble(String entrada, double porDefault) {
		double salida = porDefault;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Double.parseDouble(entrada);
		} catch (NumberFormatException e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a boolean
	public static boolean aBoolean(String entrada) {
		boolean salida = false;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Boolean.valueOf(entrada);
		} catch (ClassCastException e) {
			salida = false;
		}
		return salida;
	}

	// String a boolean con valor por default
	public static boolean aBoolean(String entrada, boolean porDefault) {
		boolean salida = porDefault;
		if (entrada == null) {
			return salida;
		}
		try {
			salida = Boolean.valueOf(entrada);
		} catch (ClassCastException e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a char especificando la posicion de la cadena
	// String a char especificando la posicion de la cadena
	public static char aChar(String entrada, int posicion) {
		char salida = 0;
		if (entrada == null || posicion < 0) {
			return salida;
		}
		try {
			salida = entrada.charAt(posicion);
		} catch (Exception e) {
			salida = 0;
		}
		return salida;
	}

	// String a char especificando la posicion de la cadena y un valor por default
	public static char aChar(String entrada, char porDefault, int posicion) {
		char salida = porDefault;
		if (entrada == null || posicion < 0) {
			return salida;
		}
		try {
			salida = entrada.charAt(posicion);
		} catch (Exception e) {
			salida = porDefault;
		}
		return salida;
	}

	// String a char convirtiendo toda la cadena en un arreglo de caracteres
	public static char[] aChar(String entrada) {
		char[] salida = {};

		if (entrada == null) {
			return salida;
		}

		salida = entrada.toCharArray();
		return salida;
	}
	
	public static boolean esTexto(String entrada) {
		for(int i = 0; i<entrada.length(); i++) {
			if(Character.isDigit(entrada.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
