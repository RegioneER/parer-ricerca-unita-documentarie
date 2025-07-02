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
 * The persistent class for the APL_PARAM_APPLIC database table.
 */
@Entity
@Cacheable
@Table(name = "APL_PARAM_APPLIC")
public class AplParamApplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idParamApplic;

    private String nmParamApplic;

    private String tiParamApplic;

    private String tiValoreParamApplic;

    private String flAppartApplic;

    private List<AplValoreParamApplic> aplValoreParamApplics = new ArrayList<>();

    public AplParamApplic() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_PARAM_APPLIC")
    public Long getIdParamApplic() {
	return this.idParamApplic;
    }

    @Column(name = "NM_PARAM_APPLIC")
    public String getNmParamApplic() {
	return this.nmParamApplic;
    }

    @Column(name = "TI_PARAM_APPLIC")
    public String getTiParamApplic() {
	return this.tiParamApplic;
    }

    @OneToMany(mappedBy = "aplParamApplic")
    public List<AplValoreParamApplic> getAplValoreParamApplics() {
	return this.aplValoreParamApplics;
    }

    @Column(name = "TI_VALORE_PARAM_APPLIC")
    public String getTiValoreParamApplic() {
	return tiValoreParamApplic;
    }

    @Column(name = "FL_APPART_APPLIC", columnDefinition = "char(1)")
    public String getFlAppartApplic() {
	return this.flAppartApplic;
    }

}
