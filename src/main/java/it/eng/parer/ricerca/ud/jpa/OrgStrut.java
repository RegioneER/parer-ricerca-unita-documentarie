package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The persistent class for the ORG_STRUT database table.
 */
@Entity
@Cacheable
@Table(name = "ORG_STRUT")
public class OrgStrut implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idStrut;

    private String dsStrut;

    private String cdStrutNormaliz;

    private String nmStrut;

    private OrgEnte orgEnte;

    public OrgStrut() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_STRUT")
    public Long getIdStrut() {
	return this.idStrut;
    }

    @Column(name = "DS_STRUT")
    public String getDsStrut() {
	return this.dsStrut;
    }

    @Column(name = "CD_STRUT_NORMALIZ")
    public String getCdStrutNormaliz() {
	return this.cdStrutNormaliz;
    }

    @Column(name = "NM_STRUT")
    public String getNmStrut() {
	return this.nmStrut;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE")
    public OrgEnte getOrgEnte() {
	return this.orgEnte;
    }

}
