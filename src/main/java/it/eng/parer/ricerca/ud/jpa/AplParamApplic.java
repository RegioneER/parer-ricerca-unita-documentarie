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
