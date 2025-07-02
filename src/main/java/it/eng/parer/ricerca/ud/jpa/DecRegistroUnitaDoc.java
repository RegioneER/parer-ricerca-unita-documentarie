package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The persistent class for the DEC_REGISTRO_UNITA_DOC database table.
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "DEC_REGISTRO_UNITA_DOC")
public class DecRegistroUnitaDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idRegistroUnitaDoc;

    private String cdRegistroNormaliz;

    private String cdRegistroUnitaDoc;

    private Long idStrut;

    public DecRegistroUnitaDoc() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_REGISTRO_UNITA_DOC")
    public Long getIdRegistroUnitaDoc() {
	return this.idRegistroUnitaDoc;
    }

    @Column(name = "CD_REGISTRO_UNITA_DOC")
    public String getCdRegistroUnitaDoc() {
	return this.cdRegistroUnitaDoc;
    }

    @Column(name = "CD_REGISTRO_NORMALIZ")
    public String getCdRegistroNormaliz() {
	return this.cdRegistroNormaliz;
    }

    @Column(name = "ID_STRUT")
    public Long getIdStrut() {
	return this.idStrut;
    }

}
