/**
 * 
 */
package com.cataneo.midas.booking.dao;

import java.util.List;
import java.util.Set;

import com.cataneo.midas.domain.beans.Spot;
import com.cataneo.midas.domain.beans.SpotExtended;
import com.cataneo.midas.domain.query.QFormat;
import com.cataneo.midas.domain.query.QFormattyp;
import com.cataneo.midas.domain.query.QPreisdetail;
import com.cataneo.midas.domain.query.QSpot;
import com.cataneo.midas.domain.query.QSpothistory;
import com.cataneo.midas.fw.exceptions.DatabaseException;
import com.cataneo.springextensions.cache.CacheDependencyManager.DependencyType;
import com.querydsl.core.types.Projections;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.dml.SQLUpdateClause;

/**
 * <P>
 * </P>
 * 
 * @author Christian Nefzger Cataneo GmbH
 * 
 *         Created 10.03.2016
 *
 */
public class SpotDao extends AbstractDao<Spot>
{

	/**
	 * <P>
	 * Konstruktor.
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 17.03.2016
	 *
	 * @param entityType
	 */
	public SpotDao(RelationalPathBase<Spot> entityType)
	{
		super(entityType);
		// TODO Auto-generated constructor stub
	}

	public Long countForSuborder(Long suborderId)
		throws DatabaseException
	{
		QSpot spot = QSpot.spot;
		return queryFactory.select(spot).from(spot).where(spot.subauftragid.eq(suborderId)).fetchCount();
	}

	public Long countForPackage(Long packageId)
		throws DatabaseException
	{
		QSpot spot = QSpot.spot;
		return queryFactory.select(spot).from(spot).where(spot.packageid.eq(packageId)).fetchCount();
	}

	public Long countForPlacement(Long placementId)
		throws DatabaseException
	{
		QSpot spot = QSpot.spot;
		return queryFactory.select(spot).from(spot).where(spot.placementid.eq(placementId)).fetchCount();
	}

	public List<Spot> findByWerberahmenId(Long id)
	{
		QSpot spot = QSpot.spot;

		return queryFactory.select(spot).from(spot).where(spot.werberahmenid.eq(id)).fetch();
	}

	/**
	 * <P>
	 * Loads all spots for the given placementIds from the database.
	 * <P/>
	 *
	 * @author Alexander Schwartz Cataneo GmbH
	 * 
	 *         Created 03.04.2017
	 *
	 * @param placementIds
	 *            a list of placements, for which the spots should be loaded
	 * 
	 * @return a list of SpotExtended
	 */
	public List<SpotExtended> findSpotsExtendedByPlacementIds(List<Long> placementIds)
	{
		QSpot spot = QSpot.spot;
		QFormat format = QFormat.format;
		QFormattyp formattyp = QFormattyp.formattyp;
		QPreisdetail preisdetail = QPreisdetail.preisdetail;

		// @formatter:off
		List<SpotExtended> spotsExt = queryFactory.
			select(Projections.bean(SpotExtended.class,
				spot.id.as("id"),
				spot.hauptspotid.as("hauptspotid"),
				spot.placementid.as("placementid"),
				spot.externeid.as("externeid"),
				spot.campaignid.as("campaignid"),
				spot.campruleid.as("campruleid"),
				spot.externerefid.as("externerefid"),
				spot.fakturadetailid.as("fakturadetailid"),
				spot.gesendet.as("gesendet"),
				spot.grp.as("grp"),
				spot.gueltigid.as("gueltigid"),
				spot.historyid.as("historyid"),
				spot.importinfoid.as("importinfoid"),
				spot.lastimportid.as("lastimportid"),
				spot.lmodified.as("lmodified"),
				spot.lupdate.as("lupdate"),
				spot.luserid.as("luserid"),
				spot.mediumid.as("mediumid"),
				spot.motivid.as("motivid"),
				spot.ndemid.as("ndemid"),
				spot.packageid.as("packageid"),
				spot.position.as("position"),
				spot.preisid.as("preisid"),
				spot.produktid.as("produktid"),
				spot.regionid.as("regionid"),
				spot.sessionid.as("sessionid"),
				spot.spotbuchungid.as("spotbuchungid"),
				spot.spotpg.as("spotpg"),
				spot.spotzgid.as("spotzgid"),
				spot.subauftragid.as("subauftragid"),
				spot.templateid.as("templateid"),
				spot.timeasrun.as("timeasrun"),
				spot.treatstatusid.as("treatstatusid"),
				spot.typid.as("typid"),
				spot.werberahmenid.as("werberahmenid"),
				spot.zubuchfunktionid.as("zubuchfunktionid"),
				spot.zubuchzeit.as("zubuchzeit"),
				spot.formatid.as("formatid"),
				format.wert.as("formatwert"),
				format.formattypid.as("formattypid"),
				format.formatdetailid.as("formatdetailid"),
				formattyp.faktor.as("formatfaktor"),
				preisdetail.listenpreis1.as("listPrice1"),
				preisdetail.listenpreis2.as("listPrice2"),
				preisdetail.virtuell.as("virtual"),
				preisdetail.mediabrutto.as("mediabrutto")))
			.from(spot)
			.join(format).on(spot.formatid.eq(format.id))
			.join(formattyp).on(format.formattypid.eq(formattyp.id))
			.join(preisdetail).on(spot.preisid.eq(preisdetail.id))
			.where(spot.placementid.in(placementIds))
			.fetch();
		// @formatter:on

		return spotsExt;
	}

