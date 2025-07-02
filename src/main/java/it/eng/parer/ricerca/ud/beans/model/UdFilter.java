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

import static it.eng.parer.ricerca.ud.beans.utils.DateUtilsConverter.convert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import it.eng.parer.ricerca.ud.beans.utils.Costants.OrderType;
import it.eng.parer.ricerca.ud.beans.utils.PageTokenUtils;
import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;

public class UdFilter {

    // standard filter-based
    private String amb;
    private String ente;
    private String strut;
    private BigDecimal anno;
    private String registro;
    private String numero;
    private LocalDate dtVersDa;
    private LocalDate dtVersA;
    private LocalDate dtUdDa;
    private LocalDate dtUdA;
    private String tipoUd;
    private String userid;
    private String dataversamento = OrderType.ASC.name().toLowerCase(); // default
    private LocalDateTime ultimoDtVersUd;
    private Integer limite;
    // page-cursor-based
    private String nextPageToken;
    private List<Long> nonIds = new ArrayList<>();

    // internal use (logic)
    private boolean paginated = false; // default

    public UdFilter() {
	super();
    }

    public UdFilter(UdFilterBuilder builder) {
	super();
	this.amb = builder.amb;
	this.ente = builder.ente;
	this.strut = builder.strut;
	this.anno = builder.anno;
	this.registro = builder.registro;
	this.numero = builder.numero;
	this.dtVersDa = builder.dtVersDa;
	this.dtVersA = builder.dtVersA;
	this.dtUdDa = builder.dtUdDa;
	this.dtUdA = builder.dtUdA;
	this.tipoUd = builder.tipoUd;
	this.userid = builder.userid;
	this.dataversamento = builder.dataversamento;
	this.ultimoDtVersUd = builder.ultimoDtVersUd;
	this.limite = builder.limite;
	this.paginated = builder.paginated;
	this.nonIds = builder.nonIds;
	this.nextPageToken = builder.nextPageToken;
    }

    public static UdFilterBuilder builder() {
	return new UdFilterBuilder();
    }

    public UdFilter(UdQuery query) {
	super();
	// normalizing ...
	// mandatory
	this.amb = StringUtils.trim(query.amb);
	this.ente = StringUtils.trim(query.ente);
	this.strut = StringUtils.trim(query.strut);
	this.anno = BigDecimal.valueOf(query.anno);
	// optional
	if (query.registro.isPresent()) {
	    this.registro = StringUtils.trim(query.registro.get());
	}
	if (query.numero.isPresent()) {
	    this.numero = StringUtils.trim(query.numero.get());
	}
	if (Objects.nonNull(query.dtversda)) {
	    this.dtVersDa = convert(query.dtversda).toLocalDate();
	}
	if (Objects.nonNull(query.dtversa)) {
	    this.dtVersA = convert(query.dtversa).toLocalDate();
	}
	if (Objects.nonNull(query.dtudda)) {
	    this.dtUdDa = convert(query.dtudda).toLocalDate();
	}
	if (Objects.nonNull(query.dtuda)) {
	    this.dtUdA = convert(query.dtuda).toLocalDate();
	}
	if (query.tipoud.isPresent()) {
	    this.tipoUd = StringUtils.trim(query.tipoud.get());
	}
	if (query.dataversamento.isPresent()) {
	    this.dataversamento = query.dataversamento.get().toLowerCase();
	}
	if (query.userid.isPresent()) {
	    this.userid = query.userid.get();
	}
	if (Objects.nonNull(query.limite)) {
	    this.limite = query.limite;
	}
	if (query.nextpagetoken.isPresent()) {
	    this.nextPageToken = PageTokenUtils.decodeAndDecompressToken(query.nextpagetoken.get());
	    this.paginated = true; // pagination case
	}
    }

    public String getAmb() {
	return amb;
    }

    public String getEnte() {
	return ente;
    }

    public String getStrut() {
	return strut;
    }

    public BigDecimal getAnno() {
	return anno;
    }

    public String getRegistro() {
	return registro;
    }

    public String getNumero() {
	return numero;
    }

    public LocalDate getDtVersDa() {
	return dtVersDa;
    }

    public LocalDate getDtVersA() {
	return dtVersA;
    }

    public LocalDate getDtUdDa() {
	return dtUdDa;
    }

    public LocalDate getDtUdA() {
	return dtUdA;
    }

    public String getTipoUd() {
	return tipoUd;
    }

