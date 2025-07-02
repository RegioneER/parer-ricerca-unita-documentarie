package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The persistent class for the APL_VALORE_PARAM_APPLIC database table.
 */
@Entity
@Table(name = "APL_VALORE_PARAM_APPLIC")
public class AplValoreParamApplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idValoreParamApplic;

    private String dsValoreParamApplic;

    private String tiAppart;

    private AplParamApplic aplParamApplic;

    private DecTipoUnitaDoc decTipoUnitaDoc;

    private OrgAmbiente orgAmbiente;

    private OrgStrut orgStrut;

    public AplValoreParamApplic() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_VALORE_PARAM_APPLIC")
    public Long getIdValoreParamApplic() {
	return this.idValoreParamApplic;
    }

    @Column(name = "DS_VALORE_PARAM_APPLIC")
    public String getDsValoreParamApplic() {
	return this.dsValoreParamApplic;
    }

    @Column(name = "TI_APPART")
    public String getTiAppart() {
	return this.tiAppart;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PARAM_APPLIC")
    public AplParamApplic getAplParamApplic() {
	return this.aplParamApplic;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_UNITA_DOC")
    public DecTipoUnitaDoc getDecTipoUnitaDoc() {
	return this.decTipoUnitaDoc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBIENTE")
    public OrgAmbiente getOrgAmbiente() {
	return this.orgAmbiente;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STRUT")
    public OrgStrut getOrgStrut() {
	return this.orgStrut;
    }
}
