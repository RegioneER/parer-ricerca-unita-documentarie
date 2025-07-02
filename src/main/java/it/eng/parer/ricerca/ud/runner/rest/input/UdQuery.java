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
package it.eng.parer.ricerca.ud.runner.rest.input;

import java.util.Date;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import it.eng.parer.ricerca.ud.beans.validator.UdQueryDateRangeValidator.ValidUdQueryDateRange;
import it.eng.parer.ricerca.ud.beans.validator.UdQueryLimiteValidator.ValidUdQueryLimite;
import it.eng.parer.ricerca.ud.beans.validator.UdQueryMandatoriesValidator.ValidUdQueryMandatories;
import it.eng.parer.ricerca.ud.beans.validator.UdQueryOrdinamentoValidator.ValidUdQueryOrdinamento;
import it.eng.parer.ricerca.ud.runner.rest.annotation.DateFormat;
import it.eng.parer.ricerca.ud.runner.rest.annotation.EnsureDigit;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.QueryParam;

/* custom validator */
@ValidUdQueryDateRange(message = "Range di date impostate non corretto, necessario impostare entrambi i valori nel formato 'DD-MM-YYYY' di tipo Da-A con data Da minore/uguale di data A")
@ValidUdQueryMandatories(message = "Nella chiamata devono essere presenti i parametri obbligatori amb, ente, struttura ed anno o, in alternativa, il solo parametro nextpagetoken a cui associare il valore ottenuto da una precedente invocazione del servizio")
@ValidUdQueryOrdinamento(message = "E' stato indicato un valore per dataversamento non ammissibile. I valori ammessi sono: asc / desc")
@ValidUdQueryLimite(message = "Valore del parametro limite non corretto. Accettato solo un valore maggiore o uguale ad 1 che non deve superare il massimo consentito di {0}")
public class UdQuery {

    @Parameter(name = "amb", description = "codice ambiente", allowEmptyValue = false, example = "PARER", schema = @Schema(type = SchemaType.STRING), required = true, in = ParameterIn.QUERY)
    @QueryParam("amb")
    public String amb;

    @Parameter(name = "ente", description = "codice ente", allowEmptyValue = false, example = "ACER", schema = @Schema(type = SchemaType.STRING), required = true, in = ParameterIn.QUERY)
    @QueryParam("ente")
    public String ente;

    @Parameter(name = "strut", description = "codice struttura", allowEmptyValue = false, example = "acepbo", schema = @Schema(type = SchemaType.STRING), required = true, in = ParameterIn.QUERY)
    @QueryParam("strut")
    public String strut;

    @Parameter(name = "anno", description = "anno di riferimento", allowEmptyValue = false, example = "2024", schema = @Schema(type = SchemaType.INTEGER, format = "int32"), required = true, in = ParameterIn.QUERY)
    @EnsureDigit(integer = true, decimal = false)
    @Min(value = 1000, message = "Anno indicato non ammesso")
    @Max(value = 3000, message = "Anno indicato non ammesso")
    @QueryParam("anno")
    public Integer anno;

    @Parameter(name = "registro", description = "codice registro", allowEmptyValue = true, example = "FATTURE ATTIVE", schema = @Schema(type = SchemaType.STRING), required = false, in = ParameterIn.QUERY)
    @QueryParam("registro")
    public Optional<String> registro;

    @Parameter(name = "numero", description = "numero", allowEmptyValue = true, example = "571107", schema = @Schema(type = SchemaType.STRING), required = false, in = ParameterIn.QUERY)
    @QueryParam("numero")
    public Optional<String> numero;

    @Parameter(name = "dtversda", description = "data di versamento da", allowEmptyValue = true, schema = @Schema(type = SchemaType.STRING, format = "dd-MM-yyyy", implementation = Date.class), required = false, example = "01-01-2024", in = ParameterIn.QUERY)
    @DateFormat("dd-MM-yyyy")
    @QueryParam("dtversda")
    public Date dtversda;

    @Parameter(name = "dtversa", description = "data di versamento a", allowEmptyValue = true, schema = @Schema(type = SchemaType.STRING, format = "dd-MM-yyyy", implementation = Date.class), required = false, example = "01-01-2024", in = ParameterIn.QUERY)
    @DateFormat("dd-MM-yyyy")
    @QueryParam("dtversa")
    public Date dtversa;

    @Parameter(name = "dtudda", description = "data unità documentaria da", allowEmptyValue = true, schema = @Schema(type = SchemaType.STRING, format = "dd-MM-yyyy", implementation = Date.class), required = false, example = "01-01-2024", in = ParameterIn.QUERY)
    @DateFormat("dd-MM-yyyy")
    @QueryParam("dtudda")
    public Date dtudda;

    @Parameter(name = "dtuda", description = "data unità documentaria a", allowEmptyValue = true, schema = @Schema(type = SchemaType.STRING, format = "dd-MM-yyyy", implementation = Date.class), required = false, example = "01-01-2024", in = ParameterIn.QUERY)
    @DateFormat("dd-MM-yyyy")
    @QueryParam("dtuda")
    public Date dtuda;

    @Parameter(name = "tipoud", description = "codice tipo unità documentaria", allowEmptyValue = true, example = "FATTURA ATTIVA", schema = @Schema(type = SchemaType.STRING), required = false, in = ParameterIn.QUERY)
    @QueryParam("tipoud")
    public Optional<String> tipoud;

    @Parameter(name = "userid", description = "nome utente", example = "nome.cognome", allowEmptyValue = true, schema = @Schema(type = SchemaType.STRING), required = false, in = ParameterIn.QUERY)
    @QueryParam("userid")
    public Optional<String> userid;

    @Parameter(name = "dataversamento", description = "ordinamento dei risultati per data di versamento (ASC/DESC)", allowEmptyValue = true, example = "asc", schema = @Schema(type = SchemaType.STRING, defaultValue = "asc"), required = false, in = ParameterIn.QUERY)
    @QueryParam("dataversamento")
    public Optional<String> dataversamento;

    @Parameter(name = "limite", description = "numero massimo di risultati ottenuti", allowEmptyValue = true, schema = @Schema(type = SchemaType.INTEGER, format = "int64"), required = false, example = "50", in = ParameterIn.QUERY)
    @EnsureDigit(integer = true, decimal = false)
    @QueryParam("limite")
    public Integer limite;

    @Parameter(name = "nextpagetoken", description = "next page token ottenuto in risposta per restituzione pagina successiva", allowEmptyValue = true, schema = @Schema(type = SchemaType.STRING), required = false, example = "token", in = ParameterIn.QUERY)
    @QueryParam("nextpagetoken")
    public Optional<String> nextpagetoken;

}
