package it.eng.parer.ricerca.ud.beans;

import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.beans.model.UdResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface IFindUnitadocService {

    /**
     * Ricerca unità documentarie con filtro applicato
     *
     * @param userId identificativo utente
     * @param filter filtro di ricerca ud
     * @param uri    URI invocato
     *
     * @return lista unità documentarie
     *
     */
    UdResponse findUdByQuertStr(@NotBlank(message = "userId non valorizzato") String userId,
	    @NotNull(message = "filter non valorizzato") UdFilter filter, String uri);

}
