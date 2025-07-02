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
 * The persistent class for the IAM_ABIL_TIPO_DATO database table.
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "IAM_ABIL_TIPO_DATO")
public class IamAbilTipoDato implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAbilTipoDato;

    private Long idTipoDatoApplic;

    private String nmClasseTipoDato;

    private IamAbilOrganiz iamAbilOrganiz;

    public IamAbilTipoDato() {/* Hibernate */
    }

    @Id
    public Long getIdAbilTipoDato() {
	return this.idAbilTipoDato;
    }

    @Column(name = "ID_TIPO_DATO_APPLIC")
    public Long getIdTipoDatoApplic() {
	return this.idTipoDatoApplic;
    }

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
	return this.nmClasseTipoDato;
    }

    // bi-directional many-to-one association to IamAbilOrganiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ABIL_ORGANIZ")
    public IamAbilOrganiz getIamAbilOrganiz() {
	return this.iamAbilOrganiz;
    }

}
