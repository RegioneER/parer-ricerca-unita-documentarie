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

/**
 *
 */
package it.eng.parer.ricerca.ud.beans.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.eng.parer.ricerca.ud.beans.dto.UdDto;

@JsonInclude(Include.NON_NULL)
public class UdResponse {

    // multi request
    @JsonProperty(index = 10)
    private String path;
    @JsonProperty(index = 20)
    private String dataversamento;
    @JsonProperty(index = 30)
    private Integer totale;
    @JsonProperty(index = 40)
    private String nextpagetoken;
    @JsonProperty(index = 50)
    private List<UdDto> unitadocumentarie;

    public UdResponse() {
	super();
    }

    public UdResponse(List<UdDto> unitadocumentarie, Integer totale, String nextpagetoken,
	    String uri, String dataversamento) {
	super();
	this.unitadocumentarie = unitadocumentarie;
	this.totale = totale;
	this.path = uri;
	this.dataversamento = dataversamento;
	this.nextpagetoken = nextpagetoken;
    }

    public String getPath() {
	return path;
    }

    public Integer getTotale() {
	return totale;
    }

    public List<UdDto> getUnitadocumentarie() {
	return unitadocumentarie;
    }

    public String getNextpagetoken() {
	return nextpagetoken;
    }

    public String getDataversamento() {
	return dataversamento;
    }

}
