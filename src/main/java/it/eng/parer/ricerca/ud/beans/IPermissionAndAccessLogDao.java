package it.eng.parer.ricerca.ud.beans;

import java.util.HashMap;

public interface IPermissionAndAccessLogDao {

    /**
     * Verifica se data la userd è abilitata al servizio censito.
     *
     * @param nmUserid userid utente che invoca il servizio
     *
     * @return true se abilitato / false altrimenti
     */
    boolean checkUserEnabledOnService(String nmUserid);

    /**
     * Caricamento dei parametri di configurazione per la gestione dei log accessi
     *
     * @param nmFunction nome funzione
     *
     * @return mappa chiave / valore
     */
    HashMap<String, String> loadIdpLoggerParams(String nmFunction);

}