    public String getUserid() {
	return userid;
    }

    public String getDataversamento() {
	return dataversamento;
    }

    public LocalDateTime getUltimoDtVersUd() {
	return ultimoDtVersUd;
    }

    public Integer getLimite() {
	return limite;
    }

    public boolean isPaginated() {
	return paginated;
    }

    public String getNextPage() {
	return nextPageToken;
    }

    public List<Long> getNonIds() {
	return nonIds;
    }

    public void updateUltimoDtVersUd(LocalDateTime ultimoDtVersUd) {
	this.ultimoDtVersUd = ultimoDtVersUd;
    }

    public void updateNonIds(List<Long> nonIds) {
	this.nonIds = nonIds;
    }

    /*
     * Utilizzata per generare la query string per token pagination + regular expression vedere
     * UdFilterParsers
     */
    public String stdQueryString() {
	return (amb != null ? "&amb=" + amb : "") + (ente != null ? "&ente=" + ente : "")
		+ (strut != null ? "&strut=" + strut : "") + (anno != null ? "&anno=" + anno : "")
		+ (registro != null ? "&registro=" + registro : "")
		+ (numero != null ? "&numero=" + numero : "")
		+ (dtVersDa != null ? "&dtVersDa=" + dtVersDa : "")
		+ (dtVersA != null ? "&dtVersA=" + dtVersA : "")
		+ (dtUdDa != null ? "&dtUdDa=" + dtUdDa : "")
		+ (dtUdA != null ? "&dtUdA=" + dtUdA : "")
		+ (tipoUd != null ? "&tipoUd=" + tipoUd : "")
		+ (userid != null ? "&userid=" + userid : "")
		+ (dataversamento != null ? "&dataversamento=" + dataversamento : "")
		+ (limite != null ? "&limite=" + limite : "")
		+ (nextPageToken != null ? "&nextPageToken=" + nextPageToken : "")
		+ (ultimoDtVersUd != null ? "&ultimoDtVersUd=" + ultimoDtVersUd : "")
		+ (nonIds != null ? "&nonIds=" + StringUtils.join(nonIds, ',') : "");
    }

    @Override
    public String toString() {
	return (amb != null ? "amb=" + amb + ", " : "")
		+ (ente != null ? "ente=" + ente + ", " : "")
		+ (strut != null ? "strut=" + strut + ", " : "")
		+ (anno != null ? "anno=" + anno + ", " : "")
		+ (registro != null ? "registro=" + registro + ", " : "")
		+ (numero != null ? "numero=" + numero + ", " : "")
		+ (dtVersDa != null ? "dtVersDa=" + dtVersDa + ", " : "")
		+ (dtVersA != null ? "dtVersA=" + dtVersA + ", " : "")
		+ (dtUdDa != null ? "dtUdDa=" + dtUdDa + ", " : "")
		+ (dtUdA != null ? "dtUdA=" + dtUdA + ", " : "")
		+ (tipoUd != null ? "tipoUd=" + tipoUd + ", " : "")
		+ (userid != null ? "userid=" + userid + ", " : "")
		+ (dataversamento != null ? "dataversamento=" + dataversamento + ", " : "")
		+ (ultimoDtVersUd != null ? "ultimoDtVersUd=" + ultimoDtVersUd + ", " : "")
		+ (limite != null ? "limite=" + limite + ", " : "")
		+ (nextPageToken != null ? "nextPageToken=" + nextPageToken + ", " : "")
		+ (nonIds != null ? "nonIds=" + nonIds + ", " : "") + "paginated=" + paginated;
    }

    public static class UdFilterBuilder {

	private String amb;
	private String ente;
	private String strut;
	private BigDecimal anno;
	private String registro;
	private String numero;
	private LocalDate dtVersDa;
	private LocalDate dtVersA;
	private LocalDate dtUdDa;
	private LocalDate dtUdA;
	private String tipoUd;
	private String userid;
	private String dataversamento = OrderType.ASC.name().toLowerCase(); // default
	private LocalDateTime ultimoDtVersUd;
	private Integer limite;
	private boolean paginated = false; // default
	// page-cursor-based
	private String nextPageToken;
	private List<Long> nonIds = new ArrayList<>();

	public UdFilter build() {
	    return new UdFilter(this);
	}

	public UdFilterBuilder amb(String amb) {
	    this.setAmb(amb);
	    return this;
	}

	private void setAmb(String amb) {
	    Objects.requireNonNull(amb, "Ambiente non valorizzato");
	    this.amb = amb;
	}

