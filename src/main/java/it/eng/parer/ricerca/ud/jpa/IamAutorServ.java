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
 * The persistent class for the IAM_AUTOR_SERV database table.
 */
@Entity
@Cacheable
@Table(name = "IAM_AUTOR_SERV")
public class IamAutorServ implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAutorServ;

    private String nmServizioWeb;

    private IamAbilOrganiz iamAbilOrganiz;

    public IamAutorServ() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_AUTOR_SERV")
    public Long getIdAutorServ() {
	return this.idAutorServ;
    }

    public void setIdAutorServ(Long idAutorServ) {
	this.idAutorServ = idAutorServ;
    }

    @Column(name = "NM_SERVIZIO_WEB")
    public String getNmServizioWeb() {
	return this.nmServizioWeb;
    }

    public void setNmServizioWeb(String nmServizioWeb) {
	this.nmServizioWeb = nmServizioWeb;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ABIL_ORGANIZ")
    public IamAbilOrganiz getIamAbilOrganiz() {
	return this.iamAbilOrganiz;
    }

    public void setIamAbilOrganiz(IamAbilOrganiz iamAbilOrganiz) {
	this.iamAbilOrganiz = iamAbilOrganiz;
    }

}
