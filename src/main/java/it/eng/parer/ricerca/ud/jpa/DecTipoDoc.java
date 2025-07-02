package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The persistent class for the DEC_TIPO_DOC database table.
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "DEC_TIPO_DOC")
public class DecTipoDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoDoc;

    private String dsTipoDoc;

    private String flTipoDocPrincipale;

    private String nmTipoDoc;

    private OrgStrut orgStrut;

    public DecTipoDoc() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_TIPO_DOC")
    public Long getIdTipoDoc() {
	return this.idTipoDoc;
    }

    @Column(name = "DS_TIPO_DOC")
    public String getDsTipoDoc() {
	return this.dsTipoDoc;
    }

    @Column(name = "FL_TIPO_DOC_PRINCIPALE", columnDefinition = "char(1)")
    public String getFlTipoDocPrincipale() {
	return this.flTipoDocPrincipale;
    }

    @Column(name = "NM_TIPO_DOC")
    public String getNmTipoDoc() {
	return this.nmTipoDoc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STRUT")
    public OrgStrut getOrgStrut() {
	return this.orgStrut;
    }

}
