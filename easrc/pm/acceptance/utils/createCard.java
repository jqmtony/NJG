package com.kingdee.eas.port.pm.acceptance.utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import chrriis.common.Filter;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.CONSTANT;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleFactory;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.AddressFactory;
import com.kingdee.eas.basedata.assistant.AddressInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.IKAClassfication;
import com.kingdee.eas.basedata.assistant.KAClassficationDetailInfo;
import com.kingdee.eas.basedata.assistant.KAClassficationFactory;
import com.kingdee.eas.basedata.assistant.KAClassficationInfo;
import com.kingdee.eas.basedata.assistant.KAccountItemInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitLayerTypeFactory;
import com.kingdee.eas.basedata.org.OrgUnitLayerTypeInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fi.fa.basedata.FaAlterModeFactory;
import com.kingdee.eas.fi.fa.basedata.FaCatFactory;
import com.kingdee.eas.fi.fa.basedata.FaCatInfo;
import com.kingdee.eas.fi.fa.basedata.FaDepreciationModeFactory;
import com.kingdee.eas.fi.fa.basedata.FaDepreciationModeInfo;
import com.kingdee.eas.fi.fa.basedata.FaEconomicPurp;
import com.kingdee.eas.fi.fa.basedata.FaEconomicPurpCollection;
import com.kingdee.eas.fi.fa.basedata.FaEconomicPurpFactory;
import com.kingdee.eas.fi.fa.basedata.FaUseStatusCollection;
import com.kingdee.eas.fi.fa.basedata.FaUseStatusFactory;
import com.kingdee.eas.fi.fa.basedata.IFaUseStatus;
import com.kingdee.eas.fi.fa.manage.FaBizStatusEnum;
import com.kingdee.eas.fi.fa.manage.FaCardOriginEnum;
import com.kingdee.eas.fi.fa.manage.FaCurAsstActCompositCollection;
import com.kingdee.eas.fi.fa.manage.FaCurAsstActCompositInfo;
import com.kingdee.eas.fi.fa.manage.FaCurCardFactory;
import com.kingdee.eas.fi.fa.manage.FaCurCardInfo;
import com.kingdee.eas.fi.fa.manage.FaCurCostCenterCollection;
import com.kingdee.eas.fi.fa.manage.FaCurCostCenterInfo;
import com.kingdee.eas.fi.fa.manage.FaCurDepartmentFactory;
import com.kingdee.eas.fi.fa.manage.FaCurDepartmentInfo;
import com.kingdee.eas.fi.fa.manage.GroupNumberStatusEnum;
import com.kingdee.eas.fi.fa.manage.client.FaClientUtils;
import com.kingdee.eas.framework.CheckedStatusEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.port.pm.acceptance.CompleteAcceptE1Info;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class createCard {

	public static IObjectPK codeToFaCard(CompleteAcceptE1Info info)
			throws EASBizException, BOSException, SQLException {
		IObjectPK pk = null;

		// �������յ���¼��Ϣ
		String codeID = info.getParent().getId().toString();
		FaCatInfo type = info.getType();// �̶��ʲ����
		MeasureUnitInfo measureUnit = info.getMeasureUnit();// ������λ
		String name = info.getName();// ����
		BigDecimal assetAmt = info.getQuantity();// ����
		String configuration = info.getConfiguration();// ����
		String model = info.getModel();// �ͺ�
		BigDecimal price = info.getPrice();// ����
		BigDecimal subtotal = info.getSubtotal();// С��
		// BigDecimal origin = info.getOrigin();// ԭֵ
		AddressInfo address = info.getLocation();// ���λ��
		AdminOrgUnitInfo management = info.getManagement();// ������
		AdminOrgUnitInfo takeOverDep = info.getTakeOverDep();// �ӹܲ���
		PersonInfo person = info.getResponsible();// ������
		PersonInfo usePerson = info.getUsePerson();// ʹ����
		BigDecimal useLife = info.getUseLife();// ʹ������
		Date CompleteDate = info.getCompleteDate();// ������������

		// �½��ʲ���Ƭ
		FaCurCardInfo cardInfo = new FaCurCardInfo();
		BOSUuid id = BOSUuid.create(cardInfo.getBOSType());
		cardInfo.setId(id);
		if (type != null) {
			cardInfo.setAssetCat(FaCatFactory.getRemoteInstance().getFaCatInfo(
					new ObjectUuidPK(type.getId())));// �ʲ����
			cardInfo.setUseYears(useLife);// ʹ������
			BigDecimal yearBase = new BigDecimal(12);
			cardInfo.setUseTermCount(useLife.multiply(yearBase));// Ԥ��ʹ���ڼ���
		}

		try {
			cardInfo.setAccountDate(getToday());// ʵ����������
			cardInfo.setStartUseDate(getToday());// ��ʼʹ������
			cardInfo.setFiAccountDate(getToday());// ������������
			cardInfo.setOriginMethod(getMethod());// ��Դ��ʽ
			cardInfo.setUseStatus(getFaUseStatus());// ʹ��״̬
			cardInfo.setCurrency(getCurrency());// �ұ�
			// cardInfo.setLevFrDate(getToday());// ��������
			cardInfo.setExRate(new BigDecimal(1));// ����
			cardInfo.setPeriod(SystemStatusCtrolUtils.getCurrentPeriod(null,
					SystemEnum.FIXEDASSETS, getCurrCompany()));// �����ڼ�
		} catch (EASBizException e2) {
			e2.printStackTrace();
		} catch (BOSException e2) {
			e2.printStackTrace();
		}

		cardInfo.setIsOveraged(false);// �Ƿ������ʲ�
		// cardInfo.setEconomicPurp(info.getAssetUse());// ������;

		cardInfo.setAssetName(name);// �ʲ�����
		cardInfo.setStoreCity(address);// ��ŵص�
		cardInfo.setMeasureUnit(MeasureUnitFactory.getRemoteInstance()
				.getMeasureUnitInfo(new ObjectUuidPK(measureUnit.getId())));// ������λ
		cardInfo.setAssetAmt(new BigDecimal(1));// �ʲ�����
		cardInfo.setOriginAmt(price.setScale(2, BigDecimal.ROUND_HALF_UP));// ԭ�ҽ��
		cardInfo.setBuyValue(new BigDecimal(0).setScale(2,
				BigDecimal.ROUND_HALF_UP));// ����ԭֵ
		cardInfo.setAssetValue(price.setScale(2, BigDecimal.ROUND_HALF_UP));// �ʲ�ԭֵ
		cardInfo.setNeatValue(price.setScale(2, BigDecimal.ROUND_HALF_UP));// ��ֵ
		cardInfo.setNeatAmt(price.setScale(2, BigDecimal.ROUND_HALF_UP));// ����

		// ʹ��״̬
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "00101", CompareType.EQUALS));
		FaUseStatusCollection usestatusColl = FaUseStatusFactory
				.getRemoteInstance().getFaUseStatusCollection(evi);
		cardInfo.setUseStatus(usestatusColl.get(0));

		// ʹ����;
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "001", CompareType.EQUALS));
		FaEconomicPurpCollection ecocoll = FaEconomicPurpFactory
				.getRemoteInstance().getFaEconomicPurpCollection(evi);
		cardInfo.setEconomicPurp(ecocoll.get(0));

		// ������
		if (management != null) {
			management = AdminOrgUnitFactory.getRemoteInstance()
					.getAdminOrgUnitInfo(new ObjectUuidPK(management.getId()));
			cardInfo.setDept(management);
			cardInfo.setCompany(getCompanyOrgUnitInfo(management));// ��˾
		}
		cardInfo.setSourceBillId(codeID);
		cardInfo.setSpecs(model);// ����ͺ�
		cardInfo.setMfr(info.getBrand());// ������
		cardInfo.setCheckedStatus(CheckedStatusEnum.UNAUDITED);
		cardInfo.setDeletedStatus(DeletedStatusEnum.NORMAL);
		cardInfo.setEffectedStatus(EffectedStatusEnum.TEMPSTORE);
		cardInfo.setHasEffected(true);
		cardInfo.setOriginFlag(FaCardOriginEnum.ADDNEW);
		cardInfo.setBizStatus(FaBizStatusEnum.ADDING);
		cardInfo.setGroupNumberStatus(GroupNumberStatusEnum.EFFECTED);// ���ű���״̬

		// ��ǰ��Ƭʹ�ò���
		FaCurDepartmentInfo curDep = new FaCurDepartmentInfo();
		curDep.setFaCurCard(cardInfo);
		curDep.setUseDepartment(management);// ʹ�ò���
		curDep.setSeq(1);

		// �̶��ʲ���Ŀ
		IKAClassfication aIKAClassfication = KAClassficationFactory
				.getRemoteInstance();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		// ����Ҫ��
		sic.add(new SelectorItemInfo("detailCollection.accountitemlink.*"));
		// ��Ŀ
		sic.add(new SelectorItemInfo("detailCollection.accountlink.*"));
		sic.add(new SelectorItemInfo(
				"detailCollection.accountlink.companyID.id"));
		KAClassficationInfo aKAClassficationInfo = aIKAClassfication
				.getKAClassficationInfo(new ObjectUuidPK(cardInfo.getAssetCat()
						.getClassfication().getId()), sic);
		if (aKAClassficationInfo.getDetailCollection() != null
				&& aKAClassficationInfo.getDetailCollection().size() > 0) {
			Iterator itr = aKAClassficationInfo.getDetailCollection()
					.iterator();
			int index = 0; // add by wei_xing
			while (itr.hasNext()) {
				KAClassficationDetailInfo aKAClassficationDetailInfo = (KAClassficationDetailInfo) itr
						.next();
				if (aKAClassficationDetailInfo.getAccountitemlink() != null
						&& aKAClassficationDetailInfo.getAccountlink() != null) {
					KAccountItemInfo aKAccountItemInfo = aKAClassficationDetailInfo
							.getAccountitemlink();
					AccountViewInfo accountViewInfo = replaceAccountViewInfo(
							aKAClassficationDetailInfo.getAccountlink(),
							getCurrCompany());
					// �̶��ʲ���Ŀ
					if (aKAccountItemInfo.getNumber().trim().equals("5001")) {
						cardInfo.setAccountAsset(accountViewInfo);
					}
					// �ۼ��۾ɿ�Ŀ
					if (aKAccountItemInfo.getNumber().trim().equals("5002")) {
						cardInfo.setAccountAccuDepr(accountViewInfo);
					}
					// ��ֵ׼����Ŀ
					if (aKAccountItemInfo.getNumber().trim().equals("5003")) {
						cardInfo.setAccountDecValue(accountViewInfo);
					}
				}
			}
		}
		// Ĭ���۾ɷ���ƽ�����޷�(���ھ�ֵ)
		String methodid = "2e193266-00ff-1000-e000-0033c0a812ae568F7C98";
		FaDepreciationModeInfo methodInfo = FaDepreciationModeFactory
				.getRemoteInstance().getFaDepreciationModeInfo(
						new ObjectUuidPK(methodid));
		cardInfo.setDeprMethod(methodInfo);
		FaCurCostCenterCollection costColl = cardInfo.getFaCurCostCenter();
		FaCurCostCenterInfo costInfo = new FaCurCostCenterInfo();
