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

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package it.eng.parer.ricerca.ud.beans.impl;

import static it.eng.parer.ricerca.ud.beans.utils.Costants.IDP_LOG_ACESSI_FUNCT;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.IDP_MAX_GIORNI;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.IDP_MAX_TENTATIVI_FALLITI;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.IDP_QRY_DISABLE_USER;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.IDP_QRY_REGISTRA_EVENTO_UTENTE;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.IDP_QRY_VERIFICA_DISATTIVAZIONE_UTENTE;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.SERVICE_NAME;
import static it.eng.parer.ricerca.ud.beans.utils.DateUtilsConverter.convert;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.Startup;
import it.eng.parer.idpjaas.logutils.IdpConfigLog;
import it.eng.parer.idpjaas.logutils.IdpLogger;
import it.eng.parer.idpjaas.logutils.LogDto;
import it.eng.parer.idpjaas.serverutils.AppServerInstance;
import it.eng.parer.ricerca.ud.beans.IPermissionAndAccessLogDao;
import it.eng.parer.ricerca.ud.beans.IPermissionAndAccessLogService;
import it.eng.parer.ricerca.ud.beans.exceptions.AppGenericRuntimeException;
import it.eng.parer.ricerca.ud.beans.exceptions.ErrorCategory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
public class PermissionAndAccessLogService implements IPermissionAndAccessLogService {

    private static final Logger log = LoggerFactory.getLogger(PermissionAndAccessLogService.class);

    @Inject
    IPermissionAndAccessLogDao dao;

    @Inject
    DataSource dataSource;

    @ConfigProperty(name = "quarkus.uuid")
    String instanceUUID;

    String servername;

    HashMap<String, String> mapIdpLoggerParams;

    @Startup
    protected void init() throws UnknownHostException {
	log.atDebug().log("RicercaUnitaDocumentarie - Init PermissionAndAccessLogService start");
	// calculate servername
	servername = new AppServerInstance().getMyHostAddress().getCanonicalHostName() + "/"
		+ instanceUUID;
	// load standard params from db
	mapIdpLoggerParams = loadIdpLoggerParams();
	log.atDebug().log("RicercaUnitaDocumentarie - Init PermissionAndAccessLogService complete");
    }

    @Override
    @Transactional(value = TxType.REQUIRED, rollbackOn = {
	    AppGenericRuntimeException.class })
    public boolean isUserEnabledOnService(
	    @NotBlank(message = "userId non valorizzato") String nmUserid) {
	try {
	    log.atDebug().log(
		    "RicercaUnitaDocumentarie - Verifica abilitazione utente {} al servizio {}",
		    nmUserid, SERVICE_NAME);
	    return dao.checkUserEnabledOnService(nmUserid);
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
		    .cause(e).message("Errore verifica abilitazione utente {0} su servizio {1}",
			    nmUserid, SERVICE_NAME)
		    .build();
	}

    }

    @Override
    @Transactional(value = TxType.REQUIRED, rollbackOn = {
	    AppGenericRuntimeException.class })
    public void createAccessLog(@NotBlank(message = "userId non valorizzato") String nmUserid,
	    String ipAddr) {

	try {
	    // create log
	    LogDto logDto = new LogDto();
	    logDto.setNmAttore("Ricerca Unità Documentarie WS");
	    logDto.setNmUser(nmUserid);
	    logDto.setCdIndIpClient(ipAddr);
	    logDto.setTsEvento(convert(LocalDateTime.now()));
	    // log della corretta autenticazione
	    logDto.setTipoEvento(LogDto.TipiEvento.LOGIN_OK);
	    logDto.setDsEvento("WS, login OK");
	    logDto.setServername(servername);

	    log.atDebug().log("RicercaUnitaDocumentarie - Creazione access log {}", logDto);
	    // insert access log
	    checkAndInsertAccessLog(logDto, mapIdpLoggerParams);
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
		    .cause(e).message("Errore generico").build();
	}

    }

    private HashMap<String, String> loadIdpLoggerParams() {
	return dao.loadIdpLoggerParams(IDP_LOG_ACESSI_FUNCT);
    }

    private void checkAndInsertAccessLog(LogDto logDto, HashMap<String, String> params) {
	if (ObjectUtils.isNotEmpty(params.get(IDP_QRY_REGISTRA_EVENTO_UTENTE))
		&& ObjectUtils.isNotEmpty(params.get(IDP_QRY_VERIFICA_DISATTIVAZIONE_UTENTE))
		&& ObjectUtils.isNotEmpty(params.get(IDP_QRY_DISABLE_USER))
		&& ObjectUtils.isNotEmpty(params.get(IDP_MAX_GIORNI))
		&& ObjectUtils.isNotEmpty(params.get(IDP_MAX_TENTATIVI_FALLITI))) {
	    try (Connection connection = dataSource.getConnection()) {

		IdpConfigLog icl = new IdpConfigLog();
		icl.setQryRegistraEventoUtente(params.get(IDP_QRY_REGISTRA_EVENTO_UTENTE));
		icl.setQryVerificaDisattivazioneUtente(
			params.get(IDP_QRY_VERIFICA_DISATTIVAZIONE_UTENTE));
		icl.setQryDisabilitaUtente(params.get(IDP_QRY_DISABLE_USER));
		icl.setMaxTentativi(Integer.parseInt(params.get(IDP_MAX_TENTATIVI_FALLITI)));
		icl.setMaxGiorni(Integer.parseInt(params.get(IDP_MAX_GIORNI)));

		IdpLogger idpLogger = new IdpLogger(icl);
		idpLogger.scriviLog(logDto, connection);

		log.atDebug().log("RicercaUnitaDocumentarie - Creazione access log completata",
			logDto);
	    } catch (UnknownHostException e) {
		throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
			.cause(e).message("Errore nel determinare il nome host per il server")
			.build();
	    } catch (SQLException e) {
		throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
			.cause(e).message("Errore nell'accesso ai dati di log").build();
	    }
	}
    }

}
