package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import it.eng.parer.ricerca.ud.jpa.constraint.AroDocCnts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The persistent class for the ARO_DOC database table.
 *
 */
@Entity
@Table(name = "ARO_DOC")
public class AroDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idDoc;

    private String cdKeyDocVers;

    private String dsMsgEsitoVerifFirme;

    private LocalDate dtCreazione;

    private String flDocFirmato;

    private BigDecimal idStrut;

    private String nmSistemaMigraz;

    private String tiConservazione;

    private String tiCreazione;

    private AroDocCnts.TiDoc tiDoc;

    private String tiEsitoVerifFirme;

    private LocalDate tsStatoElencoVers;

    private DecTipoDoc decTipoDoc;

    private Long idUnitaDoc;

    private BigDecimal pgDoc;

    public AroDoc() {
	// hibernate
    }

    @Id
    @Column(name = "ID_DOC")
    public Long getIdDoc() {
	return this.idDoc;
    }

    @Column(name = "CD_KEY_DOC_VERS")
    public String getCdKeyDocVers() {
	return this.cdKeyDocVers;
    }

    @Column(name = "DS_MSG_ESITO_VERIF_FIRME")
    public String getDsMsgEsitoVerifFirme() {
	return this.dsMsgEsitoVerifFirme;
    }

    @Column(name = "DT_CREAZIONE")
    public LocalDate getDtCreazione() {
	return this.dtCreazione;
    }

    @Column(name = "FL_DOC_FIRMATO", columnDefinition = "char(1)")
    public String getFlDocFirmato() {
	return this.flDocFirmato;
    }

    @Column(name = "ID_STRUT")
    public BigDecimal getIdStrut() {
	return this.idStrut;
    }

    @Column(name = "NM_SISTEMA_MIGRAZ")
    public String getNmSistemaMigraz() {
	return this.nmSistemaMigraz;
    }

    @Column(name = "TI_CONSERVAZIONE")
    public String getTiConservazione() {
	return this.tiConservazione;
    }

    @Column(name = "TI_CREAZIONE")
    public String getTiCreazione() {
	return this.tiCreazione;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TI_DOC")
    public AroDocCnts.TiDoc getTiDoc() {
	return this.tiDoc;
    }

    @Column(name = "TI_ESITO_VERIF_FIRME")
    public String getTiEsitoVerifFirme() {
	return this.tiEsitoVerifFirme;
    }

    @Column(name = "TS_STATO_ELENCO_VERS")
    public LocalDate getTsStatoElencoVers() {
	return this.tsStatoElencoVers;
    }

    @Column(name = "ID_UNITA_DOC")
    public Long getIdUnitaDoc() {
	return this.idUnitaDoc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DOC")
    public DecTipoDoc getDecTipoDoc() {
	return this.decTipoDoc;
    }

    @Column(name = "PG_DOC")
    public BigDecimal getPgDoc() {
	return this.pgDoc;
    }

    public void setPgDoc(BigDecimal pgDoc) {
	this.pgDoc = pgDoc;
    }

}
