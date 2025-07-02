package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The persistent class for the IAM_ABIL_ORGANIZ database table.
 */
@Entity
@Cacheable
@Table(name = "IAM_ABIL_ORGANIZ")
public class IamAbilOrganiz implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAbilOrganiz;

    private String flOrganizDefault;

    private Long idOrganizApplic;

    private IamUser iamUser;

    private List<IamAutorServ> iamAutorServs = new ArrayList<>();

    public IamAbilOrganiz() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_ABIL_ORGANIZ")
    public Long getIdAbilOrganiz() {
	return this.idAbilOrganiz;
    }

    @Column(name = "FL_ORGANIZ_DEFAULT", columnDefinition = "char(1)")
    public String getFlOrganizDefault() {
	return this.flOrganizDefault;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public Long getIdOrganizApplic() {
	return this.idOrganizApplic;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM")
    public IamUser getIamUser() {
	return this.iamUser;
    }

    @OneToMany(cascade = {
	    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
	    CascadeType.REMOVE }, mappedBy = "iamAbilOrganiz")
    public List<IamAutorServ> getIamAutorServs() {
	return this.iamAutorServs;
    }
}
