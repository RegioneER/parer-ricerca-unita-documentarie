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

package it.eng.parer.ricerca.ud.beans.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.eng.parer.ricerca.ud.jpa.AroUnitaDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoUnitaDoc;

@JsonInclude(Include.NON_NULL)
public class UdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // fields
    private String registro;
    @Schema(type = SchemaType.NUMBER, format = "int32")
    private BigDecimal anno;
    private String numero;
    private String tipologia;
    @Schema(type = SchemaType.STRING, required = false, examples = "date", format = "dd-MM-yyyy", implementation = LocalDate.class)
    private LocalDate dataunitadocumentaria;
    private String tipodocprincipale;
    @Schema(type = SchemaType.NUMBER, format = "int32")
    private BigDecimal nrallegati;
    @Schema(type = SchemaType.NUMBER, format = "int32")
    private BigDecimal nrannessi;
    @Schema(type = SchemaType.NUMBER, format = "int32")
    private BigDecimal nrannotazioni;
    private Boolean forzaaccettazione;
    private Boolean forzaconservazione;
    @Schema(type = SchemaType.STRING, required = false, examples = "date-time", format = "dd-MM-yyyy hh:mm:ss", implementation = LocalDateTime.class)
    private LocalDateTime dataversamento;
    private Boolean firmato;
    private String esitofirme;
    private String descresitofirme;
    private String statogenindiceaip;
    private String statoconservazione;
    private String oggetto;

    public UdDto() {
	super();
    }

    public UdDto(AroUnitaDoc ud, DecTipoUnitaDoc tipoUd, DecTipoDoc tiDocPrincipal) {
	// build from entities
	this.registro = ud.getCdRegistroKeyUnitaDoc();
	this.anno = ud.getAaKeyUnitaDoc();
	this.numero = ud.getCdKeyUnitaDoc();
	this.dataunitadocumentaria = ud.getDtRegUnitaDoc();
	this.nrallegati = ud.getNiAlleg();
	this.nrannessi = ud.getNiAnnessi();
	this.nrannotazioni = ud.getNiAnnot();
	this.forzaaccettazione = ud.getFlForzaAccettazione();
	this.forzaconservazione = ud.getFlForzaConservazione();
	this.dataversamento = ud.getDtCreazione();
	this.firmato = ud.getFlUnitaDocFirmato();
	this.esitofirme = ud.getTiEsitoVerifFirme();
	this.descresitofirme = ud.getDsMsgEsitoVerifFirme();
	this.statogenindiceaip = ud.getTiStatoUdElencoVers();
	this.statoconservazione = ud.getTiStatoConservazione().name();
	this.oggetto = ud.getDlOggettoUnitaDoc();
	//
	this.tipologia = tipoUd.getNmTipoUnitaDoc();
	this.tipodocprincipale = tiDocPrincipal.getNmTipoDoc();
    }

    public String getRegistro() {
	return registro;
    }

    public BigDecimal getAnno() {
	return anno;
    }

    public String getNumero() {
	return numero;
    }

    public String getTipologia() {
	return tipologia;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public LocalDate getDataunitadocumentaria() {
	return dataunitadocumentaria;
    }

    public String getTipodocprincipale() {
	return tipodocprincipale;
    }

    public BigDecimal getNrallegati() {
	return nrallegati;
    }

    public BigDecimal getNrannessi() {
	return nrannessi;
    }

    public BigDecimal getNrannotazioni() {
	return nrannotazioni;
    }

    public Boolean getForzaaccettazione() {
	return forzaaccettazione;
    }

    public Boolean getForzaconservazione() {
	return forzaconservazione;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime getDataversamento() {
	return dataversamento;
    }

    public Boolean getFirmato() {
	return firmato;
    }

    public String getEsitofirme() {
	return esitofirme;
    }

    public String getDescresitofirme() {
	return descresitofirme;
    }

    public String getStatogenindiceaip() {
	return statogenindiceaip;
    }

    public String getStatoconservazione() {
	return statoconservazione;
    }

    public String getOggetto() {
	return oggetto;
    }

}
