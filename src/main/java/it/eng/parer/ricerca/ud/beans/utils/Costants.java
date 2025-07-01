package it.eng.parer.ricerca.ud.beans.utils;

public class Costants {

    private Costants() {
	throw new IllegalStateException("Utility class");
    }

    // nome servizio
    public static final String SERVICE_NAME = "RicercaUnitaDocumentarieMach";
    // IDP log accessi
    public static final String IDP_LOG_ACESSI_FUNCT = "Gestione utenti";
    // Costanti per il log dei login ws e la disattivazione automatica utenti
    public static final String IDP_MAX_TENTATIVI_FALLITI = "MAX_TENTATIVI_FALLITI";
    public static final String IDP_MAX_GIORNI = "MAX_GIORNI";
    public static final String IDP_QRY_DISABLE_USER = "QRY_DISABLE_USER";
    public static final String IDP_QRY_VERIFICA_DISATTIVAZIONE_UTENTE = "QRY_VERIFICA_DISATTIVAZIONE_UTENTE";
    public static final String IDP_QRY_REGISTRA_EVENTO_UTENTE = "QRY_REGISTRA_EVENTO_UTENTE";

    // URI endpoint
    public static final String URL_ADMIN_BASE = "/admin";
    public static final String URL_API_BASE = "/api";

    public static final String RESOURCE_INFOS = "/infos";
    public static final String RESOURCE_UD = "/unitadocumentarie";

    //
    public static final String URL_GET_UD = URL_API_BASE + RESOURCE_UD;
    public static final String URL_GET_INFOS = URL_ADMIN_BASE + RESOURCE_INFOS;
    public static final String URL_GET_STATUS = "/status";

    public static final String MDC_LOG_UUID = "log_uuid";

    // error code
    public static final String COD_ERR_INTERNAL = "GENERIC-ERROR";
    public static final String COD_ERR_BADREQ = "BAD-REQUEST-ERROR";
    public static final String COD_PERM_INTERNAL = "PERMISSION-ERROR";

    public enum OrderType {
	ASC, DESC
    }

    public enum NmClasseTipoDato {
	REGISTRO, TIPO_UNITA_DOC, TIPO_DOC
    }

}