	/**
	 * <P>
	 * Loads all spots for the given placementId from the database.
	 * <P/>
	 *
	 * @author Alexander Schwartz Cataneo GmbH
	 * 
	 *         Created 03.04.2017
	 *
	 * @param placementId
	 *            a placementId, for which the spots should be loaded
	 * 
	 * @return a list of Spots
	 */
	public List<Spot> findByPlacementId(Long placementId)
	{
		QSpot spot = QSpot.spot;
		QFormat format = QFormat.format;
		QFormattyp formattyp = QFormattyp.formattyp;
		QPreisdetail preisdetail = QPreisdetail.preisdetail;

		// @formatter:off
		List<Spot> spotsExt = queryFactory.
			select(spot)
			.from(spot)
			
			.where(spot.placementid.eq(placementId))
			.fetch();
		// @formatter:on

		return spotsExt;
	}

	/**
	 * <P>
	 * Loads all spots for the given placementIds from the database.
	 * <P/>
	 *
	 * @author Alexander Schwartz Cataneo GmbH
	 * 
	 *         Created 03.04.2017
	 *
	 * @param placementIds
	 *            a list of placements, for which the spots should be loaded
	 * 
	 * @return a list of SpotExtended
	 */
	public SpotExtended findSpotExtendedById(Long id)
	{
		QSpot spot = QSpot.spot;
		QFormat format = QFormat.format;
		QFormattyp formattyp = QFormattyp.formattyp;
		QPreisdetail preisdetail = QPreisdetail.preisdetail;

		// @formatter:off
		SpotExtended spotExt = queryFactory.
			select(Projections.bean(SpotExtended.class,
				spot.id.as("id"),
				spot.hauptspotid.as("hauptspotid"),
				spot.placementid.as("placementid"),
				spot.externeid.as("externeid"),
				spot.campaignid.as("campaignid"),
				spot.campruleid.as("campruleid"),
				spot.externerefid.as("externerefid"),
				spot.fakturadetailid.as("fakturadetailid"),
				spot.gesendet.as("gesendet"),
				spot.grp.as("grp"),
				spot.gueltigid.as("gueltigid"),
				spot.historyid.as("historyid"),
				spot.importinfoid.as("importinfoid"),
				spot.lastimportid.as("lastimportid"),
				spot.lmodified.as("lmodified"),
				spot.lupdate.as("lupdate"),
				spot.luserid.as("luserid"),
				spot.mediumid.as("mediumid"),
				spot.motivid.as("motivid"),
				spot.ndemid.as("ndemid"),
				spot.packageid.as("packageid"),
				spot.position.as("position"),
				spot.preisid.as("preisid"),
				spot.produktid.as("produktid"),
				spot.regionid.as("regionid"),
				spot.sessionid.as("sessionid"),
				spot.spotbuchungid.as("spotbuchungid"),
				spot.spotpg.as("spotpg"),
				spot.spotzgid.as("spotzgid"),
				spot.subauftragid.as("subauftragid"),
				spot.templateid.as("templateid"),
				spot.timeasrun.as("timeasrun"),
				spot.treatstatusid.as("treatstatusid"),
				spot.typid.as("typid"),
				spot.werberahmenid.as("werberahmenid"),
				spot.zubuchfunktionid.as("zubuchfunktionid"),
				spot.zubuchzeit.as("zubuchzeit"),
				spot.formatid.as("formatid"),
				format.wert.as("formatwert"),
				format.formattypid.as("formattypid"),
				format.formatdetailid.as("formatdetailid"),
				formattyp.faktor.as("formatfaktor"),
				preisdetail.listenpreis1.as("listPrice1"),
				preisdetail.listenpreis2.as("listPrice2"),
				preisdetail.virtuell.as("virtual"),
				preisdetail.mediabrutto.as("mediabrutto")))
			.from(spot)
			.join(format).on(spot.formatid.eq(format.id))
			.join(formattyp).on(format.formattypid.eq(formattyp.id))
			.join(preisdetail).on(spot.preisid.eq(preisdetail.id))
			.where(spot.id.eq(id))
			.fetchOne();
		// @formatter:on

		return spotExt;
	}

