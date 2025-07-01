package it.eng.parer.ricerca.ud.beans.dto;

import java.io.Serializable;

public class TipoDatoAbilDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // fields
    private String nmClasseTipoDato;
    private Long idTipoDatoApplic;

    public TipoDatoAbilDto() {
	super();
    }

    public TipoDatoAbilDto(String nmClasseTipoDato, Long idTipoDatoApplic) {
	super();
	this.nmClasseTipoDato = nmClasseTipoDato;
	this.idTipoDatoApplic = idTipoDatoApplic;
    }

    public String getNmClasseTipoDato() {
	return nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
	this.nmClasseTipoDato = nmClasseTipoDato;
    }

    public Long getIdTipoDatoApplic() {
	return idTipoDatoApplic;
    }

    public void setIdTipoDatoApplic(Long idTipoDatoApplic) {
	this.idTipoDatoApplic = idTipoDatoApplic;
    }

}