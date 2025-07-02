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

package it.eng.parer.ricerca.ud.beans.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import it.eng.parer.ricerca.ud.Profiles;
import it.eng.parer.ricerca.ud.beans.IFindUnitadocService;
import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.beans.model.UdResponse;
import it.eng.parer.ricerca.ud.beans.utils.UdFilterParser;
import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;

@QuarkusTest
@TestProfile(Profiles.Core.class)
class FindUdServiceTest {

    static final String USERID = "test_microservizi"; // userid locale al db snap

    @Inject
    IFindUnitadocService service;

    static UdFilter filter = null;

    @BeforeAll
    static void init() {
	UdQuery query = new UdQuery();
	// mandatory
	query.amb = "PARER_PROVA";
	query.ente = "ente_test";
	query.strut = "PARER_TEST";
	query.anno = 2023;
	// optional
	query.tipoud = Optional.empty();
	query.registro = Optional.empty();
	query.dataversamento = Optional.empty();
	query.numero = Optional.empty();
	query.userid = Optional.empty();
	query.nextpagetoken = Optional.empty();
	//
	filter = new UdFilter(query);
    }

    @Test
    void findUdByQuertStr_ok() {
	assertDoesNotThrow(() -> service.findUdByQuertStr(USERID, filter, StringUtils.EMPTY));
    }

    @Test
    void findUdByQuertStrNoResult_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(1800)).build();

	UdResponse response = assertDoesNotThrow(
		() -> service.findUdByQuertStr(USERID, myfilter, StringUtils.EMPTY));
	assertEquals(true, response.getTotale() == 0);
    }

    @Test
    void findUdByQuertStrNoFilter_ko() {
	ConstraintViolationException exe = assertThrows(ConstraintViolationException.class,
		() -> service.findUdByQuertStr(USERID, null, StringUtils.EMPTY));
	assertEquals("filter non valorizzato",
		exe.getConstraintViolations().stream().findFirst().get().getMessage());

    }

    @Test
    void findUdByQuertStrNoUserId_ko() {
	ConstraintViolationException exe = assertThrows(ConstraintViolationException.class,
		() -> service.findUdByQuertStr(null, filter, StringUtils.EMPTY));
	assertEquals("userId non valorizzato",
		exe.getConstraintViolations().stream().findFirst().get().getMessage());
    }

    @Test
    void findUdByQuertStrNextPageTokenNotNull_ok() {
	UdResponse response = assertDoesNotThrow(
		() -> service.findUdByQuertStr(USERID, filter, StringUtils.EMPTY));
	assertNotNull(response.getNextpagetoken());
    }

    @Test
    void findUdByQuertStrPaginationWithNextPageToken_ok() {
	// first query
	UdQuery query1 = new UdQuery();
	// mandatory
	query1.amb = "PARER_PROVA";
	query1.ente = "ente_test";
	query1.strut = "PARER_TEST";
	query1.anno = 2023;
	query1.limite = BigInteger.ONE.intValue();
	// optional
	query1.tipoud = Optional.empty();
	query1.registro = Optional.empty();
	query1.dataversamento = Optional.empty();
	query1.numero = Optional.empty();
	query1.userid = Optional.empty();
	query1.nextpagetoken = Optional.empty();
	// filter
	UdFilter myfilter1 = new UdFilter(query1);

	UdResponse response1 = assertDoesNotThrow(
		() -> service.findUdByQuertStr(USERID, myfilter1, StringUtils.EMPTY));
	assertEquals(1, response1.getTotale());
	assertNotNull(response1.getNextpagetoken());

	// next page
	UdQuery query2 = new UdQuery();
	query2.nextpagetoken = Optional.of(response1.getNextpagetoken());
	UdFilter myfilter2 = UdFilterParser.parseUdQuery(query2);

	UdResponse response2 = assertDoesNotThrow(
		() -> service.findUdByQuertStr(USERID, myfilter2, StringUtils.EMPTY));
	assertEquals(1, response2.getTotale());

	// check different list uds on response1 vs response2
	assertFalse(response2.getUnitadocumentarie().containsAll(response1.getUnitadocumentarie()));
    }
}
