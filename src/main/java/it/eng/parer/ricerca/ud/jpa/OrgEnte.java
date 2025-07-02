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

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the ORG_ENTE database table.
 */
@Entity
@Cacheable
@Table(name = "ORG_ENTE")
public class OrgEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEnte;

    private String dsEnte;

    private String nmEnte;

    private String cdEnteNormaliz;

    private OrgAmbiente orgAmbiente;

    private List<OrgStrut> orgStruts = new ArrayList<>();

    public OrgEnte() {
	// hibernate
    }

    @Id
    @Column(name = "ID_ENTE")
    public Long getIdEnte() {
	return this.idEnte;
    }

    @Column(name = "DS_ENTE")
    public String getDsEnte() {
	return this.dsEnte;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
	return this.nmEnte;
    }

    @Column(name = "CD_ENTE_NORMALIZ")
    public String getCdEnteNormaliz() {
	return this.cdEnteNormaliz;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBIENTE")
    public OrgAmbiente getOrgAmbiente() {
	return this.orgAmbiente;
    }

    @OneToMany(mappedBy = "orgEnte")
    @XmlTransient
    public List<OrgStrut> getOrgStruts() {
	return this.orgStruts;
    }

}
