package it.eng.parer.ricerca.ud.beans.dao;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.jpa.HibernateHints;

import it.eng.parer.ricerca.ud.beans.IFindUnitaDocDao;
import it.eng.parer.ricerca.ud.beans.dto.TipoDatoAbilDto;
import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import it.eng.parer.ricerca.ud.beans.exceptions.AppGenericRuntimeException;
import it.eng.parer.ricerca.ud.beans.exceptions.ErrorCategory;
import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.beans.utils.Costants;
import it.eng.parer.ricerca.ud.beans.utils.Costants.NmClasseTipoDato;
import it.eng.parer.ricerca.ud.jpa.AroUnitaDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoDoc;
import it.eng.parer.ricerca.ud.jpa.DecTipoUnitaDoc;
import it.eng.parer.ricerca.ud.jpa.constraint.AroDocCnts;
import it.eng.parer.ricerca.ud.jpa.constraint.AroUnitaDocCnts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class FindUdDao implements IFindUnitaDocDao {

    private static final String QUERY_AND = " and ";

    @ConfigProperty(name = "parer.ricerca.ud.limite")
    Integer limite;

    @Inject
    EntityManager entityManager;

    private static final String IDSTRUT_USRABIL_QUERY = "select abil.idOrganizApplic "
	    + "from IamAbilOrganiz abil " + "join abil.iamUser usr "
	    + "where usr.nmUserid = :nmUserid and abil.idOrganizApplic = ("
	    + "   select strut.idStrut " + "   from OrgStrut strut " + "   join strut.orgEnte ente "
	    + "   join ente.orgAmbiente amb "
	    + "   where amb.nmAmbiente = :nmAmbiente and ente.nmEnte = :nmEnte and strut.nmStrut = :nmStrut "
	    + ")";

    private static final String IDREG_QUERY = "select reg.idRegistroUnitaDoc "
	    + "from DecRegistroUnitaDoc reg "
	    + "where reg.idStrut = :idStrut and reg.cdRegistroUnitaDoc = :cdRegistroUnitaDoc";

    private static final String IDTIPOUD_QUERY = "select tipo.idTipoUnitaDoc "
	    + "from DecTipoUnitaDoc tipo "
	    + "where tipo.idStrut = :idStrut and tipo.nmTipoUnitaDoc = :nmTipoUnitaDoc";

    private static final String DECTIPODOC_PRINCIPALE_QUERY = "select tipodoc "
	    + "from AroDoc doc join doc.decTipoDoc tipodoc "
	    + "where doc.idUnitaDoc = :idUnitaDoc and doc.idStrut = :idStrut and doc.pgDoc = :pgDoc and doc.tiDoc = :tiDocPRINCIPALE";

    private static final String TIPODATO_QUERY = "select NEW it.eng.parer.ricerca.ud.beans.dto.TipoDatoAbilDto(abil.nmClasseTipoDato, abil.idTipoDatoApplic) "
	    + "from IamAbilTipoDato abil join abil.iamAbilOrganiz org join org.iamUser usr "
	    + "where org.idOrganizApplic = :idOrganizApplic and abil.nmClasseTipoDato in (:nmClasseTipoDato) and usr.nmUserid = :nmUserid ";

    private static final String IDUSERVERS_QUERY = "select usr.idUserIam " + "from IamUser usr "
	    + "where usr.nmUserid = :nmUserid";

    @Override
    public Stream<AroUnitaDoc> findUnitadocsByQueryStr(Long idStrut, Optional<Long> idResgistroUd,
	    Optional<Long> idTipoUd, Optional<Long> idUserVers, UdFilter filter) {
	StringBuilder queryStr = new StringBuilder();
	try {
	    // std select
	    queryStr.append("select ud ");
	    // std join
	    queryStr.append("from AroUnitaDoc ud ");
	    // where (static / dynamic)
	    queryStr.append("where ");

	    // mandatory
	    queryStr.append("ud.idStrut = :idStrut").append(QUERY_AND);
	    queryStr.append("ud.aaKeyUnitaDoc = :aaKeyUnitaDoc").append(QUERY_AND);
	    // ud non annullata
	    queryStr.append("ud.tiStatoConservazione <> :tiStatoConsANNULLATA").append(QUERY_AND);

	    // dinamyc params
	    // registro
	    if (idResgistroUd.isPresent()) {
		queryStr.append("ud.idRegistroUnitaDoc = :idRegistroUnitaDoc").append(QUERY_AND);
	    }
	    // numero
	    if (Objects.nonNull(filter.getNumero())) {
		queryStr.append("ud.cdKeyUnitaDoc = :cdKeyUnitaDoc").append(QUERY_AND);
	    }
	    // dt ud da-a
	    if (Objects.nonNull(filter.getDtUdDa()) && Objects.nonNull(filter.getDtUdA())) {
		queryStr.append(
			"to_char(ud.dtRegUnitaDoc,'YYYY-MM-DD') between to_char(:dtRegUnitaDocDa,'YYYY-MM-DD') and to_char(:dtRegUnitaDocA,'YYYY-MM-DD')")
			.append(QUERY_AND);
	    }
	    // dt versa da-a
	    if (Objects.nonNull(filter.getDtVersDa()) && Objects.nonNull(filter.getDtVersA())) {
		queryStr.append(
			"to_char(ud.dtCreazione,'YYYY-MM-DD') between to_char(:dtCreazioneDa,'YYYY-MM-DD') and to_char(:dtCreazioneA,'YYYY-MM-DD')")
			.append(QUERY_AND);
	    }
	    // tipo ud
	    if (idTipoUd.isPresent()) {
		queryStr.append("ud.idTipoUnitaDoc = :idTipoUnitaDoc").append(QUERY_AND);
	    }
	    // userid
	    if (idUserVers.isPresent()) {
		queryStr.append("ud.idUserVers = :idUserVers").append(QUERY_AND);
	    }

	    // Cursor-based pagination https://www.merge.dev/blog/rest-api-pagination
	    // Viene dinamicamente calcolato ad ogni ricerca un corsore (o token) che il
	    // client potrà utilizzare
	    // per invocare nuovamente il servizio ed ottenere la pagina successiva
	    if (filter.isPaginated()) {
		queryStr.append(
			"to_char(ud.dtCreazione,'YYYY-MM-DD HH24:MI:SS') >= to_char(:ultimoDtVersUd,'YYYY-MM-DD HH24:MI:SS')")
			.append(QUERY_AND);
		queryStr.append("ud.idUnitaDoc not in (:ids)").append(QUERY_AND);
	    }

	    // clean
	    String queryClean = StringUtils.removeEnd(queryStr.toString(), QUERY_AND);
	    // order by to and versus
	    // ordinamento fisso per dtCrezione (pagination
	    // purpose..https://engineering.getmidas.com/why-do-you-need-keyset-seek-pagination-ef99815b518e)
	    queryClean = queryClean.concat(" order by ud.dtCreazione ")
		    .concat(filter.getDataversamento());

	    //
	    TypedQuery<AroUnitaDoc> query = entityManager.createQuery(queryClean,
		    AroUnitaDoc.class);

	    // max result utilizzato per fine di paginazione
	    query.setMaxResults(Optional.ofNullable(filter.getLimite()).orElse(limite)); // max
											 // result
	    // check hint for optimizations
	    query.setHint(HibernateHints.HINT_READ_ONLY, true);
	    query.setHint(HibernateHints.HINT_CACHEABLE, true);
	    // query.setHint(HibernateHints.HINT_FETCH_SIZE, Integer.valueOf("1000"));

	    // fixed
	    query.setParameter("idStrut", idStrut);
	    query.setParameter("aaKeyUnitaDoc", filter.getAnno());
	    query.setParameter("tiStatoConsANNULLATA",
		    AroUnitaDocCnts.TiStatoConservazione.ANNULLATA);

	    // dynamic
	    if (idResgistroUd.isPresent()) {
		query.setParameter("idRegistroUnitaDoc", idResgistroUd.get());
	    }
	    if (idTipoUd.isPresent()) {
		query.setParameter("idTipoUnitaDoc", idTipoUd.get());
	    }
	    if (idUserVers.isPresent()) {
		query.setParameter("idUserVers", idUserVers.get());
	    }
	    //
	    if (Objects.nonNull(filter.getNumero())) {
		query.setParameter("cdKeyUnitaDoc", filter.getNumero());
	    }
	    if (Objects.nonNull(filter.getDtUdDa()) && Objects.nonNull(filter.getDtUdA())) {
		query.setParameter("dtRegUnitaDocDa", filter.getDtUdDa());
		query.setParameter("dtRegUnitaDocA", filter.getDtUdA());
	    }
	    if (Objects.nonNull(filter.getDtVersDa()) && Objects.nonNull(filter.getDtVersA())) {
		query.setParameter("dtCreazioneDa", filter.getDtVersDa());
		query.setParameter("dtCreazioneA", filter.getDtVersA());
	    }
	    if (filter.isPaginated()) {
		query.setParameter("ultimoDtVersUd", filter.getUltimoDtVersUd());
		query.setParameter("ids", filter.getNonIds());
	    }
	    return query.getResultStream();
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message(
			    "Errore interno nella fase ricerca delle unità documentarie per idStrut {0}, idResgistroUd {2}, idTipoUd {3}, idUserVers {4}, e filtro {5}",
			    idStrut, idResgistroUd.orElse(Long.MIN_VALUE),
			    idTipoUd.orElse(Long.MIN_VALUE), idUserVers.orElse(Long.MIN_VALUE),
			    filter)
		    .build();
	}
    }

    @Override
    public Long findIdStrutByUserAbilAndQueryStr(String userid, String amb, String ente,
	    String strut) {
	try {
	    TypedQuery<Long> query = entityManager.createQuery(IDSTRUT_USRABIL_QUERY, Long.class);
	    // userid
	    query.setParameter("nmUserid", userid);
	    // query filter ambiente+ente+struttura
	    query.setParameter("nmAmbiente", amb);
	    query.setParameter("nmEnte", ente);
	    query.setParameter("nmStrut", strut);
	    return query.getSingleResult();
	} catch (NoResultException nre) {
	    throw AppBadRequestException.builder().cause(nre).message(
		    "L''utente {0} non risulta abilitato alla struttura richiesta o la struttura {1} / {2} / {3} non è esistente",
		    userid, amb, ente, strut).build();
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message(
			    "Errore interno nella fase ricerca identificativo struttura per amb {0}, ente {1} e strut {2}",
			    amb, ente, strut)
		    .build();
	}
    }

    @Override
    public Optional<Long> findIdRegistroUdByQueryStr(Long idStrut, String registro) {
	try {
	    TypedQuery<Long> query = entityManager.createQuery(IDREG_QUERY, Long.class);
	    query.setParameter("idStrut", idStrut);
	    query.setParameter("cdRegistroUnitaDoc", registro);
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException nre) {
	    return Optional.of(Long.MIN_VALUE); // non-existence!
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message(
			    "Errore interno nella fase ricerca identificativo registro da query string per registro {0} e idStrut {1}",
			    registro, idStrut)
		    .build();
	}
    }

    @Override
    public Optional<Long> findIdTipoUdByQueryStr(Long idStrut, String tipoUd) {
	try {
	    TypedQuery<Long> query = entityManager.createQuery(IDTIPOUD_QUERY, Long.class);
	    query.setParameter("idStrut", idStrut);
	    query.setParameter("nmTipoUnitaDoc", tipoUd);
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException nre) {
	    return Optional.of(Long.MIN_VALUE); // non-existence!
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message(
			    "Errore interno nella fase ricerca identificativo tipo ud da query string per tipoUd {0} e idStrut {1}",
			    tipoUd, idStrut)
		    .build();
	}
    }

    @Override
    public DecTipoUnitaDoc findDecTipoUnitaDocByUd(Long idStrut, Long idTipoUd) {
	try {
	    return entityManager.find(DecTipoUnitaDoc.class, idTipoUd);
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message(
			    "Errore interno nella fase ricerca di dec tipo unita doc per idTipoUd {0} e idStrut {1}",
			    idTipoUd, idStrut)
		    .build();
	}
    }

    @Override
    public DecTipoDoc findDecTipoDocByUd(Long idStrut, Long idUnitaDoc) {
	try {
	    TypedQuery<DecTipoDoc> query = entityManager.createQuery(DECTIPODOC_PRINCIPALE_QUERY,
		    DecTipoDoc.class);
	    query.setParameter("idUnitaDoc", idUnitaDoc);
	    query.setParameter("idStrut", idStrut);
	    query.setParameter("pgDoc", BigDecimal.ONE);
	    query.setParameter("tiDocPRINCIPALE", AroDocCnts.TiDoc.PRINCIPALE);
	    return query.getSingleResult();
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message(
			    "Errore interno nella fase ricerca di dec tipo doc per idUnitaDoc {0 e idStrut {1}",
			    idUnitaDoc, idStrut)
		    .build();
	}
    }

    @Override
    public Stream<TipoDatoAbilDto> findTipoDatoApplicByIdStrut(Long idStrut, String userid) {
	try {
	    TypedQuery<TipoDatoAbilDto> query = entityManager.createQuery(TIPODATO_QUERY,
		    TipoDatoAbilDto.class);
	    // idStrut
	    query.setParameter("idOrganizApplic", idStrut);
	    // static
	    query.setParameter("nmClasseTipoDato", Stream.of(Costants.NmClasseTipoDato.values())
		    .map(NmClasseTipoDato::name).toList());
	    // userId
	    query.setParameter("nmUserid", userid);
	    return query.getResultStream();
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message(
			    "Errore interno nella fase ricerca tipo dato abilitato per idstrut {0}",
			    idStrut)
		    .build();
	}
    }

    @Override
    public Optional<Long> findIdUserVersByQueryStr(String userid) {
	try {
	    TypedQuery<Long> query = entityManager.createQuery(IDUSERVERS_QUERY, Long.class);
	    query.setParameter("nmUserid", userid);
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException nre) {
	    return Optional.of(Long.MIN_VALUE); // non-existence!
	} catch (Exception e) {
	    throw AppGenericRuntimeException.builder().cause(e).category(ErrorCategory.HIBERNATE)
		    .message("Errore interno nella fase ricerca di utente versante per userid {0}",
			    userid)
		    .build();
	}
    }
}
