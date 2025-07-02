/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

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