//		String accountViewNumber = getAccountViewNumber(management, cardInfo
//				.getAssetCat());// �۾ɷ��÷�̯��Ŀ����
		costInfo.setAccountView(getAccountViewInfo("660208",
				getCurrCompany().getId().toString()));
		costInfo.setApportionScale(new BigDecimal(100));
		costInfo.setFaCurCard(cardInfo);
	
		costColl.add(costInfo);
		if (cardInfo.getFaCurInitData() == null) {
			cardInfo.put("faCurInitData", cardInfo.getFaCurInitData());
		}

		// �ʲ�����ȡ����ֵ��
		FaCatInfo facat = cardInfo.getAssetCat();
		BigDecimal neatLeftRate = facat.getPrePureRate();
		// �������ֵ��Ϊ��,��Ĭ�Ͼ���ֵ��Ϊ0
		if (neatLeftRate == null) {
			neatLeftRate = new BigDecimal(0);
		}
		cardInfo.setNeatLeftRate(neatLeftRate);
		// ����Ԥ�ƾ���ֵ
		cardInfo.setNeatRemValue(price.multiply(neatLeftRate).divide(
				new BigDecimal(100)));

		String number = getNumber(cardInfo, OrgConstants.DEF_CU_ID);
		cardInfo.setNumber(number);// ���ݱ��
		FaCurDepartmentFactory.getRemoteInstance().save(curDep);
		FaCurCardFactory.getRemoteInstance().save(cardInfo);

		// �������ݱ�(inset ���)
		// Ŀ�굥��IDԴ����ID
		// XRSQLBuilder build = new XRSQLBuilder();
		// String guiid = "newbosid('42AC39EC')";
		// String user = SysContext.getSysContext().getCurrentUserInfo()
		// .getNumber();
		// if ("".equals(user.trim())) {
		// user = "user";
		// }
		// String sql =
		// "/*dialect*/ INSERT INTO T_BOT_Relation(fid,fsrcentityid,fdestentityid,fsrcobjectid,fdestobjectid,fiseffected,fbotmappingid,ftype,foperatorid) "
		// + "VALUES ("
		// + guiid
		// + ",'"
		// + "8B9830CC"
		// + "','"
		// + "42AC39EC"
		// + "','"
		// + codeID
		// + "','"
		// + id
		// + "','0','20W/APEzTqKgrwSX7BwC+wRRIsQ=','0'," + user + ") ";
		// build.appendSql(sql);
		// build.executeUpdate();
		return pk;
	}

	public static Date getToday() throws EASBizException, BOSException {
		// ��õ�ǰ�ڼ�Info
		PeriodInfo currentPeriod = SystemStatusCtrolUtils.getCurrentPeriod(
				null, SystemEnum.FIXEDASSETS, getCurrCompany());
		Date today = new Date(System.currentTimeMillis());
		today = DateTimeUtils.truncateDate(today);
		today = FaClientUtils.getBizDate(getCurrCompany(), currentPeriod);
		return today;
	}

	public static CompanyOrgUnitInfo getCurrCompany() {
		return SysContext.getSysContext().getCurrentFIUnit();
	}

	/**
	 * ȡ��Դ��ʽ - ����
	 * 
	 * @throws BOSException
	 */
	public static com.kingdee.eas.fi.fa.basedata.FaAlterModeInfo getMethod()
			throws BOSException {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "00101", CompareType.EQUALS));
		com.kingdee.eas.fi.fa.basedata.IFaAlterMode ifam = FaAlterModeFactory
				.getRemoteInstance();
		com.kingdee.eas.fi.fa.basedata.FaAlterModeCollection faColl = ifam
				.getFaAlterModeCollection(evi);
		return faColl.get(0);
	}

	public static com.kingdee.eas.fi.fa.basedata.FaUseStatusInfo getFaUseStatus()
			throws BOSException, EASBizException {
		// ��ȡ001-001��ʹ��״̬--����ʹ��
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "001-001", CompareType.EQUALS));
		IFaUseStatus ifam = FaUseStatusFactory.getRemoteInstance();
		FaUseStatusCollection usecoll = ifam.getFaUseStatusCollection(evi);
		return usecoll.get(0);
	}

	/**
	 * ȡ�ұ� - �����
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
			throws EASBizException, BOSException {
		com.kingdee.eas.basedata.assistant.CurrencyCollection currencyCollection = CurrencyFactory
				.getRemoteInstance().getCurrencyCollection(true);
		return currencyCollection.get(0);
	}

	/**
	 * ���ݹ����Ż�ȡ��˾
	 * 
	 * @param adminOrgUnitInfo
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException
	 */
	public static CompanyOrgUnitInfo getCompanyOrgUnitInfo(
			AdminOrgUnitInfo adminOrgUnitInfo) throws BOSException,
			SQLException, EASBizException {
		AdminOrgUnitInfo admin = adminOrgUnitInfo;
		CompanyOrgUnitInfo companyInfo = null;
		while (admin.getParent() != null && !admin.isIsCompanyOrgUnit())
			admin = admin.getParent();
		companyInfo = CompanyOrgUnitFactory.getRemoteInstance()
				.getCompanyOrgUnitInfo(new ObjectUuidPK(admin.getId()));
		return companyInfo;

	}

	public static CompanyOrgUnitInfo getComOrgByAdminOrg(Context ctx,
			AdminOrgUnitInfo adminOrgUnitInfo) throws EASBizException,
			BOSException {
		CompanyOrgUnitInfo costCenterOrgUnitInfo = null;
		AdminOrgUnitInfo parentCost = null;
		parentCost = adminOrgUnitInfo;
		do {
			if (parentCost == null)
				break;
			if (parentCost.isIsCompanyOrgUnit()) {
				String id = parentCost.getId().toString();
				if (id == null)
					continue;
				ICompanyOrgUnit iCompanyOrgUnit = null;
				if (ctx != null)
					iCompanyOrgUnit = CompanyOrgUnitFactory
							.getLocalInstance(ctx);
				else
					iCompanyOrgUnit = CompanyOrgUnitFactory.getRemoteInstance();
				costCenterOrgUnitInfo = (CompanyOrgUnitInfo) iCompanyOrgUnit
						.getValue(new ObjectUuidPK(id));
				break;
			}
			parentCost = parentCost.getParent();
			if (parentCost != null) {
				String id = parentCost.getId().toString();
				if (id != null) {
					IAdminOrgUnit iAdmin = null;
					if (ctx != null)
						iAdmin = AdminOrgUnitFactory.getLocalInstance(ctx);
					else
						iAdmin = AdminOrgUnitFactory.getRemoteInstance();
					parentCost = (AdminOrgUnitInfo) iAdmin
							.getValue(new ObjectUuidPK(id));
				}
			}
		} while (true);
		return costCenterOrgUnitInfo;
	}

	/**
	 * �ᵥR120216-0347��R111216-0372��Ǩ����������ʷ�������⣬���F7�༭ʱ���޸�����м��˷���Я���Ŀ�ĿΪ����֯�Ŀ�Ŀ
	 * �ڱ���֯��Ѱ�ұ��롢������ͬ��������֯���õĿ�Ŀ��Ҷ�ӽڵ��Ŀ��δ���õĿ�Ŀ
	 * 
	 * @param accountViewInfo
	 * @author jilin_qin 2012-03-09
	 * 
	 */
	private static AccountViewInfo replaceAccountViewInfo(
			AccountViewInfo accountViewInfo, CompanyOrgUnitInfo currentCompany) {
		AccountViewInfo curAccountViewInfo = new AccountViewInfo();
		if (currentCompany != null
				&& accountViewInfo.getCompanyID().getId() != null
				&& !accountViewInfo.getCompanyID().getId().equals(
						currentCompany)) {
			String oql = "select * where number='"
					+ accountViewInfo.getNumber() + "' and longName='"
					+ accountViewInfo.getLongName() + "' and companyID='"
					+ currentCompany.getId() + "' and accounttableid='"
					+ currentCompany.getAccountTable().getId()
					+ "' and isleaf=1 and isCFreeze=0";

			try {
				curAccountViewInfo = AccountViewFactory.getRemoteInstance()
						.getAccountViewInfo(oql);
			} catch (EASBizException e) {
			} catch (BOSException e) {
				// ����Ҳ�����¼��˵����ǰ��֯û�����ϼ���Ӧ�Ŀ�Ŀ�����ؿգ���Ԥ���Ŀ�ÿռ���
				if (e instanceof ObjectNotFoundException) {
					return null;
				}
			}
		}

		return curAccountViewInfo;
	}

	/**
	 * ����ʹ�ò������ԣ��ʲ����ʣ���ȡ��Ŀ����
	 * 
	 * @return
	 */
	public static String getAccountViewNumber(AdminOrgUnitInfo infoss,
			FaCatInfo faCatInfo) {
		String accountViewNumber = null;
		if (infoss != null && faCatInfo != null) {
			OrgUnitLayerTypeInfo info = null;
			try {
				info = OrgUnitLayerTypeFactory.getRemoteInstance()
						.getOrgUnitLayerTypeInfo(
								new ObjectUuidPK(infoss.getUnitLayerType()
										.getId()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if ("��Ӫ".equals(info.getName())
					&& "01".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.03.12.01";
			if ("����".equals(info.getName())
					&& "01".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.01.17.01";
			if ("ְ��".equals(info.getName())
					&& "01".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6602.13.01";
			if ("��Ӫ".equals(info.getName())
					&& "02".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.03.12.02";
			if ("����".equals(info.getName())
					&& "02".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.01.17.02";
			if ("ְ��".equals(info.getName())
					&& "02".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6602.13.02";
			if ("��Ӫ".equals(info.getName())
					&& "03".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.03.12.03";
			if ("����".equals(info.getName())
					&& "03".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.01.17.03";
			if ("ְ��".equals(info.getName())
					&& "03".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6602.13.03";
			if ("��Ӫ".equals(info.getName())
					&& "04".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.03.12.04";
			if ("����".equals(info.getName())
					&& "04".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6401.01.17.04";
			if ("ְ��".equals(info.getName())
					&& "04".equals(faCatInfo.getNumber().substring(0, 2)))
				accountViewNumber = "6602.13.04";
		}
		return accountViewNumber;
	}

	/**
	 * ���ݿ�Ŀ���룬��˾��ȡ��Ŀ����
	 * 
	 * @return
	 */
	public static AccountViewInfo getAccountViewInfo(String number,
			String FCOMPANYID) {
		AccountViewInfo info = null;
		String sql = "select fid from T_BD_AccountView where fnumber='"
				+ number + "' and FCOMPANYID='" + FCOMPANYID + "'";
		// FDCSQLBuilder build = new FDCSQLBuilder();
		XRSQLBuilder build = new XRSQLBuilder();
		build.appendSql(sql);
		String id = null;
		try {
			IRowSet set = build.executeQuery();
			while (set.next()) {
				id = set.getString("fid");
			}
			if (id != null)
				info = AccountViewFactory.getRemoteInstance()
						.getAccountViewInfo(new ObjectUuidPK(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * ���ݱ�������ȡ���룬���쳣��ʾ�ġ�
	 * 
	 * @param info
	 * @param companyId
	 * @return
	 */
	public static String getNumber(CoreBaseInfo info, String companyId) {
		if ((info == null) || (companyId == null) || companyId.equals("")) {
			return null;
		}
		String result = null;
		try {
			ICodingRuleManager codingRuleManager = CodingRuleManagerFactory
					.getRemoteInstance();
			result = codingRuleManager.getNumber(info, companyId);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		if (result != null && result.equals("")) {
			result = null;
		}
		return result;
	}
}
