package it.eng.parer.ricerca.ud.jpa.constraint;

public final class AroUnitaDocCnts {

    private AroUnitaDocCnts() {
    }

    public enum TiStatoConservazione {
	AIP_DA_GENERARE, AIP_FIRMATO, AIP_GENERATO, AIP_IN_AGGIORNAMENTO, ANNULLATA, IN_ARCHIVIO,
	IN_CUSTODIA, IN_VOLUME_DI_CONSERVAZIONE, PRESA_IN_CARICO, VERSAMENTO_IN_ARCHIVIO
    }

}
