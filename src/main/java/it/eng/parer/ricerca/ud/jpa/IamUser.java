/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.parer.ricerca.ud.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The persistent class for the IAM_USER database table.
 *
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "IAM_USER")
public class IamUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUserIam;

    private String flAttivo;

    private String flContrIp;

    private String flUserAdmin;

    private String nmUserid;

    private String tipoUser;

    private List<IamAbilOrganiz> iamAbilOrganizs = new ArrayList<>();

    public IamUser() {/* Hibernate */
    }

    @Id
    @Column(name = "ID_USER_IAM")
    public Long getIdUserIam() {
	return this.idUserIam;
    }

    @Column(name = "FL_ATTIVO", columnDefinition = "char(1)")
    public String getFlAttivo() {
	return this.flAttivo;
    }

    @Column(name = "FL_CONTR_IP", columnDefinition = "char(1)")
    public String getFlContrIp() {
	return this.flContrIp;
    }

    @Column(name = "FL_USER_ADMIN", columnDefinition = "char(1)")
    public String getFlUserAdmin() {
	return this.flUserAdmin;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
	return this.nmUserid;
    }

    @Column(name = "TIPO_USER")
    public String getTipoUser() {
	return this.tipoUser;
    }

    @OneToMany(mappedBy = "iamUser")
    public List<IamAbilOrganiz> getIamAbilOrganizs() {
	return this.iamAbilOrganizs;
    }

}