	/**
	 * <P>
	 * Loads all spots for the given werberahmenId from the database.
	 * <P/>
	 *
	 * @author Alexander Schwartz Cataneo GmbH
	 * 
	 *         Created 03.04.2017
	 *
	 * @param werberahmenId
	 *            the Id of the CommercialBlock, for which the spots should be loaded
	 * 
	 * @return a list of SpotExtended
	 */
	public List<SpotExtended> findSpotExtendedByWerberahmenId(Long werberahmenId)
	{
		QSpot spot = QSpot.spot;
		QFormat format = QFormat.format;
		QFormattyp formattyp = QFormattyp.formattyp;
		QPreisdetail preisdetail = QPreisdetail.preisdetail;

		// @formatter:off
		List<SpotExtended> spotsExt = queryFactory.
			select(Projections.bean(SpotExtended.class,
				spot.id.as("id"),
				spot.hauptspotid.as("hauptspotid"),
				spot.placementid.as("placementid"),
				spot.externeid.as("externeid"),
				spot.campaignid.as("campaignid"),
				spot.campruleid.as("campruleid"),
				spot.externerefid.as("externerefid"),
				spot.fakturadetailid.as("fakturadetailid"),
				spot.gesendet.as("gesendet"),
				spot.grp.as("grp"),
				spot.gueltigid.as("gueltigid"),
				spot.historyid.as("historyid"),
				spot.importinfoid.as("importinfoid"),
				spot.lastimportid.as("lastimportid"),
				spot.lmodified.as("lmodified"),
				spot.lupdate.as("lupdate"),
				spot.luserid.as("luserid"),
				spot.mediumid.as("mediumid"),
				spot.motivid.as("motivid"),
				spot.ndemid.as("ndemid"),
				spot.packageid.as("packageid"),
				spot.position.as("position"),
				spot.preisid.as("preisid"),
				spot.produktid.as("produktid"),
				spot.regionid.as("regionid"),
				spot.sessionid.as("sessionid"),
				spot.spotbuchungid.as("spotbuchungid"),
				spot.spotpg.as("spotpg"),
				spot.spotzgid.as("spotzgid"),
				spot.subauftragid.as("subauftragid"),
				spot.templateid.as("templateid"),
				spot.timeasrun.as("timeasrun"),
				spot.treatstatusid.as("treatstatusid"),
				spot.typid.as("typid"),
				spot.werberahmenid.as("werberahmenid"),
				spot.zubuchfunktionid.as("zubuchfunktionid"),
				spot.zubuchzeit.as("zubuchzeit"),
				spot.formatid.as("formatid"),
				format.wert.as("formatwert"),
				format.formattypid.as("formattypid"),
				format.formatdetailid.as("formatdetailid"),
				formattyp.faktor.as("formatfaktor"),
				preisdetail.listenpreis1.as("listPrice1"),
				preisdetail.listenpreis2.as("listPrice2"),
				preisdetail.virtuell.as("virtual"),
				preisdetail.mediabrutto.as("mediabrutto")))
			.from(spot)
			.join(format).on(spot.formatid.eq(format.id))
			.join(formattyp).on(format.formattypid.eq(formattyp.id))
			.join(preisdetail).on(spot.preisid.eq(preisdetail.id))
			.where(spot.werberahmenid.eq(werberahmenId))
			.fetch();
		// @formatter:on

		return spotsExt;
	}

