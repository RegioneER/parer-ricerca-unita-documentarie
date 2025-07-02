package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The persistent class for the ORG_AMBIENTE database table.
 */
@Entity
@Cacheable
@Table(name = "ORG_AMBIENTE")
public class OrgAmbiente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAmbiente;

    private String dsAmbiente;

    private String nmAmbiente;

    private List<OrgEnte> orgEntes = new ArrayList<>();

    public OrgAmbiente() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_AMBIENTE")
    public Long getIdAmbiente() {
	return this.idAmbiente;
    }

    @Column(name = "DS_AMBIENTE")
    public String getDsAmbiente() {
	return this.dsAmbiente;
    }

    @Column(name = "NM_AMBIENTE")
    public String getNmAmbiente() {
	return this.nmAmbiente;
    }

    @OneToMany(mappedBy = "orgAmbiente")
    public List<OrgEnte> getOrgEntes() {
	return this.orgEntes;
    }
}
