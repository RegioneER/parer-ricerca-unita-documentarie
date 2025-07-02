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
