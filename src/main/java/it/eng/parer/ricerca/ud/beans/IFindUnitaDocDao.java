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

import java.util.Optional;
import java.util.stream.Stream;

import it.eng.parer.ricerca.ud.beans.dto.TipoDatoAbilDto;
import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.jpa.AroUnitaDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoUnitaDoc;

public interface IFindUnitaDocDao {

    /**
     * Restituisce uno stream di {@link AroUnitaDoc} secondo i filtri impostati
     *
     * @param idStrut       id struttura (mandatory)
     * @param idResgistroUd id registro (opzionale)
     * @param idTipoUd      id tipo unità documentaria (opzionale)
     * @param idUserVers    id user versante (opzionale)
     * @param filter        filtro ottenuta da {@link UdFilter}
     *
     * @return stream con unità documetarie 0..N
     */
    Stream<AroUnitaDoc> findUnitadocsByQueryStr(Long idStrut, Optional<Long> idResgistroUd,
	    Optional<Long> idTipoUd, Optional<Long> idUserVers, UdFilter filter);

    /**
     * Restituisce la pk della struttura abilitata su utente invocante
     *
     * @param userId nome utente
     * @param amb    nome ambiente
     * @param ente   nome ente
     * @param strut  nome struttura
     *
     * @return pk
     */
    Long findIdStrutByUserAbilAndQueryStr(String userId, String amb, String ente, String strut);

    /**
     * Restituisce la pk del registro data la struttura abilitata sull'utente invocante e il
     * registro fornito da query string
     *
     * @param idStrut  id struttura
     * @param registro nome del registro da query string
     *
     * @return pk (Optional)
     */
    Optional<Long> findIdRegistroUdByQueryStr(Long idStrut, String registro);

    /**
     * Restituisce la pk del tipo unità documentaria data la struttura abilitata sull'utente
     * invocante e il tipo fornito da query string
     *
     * @param idStrut id struttura
     * @param tipoUd  nome tipo unità documentaria
     *
     * @return pk (Optional)
     */
    Optional<Long> findIdTipoUdByQueryStr(Long idStrut, String tipoUd);

    /**
     * Restitusce una {@link DecTipoUnitaDoc} data la struttura e la pk del tipo unità documentaria
     *
     * @param idStrut  id struttura
     * @param idTipoUd id tipo unità documentaria
     *
     * @return entity {@link DecTipoUnitaDoc}
     */
    DecTipoUnitaDoc findDecTipoUnitaDocByUd(Long idStrut, Long idTipoUd);

    /**
     * Restituisce un {@link DecTipoDoc} data la struttura e la pk dell'unità documentaria
     *
     * @param idStrut    id struttura
     * @param idUnitaDoc id unità documentaria
     *
     * @return entity {@link DecTipoDoc}
     */
    DecTipoDoc findDecTipoDocByUd(Long idStrut, Long idUnitaDoc);

    /**
     * Restituisce lo stream con una lista 0..N di classe dato e id data la struttura
     *
     * @param idStrut id struttura
     * @param userid  nome utente
     *
     * @return lista con POJO {@link TipoDatoAbilDto}
     */
    Stream<TipoDatoAbilDto> findTipoDatoApplicByIdStrut(Long idStrut, String userid);

    /**
     * @param userid nome utente
     *
     * @return pk
     */
    Optional<Long> findIdUserVersByQueryStr(String userid);

}