	public List<SpotExtended> findSpotExtendedByWerberahmenIds(Set<Long> commBlockIds)
	{
		QSpot spot = QSpot.spot;
		QFormat format = QFormat.format;
		QFormattyp formattyp = QFormattyp.formattyp;
		QPreisdetail preisdetail = QPreisdetail.preisdetail;

		// @formatter:off
		List<SpotExtended> spotsExt = queryFactory.
			select(Projections.bean(SpotExtended.class,
				spot.id.as("id"),
				spot.hauptspotid.as("hauptspotid"),
				spot.placementid.as("placementid"),
				spot.externeid.as("externeid"),
				spot.campaignid.as("campaignid"),
				spot.campruleid.as("campruleid"),
				spot.externerefid.as("externerefid"),
				spot.fakturadetailid.as("fakturadetailid"),
				spot.gesendet.as("gesendet"),
				spot.grp.as("grp"),
				spot.gueltigid.as("gueltigid"),
				spot.historyid.as("historyid"),
				spot.importinfoid.as("importinfoid"),
				spot.lastimportid.as("lastimportid"),
				spot.lmodified.as("lmodified"),
				spot.lupdate.as("lupdate"),
				spot.luserid.as("luserid"),
				spot.mediumid.as("mediumid"),
				spot.motivid.as("motivid"),
				spot.ndemid.as("ndemid"),
				spot.packageid.as("packageid"),
				spot.position.as("position"),
				spot.preisid.as("preisid"),
				spot.produktid.as("produktid"),
				spot.regionid.as("regionid"),
				spot.sessionid.as("sessionid"),
				spot.spotbuchungid.as("spotbuchungid"),
				spot.spotpg.as("spotpg"),
				spot.spotzgid.as("spotzgid"),
				spot.subauftragid.as("subauftragid"),
				spot.templateid.as("templateid"),
				spot.timeasrun.as("timeasrun"),
				spot.treatstatusid.as("treatstatusid"),
				spot.typid.as("typid"),
				spot.werberahmenid.as("werberahmenid"),
				spot.zubuchfunktionid.as("zubuchfunktionid"),
				spot.zubuchzeit.as("zubuchzeit"),
				spot.formatid.as("formatid"),
				format.wert.as("formatwert"),
				format.formattypid.as("formattypid"),
				format.formatdetailid.as("formatdetailid"),
				formattyp.faktor.as("formatfaktor"),
				preisdetail.listenpreis1.as("listPrice1"),
				preisdetail.listenpreis2.as("listPrice2"),
				preisdetail.virtuell.as("virtual"),
				preisdetail.mediabrutto.as("mediabrutto")))
			.from(spot)
			.join(format).on(spot.formatid.eq(format.id))
			.join(formattyp).on(format.formattypid.eq(formattyp.id))
			.join(preisdetail).on(spot.preisid.eq(preisdetail.id))
			.where(spot.werberahmenid.in(commBlockIds))
			.orderBy(spot.werberahmenid.asc())
			.fetch();
		// @formatter:on

		return spotsExt;
	}

	public List<Long> findTandemSpotIdsForSpot(Long spotId)
	{
		QSpot spot = QSpot.spot;
		QSpot spot2 = new QSpot("spot2");

		// Added hauptspotid <> 0 to handle cases where the given spot isn't a tandem spot
		// Now correctly returns empty set in these cases
		return queryFactory	.select(spot.id).from(spot)
							.where(spot.hauptspotid.ne(0l).and(spot.hauptspotid.eq(SQLExpressions.select(spot2.hauptspotid).from(spot2).where(spot2.id.eq(spotId))))).fetch();
	}

