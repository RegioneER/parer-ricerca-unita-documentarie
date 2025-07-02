package it.eng.parer.ricerca.ud.beans.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.ricerca.ud.beans.IFindUnitaDocDao;
import it.eng.parer.ricerca.ud.beans.IFindUnitadocService;
import it.eng.parer.ricerca.ud.beans.dto.TipoDatoAbilDto;
import it.eng.parer.ricerca.ud.beans.dto.UdDto;
import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import it.eng.parer.ricerca.ud.beans.exceptions.AppGenericRuntimeException;
import it.eng.parer.ricerca.ud.beans.exceptions.ErrorCategory;
import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.beans.model.UdResponse;
import it.eng.parer.ricerca.ud.beans.utils.Costants.NmClasseTipoDato;
import it.eng.parer.ricerca.ud.beans.utils.PageTokenUtils;
import it.eng.parer.ricerca.ud.jpa.AroUnitaDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoUnitaDoc;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class FindUnitaDocService implements IFindUnitadocService {

    private static final Logger log = LoggerFactory.getLogger(FindUnitaDocService.class);

    @Inject
    IFindUnitaDocDao dao;

    @Override
    @Transactional(value = TxType.REQUIRED, rollbackOn = {
	    AppGenericRuntimeException.class, AppBadRequestException.class })
    public UdResponse findUdByQuertStr(String userId, UdFilter filter, String uri) {
	// get idStrut anche check user abilitation
	Long idStrut = verifyUserIdAndGetIdStrut(userId, filter);
	// elab response
	UdResponse response = elabUdsResponse(userId, idStrut, filter, uri);
	//
	return response;
    }

    private UdResponse elabUdsResponse(String userId, Long idStrut, UdFilter filter, String uri) {
	List<UdDto> uds = new ArrayList<>();
	Map<LocalDateTime, List<Long>> nonIdsMap = new HashMap<>();

	try {
	    final LocalDateTime start = LocalDateTime.now();
	    log.atInfo().log("RicercaUnitaDoc -- ricerca ud con filtro {}", filter);

	    // 1. get tipo dato abilitato 'REGISTRO', 'TIPO_UNITA_DOC', 'TIPO_DOC
	    Map<NmClasseTipoDato, List<Long>> tipoDatoAbilMap = createTipoDatoAbilMap(idStrut,
		    userId);
	    // 2. find idregistroud (optional)
	    Optional<Long> idResgistroUd = findRegistro(idStrut, filter);
	    // 3. find idtipoud (optional)
	    Optional<Long> idTipoUd = findTipoUd(idStrut, filter);
	    // 4. find user (optional)
	    Optional<Long> idUserVers = findUserVers(filter);
	    // 5. find uds
	    Stream<AroUnitaDoc> udsAsStream = dao.findUnitadocsByQueryStr(idStrut, idResgistroUd,
		    idTipoUd, idUserVers, filter);
	    // for each ud filter on tipoDatoAbilMap REGISTRO and TIPO_UNITA_DOC
	    udsAsStream
		    .filter(ud -> tipoDatoAbilMap.get(NmClasseTipoDato.REGISTRO)
			    .contains(ud.getIdRegistroUnitaDoc())
			    && tipoDatoAbilMap.get(NmClasseTipoDato.TIPO_UNITA_DOC)
				    .contains(ud.getIdTipoUnitaDoc()))
		    .forEach(ud -> populateDto(idStrut, uds, nonIdsMap, tipoDatoAbilMap, ud));
	    //
	    log.atInfo().log("RicercaUnitaDoc -- individuate {} ud in {}s", uds.size(),
		    Duration.between(start, LocalDateTime.now()).getSeconds());
	    // calculate latest AroUnitaDoc.dtCreazione
	    Optional<LocalDateTime> latestDataveramento = uds.stream().map(UdDto::getDataversamento)
		    .max(LocalDateTime::compareTo);
	    // generate nextPage
	    String nextPageToken = generateNextPageToken(filter, nonIdsMap, latestDataveramento);
	    // response
	    return new UdResponse(uds, uds.size(), nextPageToken, uri, filter.getDataversamento());
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
		    .cause(e)
		    .message(
			    "Errore estrazione lista unità documentarie per con query string {0} per idStrut {1}",
			    filter, idStrut)
		    .build();
	}
    }

    private void populateDto(Long idStrut, List<UdDto> uds,
	    Map<LocalDateTime, List<Long>> nonIdsMap,
	    Map<NmClasseTipoDato, List<Long>> tipoDatoAbilMap, AroUnitaDoc ud) {
	// populate dto
	// get DecTipoDoc -> se non abilitato skip
	DecTipoDoc tiDocPrincipal = dao.findDecTipoDocByUd(idStrut, ud.getIdUnitaDoc());
	// check on tipoDatoAbilMap TIPO_DOC
	if (tipoDatoAbilMap.get(NmClasseTipoDato.TIPO_DOC)
		.contains(tiDocPrincipal.getIdTipoDoc())) {
	    // get DecTipoUnitaDoc
	    DecTipoUnitaDoc tipoUd = dao.findDecTipoUnitaDocByUd(idStrut, ud.getIdTipoUnitaDoc());
	    // dto
	    UdDto dto = new UdDto(ud, tipoUd, tiDocPrincipal);
	    uds.add(dto);
	    // populate map
	    nonIdsMap.merge(ud.getDtCreazione(), Arrays.asList(ud.getIdUnitaDoc()),
		    this::mergeListIdsOnMapPerKey);
	}
    }

    private Optional<Long> findUserVers(UdFilter filter) {
	Optional<Long> idUserVers = Optional.empty();
	if (Objects.nonNull(filter.getUserid())) {
	    idUserVers = dao.findIdUserVersByQueryStr(filter.getUserid());
	    log.atDebug().log("RicercaUnitaDoc -- ricerca ud con idUserVers {}",
		    Optional.ofNullable(idUserVers));
	}
	return idUserVers;
    }

    private Optional<Long> findTipoUd(Long idStrut, UdFilter filter) {
	Optional<Long> idTipoUd = Optional.empty();
	if (Objects.nonNull(filter.getTipoUd())) {
	    idTipoUd = dao.findIdTipoUdByQueryStr(idStrut, filter.getTipoUd());
	    log.atDebug().log("RicercaUnitaDoc -- ricerca ud con idTipoUd {}",
		    Optional.ofNullable(idTipoUd));
	}
	return idTipoUd;
    }

    private Optional<Long> findRegistro(Long idStrut, UdFilter filter) {
	Optional<Long> idResgistroUd = Optional.empty();
	if (Objects.nonNull(filter.getRegistro())) {
	    idResgistroUd = dao.findIdRegistroUdByQueryStr(idStrut, filter.getRegistro());
	    log.atDebug().log("RicercaUnitaDoc -- ricerca ud con idResgistroUd {}",
		    Optional.ofNullable(idResgistroUd));
	}
	return idResgistroUd;
    }

    private Long verifyUserIdAndGetIdStrut(String userId, UdFilter filter) {
	return dao.findIdStrutByUserAbilAndQueryStr(userId, filter.getAmb(), filter.getEnte(),
		filter.getStrut());
    }

    private Map<NmClasseTipoDato, List<Long>> createTipoDatoAbilMap(Long idStrut, String userId) {
	Map<NmClasseTipoDato, List<Long>> tipoDatoAbilMap = new EnumMap<>(NmClasseTipoDato.class);
	// 0. map init
	Stream.of(NmClasseTipoDato.values())
		.forEach(k -> tipoDatoAbilMap.put(k, Arrays.asList(Long.MIN_VALUE)));
	// 1. get result by dao
	Stream<TipoDatoAbilDto> result = dao.findTipoDatoApplicByIdStrut(idStrut, userId);
	// 2. create map key=classe / value=ids
	result.collect(Collectors.groupingBy(TipoDatoAbilDto::getNmClasseTipoDato))
		.forEach((k, v) -> tipoDatoAbilMap.put(NmClasseTipoDato.valueOf(k.toUpperCase()),
			v.stream().map(TipoDatoAbilDto::getIdTipoDatoApplic).toList()));
	return tipoDatoAbilMap;
    }

    private List<Long> mergeListIdsOnMapPerKey(List<Long> existingIds, List<Long> newIds) {
	return Stream.concat(existingIds.stream(), newIds.stream()).toList();
    }

    private String generateNextPageToken(UdFilter filter, Map<LocalDateTime, List<Long>> nonIdsMap,
	    Optional<LocalDateTime> latestDataveramento) {
	String nextPage = null;
	if (latestDataveramento.isPresent()) {
	    // update latest value of latestDataveramento and notIds
	    filter.updateUltimoDtVersUd(latestDataveramento.get());
	    filter.updateNonIds(nonIdsMap.get(latestDataveramento.get()));
	    //
	    nextPage = PageTokenUtils.compressAndEncodeToken(filter.stdQueryString());
	}
	return nextPage;
    }
}
