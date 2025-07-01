package it.eng.parer.ricerca.ud.beans.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import it.eng.parer.ricerca.ud.Profiles;
import it.eng.parer.ricerca.ud.beans.IFindUnitaDocDao;
import it.eng.parer.ricerca.ud.beans.dto.TipoDatoAbilDto;
import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.beans.utils.Costants.OrderType;
import it.eng.parer.ricerca.ud.jpa.AroUnitaDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoUnitaDoc;
import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;
import jakarta.inject.Inject;

@QuarkusTest
@TestProfile(Profiles.Core.class)
class FindUdDaoTest {

    static final Long IDSTRUT = 8L; // idstrut 'PARER_PROVA' locale al db snap
    static final String USERID = "test_microservizi"; // userd locale al db snap

    @Inject
    IFindUnitaDocDao dao;

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
    void findUnitadocsByQueryStr_ok() {
	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), Optional.empty(), Optional.empty(), filter));
	assertEquals(true, result.count() != 0);
    }

    @Test
    void findUnitadocsByQueryStrNoResult_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("fake_amb").ente("fake_test").strut("fake_strut")
		.anno(new BigDecimal(2023)).build();
	final Long IDSTRUT_FAKE = Long.MIN_VALUE; // idstrut fake

	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(
		IDSTRUT_FAKE, Optional.empty(), Optional.empty(), Optional.empty(), myfilter));
	assertEquals(0, result.count());
    }

    @Test
    void findUnitadocsByQueryStrTipoUd_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023)).tipoUd("Scontratto").build();

	Optional<Long> idTipoUd = assertDoesNotThrow(
		() -> dao.findIdTipoUdByQueryStr(IDSTRUT, myfilter.getTipoUd()));
	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), idTipoUd, Optional.empty(), myfilter));
	// assert
	assertEquals(true, result.count() != 0);
    }

    @Test
    void findUnitadocsByQueryStrReg_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023)).registro("CT-").build();

	Optional<Long> idRegistroUd = assertDoesNotThrow(
		() -> dao.findIdRegistroUdByQueryStr(IDSTRUT, myfilter.getRegistro()));
	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		idRegistroUd, Optional.empty(), Optional.empty(), myfilter));
	// assert
	assertEquals(true, result.count() != 0);
    }

    @Test
    void findUnitadocsByQueryStrNum_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023)).numero("test_30395_0900").build();

	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), Optional.empty(), Optional.empty(), myfilter));
	List<AroUnitaDoc> uds = result.toList();
	assertEquals(1, uds.size());
	assertEquals("test_30395_0900", uds.get(0).getCdKeyUnitaDoc());
    }

    @Test
    void findUnitadocsByQueryStrLimite_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023)).limite(1).build();

	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), Optional.empty(), Optional.empty(), myfilter));
	assertEquals(1, result.count());
    }

    @Test
    void findUnitadocsByQueryStrOrdinamento_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023))
		.dataversamento(OrderType.DESC.name()).build();

	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), Optional.empty(), Optional.empty(), myfilter));
	List<AroUnitaDoc> uds = result.toList();
	assertTrue(uds.get(0).getDtCreazione().isAfter(uds.get(1).getDtCreazione()));
    }

    @Test
    void findUnitadocsByQueryStrDtUd_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023)).dtUdDa(LocalDate.of(2017, 11, 28))
		.dtUdA(LocalDate.of(2017, 11, 28)).build();

	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), Optional.empty(), Optional.empty(), myfilter));
	List<AroUnitaDoc> uds = result.toList();
	List<LocalDate> dtuds = uds.stream().map(AroUnitaDoc::getDtRegUnitaDoc).toList();
	assertTrue(!uds.isEmpty());
	assertTrue(dtuds.contains(LocalDate.of(2017, 11, 28)));
    }

    @Test
    void findUnitadocsByQueryStrDtVers_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023)).dtVersDa(LocalDate.of(2024, 11, 6))
		.dtVersA(LocalDate.of(2024, 11, 6)).build();

	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), Optional.empty(), Optional.empty(), myfilter));
	List<AroUnitaDoc> uds = result.toList();
	List<LocalDate> dtcreuds = uds.stream().map(AroUnitaDoc::getDtCreazione)
		.map(LocalDateTime::toLocalDate).toList();
	assertEquals(2, uds.size());
	assertTrue(dtcreuds.contains(LocalDate.of(2024, 11, 6)));
    }

    @Test
    void findUnitadocsByQueryStrNumNoResult_ok() {
	// filter
	UdFilter myfilter = UdFilter.builder().amb("PARER_PROVA").ente("ente_test")
		.strut("PARER_TEST").anno(new BigDecimal(2023)).numero("fakenum").build();

	Stream<AroUnitaDoc> result = assertDoesNotThrow(() -> dao.findUnitadocsByQueryStr(IDSTRUT,
		Optional.empty(), Optional.empty(), Optional.empty(), myfilter));
	List<AroUnitaDoc> uds = result.toList();
	assertEquals(0, uds.size());
    }

    @Test
    void findIdStrutByUserAbilAndQueryStr_ok() {
	Long result = assertDoesNotThrow(() -> dao.findIdStrutByUserAbilAndQueryStr(USERID,
		"PARER_PROVA", "ente_test", "PARER_TEST"));
	assertEquals(8L, result);
    }

    @Test
    void findIdStrutByUserAbilAndQueryStrBadReq_ko() {
	assertThrows(AppBadRequestException.class,
		() -> dao.findIdStrutByUserAbilAndQueryStr("fake_user", "fake_amb", "fake_ente",
			"fake_strut"));
    }

    @Test
    void findIdRegistroUdByQueryStr_ok() {
	Optional<Long> result = assertDoesNotThrow(
		() -> dao.findIdRegistroUdByQueryStr(IDSTRUT, "CT-"));
	assertTrue(result.isPresent());
    }

    @Test
    void findIdRegistroUdByQueryStrNotFound_ok() {
	Optional<Long> result = assertDoesNotThrow(
		() -> dao.findIdRegistroUdByQueryStr(IDSTRUT, "fake_reg"));
	assertTrue(result.isPresent());
	assertEquals(Long.MIN_VALUE, result.get());
    }

    @Test
    void findIdTipoUdByQueryStr_ok() {
	Optional<Long> result = assertDoesNotThrow(
		() -> dao.findIdTipoUdByQueryStr(IDSTRUT, "Scontratto"));
	assertTrue(result.isPresent());
    }

    @Test
    void findIdTipoUdByQueryStrNotFound_ok() {
	Optional<Long> result = assertDoesNotThrow(
		() -> dao.findIdTipoUdByQueryStr(IDSTRUT, "fake_tipo"));
	assertTrue(result.isPresent());
	assertEquals(Long.MIN_VALUE, result.get());
    }

    @Test
    void findDecTipoUnitaDocByUd_ok() {
	final Long IDTIPOUD = 29226903L; // id tipo ud (db snap)
	DecTipoUnitaDoc result = assertDoesNotThrow(
		() -> dao.findDecTipoUnitaDocByUd(IDSTRUT, IDTIPOUD));
	assertEquals("Scontratto", result.getNmTipoUnitaDoc());
    }

    @Test
    void findDecTipoDocByUd_ok() {
	final Long IDUD = 78935159480L; // id unità doc (db snap)
	DecTipoDoc result = assertDoesNotThrow(() -> dao.findDecTipoDocByUd(IDSTRUT, IDUD));
	assertEquals("CONTRATTO", result.getNmTipoDoc());
    }

    @Test
    void findTipoDatoApplicByIdStrut_ok() {
	Stream<TipoDatoAbilDto> result = assertDoesNotThrow(
		() -> dao.findTipoDatoApplicByIdStrut(IDSTRUT, USERID));
	List<TipoDatoAbilDto> tds = result.toList();
	assertFalse(tds.isEmpty());
    }

    @Test
    void findIdUserVersByQueryStr_ok() {
	Optional<Long> result = assertDoesNotThrow(() -> dao.findIdUserVersByQueryStr(USERID));
	assertTrue(result.isPresent());
    }

    @Test
    void findIdUserVersByQueryStrNotFound_ok() {
	Optional<Long> result = assertDoesNotThrow(() -> dao.findIdUserVersByQueryStr("fake_user"));
	assertTrue(result.isPresent());
	assertEquals(Long.MIN_VALUE, result.get());
    }
}