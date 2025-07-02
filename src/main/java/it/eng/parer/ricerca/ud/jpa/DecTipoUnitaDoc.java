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
 * The persistent class for the DEC_TIPO_UNITA_DOC database table.
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "DEC_TIPO_UNITA_DOC")
public class DecTipoUnitaDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoUnitaDoc;

    private String dsTipoUnitaDoc;

    private String nmTipoUnitaDoc;

    private Long idStrut;

    public DecTipoUnitaDoc() {
	// hibernate
    }

    @Id
    @Column(name = "ID_TIPO_UNITA_DOC")
    public Long getIdTipoUnitaDoc() {
	return this.idTipoUnitaDoc;
    }

    @Column(name = "DS_TIPO_UNITA_DOC")
    public String getDsTipoUnitaDoc() {
	return this.dsTipoUnitaDoc;
    }

    @Column(name = "NM_TIPO_UNITA_DOC")
    public String getNmTipoUnitaDoc() {
	return this.nmTipoUnitaDoc;
    }

    @Column(name = "ID_STRUT")
    public Long getIdStrut() {
	return this.idStrut;
    }

}
