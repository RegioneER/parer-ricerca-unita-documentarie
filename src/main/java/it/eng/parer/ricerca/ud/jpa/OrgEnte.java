package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the ORG_ENTE database table.
 */
@Entity
@Cacheable
@Table(name = "ORG_ENTE")
public class OrgEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEnte;

    private String dsEnte;

    private String nmEnte;

    private String cdEnteNormaliz;

    private OrgAmbiente orgAmbiente;

    private List<OrgStrut> orgStruts = new ArrayList<>();

    public OrgEnte() {
	// hibernate
    }

    @Id
    @Column(name = "ID_ENTE")
    public Long getIdEnte() {
	return this.idEnte;
    }

    @Column(name = "DS_ENTE")
    public String getDsEnte() {
	return this.dsEnte;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
	return this.nmEnte;
    }

    @Column(name = "CD_ENTE_NORMALIZ")
    public String getCdEnteNormaliz() {
	return this.cdEnteNormaliz;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBIENTE")
    public OrgAmbiente getOrgAmbiente() {
	return this.orgAmbiente;
    }

    @OneToMany(mappedBy = "orgEnte")
    @XmlTransient
    public List<OrgStrut> getOrgStruts() {
	return this.orgStruts;
    }

}
