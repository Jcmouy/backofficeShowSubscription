package com.uydevs.backoffice.config;

/**
 * Application constants.
 */
public final class Constants {
	public static final String CRIPTOGRAPHIC_HASH_FUNCTION = "SHA-512";

	public static final String CARACTER_SEPARADOR_LINEA = "[ |;]";

	public static final String DEFAULT_PASS = "123";
	public static final int DEFAULT_HASH_ITERATIONS = 500000;
	public static final int PERIODO_EXPIRACION_TOKEN = 432000000;

	public static final String VALUE_SEQ = "_ID_SEQ";

	public static final String ATRIBUTO_ID = "id";
	public static final String ATRIBUTO_NAME = "nombre";
	public static final String ATRIBUTO_DESCRIPTION = "descripcion";

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String DEFAULT_LANGUAGE = "es";
	public static final String ANONYMOUS_USER = "anonymoususer";

	private Constants() {
	}
}