	/**
	 * <P>
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 21.11.2016
	 *
	 * @param suborderId
	 */
	public void updateHistoryIdForSuborder(Long suborderId)
	{
		QSpot spot = QSpot.spot;
		QSpothistory spothistory = QSpothistory.spothistory;

		SQLUpdateClause update = queryFactory	.update(spot)
												.set(spot.historyid,
													SQLExpressions.select(SQLExpressions.max(spothistory.id)).from(spothistory).where(spothistory.spotid.eq(spot.id)))
												.where(spot.subauftragid.eq(suborderId));
		update.execute();
	}

	/**
	 * <P>
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 24.11.2016
	 *
	 * @param packageId
	 */
	public void updateHistoryIdForPackage(Long packageId)
	{
		QSpot spot = QSpot.spot;
		QSpothistory spothistory = QSpothistory.spothistory;

		SQLUpdateClause update = queryFactory	.update(spot)
												.set(spot.historyid,
													SQLExpressions.select(SQLExpressions.max(spothistory.id)).from(spothistory).where(spothistory.spotid.eq(spot.id)))
												.where(spot.packageid.eq(packageId));
		update.execute();
	}

	/**
	 * <P>
	 * <P/>
	 *
	 * @author Alexander Schwartz Cataneo GmbH
	 * 
	 *         Created 05.05.2017
	 *
	 * @param placementId
	 */
	public void updateHistoryIdForPlacement(Long placementId)
	{
		QSpot spot = QSpot.spot;
		QSpothistory spothistory = QSpothistory.spothistory;

		SQLUpdateClause update = queryFactory	.update(spot)
												.set(spot.historyid,
													SQLExpressions.select(SQLExpressions.max(spothistory.id)).from(spothistory).where(spothistory.spotid.eq(spot.id)))
												.where(spot.placementid.eq(placementId));
		update.execute();
	}

	/**
	 * <P>
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 05.12.2016
	 *
	 * @param suborderId
	 */
	public void lockSpotsBySuborderId(Long suborderId)
	{
		QSpot spot = QSpot.spot;

		SQLUpdateClause update = queryFactory.update(spot).set(spot.lupdate, spot.lupdate).where(spot.subauftragid.eq(suborderId));
		update.execute();
	}

	/**
	 * <P>
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 05.12.2016
	 *
	 * @param packageId
	 */
	public void lockSpotsByPackageId(Long packageId)
	{
		QSpot spot = QSpot.spot;

		SQLUpdateClause update = queryFactory.update(spot).set(spot.lupdate, spot.lupdate).where(spot.packageid.eq(packageId));
		update.execute();
	}

	/**
	 * <P>
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 13.12.2016
	 *
	 * @param hauptSpotID
	 */
	public void lockSpotsByHauptspotId(Long hauptSpotID)
	{
		QSpot spot = QSpot.spot;

		SQLUpdateClause update = queryFactory.update(spot).set(spot.lupdate, spot.lupdate).where(spot.hauptspotid.eq(hauptSpotID));
		update.execute();
	}

	/**
	 * <P>
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 13.12.2016
	 *
	 * @param spotBuchungID
	 */
	public void lockSpotsBySpotBuchungId(Long spotBuchungID)
	{
		QSpot spot = QSpot.spot;

		SQLUpdateClause update = queryFactory.update(spot).set(spot.lupdate, spot.lupdate).where(spot.spotbuchungid.eq(spotBuchungID));
		update.execute();
	}

	/**
	 * <P>
	 * Locks all spots with the given placementId
	 * <P/>
	 *
	 * @author Alexander Schwartz Cataneo GmbH
	 * 
	 *         Created 05.05.2017
	 *
	 * @param placementId
	 *            spots with this placementId will be locked
	 */
	public void lockSpotsByPlacementId(Long placementId)
	{
		QSpot spot = QSpot.spot;

		SQLUpdateClause update = queryFactory.update(spot).set(spot.lupdate, spot.lupdate).where(spot.placementid.eq(placementId));
		update.execute();
	}

	@Override
	public void init()
	{
		super.init();
		cacheDependencyManager.registerDependency("SPOT.ID", "OLD.SPOT.ID", DependencyType.SAME_ID);
	}
}
