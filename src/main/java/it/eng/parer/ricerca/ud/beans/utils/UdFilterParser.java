package it.eng.parer.ricerca.ud.beans.utils;

import static it.eng.parer.ricerca.ud.beans.utils.DateUtilsConverter.parseLocalDate;
import static it.eng.parer.ricerca.ud.beans.utils.DateUtilsConverter.parseLocalDateTime;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;

public class UdFilterParser {

    private static final Logger log = LoggerFactory.getLogger(UdFilterParser.class);

    static final String FMT_DATE = "yyyy-MM-dd";
    static final String FMT_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";

    private UdFilterParser() {
	throw new IllegalStateException("Utility class");
    }

    /*
     * Cursor page based nextpage=ultimodtversud&ids=1,2,3,5,....N Regular expression for compiling
     * final filter
     */
    public static UdFilter parseUdQuery(UdQuery query) {
	// next page token
	if (query.nextpagetoken.isPresent()) {
	    try {
		String nextpagetoken = PageTokenUtils
			.decodeAndDecompressToken(query.nextpagetoken.get());
		Map<String, String> imatches = createIMatches(nextpagetoken);
		//
		if (!imatches.isEmpty()) {
		    // create filter
		    UdFilter filter = UdFilter.builder().amb(imatches.get("amb"))
			    .ente(imatches.get("ente")).strut(imatches.get("strut"))
			    .anno(new BigDecimal(imatches.get("anno")))
			    .registro(imatches.get("registro")).numero(imatches.get("numero"))
			    .dtVersDa(parseLocalDate(imatches.get("dtVersDa"), FMT_DATE))
			    .dtVersA(parseLocalDate(imatches.get("dtVersA"), FMT_DATE))
			    .dtUdDa(parseLocalDate(imatches.get("dtUdDa"), FMT_DATE))
			    .dtUdA(parseLocalDate(imatches.get("dtUdA"), FMT_DATE))
			    .tipoUd(imatches.get("tipoUd")).userid(imatches.get("userid"))
			    .dataversamento(imatches.get("dataversamento"))
			    .ultimoDtVersUd(parseLocalDateTime(imatches.get("ultimoDtVersUd"),
				    FMT_DATE_TIME))
			    .limite(NumberUtils.createInteger(imatches.get("limite")))
			    .nextpagetoken(imatches.get("nextpagetoken"))
			    .nonIds(Arrays.stream(imatches.get("nonIds").split(","))
				    .map(s -> Long.parseLong(s.trim())).toList())
			    .paginated(true).build();
		    log.atDebug().log("Filter from nextpagetoken {}", filter);
		    return filter;
		} else {
		    throw AppBadRequestException.builder().message(
			    "Il valore fornito {0} del parametro nextpagetoken non è conforme. Effettuare chiamata senza il parametro nextpagetoken per il ricalcolo.",
			    query.nextpagetoken.get()).build();
		}
	    } catch (Exception e) {
		throw AppBadRequestException.builder().cause(e).message(
			"Errore generico per il valore fornito {0} del parametro nextpagetoken. Effettuare chiamata senza il parametro nextpagetoken per il ricalcolo.",
			query.nextpagetoken.get()).build();
	    }
	}
	return new UdFilter(query); // standard
    }

    /*
     * Espressione regolare standard per ottenere i gruppi chiave=valore presenti nella query string
     * ottenuta dal nextpagetoken. Si restituisce la mappa utilizzata per costruire l'apposito bean
     * UdFilter
     */
    private static final Map<String, String> createIMatches(final String nextpagetoken) {
	// regxexp case insensitive
	Pattern queryStrPattern = Pattern.compile("&(?<name>[^=]+)=(?<value>[^&=]*)",
		Pattern.CASE_INSENSITIVE);
	Matcher queryStrMatcher = queryStrPattern.matcher(nextpagetoken);
	//
	Map<String, String> matches = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	while (queryStrMatcher.find()) {
	    matches.put(queryStrMatcher.group("name"), queryStrMatcher.group("value"));
	}
	return Collections.unmodifiableMap(matches);
    }

}
