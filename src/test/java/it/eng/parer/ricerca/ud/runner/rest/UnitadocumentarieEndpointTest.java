package it.eng.parer.ricerca.ud.runner.rest;

import static io.restassured.RestAssured.given;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.COD_ERR_BADREQ;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.URL_GET_UD;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import it.eng.parer.ricerca.ud.Profiles;

@QuarkusTest
@TestProfile(Profiles.EndToEnd.class)
class UnitadocumentarieEndpointTest {

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successNoResult() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "1949").get(URL_GET_UD).then()
		.statusCode(200).body("$", hasKey("totale")).body("totale", is(0));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successMandatoryFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("limite", "2").queryParam("ordinamento", "desc").get(URL_GET_UD).then()
		.statusCode(200).body("$", hasKey("totale")).body("totale", is(2));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successNumeroFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("numero", "test_30395_0900").queryParam("limite", "1").get(URL_GET_UD)
		.then().statusCode(200).body("$", hasKey("totale")).body("totale", is(1))
		.body("unitadocumentarie[0].anno", is(2023))
		.body("unitadocumentarie[0].numero", is("test_30395_0900"));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successRegistroFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("registro", "CT-").queryParam("limite", "1").get(URL_GET_UD).then()
		.statusCode(200).body("$", hasKey("totale")).body("totale", is(1))
		.body("unitadocumentarie[0].anno", is(2023))
		.body("unitadocumentarie[0].registro", is("CT-"));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successTipoUdFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("tipoud", "Scontratto").queryParam("limite", "1").get(URL_GET_UD).then()
		.statusCode(200).body("$", hasKey("totale")).body("totale", is(1))
		.body("unitadocumentarie[0].anno", is(2023))
		.body("unitadocumentarie[0].tipologia", is("Scontratto"));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successDtVersFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("dtversda", "05-11-2024").queryParam("dtversa", "06-11-2024")
		.queryParam("limite", "1").get(URL_GET_UD).then().statusCode(200)
		.body("$", hasKey("totale")).body("totale", is(1))
		.body("unitadocumentarie[0].anno", is(2023))
		.body("unitadocumentarie[0].dataversamento", startsWith("06-11-2024"));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successDtVersNoResultFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("dtversda", "05-11-2001").queryParam("dtversa", "06-11-2002")
		.get(URL_GET_UD).then().statusCode(200).body("$", hasKey("totale"))
		.body("totale", is(0));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successDtUdFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("dtudda", "20-11-2017").queryParam("dtuda", "30-11-2017")
		.queryParam("limite", "1").get(URL_GET_UD).then().statusCode(200)
		.body("$", hasKey("totale")).body("totale", is(1))
		.body("unitadocumentarie[0].anno", is(2023))
		.body("unitadocumentarie[0].dataunitadocumentaria", startsWith("28-11-2017"));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successDtUdNoResultFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("dtudda", "20-11-2001").queryParam("dtuda", "30-11-2002")
		.get(URL_GET_UD).then().statusCode(200).body("$", hasKey("totale"))
		.body("totale", is(0));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successUseridFilter() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "2023")
		.queryParam("userid", "test_microservizi").queryParam("limite", "1").get(URL_GET_UD)
		.then().statusCode(200).body("$", hasKey("totale")).body("totale", is(1))
		.body("unitadocumentarie[0].anno", is(2023))
		.body("unitadocumentarie[0].numero", is("test_30395_0900"));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void successNextPageTokenFilter() {

	final String nextpagetoken = given().when().queryParam("amb", "PARER_PROVA")
		.queryParam("ente", "ente_test").queryParam("strut", "PARER_TEST")
		.queryParam("anno", "2023").queryParam("userid", "test_microservizi")
		.queryParam("limite", "1").queryParam("dataversamento", "asc").get(URL_GET_UD)
		.then().statusCode(200).body("$", hasKey("totale")).body("totale", is(1)).extract()
		.body().path("nextpagetoken");

	given().when().queryParam("nextpagetoken", nextpagetoken).get(URL_GET_UD).then()
		.statusCode(200).body("$", hasKey("totale")).body("totale", is(1))
		.body("unitadocumentarie[0].anno", is(2023))
		.body("unitadocumentarie[0].numero", is("test_30395_0901"));
    }

    @Test
    @TestSecurity(user = " ", roles = {
	    "versatore" })
    void badUserEmptyRequest() {
	given().when().get(URL_GET_UD).then().statusCode(400);
    }

    @Test
    @TestSecurity(authorizationEnabled = true)
    void authNoTokenRequest() {
	given().when().get(URL_GET_UD).then().statusCode(401);
    }

    @Test
    @TestSecurity(user = "fakeuser", roles = {
	    "fakerole" })
    void noAuthRequest() {
	given().when().get(URL_GET_UD).then().statusCode(403);
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badNoFilter() {
	given().when().get(URL_GET_UD).then().statusCode(400).body("$",
		hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badNoAnnoFilterRequest() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badNoAmbFilterRequest() {
	given().when().queryParam("ente", "ente_test").queryParam("strut", "PARER_TEST")
		.queryParam("anno", "2023").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badNoEnteFilterRequest() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("strut", "PARER_TEST")
		.queryParam("anno", "2023").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badNoStrutFilterRequest() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("anno", "2023").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badYearFilterRequest() {
	given().when().queryParam("ente", "ente_test").queryParam("strut", "PARER_TEST")
		.queryParam("anno", "fake_year").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badYearSizeFilterRequest() {
	given().when().queryParam("ente", "ente_test").queryParam("strut", "PARER_TEST")
		.queryParam("anno", "202").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badNextPageTokenFilterRequest() {
	given().when().queryParam("nextpagetoken", "fake").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badDataversamentoFilterRequest() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "1949")
		.queryParam("dataversamento", "bad").get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badDtRangeNoAFilterRequest() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "1949")
		.queryParam("dtversda", "1-1-2024").queryParam("dtuda", "1-1-2024").get(URL_GET_UD)
		.then().statusCode(400).body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

    @Test
    @TestSecurity(user = "test_microservizi", roles = {
	    "versatore" })
    void badDtRangeValuesFilterRequest() {
	given().when().queryParam("amb", "PARER_PROVA").queryParam("ente", "ente_test")
		.queryParam("strut", "PARER_TEST").queryParam("anno", "1949")
		.queryParam("dtversda", "1-1-2024").queryParam("dtversa", "1-1-2023")
		.get(URL_GET_UD).then().statusCode(400)
		.body("$", hasKey(startsWith(COD_ERR_BADREQ)));
    }

}
