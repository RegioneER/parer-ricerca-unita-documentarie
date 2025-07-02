package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import it.eng.parer.ricerca.ud.jpa.constraint.AroUnitaDocCnts;
import it.eng.parer.ricerca.ud.jpa.converter.FlBooleanConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The persistent class for the ARO_UNITA_DOC database table.
 */
@Entity
@Table(name = "ARO_UNITA_DOC")
public class AroUnitaDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUnitaDoc;

    private BigDecimal aaKeyUnitaDoc;

    private String cdKeyUnitaDoc;

    private String cdKeyUnitaDocNormaliz;

    private String cdRegistroKeyUnitaDoc;

    private String dsMsgEsitoVerifFirme;

    private LocalDateTime dtCreazione;

    private LocalDate dtRegUnitaDoc;

    private Boolean flForzaAccettazione;

    private Boolean flForzaCollegamento;

    private Boolean flForzaConservazione;

    private Boolean flUnitaDocFirmato;

    private BigDecimal niAlleg;

    private BigDecimal niAnnessi;

    private BigDecimal niAnnot;

    private String tiConservazione;

    private String tiEsitoVerifFirme;

    private AroUnitaDocCnts.TiStatoConservazione tiStatoConservazione;

    private String tiStatoUdElencoVers;

    private Long idRegistroUnitaDoc;

    private Long idTipoUnitaDoc;

    private Long idStrut;

    private Long idUserVers;

    public AroUnitaDoc() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_UNITA_DOC")
    public Long getIdUnitaDoc() {
	return this.idUnitaDoc;
    }

    @Column(name = "AA_KEY_UNITA_DOC")
    public BigDecimal getAaKeyUnitaDoc() {
	return this.aaKeyUnitaDoc;
    }

    @Column(name = "CD_KEY_UNITA_DOC")
    public String getCdKeyUnitaDoc() {
	return this.cdKeyUnitaDoc;
    }

    @Column(name = "CD_KEY_UNITA_DOC_NORMALIZ")
    public String getCdKeyUnitaDocNormaliz() {
	return this.cdKeyUnitaDocNormaliz;
    }

    @Column(name = "CD_REGISTRO_KEY_UNITA_DOC")
    public String getCdRegistroKeyUnitaDoc() {
	return this.cdRegistroKeyUnitaDoc;
    }

    @Column(name = "DS_MSG_ESITO_VERIF_FIRME")
    public String getDsMsgEsitoVerifFirme() {
	return this.dsMsgEsitoVerifFirme;
    }

    @Column(name = "DT_CREAZIONE")
    public LocalDateTime getDtCreazione() {
	return this.dtCreazione;
    }

    @Column(name = "DT_REG_UNITA_DOC")
    public LocalDate getDtRegUnitaDoc() {
	return this.dtRegUnitaDoc;
    }

    @Convert(converter = FlBooleanConverter.class)
    @Column(name = "FL_FORZA_ACCETTAZIONE")
    public Boolean getFlForzaAccettazione() {
	return this.flForzaAccettazione;
    }

    @Convert(converter = FlBooleanConverter.class)
    @Column(name = "FL_FORZA_COLLEGAMENTO")
    public Boolean getFlForzaCollegamento() {
	return this.flForzaCollegamento;
    }

    @Convert(converter = FlBooleanConverter.class)
    @Column(name = "FL_FORZA_CONSERVAZIONE")
    public Boolean getFlForzaConservazione() {
	return this.flForzaConservazione;
    }

    @Column(name = "FL_UNITA_DOC_FIRMATO")
    public Boolean getFlUnitaDocFirmato() {
	return this.flUnitaDocFirmato;
    }

    @Column(name = "NI_ALLEG")
    public BigDecimal getNiAlleg() {
	return this.niAlleg;
    }

    @Column(name = "NI_ANNESSI")
    public BigDecimal getNiAnnessi() {
	return this.niAnnessi;
    }

    @Column(name = "NI_ANNOT")
    public BigDecimal getNiAnnot() {
	return this.niAnnot;
    }

    @Column(name = "TI_CONSERVAZIONE")
    public String getTiConservazione() {
	return this.tiConservazione;
    }

    @Column(name = "TI_ESITO_VERIF_FIRME")
    public String getTiEsitoVerifFirme() {
	return this.tiEsitoVerifFirme;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TI_STATO_CONSERVAZIONE")
    public AroUnitaDocCnts.TiStatoConservazione getTiStatoConservazione() {
	return this.tiStatoConservazione;
    }

    @Column(name = "TI_STATO_UD_ELENCO_VERS")
    public String getTiStatoUdElencoVers() {
	return this.tiStatoUdElencoVers;
    }

    @Column(name = "ID_REGISTRO_UNITA_DOC")
    public Long getIdRegistroUnitaDoc() {
	return this.idRegistroUnitaDoc;
    }

    @Column(name = "ID_TIPO_UNITA_DOC")
    public Long getIdTipoUnitaDoc() {
	return this.idTipoUnitaDoc;
    }

    @Column(name = "ID_STRUT")
    public Long getIdStrut() {
	return this.idStrut;
    }

    @Column(name = "ID_USER_VERS")
    public Long getIdUserVers() {
	return this.idUserVers;
    }

}