	public UdFilterBuilder ente(String ente) {
	    this.setEnte(ente);
	    return this;
	}

	private void setEnte(String ente) {
	    Objects.requireNonNull(ente, "Ente non valorizzato");
	    this.ente = ente;
	}

	public UdFilterBuilder strut(String strut) {
	    this.setStrut(strut);
	    return this;
	}

	private void setStrut(String strut) {
	    Objects.requireNonNull(strut, "Struttura non valorizzata");
	    this.strut = strut;
	}

	public UdFilterBuilder anno(BigDecimal anno) {
	    this.setAnno(anno);
	    return this;
	}

	private void setAnno(BigDecimal anno) {
	    Objects.requireNonNull(anno, "Anno non valorizzato");
	    this.anno = anno;
	}

	public UdFilterBuilder registro(String registro) {
	    this.setRegistro(registro);
	    return this;
	}

	private void setRegistro(String registro) {
	    this.registro = registro;
	}

	public UdFilterBuilder numero(String numero) {
	    this.setNumero(numero);
	    return this;
	}

	private void setNumero(String numero) {
	    this.numero = numero;
	}

	public UdFilterBuilder dtVersDa(LocalDate dtVersDa) {
	    this.setDtVersDa(dtVersDa);
	    return this;
	}

	private void setDtVersDa(LocalDate dtVersDa) {
	    this.dtVersDa = dtVersDa;
	}

	public UdFilterBuilder dtVersA(LocalDate dtVersA) {
	    this.setDtVersA(dtVersA);
	    return this;
	}

	private void setDtVersA(LocalDate dtVersA) {
	    this.dtVersA = dtVersA;
	}

	public UdFilterBuilder dtUdDa(LocalDate dtUdDa) {
	    this.setDtUdDa(dtUdDa);
	    return this;
	}

	private void setDtUdDa(LocalDate dtUdDa) {
	    this.dtUdDa = dtUdDa;
	}

	public UdFilterBuilder dtUdA(LocalDate dtUdA) {
	    this.setDtUdA(dtUdA);
	    return this;
	}

	private void setDtUdA(LocalDate dtUdA) {
	    this.dtUdA = dtUdA;
	}

	public UdFilterBuilder tipoUd(String tipoUd) {
	    this.setTipoUd(tipoUd);
	    return this;
	}

	private void setTipoUd(String tipoUd) {
	    this.tipoUd = tipoUd;
	}

	public UdFilterBuilder userid(String userid) {
	    this.setUserid(userid);
	    return this;
	}

	private void setUserid(String userid) {
	    this.userid = userid;
	}

	public UdFilterBuilder dataversamento(String dataversamento) {
	    this.setDataversamento(dataversamento);
	    return this;
	}

	private void setDataversamento(String dataversamento) {
	    this.dataversamento = dataversamento;
	}

	public UdFilterBuilder ultimoDtVersUd(LocalDateTime ultimoDtVersUd) {
	    this.setUltimoDtVersUd(ultimoDtVersUd);
	    return this;
	}

	private void setUltimoDtVersUd(LocalDateTime ultimoDtVersUd) {
	    Objects.requireNonNull(ultimoDtVersUd, "Ultimo data versamento non valorizzata");
	    this.ultimoDtVersUd = ultimoDtVersUd;
	}

	public UdFilterBuilder limite(Integer limite) {
	    this.setLimite(limite);
	    return this;
	}

	private void setLimite(Integer limite) {
	    this.limite = limite;
	}

	public UdFilterBuilder paginated(boolean paginated) {
	    this.setPaginated(paginated);
	    return this;
	}

	public boolean isPaginated() {
	    return paginated;
	}

	private void setPaginated(boolean paginated) {
	    this.paginated = paginated;
	}

	public UdFilterBuilder nonIds(List<Long> nonIds) {
	    this.setNonIds(nonIds);
	    return this;
	}

	private void setNonIds(List<Long> nonIds) {
	    if (nonIds.isEmpty()) {
		throw new IllegalArgumentException("Elemento non può essere vuoto");
	    }
	    this.nonIds = nonIds;
	}

	public UdFilterBuilder nextpagetoken(String nextPageToken) {
	    this.setNextPageToken(nextPageToken);
	    return this;
	}

	private void setNextPageToken(String nextPageToken) {
	    this.nextPageToken = nextPageToken;
	}

    }

}
