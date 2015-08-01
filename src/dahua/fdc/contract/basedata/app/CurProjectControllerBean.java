package com.kingdee.eas.fdc.basedata.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.assistant.IProject;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.ProjectStatus;
import com.kingdee.eas.basedata.assistant.ProjectTypeEnum;
import com.kingdee.eas.basedata.master.auxacct.app.AssistUtil;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.HisProjectCollection;
import com.kingdee.eas.fdc.basedata.HisProjectFactory;
import com.kingdee.eas.fdc.basedata.HisProjectInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICurProjProductEntries;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.IHisProject;
import com.kingdee.eas.fdc.basedata.IIncomeAccount;
import com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectFacadeFactory;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.IPaymentSplit;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.TraceOldSplitVoucherFacadeFactory;
import com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * ����: *
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class CurProjectControllerBean extends AbstractCurProjectControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.CurProjectControllerBean");

	private static final String ANTIGETTED = "antiGetted";

	private static final String ANTIFLOW = "antiFlow";

	private static final String CLOSE = "close";

	private static final String ANTICLOSE = "antiClose";
	
	private static final String TRANSFER = "transfer";
	/**
	 * 
	 * ������ͬ����������±�׼��Ŀ
	 * @param cpi
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * ����ʱ�䣺2010-9-13
	 * �����ˣ�zhiqiao_yang
	 */
	private void saveBaseProject(CurProjectInfo projectInfo, CurProjectInfo parentInfo, Context ctx, boolean isNeedUpdateID)throws BOSException, EASBizException {
		IObjectPK objectPK = new ObjectUuidPK(projectInfo.getId());
		IProject ip = ProjectFactory.getLocalInstance(ctx);
		ProjectInfo pi = null;
		if(!StringUtils.isEmpty(projectInfo.getBdProjectID())){
			IObjectPK bdProjectPK = new ObjectStringPK(projectInfo.getBdProjectID());
			if(ip.exists(bdProjectPK)){
				pi = ip.getProjectInfo(bdProjectPK);
			}
		}
		if(pi == null){
			pi = new ProjectInfo();
		}
		String orgId = null;
		if(projectInfo.getFullOrgUnit()==null){
			orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		}else{
			orgId = projectInfo.getFullOrgUnit().getId().toString();
		}
		
		IObjectPK cpk = new ObjectUuidPK(BOSUuid.read(orgId));
		ICompanyOrgUnit icompany = null;
		CompanyOrgUnitInfo companyinfo = null;
		icompany = CompanyOrgUnitFactory.getLocalInstance(ctx);
		companyinfo = (CompanyOrgUnitInfo) icompany.getCompanyOrgUnitInfo(cpk);
		pi.setCompany(companyinfo);// ��Ŀ������˾
		pi.setName(projectInfo.getName());
		String orgNumber = companyinfo.getNumber();
		String longNumber = projectInfo.getLongNumber();
		if (longNumber != null && longNumber.length() > 0) {
			longNumber = longNumber.replace('!', '.');
		}
		
		// ����'������Ŀͬ�������������е���Ŀʱ�Ƿ���ʾ��֯����'����������׼��Ʒ��Ŀ�ı���
		String fdcParamValue = ParamControlFactory.getLocalInstance(ctx)
				.getParamValue(null,
						FDCConstants.FDC_PARAM_ISSHOWORGTOPROJECTNUM);
		boolean isShowOrg = Boolean.valueOf(fdcParamValue).booleanValue();
		if (isShowOrg) {
			pi.setNumber(orgNumber + longNumber);
		} else {
			pi.setNumber(longNumber);
		}
		
		pi.setStatus(ProjectStatus.EXECUTE);// ��Ŀ״̬
		
		IObjectPK projectPK = null;
		IObjectPK parentProjectPK = null;
		if (projectInfo.getLevel() == 1) {// ��������Ŀ
			pi.setType(ProjectTypeEnum.CUS_PROJECT);// ��Ŀ����
			pi.setIsListItem(true);

			projectPK = ip.save(pi);
			if (projectPK != null) {
				String s = "update t_fdc_curProject set FBdProjectID = ? where FID = ?";
				DbUtil.execute(ctx, s, new Object[] { projectPK.toString(), 	objectPK.toString() });
			}
		} else {// �Ǹ���Ŀ
			// ���ϼ�,������֯�볤����			
			if (parentInfo!=null&&parentInfo.getBdProjectID()!=null) {
				String parentProjectId = parentInfo.getBdProjectID();
				IObjectPK iopk = new ObjectUuidPK(BOSUuid.read(parentProjectId));
				if(!ip.exists(iopk)) return;//�������ڸ�������Ŀ��������������»�����Ŀ
				ProjectInfo ppi = ip.getProjectInfo(iopk);
				pi.setParent(ppi);
				pi.setType(ppi.getType());// //����Ŀ��������Ŀʱ������Ŀ�̳��ϼ���Ŀ������
				
				parentProjectPK = iopk;
				ppi.setIsListItem(false);
				ip.save(iopk, ppi);

				pi.setIsListItem(true);
				IObjectPK objectPK2 = ip.save(pi);
				projectPK = objectPK2;
				if (objectPK2 != null) {
					String s = "update t_fdc_curProject set FBdProjectID = ? where FID = ?";
					DbUtil.execute(ctx, s, new Object[] { objectPK2.toString(),	objectPK.toString() });
					if(!isNeedUpdateID){
						String upsql = "update T_BD_AssistantHg set fProjectid=? where fProjectid=?";
						DbUtil.execute(ctx,upsql,new Object[]{objectPK2.toString(),ppi.getId().toString()});
						AssistUtil.updateAssist(ctx,objectPK2.toString(),pi.getBOSType());
					}
				}
			}
			// else {
			// //��ͬ��
			// }
		}
		/***
		 * ����curproject��Ӧ�Ļ�����Ŀid
		 */
		String changeIdSql = "update t_fdc_curProject set FBdProjectID = ? where FID = ?";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(parentInfo != null &&  parentProjectPK!= null){
			IObjectPK parentPK = new ObjectUuidPK(parentInfo.getId());
			builder.appendSql(changeIdSql);
			builder.addParam(parentProjectPK.toString());
			builder.addParam(parentPK.toString());
			builder.executeUpdate();
			builder.clear();
		}
		if( projectPK!= null){
			builder.appendSql(changeIdSql);
			builder.addParam(projectPK.toString());
			builder.addParam(objectPK.toString());
			builder.executeUpdate();
		}
	}
	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		CurProjectInfo cpi = (CurProjectInfo) model;
		// ����в�Ʒ��¼,����֮
		if (cpi.getCurProjProductEntries() != null) {
			ICurProjProductEntries iCurProjProductEntries = CurProjProductEntriesFactory
					.getLocalInstance(ctx);
			CurProjProductEntriesCollection curProjProductEntriesCollection = new CurProjProductEntriesCollection();
			curProjProductEntriesCollection = ((CurProjectInfo) model)
					.getCurProjProductEntries();
			Iterator iteratorEntry = curProjProductEntriesCollection.iterator();
			CurProjProductEntriesInfo curProjProductEntriesInfo;
			while (iteratorEntry.hasNext()) {
				curProjProductEntriesInfo = (CurProjProductEntriesInfo) iteratorEntry
						.next();
				curProjProductEntriesInfo.setCurProject(cpi);
				iCurProjProductEntries.addnew(curProjProductEntriesInfo);
			}
		}
		// ������Ŀ��������
		CurProjectInfo projectInfo = (CurProjectInfo) model;
		String number = projectInfo.getLongNumber();
		if (projectInfo.getLongNumber().indexOf(".") > 0) {// ��ǰ������ĿΪ���ڻ��߷�������
			if (projectInfo.getLongNumber().indexOf(".") == projectInfo
					.getLongNumber().lastIndexOf(".")) {
				number = projectInfo.getNumber();
				((CurProjectInfo) model).setProjectPeriod(number);
			} else {
				number = projectInfo.getLongNumber();
				int b = number.indexOf(".");
				number = number.substring(number.indexOf(".") + 1, number
						.length());
				number = number.substring(0, number.indexOf("."));
			}
			((CurProjectInfo) model).setProjectPeriod(number);
		}
		
		cpi.setCodingNumber(projectInfo.getLongNumber().replace('!','.'));
		super._addnew(ctx, pk, model);
		cpi.setId(BOSUuid.read(pk.toString()));
		
		// ͬ��������׼��Ŀ
		if (cpi.getLevel() == 1) {// ��������Ŀ
			ProjectInfo pi = new ProjectInfo();
			IObjectPK cpk = new ObjectUuidPK(BOSUuid.read(cpi.getFullOrgUnit()
					.getId().toString()));
			ICompanyOrgUnit icompany = null;
			CompanyOrgUnitInfo companyinfo = null;
			icompany = CompanyOrgUnitFactory.getLocalInstance(ctx);
			companyinfo = (CompanyOrgUnitInfo) icompany
					.getCompanyOrgUnitInfo(cpk);
			pi.setCompany(companyinfo);// ��Ŀ������˾
			pi.setName(cpi.getName());
			pi.setNumber(cpi.getNumber());
			pi.setType(ProjectTypeEnum.CUS_PROJECT);// ��Ŀ����
			pi.setStatus(ProjectStatus.PREPARE);// ��Ŀ״̬
			pi.setDescription(cpi.getDescription());
			IProject ip = ProjectFactory.getLocalInstance(ctx);
			ip.addnew(pi);
		} else {// �Ǹ���Ŀ

		}
		// //ָ�����
		// this.refreshGatherData(ctx, cpi.getParent());
		// HashMap hm = new HashMap();
		// CurProjCostEntriesCollection cpcec = cpi.getCurProjCostEntries();
		// if(cpcec.size()!=0){
		// CurProjCostEntriesInfo cpcei ;
		// for(int i = 0;i<cpcec.size();i++){
		// cpcei = cpcec.get(i);
		// hm.put(cpcei.getApportionType().getId().toString(),cpcei.getValue());
		// }
		// }
		// if(hm.size()!=0){
		// CurProjectInfo parent ;
		// CurProjCostEntriesCollection parentCpcec;
		// SelectorItemCollection selector = new SelectorItemCollection();
		// selector.add(new SelectorItemInfo("id"));
		// selector.add(new SelectorItemInfo("curProjCostEntries.*"));
		// selector.add(new
		// SelectorItemInfo("curProjCostEntries.apportionType.*"));
		// selector.add(new SelectorItemInfo("curProjCostEntries.value"));
		// if(cpi.getParent()!=null){//Ҷ���
		// parent = this.getCurProjectInfo(ctx,new
		// ObjectUuidPK(cpi.getParent().getId().toString()),selector);
		// parentCpcec = parent.getCurProjCostEntries();
		// if(parentCpcec.size()!=0){
		//					
		// }
		// }
		// }
		
		//������Ŀ״̬�ڼ����
		ProjectPeriodStatusUtil._save(ctx,pk,model);
				
		//
		updateProject(ctx, ((CurProjectInfo) model));
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK objectPK = null;
		IObjectPK parentPK = null;
		// ������Ŀ��������
		boolean isNeedUpdateID = false;
		CurProjectInfo projectInfo = (CurProjectInfo) model;
		projectInfo.setCodingNumber(projectInfo.getLongNumber().replace('!','.'));
		
		if(projectInfo.getFullOrgUnit()==null){
			projectInfo.setFullOrgUnit(ContextUtil.getCurrentFIUnit(ctx).castToFullOrgUnitInfo());
		}
		
		String number = projectInfo.getLongNumber();
		if (projectInfo.getLongNumber().indexOf(".") > 0) {// ��ǰ������ĿΪ���ڻ��߷�������
			if (projectInfo.getLongNumber().indexOf(".") == projectInfo
					.getLongNumber().lastIndexOf(".")) {
				number = projectInfo.getNumber();
				((CurProjectInfo) model).setProjectPeriod(number);
			} else {
				number = projectInfo.getLongNumber();
				int b = number.indexOf(".");
				number = number.substring(number.indexOf(".") + 1, number
						.length());
				number = number.substring(0, number.indexOf("."));
			}
			((CurProjectInfo) model).setProjectPeriod(number);
		}
		CurProjectInfo parentInfo = null;
		if (projectInfo.getParent() != null) {
//			if(1==1)
//				throw new BOSException();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			parentInfo = (CurProjectInfo)CurProjectFactory
					.getLocalInstance(ctx).getValue(new ObjectUuidPK(projectInfo.getParent().getId().toString()),sic);
			
			projectInfo.setIsEnabled(parentInfo.isIsEnabled());
		}
		
		objectPK = super._addnew(ctx, projectInfo);
		projectInfo.setId(BOSUuid.read(objectPK.toString()));
		
		// ����в�Ʒ��¼,����֮
		if (((CurProjectInfo) model).getCurProjProductEntries() != null) {
			CurProjectInfo curProjectInfo = (CurProjectInfo) model;
			ICurProjProductEntries iCurProjProductEntries = CurProjProductEntriesFactory
					.getLocalInstance(ctx);

			CurProjProductEntriesCollection curProjProductEntriesCollection = new CurProjProductEntriesCollection();
			curProjProductEntriesCollection = ((CurProjectInfo) model)
					.getCurProjProductEntries();
			Iterator iteratorEntry = curProjProductEntriesCollection.iterator();
			CurProjProductEntriesInfo curProjProductEntriesInfo;

			curProjectInfo.put("id", objectPK.getKeyValue("id"));
			while (iteratorEntry.hasNext()) {
				curProjProductEntriesInfo = (CurProjProductEntriesInfo) iteratorEntry
						.next();
				curProjProductEntriesInfo.setCurProject(curProjectInfo);
				iCurProjProductEntries.addnew(curProjProductEntriesInfo);
			}

		}

		// //ָ�����
		// this.refreshGatherData(ctx, ((CurProjectInfo) model).getParent());

		// ͬ������һ����ʷ�汾������Ŀ��Ϣ
		// IHisProject iHisProject = HisProjectFactory.getLocalInstance(ctx);
		// HisProjectInfo hisProjectInfo =
		// this.transFromCurToHis((CurProjectInfo) model);
		// // FullOrgUnitInfo foui =
		// FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new
		// ObjectUuidPK(((CurProjectInfo)model).getFullOrgUnit().getId().toString()));
		// hisProjectInfo.setVersionNumber("V1.0");
		// hisProjectInfo.setVersionName("V1.0");
		// iHisProject.addnew(hisProjectInfo);
		// ������ϼ�
		if (((CurProjectInfo) model).getParent() != null) {
			
			//������Ŀ״̬�ڼ����
			String parentid = ((CurProjectInfo) model).getParent().getId().toString();
			/***
			 * ���������Ŀ��Ҷ�ӽڵ㣬˵�����ܷ�����ҵ�����ݣ�����Ʒ��¼����ҵ������,������Ŀ�µĳɱ���ĿҲ������ҵ������
			 * ���Բ���reference�ж� 
			 */
			parentPK = new ObjectUuidPK(((CurProjectInfo) model).getParent().getId());
			//CurProjectInfo parentInfo = this.getCurProjectInfo(ctx,parentPK);
			if (parentInfo.isIsLeaf()) {
				Map ref = ProjectFacadeFactory.getLocalInstance(ctx).canAddNew(parentPK.toString());
				if (ref.containsKey("HASUSED")&&((Boolean)(ref.get("HASUSED"))).booleanValue()) {
					/***
					 * ���������˵����Ҫ����ID
					 */
					isNeedUpdateID = true;
				}
			}
			if(!isNeedUpdateID)
			{
				ProjectPeriodStatusUtil._delete(ctx,new ObjectUuidPK(parentid));
				//��������Ϣͬ��			
//				updateAssitHg(ctx,objectPK.toString(),parentid);			
				String upsql = "update T_BD_AssistantHg set fcurProjectid=? where fcurProjectid=?";
				DbUtil.execute(ctx,upsql,new Object[]{objectPK.toString(),parentid});
				AssistUtil.updateAssist(ctx,objectPK.toString(),projectInfo.getBOSType());
			}
			
			// ͬ������ɱ���Ŀ����
			// ���������� R100527-103������ʱ����Ҫ����ɱ���Ŀ������ע���¾䡣By Owen_wen 2011-02-25
			// synCostAccount(ctx, model, objectPK);

			// ͬ�����������Ŀ
			synIncomAccount(ctx, model, objectPK);
		} else {
			// ���û���ϼ�,���˵����,�ֶ�����(����֯��������Ŀ)
		}
		
		// ͬ��������׼��Ŀ
		saveBaseProject(projectInfo, parentInfo, ctx, isNeedUpdateID);
		// ����֮��Ҫ�Ѷ�Ӧ������Ŀ����Ӫ����Ķ�Ӧ�ɱ���Ŀflag��־��Ϊ1

		String sql = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx, sql);

		String sqlOR = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx, sqlOR);
		
		//������Ŀ״̬�ڼ����
		if (!isNeedUpdateID)
			ProjectPeriodStatusUtil._save(ctx,objectPK,model);

		
		updateProject(ctx, ((CurProjectInfo) model));
		/***
		 * ��Ҫ������Id,����Id
		 */
		if(isNeedUpdateID){
			/**
			 * ����һ����ʱID
			 */
			String tempId = BOSUuid.create(((CurProjectInfo) model).getBOSType()).toString(); 
			String changeIdSql = "update t_fdc_curProject set fid=? where fid=?";
			List params=new ArrayList();
			params.add(Arrays.asList(new String[]{tempId,parentPK.toString()}));
			params.add(Arrays.asList(new String[]{parentPK.toString(),objectPK.toString()}));
			params.add(Arrays.asList(new String[]{objectPK.toString(),tempId}));
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.executeBatch(changeIdSql, params);
			/**
			 * ���������ɵ���Ŀ����Ŀ״̬���ϼ���Ŀͬ
			 */
			builder.clear();
			builder.appendSql("update T_fdc_curProject set fprojectStatusid=(select fprojectStatusid from T_fdc_curProject where fid=?),fparentid=? where fid=?");
			builder.addParam(objectPK.toString());
			builder.addParam(objectPK.toString());
			builder.addParam(parentPK.toString());
			builder.execute();
			
			/***
			 * ���³ɱ����ĵĶ�Ӧ��ϵ
			 */
			String updateCostCenterSql = "update t_fdc_curProject set fcostcenterid=(select fcostcenterid from t_fdc_curProject where fid=?) where fid=? ";
			builder.clear();
			builder.appendSql(updateCostCenterSql);
			builder.addParam(objectPK.toString());
			builder.addParam(parentPK.toString());
			builder.executeUpdate();
			//�����¼��Ĺ�����Ŀ��ɱ����ĵĶ�Ӧ��ϵ����
			builder.clear();
			builder.appendSql("update t_Fdc_Projectwithcostcenterou set fcurProjectid=? where fcurProjectid=?");
			builder.addParam(objectPK.toString());
			builder.addParam(parentPK.toString());
			
/*			updateCostCenterSql = "update t_fdc_curproject set fcostcenterid=null where fid=?";
			builder.clear();
			builder.appendSql(updateCostCenterSql);
			builder.addParam(objectPK.toString());*/
			builder.executeUpdate();
			return parentPK;
		}
		return objectPK;
	}
	
	/**
	 * ͬ�����������Ŀ
	 * @param ctx
	 * @param model
	 * @param objectPK
	 * @throws BOSException
	 * @throws SQLDataException
	 */
	private void synIncomAccount(Context ctx, IObjectValue model, IObjectPK objectPK) throws BOSException, SQLDataException {
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", ((CurProjectInfo) model).getParent().getId().toString(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true), CompareType.EQUALS));
		evi.setFilter(filter);
		SorterItemInfo sortItemInfo = new SorterItemInfo("longNumber");
		sortItemInfo.setSortType(SortType.ASCEND);
		evi.getSorter().add(sortItemInfo);
		IncomeAccountCollection incomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
		// ����
		if (incomeAccountCollection.size() != 0) {
			String insert_sql = "insert into t_fdc_incomeAccount(FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, FNAME_L1, FNAME_L2, FNAME_L3, FNUMBER, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, FSIMPLENAME, FISLEAF, FLEVEL, FLONGNUMBER, FDISPLAYNAME_L1, FDISPLAYNAME_L2, FDISPLAYNAME_L3, FASSIGNED, FISENABLE, FCURPROJECT, FPARENTID, FFULLORGUNIT, FISSOURCE, FSRCINCOMEACCOUNTID, FCodingNumber) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Map parentMap = new HashMap();
			Connection connection = null;
			PreparedStatement statement = null;
			try {
				connection = getConnection(ctx);
				statement = connection.prepareStatement(insert_sql);
				Timestamp now = new Timestamp(System.currentTimeMillis());
				String userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();

				Locale l1 = new Locale("L1");
				Locale l2 = new Locale("L2");
				Locale l3 = new Locale("L3");
				String projId = objectPK.toString();
				for (int i = 0; i < incomeAccountCollection.size(); i++) {
					IncomeAccountInfo info = (IncomeAccountInfo) incomeAccountCollection.get(i);
					BOSUuid uuid = BOSUuid.create(info.getBOSType());
					String id = uuid.toString();
					String longNumber = info.getLongNumber();

					int x = 1;

					statement.setString(x++, id); // id
					statement.setString(x++, userId); // creator
					statement.setTimestamp(x++, now); // createtime
					statement.setString(x++, userId); // lastupduser
					statement.setTimestamp(x++, now); // lastupdtime
					statement.setString(x++, info.getCU().getId().toString()); // cu

					statement.setString(x++, info.getName(l1)); // name1
					statement.setString(x++, info.getName(l2)); // name2
					statement.setString(x++, info.getName(l3)); // name3
					statement.setString(x++, info.getNumber()); // number
					statement.setString(x++, info.getDescription(l1)); // desc1
					statement.setString(x++, info.getDescription(l2)); // desc2
					statement.setString(x++, info.getDescription(l3)); // desc3
					statement.setString(x++, info.getSimpleName()); // simplename
					statement.setBoolean(x++, info.isIsLeaf()); // isLeaf
					statement.setInt(x++, info.getLevel()); // level
					statement.setString(x++, longNumber); // longnumber
					statement.setString(x++, info.getDisplayName(l1)); // dispName1
					statement.setString(x++, info.getDisplayName(l2)); // dispname2
					statement.setString(x++, info.getDisplayName(l3)); // dispname3
					statement.setBoolean(x++, false); // isassigned
					statement.setBoolean(x++, info.isIsEnabled()); // enabled
					statement.setString(x++, projId); // curproject
					if (info.getParent() != null) {
						String ln = longNumber.substring(0, longNumber.lastIndexOf('!'));
						String pId = (String) parentMap.get(ln);
						statement.setString(x++, pId); // parentid
					} else {
						statement.setString(x++, null);
					}
					statement.setString(x++, null); // fullorgunit
					statement.setBoolean(x++, false); // issource
					statement.setString(x++, info.isIsSource() ? info.getId().toString() : info.getSrcIncomeAccountId());	//FSRCCOSTACCOUNTID
					statement.setString(x++, info.getCodingNumber()); // codingNumber  by sxhong 2008-10-20 18:38:43
					parentMap.put(info.getLongNumber(), id);

					statement.addBatch();
				}
				parentMap.clear();
				statement.executeBatch();
			} catch (SQLException ex) {
				throw new BOSException(ex);
			} finally {
				try {
					statement.close();
					connection.close();
				} catch (SQLException ex) {
					throw new BOSException(ex);
				}
			}
		}
	}
	
	
	/**
	 * ͬ������ɱ���Ŀ����
	 * 
	 * @param ctx
	 * @param model
	 * @param objectPK
	 * @throws BOSException
	 * @throws SQLDataException
	 */
	private void synCostAccount(Context ctx, IObjectValue model, IObjectPK objectPK) throws BOSException, SQLDataException {
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", ((CurProjectInfo) model).getParent().getId().toString(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true), CompareType.EQUALS));
		evi.setFilter(filter);
		SorterItemInfo sortItemInfo = new SorterItemInfo("longNumber");
		sortItemInfo.setSortType(SortType.ASCEND);
		evi.getSorter().add(sortItemInfo);
		CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);

		// ����
		if (costAccountCollection.size() != 0) {
			String insert_sql = "insert into t_fdc_costAccount(FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, FNAME_L1, FNAME_L2, FNAME_L3, FNUMBER, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, FSIMPLENAME, FISLEAF, FLEVEL, FLONGNUMBER, FDISPLAYNAME_L1, FDISPLAYNAME_L2, FDISPLAYNAME_L3, FASSIGNED, FISENABLE, FCURPROJECT, FPARENTID, FFULLORGUNIT, FISSOURCE, FSRCCOSTACCOUNTID, FTYPE,FIsCostAccount, FCodingNumber) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Map parentMap = new HashMap();
			Connection connection = null;
			PreparedStatement statement = null;
			try {
				connection = getConnection(ctx);
				statement = connection.prepareStatement(insert_sql);
				Timestamp now = new Timestamp(System.currentTimeMillis());
				String userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();

				Locale l1 = new Locale("L1");
				Locale l2 = new Locale("L2");
				Locale l3 = new Locale("L3");
				String projId = objectPK.toString();
				for (int i = 0; i < costAccountCollection.size(); i++) {
					CostAccountInfo info = (CostAccountInfo) costAccountCollection.get(i);
					BOSUuid uuid = BOSUuid.create(info.getBOSType());
					String id = uuid.toString();
					String longNumber = info.getLongNumber();

					int x = 1;

					statement.setString(x++, id); // id
					statement.setString(x++, userId); // creator
					statement.setTimestamp(x++, now); // createtime
					statement.setString(x++, userId); // lastupduser
					statement.setTimestamp(x++, now); // lastupdtime
					statement.setString(x++, info.getCU().getId().toString()); // cu

					statement.setString(x++, info.getName(l1)); // name1
					statement.setString(x++, info.getName(l2)); // name2
					statement.setString(x++, info.getName(l3)); // name3
					statement.setString(x++, info.getNumber()); // number
					statement.setString(x++, info.getDescription(l1)); // desc1
					statement.setString(x++, info.getDescription(l2)); // desc2
					statement.setString(x++, info.getDescription(l3)); // desc3
					statement.setString(x++, info.getSimpleName()); // simplename
					statement.setBoolean(x++, info.isIsLeaf()); // isLeaf
					statement.setInt(x++, info.getLevel()); // level
					statement.setString(x++, longNumber); // longnumber
					statement.setString(x++, info.getDisplayName(l1)); // dispName1
					statement.setString(x++, info.getDisplayName(l2)); // dispname2
					statement.setString(x++, info.getDisplayName(l3)); // dispname3
					statement.setBoolean(x++, false); // isassigned
					statement.setBoolean(x++, info.isIsEnabled()); // enabled
					statement.setString(x++, projId); // curproject
					if (info.getParent() != null) {
						String ln = longNumber.substring(0, longNumber.lastIndexOf('!'));
						String pId = (String) parentMap.get(ln);
						statement.setString(x++, pId); // parentid
					} else {
						statement.setString(x++, null);
					}
					statement.setString(x++, null); // fullorgunit
					statement.setBoolean(x++, false); // issource
					statement.setString(x++, info.isIsSource() ? info.getId().toString() : info.getSrcCostAccountId()); // FSRCCOSTACCOUNTID
					String type = info.getType() == null ? null : info.getType().getValue();
					statement.setString(x++, type); // TYPE
					statement.setBoolean(x++, info.isIsCostAccount()); // isIsCostAccount
					statement.setString(x++, info.getCodingNumber()); // codingNumber
					parentMap.put(info.getLongNumber(), id);

					statement.addBatch();
				}

				parentMap.clear();

				statement.executeBatch();
			} catch (SQLException ex) {
				throw new BOSException(ex);
			} finally {
				try {
					statement.close();
					connection.close();
				} catch (SQLException ex) {
					throw new BOSException(ex);
				}
			}
		}
	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		CurProjectInfo curProjectInfo = (CurProjectInfo) model;
		CurProjectInfo tmp = this.getCurProjectInfo(ctx, pk);// δ�����޸�֮ǰ�Ķ���
		ICurProjProductEntries iCurProjProductEntries = CurProjProductEntriesFactory
				.getLocalInstance(ctx);
		iCurProjProductEntries.delete("where curProject.id = '"
				+ curProjectInfo.getId().toString() + "'");
		// ����в�Ʒ��¼,����֮
		if ((curProjectInfo).getCurProjProductEntries() != null) {
			CurProjProductEntriesCollection curProjProductEntriesCollection = new CurProjProductEntriesCollection();
			curProjProductEntriesCollection = ((CurProjectInfo) model)
					.getCurProjProductEntries();
			Iterator iteratorEntry = curProjProductEntriesCollection.iterator();
			CurProjProductEntriesInfo curProjProductEntriesInfo;
			while (iteratorEntry.hasNext()) {
				curProjProductEntriesInfo = (CurProjProductEntriesInfo) iteratorEntry
						.next();
				curProjProductEntriesInfo.setCurProject(curProjectInfo);
				iCurProjProductEntries.addnew(curProjProductEntriesInfo);
			}
		}
		// �޸ĵ�����Ƿ��ڱ���Ļ�,Ҫͬ���޸��¼�����Ŀ�����ֶ�Ϊ�µķ��ڱ���
		// CurProjectInfo projectInfo = (CurProjectInfo) model;
		if (curProjectInfo.getLevel() == 2) {
			curProjectInfo.setProjectPeriod(curProjectInfo
					.getNumber());
			String sql = "update T_FDC_CurProject set  FProjectPeriod = ?  where FLongNumber like ? ";
			Object[] params = new Object[] { curProjectInfo.getNumber(),
					curProjectInfo.getLongNumber().replace('.', '!') + "!%" };
			DbUtil.execute(ctx, sql, params);
		}

		// ͬ����Ŀϵ���ֶ�
		if (curProjectInfo.getLevel() == 1) {
			String sql = "update T_FDC_CurProject set  FProjectTypeID = ?  where FLongNumber like ? ";
			Object[] params = new Object[] {
					curProjectInfo.getProjectType().getId().toString(),
					curProjectInfo.getLongNumber().replace('.', '!') + "!%" };
			DbUtil.execute(ctx, sql, params);
		}
		// String number = projectInfo.getLongNumber();
		// if(projectInfo.getLongNumber().indexOf(".")>0){//��ǰ������ĿΪ���ڻ��߷�������
		// if(projectInfo.getLongNumber().indexOf(".")==projectInfo.getLongNumber().lastIndexOf(".")){
		// number = projectInfo.getNumber();
		// ((CurProjectInfo)model).setProjectPeriod(number);
		// }else{
		// number = projectInfo.getLongNumber();
		// int b = number.indexOf(".");
		// number = number.substring(number.indexOf(".")+1,number.length());
		// number = number.substring(0,number.indexOf("."));
		// }
		// ((CurProjectInfo)model).setProjectPeriod(number);
		// }

		//curProjectInfo.setCodingNumber(curProjectInfo.getLongNumber().replace('!','.'));
		
		super._update(ctx, pk, curProjectInfo);
		CurProjectInfo parentCurProject = curProjectInfo.getParent();
		if(parentCurProject != null && StringUtils.isEmpty(parentCurProject.getBdProjectID())){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("id"));
			selector.add(new SelectorItemInfo("bdProjectID"));
			
			parentCurProject = getCurProjectInfo(ctx, new ObjectUuidPK(parentCurProject.getId()), selector);
		}
		saveBaseProject(curProjectInfo, parentCurProject, ctx, false);
		AssistUtil.updateAssist(ctx,pk.toString(),curProjectInfo.getBOSType());

		ProjectPeriodStatusUtil._update(ctx,pk,curProjectInfo);
		
		updateProject(ctx, ((CurProjectInfo) curProjectInfo));
		// CurProjectInfo parent = ((CurProjectInfo) model).getParent();
		// ����ָ��
		// this.refreshGatherData(ctx, parent);

		// ����һ��������Ŀ��ʷ�汾����
		// �õ�ǰ�����滻��ǰ��ʷ�汾
		// IHisProject iHisProject = HisProjectFactory.getLocalInstance(ctx);
		// HisProjectInfo lastHisProjectInfo = getLastHisProject(ctx,
		// curProjectInfo.getId().toString());
		// //����Ŀǰ�汾���������Ʋ�Ʒ��¼���ݲ��԰汾��Ϣ�Ĳ�Ʒ��¼���и��²���
		// if (lastHisProjectInfo != null) {
		// for (int i = 0; i < curProjectInfo.getCurProjCostEntries().size();
		// i++) {
		// for (int j = 0; j <
		// lastHisProjectInfo.getHisProjCostEntries().size(); j++) {
		// if (curProjectInfo.getCurProjCostEntries().get(i).getApportionType()
		// != null &&
		// lastHisProjectInfo.getHisProjCostEntries().get(j).getApportionType()
		// != null) {
		// if
		// (curProjectInfo.getCurProjCostEntries().get(i).getApportionType().getId().toString().equals(
		// lastHisProjectInfo.getHisProjCostEntries().get(j).getApportionType().getId().toString()))
		// {
		// lastHisProjectInfo.getHisProjCostEntries().get(j).setValue(curProjectInfo.getCurProjCostEntries().get(i).getValue());
		// continue;
		// }
		// }
		// }
		// }
		// // SelectorItemCollection selector = new SelectorItemCollection();
		// // selector.add(new
		// SelectorItemInfo("hisProjCostEntries.apportionType.*"));
		// // selector.add(new SelectorItemInfo("isUserDefaultSolu"));
		// iHisProject.update(new
		// ObjectUuidPK(lastHisProjectInfo.getId().toString()),
		// lastHisProjectInfo);
		// }
		// IHisProject iHisProject = HisProjectFactory.getLocalInstance(ctx);
		// HisProjectInfo hisProjectInfo =
		// this.transFromCurToHis((CurProjectInfo) model);
		// if(lastHisProjectInfo != null) {
		// // FullOrgUnitInfo foui =
		// FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new
		// ObjectUuidPK(((CurProjectInfo)model).getFullOrgUnit().getId().toString()));
		// if(lastHisProjectInfo.getVersionNumber()!=null)
		// hisProjectInfo.setVersionNumber(lastHisProjectInfo.getVersionNumber());
		// if(lastHisProjectInfo.getVersionName()!=null)
		// hisProjectInfo.setVersionName(lastHisProjectInfo.getVersionName());
		// //ɾ����ǰ��ʷ�汾
		// iHisProject.delete(new
		// ObjectUuidPK(lastHisProjectInfo.getId().toString()));
		// }
		// //����һ����ʷ�汾
		// iHisProject.addnew(hisProjectInfo);
		// iHisProject.addnew(this.transFromCurToHis(curProjectInfo));

		// ͬ���޸ı�׼��Ŀ
		IProject ip = ProjectFactory.getLocalInstance(ctx);
		ProjectInfo pi = null;
		IObjectPK cpk = new ObjectUuidPK(BOSUuid.read(curProjectInfo
				.getFullOrgUnit().getId().toString()));
		ICompanyOrgUnit icompany = null;
		CompanyOrgUnitInfo companyinfo = null;
		icompany = CompanyOrgUnitFactory.getLocalInstance(ctx);
		companyinfo = (CompanyOrgUnitInfo) icompany.getCompanyOrgUnitInfo(cpk);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("company.id", companyinfo.getId()
								.toString()));
		String curLn = tmp.getLongNumber();
		filter.getFilterItems().add(new FilterItemInfo("longnumber", curLn));
		evi.setFilter(filter);
		ProjectCollection pc = ip.getProjectCollection(evi);
		if (pc.size() != 0) {
			pi = pc.get(0);
			pi.setName(curProjectInfo.getName());
			pi.setNumber(curProjectInfo.getNumber());
			pi.setLongNumber(curProjectInfo.getLongNumber());
			IObjectPK iopk = new ObjectUuidPK(pi.getId().toString());
			ip.update(iopk, pi);
			
			//����
			AssistUtil.updateAssist(ctx,iopk.toString(),pi.getBOSType());
		}
		_setIsDevPrj(ctx, pk, curProjectInfo.isIsDevPrj());
	}

	/**
	 * ������ʷ�汾����
	 * 
	 * @return
	 * @throws BOSException
	 */
	private HisProjectInfo getLastHisProject(Context ctx, String curProjectID)
			throws BOSException {
		HisProjectCollection hisPC = null;
		IHisProject iHisProject;
		iHisProject = HisProjectFactory.getLocalInstance(ctx);
		EntityViewInfo hisEvi = new EntityViewInfo();
		hisEvi.getSelector().add("*");
		hisEvi.getSelector().add("landDeveloper.*");
		hisEvi.getSelector().add("fullOrgUnit.*");
		hisEvi.getSelector().add("hisProjProductEntries.*");
		hisEvi.getSelector().add(
				"hisProjProductEntries.hisProjProEntrApporData.*");
		hisEvi.getSelector().add("hisProjCostEntries.*");
		hisEvi.getSelector().add("hisProjCostEntries.apportionType.*");
		FilterInfo hisFilter = new FilterInfo();
		hisFilter.getFilterItems().add(
				new FilterItemInfo("curProject.id", curProjectID));
		hisEvi.setFilter(hisFilter);
		SorterItemInfo sii = new SorterItemInfo("createTime");
		sii.setSortType(SortType.DESCEND);
		hisEvi.getSorter().add(sii);
		hisPC = iHisProject.getHisProjectCollection(hisEvi);
		if (hisPC.size() != 0)
			return hisPC.get(0);
		else
			return null;
	}

	/**
	 * ������Ŀɾ��
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		CurProjectInfo info = (CurProjectInfo)getValueWithParent(ctx , pk);
		
		boolean isLeafDevPrj = false;
		if(info.isIsLeaf()&&(!info.isIsDevPrj())){// ��ϸ,������Ŀ,��ɾ���������� 
			String curProjectID = info.getId().toString();
			FilterInfo filter = new FilterInfo();
			//����
			filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectID));
			MeasureCostFactory.getLocalInstance(ctx).delete(filter);
			//��Ŀ
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectID));
			CostAccountFactory.getLocalInstance(ctx).delete(filter);
			//�ڼ�״̬
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", curProjectID));
			ProjectPeriodStatusFactory.getLocalInstance(ctx).delete(filter);
			
			isLeafDevPrj = true;
		}
		
		if(!isLeafDevPrj){//������Ŀ��У��
				
			FilterInfo filterCheck = new FilterInfo();
			filterCheck.getFilterItems().add(
					new FilterItemInfo("curProject.id", pk.toString()));
			if (ContractBillFactory.getLocalInstance(ctx).exists(filterCheck)
					|| ConNoCostSplitEntryFactory.getLocalInstance(ctx).exists(filterCheck)
					|| ConChangeNoCostSplitEntryFactory.getLocalInstance(ctx).exists(filterCheck)
					|| ProjectWithCostCenterOUFactory.getLocalInstance(ctx).exists(filterCheck)) {
				throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
						FDCBasedataException.DELETE_ISREFERENCE_FAIL);
			}
	
			filterCheck = new FilterInfo();
			filterCheck.getFilterItems().add(
					new FilterItemInfo("costAccount.curProject.id", pk.toString()));
			if (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists(
					filterCheck)
					|| ConChangeSplitEntryFactory.getLocalInstance(ctx).exists(
							filterCheck)) {
				throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
						FDCBasedataException.DELETE_ISREFERENCE_FAIL);
			}
			
			// ��Ϊ�Ѿ�ȡ���������Ӽ�������Ŀʱ�Զ�����ɱ���Ŀ������û�û���ֶ�����ɱ���Ŀ�����ʾ����ɾ��
			// By Owen_wen 2011-02-28
			filterCheck.getFilterItems().clear();
			filterCheck.getFilterItems().add(new FilterItemInfo("curProject.id", pk.toString()));
			if (!CostAccountFactory.getLocalInstance(ctx).exists(filterCheck)) { // ��û�з���ɱ���Ŀ
				// ��ɾ���Զ������һ�ݳɱ��½�
				String deleteSql = "delete from T_FNC_ProjectPeriodStatus  where fprojectid=? ";
				DbUtil.execute(ctx, deleteSql, new Object[] { pk.toString() });
			} else { // �Ѿ������˳ɱ���Ŀ��Ҫ�������
				_isReferenced(ctx, pk);
			}
		}
		
		ICurProjProductEntries iCurProjProductEntries = CurProjProductEntriesFactory
				.getLocalInstance(ctx);
		iCurProjProductEntries.delete("where curProject.id = '" + pk + "'");
		// ɾ����ʷ�汾��Ϣ
		// IHisProject iHisProject = HisProjectFactory.getLocalInstance(ctx);
		// EntityViewInfo evi = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("curProject.id",
		// pk.toString()));
		// evi.setFilter(filter);
		// HisProjectCollection hisProjectCollection =
		// iHisProject.getHisProjectCollection(evi);
		// HashSet hs = new HashSet();
		// if (hisProjectCollection.size() != 0) {
		// for (int i = 0; i < hisProjectCollection.size(); i++) {
		// hs.add(hisProjectCollection.get(i).getId().toString());
		// }
		// }
		// IHisProjProductEntries iHisProjProductEntries =
		// HisProjProductEntriesFactory.getLocalInstance(ctx);
		// FilterInfo filterDel = new FilterInfo();
		// filterDel.getFilterItems().add(new FilterItemInfo("hisProject.id",
		// hs, CompareType.INCLUDE));
		// iHisProjProductEntries.delete(filterDel);
		// CurProjectInfo parent = ((CurProjectInfo) getValue(ctx,
		// pk)).getParent();
		// iHisProject.delete("where curProject.id = '" + pk + "'");

		/*
		 * ɾ��ָ������
		 */
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("projOrOrgID", pk.toString()));
		ProjectIndexDataFactory.getLocalInstance(ctx).delete(filter);

		// ɾ��������ģ�����Ŀ
		/*
		 * filter = new FilterInfo(); CurProjectInfo curProjectInfo =
		 * getCurProjectInfo(ctx, pk); filter.getFilterItems().add( new
		 * FilterItemInfo("number", curProjectInfo.getNumber()));
		 * ProjectFactory.getLocalInstance(ctx).delete(filter);
		 */
//		��ôɾ�ǲ��Եģ� ����ö�Ӧ�Ľӿ���ɾ���� modify by zhiqiao_yang at 2010-09-13
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("delete from T_BD_Project where fid in (select FBdProjectID from T_FDC_curProject where fid=?)");
//		builder.addParam(pk.toString());
//		builder.execute();
		IProject ip = ProjectFactory.getLocalInstance(ctx);
		IObjectPK ppk = new ObjectStringPK(info.getBdProjectID());
		if(ip.exists(ppk)){
			ProjectFactory.getLocalInstance(ctx).delete(ppk);
		}
		super._delete(ctx, pk);
		// ˢ�»���ֵ
		// this.refreshGatherData(ctx, parent);
		
		//��ɾ���������һ������ʱ��������Ŀ��Ҫ		
		CurProjectInfo curProject =  null;
		if(info.getParent()!=null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isLeaf");
			sic.add("isEnabled");
			sic.add("startDate");
			sic.add("fullOrgUnit.id");
			curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(info.getParent().getId().toString()), sic);
			
	        int childCount = _getChildren(ctx , curProject).size();
	        if (childCount == 0)
	        {
	    		ProjectPeriodStatusUtil._save(ctx,pk,curProject);
	        }
		}
		
	}

	private HisProjectInfo transFromCurToHis(CurProjectInfo curProjectInfo) {

		HisProjectInfo hisProjectInfo = new HisProjectInfo();
		/*
		 * //������Ϣ hisProjectInfo.setNumber(curProjectInfo.getNumber());
		 * hisProjectInfo.setName(curProjectInfo.getName());
		 * hisProjectInfo.setLongNumber(curProjectInfo.getLongNumber());
		 * hisProjectInfo.setLandDeveloper(curProjectInfo.getLandDeveloper());
		 * hisProjectInfo.setSortNo(curProjectInfo.getSortNo());
		 * hisProjectInfo.setStartDate(curProjectInfo.getStartDate());
		 * hisProjectInfo.setFullOrgUnit(curProjectInfo.getFullOrgUnit()); //�����
		 * hisProjectInfo.setParent(null);/////////////////????????????????????????????????
		 * //��ǰ������Ŀ hisProjectInfo.setCurProject(curProjectInfo); //�ɱ���¼
		 * HisProjCostEntriesCollection hisProjCostEntriesCollection = new
		 * HisProjCostEntriesCollection(); HisProjCostEntriesInfo
		 * hisProjCostEntriesInfo; if
		 * (curProjectInfo.getCurProjCostEntries().size() > 0) { for (int i = 0;
		 * i < curProjectInfo.getCurProjCostEntries().size(); i++) {
		 * hisProjCostEntriesInfo = new HisProjCostEntriesInfo();
		 * hisProjCostEntriesInfo.setApportionType(curProjectInfo.getCurProjCostEntries().get(i).getApportionType());
		 * hisProjCostEntriesInfo.setValue(curProjectInfo.getCurProjCostEntries().get(i).getValue());
		 * 
		 * hisProjCostEntriesInfo.setHisProject(hisProjectInfo);
		 * hisProjCostEntriesCollection.add(hisProjCostEntriesInfo); }
		 * hisProjectInfo.getHisProjCostEntries().addCollection(hisProjCostEntriesCollection); }
		 * //��Ʒ��¼ HisProjProductEntriesCollection
		 * hisProjProductEntriesCollection = new
		 * HisProjProductEntriesCollection(); HisProjProductEntriesInfo
		 * hisprojProductEntriesInfo; HisProjProEntrApporDataCollection
		 * hisProjProEntrApporDataCollection; HisProjProEntrApporDataInfo
		 * hisProjProEntrApporDataInfo; if
		 * (curProjectInfo.getCurProjProductEntries().size() > 0) { for (int i =
		 * 0; i < curProjectInfo.getCurProjProductEntries().size(); i++) {
		 * hisprojProductEntriesInfo = new HisProjProductEntriesInfo();
		 * hisProjProEntrApporDataCollection = new
		 * HisProjProEntrApporDataCollection(); for (int j = 0; j <
		 * curProjectInfo.getCurProjProductEntries().get(i).getCurProjProEntrApporData().size();
		 * j++) { hisProjProEntrApporDataInfo = new
		 * HisProjProEntrApporDataInfo();
		 * hisProjProEntrApporDataInfo.setApportionType(curProjectInfo.getCurProjProductEntries().get(i).getCurProjProEntrApporData().get(j).getApportionType());
		 * hisProjProEntrApporDataInfo.setValue(curProjectInfo.getCurProjProductEntries().get(i).getCurProjProEntrApporData().get(j).getValue());
		 * 
		 * hisProjProEntrApporDataCollection.add(hisProjProEntrApporDataInfo); }
		 * hisprojProductEntriesInfo.getHisProjProEntrApporData().addCollection(hisProjProEntrApporDataCollection);
		 * 
		 * hisprojProductEntriesInfo.setHisProject(hisProjectInfo);
		 * hisprojProductEntriesInfo.setIsAccObj(curProjectInfo.getCurProjProductEntries().get(i).isIsAccObj());
		 * hisprojProductEntriesInfo.setProductType(curProjectInfo.getCurProjProductEntries().get(i).getProductType());
		 * 
		 * hisProjProductEntriesCollection.add(hisprojProductEntriesInfo); }
		 * hisProjectInfo.getHisProjProductEntries().addCollection(hisProjProductEntriesCollection); }
		 */
		return hisProjectInfo;
	}

	protected boolean _disEnabled(Context ctx, IObjectPK cpPK)
			throws BOSException, EASBizException {
		if (this.checkIsOnlyOneEnabled(ctx, cpPK)) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
					FDCBasedataException.DISENABLE_CANNOT_ONLY);
		}
		
		if (changeUseStatus(ctx, cpPK, false))
			return true;
		else
			return false;
	}

	private boolean checkIsOnlyOneEnabled(Context ctx, IObjectPK PK)
			throws BOSException, EASBizException {
		CurProjectInfo cai = this.getCurProjectInfo(ctx, PK).getParent();
		if (cai != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("parent.id", cai.getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getCurProjectCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}

	protected boolean _enabled(Context ctx, IObjectPK cpPK)
			throws BOSException, EASBizException {
		boolean flag = false;
		ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		CurProjectInfo curProjectInfo = new CurProjectInfo();

		curProjectInfo = iCurProject.getCurProjectInfo(cpPK);
		if (curProjectInfo.getParent() != null) {
			IObjectPK parentPK = new ObjectStringPK(curProjectInfo.getParent()
					.getId().toString());
			CurProjectInfo parentCurProjectInfo = iCurProject
					.getCurProjectInfo(parentPK);
			if (!parentCurProjectInfo.isIsEnabled()) {
				// ����ϼ�������,������ʾ������
				throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(
						FDCBasedataException.PROJECT_PARENT_DISENABLE);
			} else {
				if (changeUseStatus(ctx, cpPK, true))
					flag = true;
			}

		} else {
			if (changeUseStatus(ctx, cpPK, true))
				flag = true;
		}
		
		return flag;
	}

	/*
	 */
	protected boolean changeUseStatus(Context ctx, IObjectPK PK, boolean flag)
			throws EASBizException, BOSException {
		ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		CurProjectInfo curProjectInfo = iCurProject.getCurProjectInfo(PK);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.id", curProjectInfo.getId()
						.toString()));
		evi.setFilter(filter);
		CurProjectCollection curProjectCollection = iCurProject
				.getCurProjectCollection(evi);
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isEnabled");
		// ������¼�,Ҫͬʱ����/�����¼�
		if (curProjectCollection.size() > 0) {
			// ������/�����Լ�
			curProjectInfo.setIsEnabled(flag);
			_updatePartial(ctx, curProjectInfo, selectors);
			// ������/�����¼�
			CurProjectInfo childContractTypeInfo;
			IObjectPK childPK;
			for (int i = 0; i < curProjectCollection.size(); i++) {
				if (!Boolean.valueOf(curProjectCollection.get(i).isIsEnabled()).equals(Boolean
						.valueOf(flag))) {
					childContractTypeInfo = curProjectCollection.get(i);
					childContractTypeInfo.setIsEnabled(flag);
					childPK = new ObjectStringPK(childContractTypeInfo.getId()
							.toString());
					changeUseStatus(ctx, childPK, flag);

				}
			}
		} else {
			//2008-10-31 ȥ��������Ŀ����ʱ�Ժ�ͬ��У��
			//����ʱ����Ƿ��к�ͬ��
//			if(!flag){
//				checkContract(ctx, curProjectInfo);
//			}
			
			// ���û���¼�
			curProjectInfo.setIsEnabled(flag);

			_updatePartial(ctx, curProjectInfo, selectors);
		}
		// }else{

		// }
		if(flag){
			//�ж��Ƿ��Ѿ�������ʼ��
			ProjectPeriodStatusUtil._save(ctx,PK,null);
			
			updateProject(ctx, curProjectInfo);
			
			//���ú����±���һ��  ������Ŀ��ɱ����Ķ�Ӧ��ϵ
			IProjectWithCostCenterOU iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getLocalInstance(ctx);
			ProjectWithCostCenterOUCollection collections = iProjectWithCostCenterOU.getProjectWithCostCenterOUCollection();
			if (collections != null && collections.size() > 0) {
				iProjectWithCostCenterOU.submitAll(collections);
			}

			
		}else{
			
			//�ж��Ƿ��Ѿ�������ʼ��
			ProjectPeriodStatusUtil._delete(ctx,PK);
		}
		
		return true;
	}

	/**
	 * ��ͬ�ĸ��ڵ��²�����ͬ�ı��롣
	 * 
	 * @param ctx
	 *            Context
	 * @param model
	 *            DataBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		TreeBaseInfo treeModel = (TreeBaseInfo) model;

		// if no parent,no need to check
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// ���ڵ�Ϊ��ʱ������������Ƿ��ظ���
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number,
					treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(
					new FilterItemInfo(IFWEntityStruct.tree_Parent, null,
							CompareType.EQUALS));
			filter.setMaskString("#0 and #1");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				// filter.getFilterItems().add(new FilterItemInfo("level",new
				// Integer(treeModel.getLevel()),CompareType.EQUALS));
				// �޸ģ�Ӧ��ʹ��parentID����Ϊlevle�Ǽ������ɵģ���Ӧ�ɿͻ��˴��ݡ� Jacky at 2004-11-4
				filter.setMaskString("#0 and #1 and #2");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number,
					treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent,
						treeModel.innerGetParent().getId().toString(),
						CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
						treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		// CU����
		// CurProjectInfo cpi = (CurProjectInfo)treeModel;

		// FilterInfo filterCU = getFilterForDefaultCU(ctx,treeModel);
		// if(cpi.getFullOrgUnit()!=null){
		// FilterInfo filterCU = new FilterInfo();
		// filterCU.getFilterItems().add(new
		// FilterItemInfo("fullOrgUnit.id",cpi.getFullOrgUnit().getId().toString(),CompareType.EQUALS));
		// if(FilterUtility.hasFilterItem(filterCU))
		// {
		// if(FilterUtility.hasFilterItem(filter))
		// {
		// filter.mergeFilter(filterCU,"AND");
		// }
		// else
		// {
		// filter = filterCU;
		// }
		// }
		// }
		view.setFilter(filter);

		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);

		// DataBaseCollection results = this.getDataBaseCollection(ctx,view);

		if (results != null && results.size() > 0) {
			throw new TreeBaseException(
					TreeBaseException.CHECKNUMBERDUPLICATED,
					new Object[] { treeModel.getNumber() });

		}

	}

	/**
	 * //TODO  ���߳�����¿��ܻ�������� sxhong
	 * ���ˢ��
	 */
	private Observable observerable = null;

	protected int _idxRefresh(Context ctx, String projId) throws BOSException {
		int v=_idxRefresh(ctx, projId, null,null);
		
		//�޸�֮ǰ�ĵ��ù���
		if(true) return v;
 		// ���۲��߶���
		if (observerable == null) {
			observerable = new ProjectObservable(ctx);
			// Observable observerable = ProjectObservable.getInstance(ctx);

			// �۲��߶���
			Observer contractSplitObserver;
			Observer conChangeSplitObserver;
			Observer conSettleSplitObserver;
			Observer paymentSplitObserver;
			try {
				contractSplitObserver = SplitObserverFactory
						.factory(SplitObserverFactory.CONTRACT_SPLIT);
				conChangeSplitObserver = SplitObserverFactory
						.factory(SplitObserverFactory.CON_CHANGE_SPLIT);
				conSettleSplitObserver = SplitObserverFactory
						.factory(SplitObserverFactory.CON_SETTLE_SPLIT);
				paymentSplitObserver = SplitObserverFactory
						.factory(SplitObserverFactory.PAYMENT_SPLIT);
			} catch (BadSplitObserverException e) {
				throw new BOSException(e);
			}

			// ע��۲��ߣ�����˳�򣨺�ͬ-���-����-�����ִ�е�ʱ������ӵķ�˳��ִ��
			observerable.addObserver(paymentSplitObserver);

			observerable.addObserver(conSettleSplitObserver);

			observerable.addObserver(conChangeSplitObserver);

			observerable.addObserver(contractSplitObserver);

		}

		int errCode = 0;
		try {
			// ��������֪ͨ
			observerable.notifyObservers(projId);
		} catch (Exception e) {
			errCode = -1;
			throw new BOSException(e);

		}

		return errCode;
	}

	protected int _idxRefresh(Context ctx, String projId,String productId,List apportionTypeList) throws BOSException {

		// ���۲��߶���
		if (observerable == null) {
			observerable = new ProjectObservable(ctx);
			// Observable observerable = ProjectObservable.getInstance(ctx);

			// �۲��߶���
			Observer contractSplitObserver;
//			Observer conChangeSplitObserver;
//			Observer conSettleSplitObserver;
			Observer paymentSplitObserver;
			try {
				contractSplitObserver = SplitObserverFactory
						.factory(SplitObserverFactory.CONTRACT_SPLIT);
//				conChangeSplitObserver = SplitObserverFactory
//						.factory(SplitObserverFactory.CON_CHANGE_SPLIT);
//				conSettleSplitObserver = SplitObserverFactory
//						.factory(SplitObserverFactory.CON_SETTLE_SPLIT);
				paymentSplitObserver = SplitObserverFactory
						.factory(SplitObserverFactory.PAYMENT_SPLIT);
			} catch (BadSplitObserverException e) {
				throw new BOSException(e);
			}

			// ע��۲��ߣ�����˳�򣨺�ͬ-���-����-�����ִ�е�ʱ������ӵķ�˳��ִ��
			observerable.addObserver(paymentSplitObserver);
//
//			observerable.addObserver(conSettleSplitObserver);

//			observerable.addObserver(conChangeSplitObserver);

			observerable.addObserver(contractSplitObserver);

		}

		int errCode = 0;
		try {
			// ��������֪ͨ
			Map map=new HashMap();
			map.put("projId", projId);
			map.put("apportions", apportionTypeList);
			map.put("productId", productId);
			observerable.notifyObservers(map);
		} catch (Exception e) {
			errCode = -1;
			throw new BOSException(e);

		}

		return errCode;
	}
	protected void _traceVoucher4Flow(Context ctx, IObjectPK projectPK)
			throws BOSException, EASBizException {
		CurProjectInfo info = super.getCurProjectInfo(ctx, projectPK);
		boolean isAdjust = isAdjustModel(ctx);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.notGetID) 
				&& info.isIsLeaf()) {
			info.setProjectStatus(ProjectStatusFactory.getLocalInstance(ctx)
					.getProjectStatusInfo(
							new ObjectUuidPK(BOSUuid
									.read(ProjectStatusInfo.flowID))));
			PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, info
					.getId().toString(), false);
			CostCenterOrgUnitInfo costOrg = getCostOrg(ctx, info);
			info.setCostOrg(costOrg);
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_FDC_CurProject set fcostorgid=?,fprojectstatusid=? where fid=?");
			builder.addParam(info.getCostOrg().getId().toString());
			builder.addParam(info.getProjectStatus().getId().toString());
			builder.addParam(info.getId().toString());
			builder.execute();
			if (costOrg != null) {
				if(!isAdjust)
					traceVoucher4FlowRedPattern(ctx, info, currentPeriod , costOrg);
				else
					traceVoucher4FlowAdjustPattern(ctx,info,currentPeriod,costOrg);
			} else {
				throw new FDCException(FDCException.PRJNOCOST);
			}
		} else {
			throw new FDCException(FDCException.PRJSTATEWRONG);
		}

	}
	private void traceVoucher4FlowAdjustPattern(Context ctx, CurProjectInfo info, PeriodInfo currentPeriod,CostCenterOrgUnitInfo costOrg) throws BOSException, EASBizException {
		tractVoucherAdjustPattern(ctx, info,VoucherAdjustReasonEnum.STATUSFLOW);
	}

	/**
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void tractVoucherAdjustPattern(Context ctx, CurProjectInfo info,VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException, EASBizException {
		EntityViewInfo viewContract = new EntityViewInfo();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems().add(
				new FilterItemInfo("curProject.id", info.getId()
						.toString()));
		viewContract.setFilter(filterContract);
		viewContract.getSelector().add("id");
		viewContract.getSelector().add("isCoseSplit");
		/***
		 * �ҵ���غ�ͬ
		 */
		ContractBillCollection contractColl = ContractBillFactory
				.getLocalInstance(ctx).getContractBillCollection(
						viewContract);
		List idList = new ArrayList();
		
		for(Iterator it = contractColl.iterator();it.hasNext();){
			ContractBillInfo contractBillInfo = (ContractBillInfo)it.next();
			if(contractBillInfo.isIsCoseSplit())
				idList.add(contractBillInfo.getId().toString());
		}
		if(idList.size()>0)
			TraceOldSplitVoucherFacadeFactory.getLocalInstance(ctx).traceAdjustContracts(idList,false,voucherAdjustReasonEnum);
		
		viewContract.getSelector().clear();
		viewContract.getSelector().add("id");
		viewContract.getSelector().add("isCostSplit");
		ContractWithoutTextCollection contractWithoutTextColl = ContractWithoutTextFactory
		.getLocalInstance(ctx).getContractWithoutTextCollection(
				viewContract);
		idList.clear();
		for(Iterator it = contractWithoutTextColl.iterator();it.hasNext();){
			ContractWithoutTextInfo contractWithoutTextInfo = (ContractWithoutTextInfo)it.next();
			if(contractWithoutTextInfo.isIsCostSplit())
				idList.add(contractWithoutTextInfo.getId().toString());
		}
		if(idList.size()>0)
			TraceOldSplitVoucherFacadeFactory.getLocalInstance(ctx).traceAdjustContracts(idList,true,voucherAdjustReasonEnum);
	}

	/**
	 * ������ǰ�ĺ��ģʽ
	 * @param ctx
	 * @param info
	 * @param currentPeriod
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void traceVoucher4FlowRedPattern(Context ctx, CurProjectInfo info, PeriodInfo currentPeriod,CostCenterOrgUnitInfo costOrg) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		/*
		 * info.setCostOrg(getCostOrg(ctx, info));
		 * SelectorItemCollection sic = new SelectorItemCollection();
		 * sic.add(new SelectorItemInfo("projectStatus.id"));
		 * sic.add(new SelectorItemInfo("costOrg.id"));
		 * super._updatePartial(ctx, info, sic);
		 */

		// ƾ֤���� Ԥ���ʿ�ת����
		Set idSet = new HashSet();
		idSet.add(PaymentTypeInfo.progressID);
		idSet.add(PaymentTypeInfo.settlementID);

		EntityViewInfo viewContract = new EntityViewInfo();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems().add(
				new FilterItemInfo("curProject.id", info.getId()
						.toString()));
		viewContract.setFilter(filterContract);
		/***
		 * �ҵ���غ�ͬ
		 */
		ContractBillCollection contractColl = ContractBillFactory
				.getLocalInstance(ctx).getContractBillCollection(
						viewContract);
		IPaymentSplit paymentSplit = PaymentSplitFactory
				.getLocalInstance(ctx);
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		VoucherInfo newVoucher = new VoucherInfo();
		for (Iterator it = contractColl.iterator(); it.hasNext();) {
			ContractBillInfo contract = (ContractBillInfo) it.next();
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.contractBillId", contract
							.getId().toString()));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.fdcPayType.payType.id",
							idSet, CompareType.INCLUDE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("Fivouchered", Boolean.TRUE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("isRedVouchered", Boolean.TRUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("completePrjAmt", null,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			viewSplit.getSelector().add("state");
			viewSplit.getSelector().add("createTime");
			viewSplit.getSelector().add("paymentBill.id");
			viewSplit.getSelector().add("paymentBill.fdcPayReqID");
			viewSplit.getSelector().add("hisVoucher.id");
			viewSplit.getSelector().add("isRedVouchered");
			viewSplit.getSelector().add("Fivouchered");
			PaymentSplitCollection splitColl = paymentSplit
					.getPaymentSplitCollection(viewSplit);
			int splitSize = splitColl.size();
			IObjectPK[] pks = new IObjectPK[splitSize];
			List idList = new ArrayList();
			PaymentSplitInfo splitInfo = new PaymentSplitInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("Fivouchered");
			if (splitSize > 0) {
				for (int i = 0; i < splitSize; i++) {
					splitInfo = splitColl.get(i);
					pks[i] = new ObjectUuidPK(splitInfo.getId());
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", splitInfo
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID",
									newVoucher.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", splitInfo
									.getId().toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					mapping.getSorter().add(new SorterItemInfo("date"));
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(
									mapping);
					if (relationColl.size() > 0) {
						BOTRelationInfo botInfo = relationColl
								.get(relationColl.size() - 1);
						SelectorItemCollection voucherSelector = new SelectorItemCollection();
						voucherSelector.add("id");
						voucherSelector.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher
								.getValue(new ObjectUuidPK(
										BOSUuid.read(botInfo
												.getDestObjectID())),
										voucherSelector);
						if (!oldInfo.isHasReversed()) {
							idList.add(botInfo.getDestObjectID());
							Date bookedDate = DateTimeUtils
									.truncateDate(splitInfo
											.getCreateTime());
							if (currentPeriod != null) {
								String payreqID = splitInfo
										.getPaymentBill()
										.getFdcPayReqID();
								SelectorItemCollection reqPer = new SelectorItemCollection();
								reqPer.add("id");
								reqPer.add("period.number");
								reqPer.add("period.beginDate");
								reqPer.add("period.endDate");
								PayRequestBillInfo reqInfo = PayRequestBillFactory
										.getLocalInstance(ctx)
										.getPayRequestBillInfo(
												new ObjectUuidPK(
														BOSUuid
																.read(payreqID)),
												reqPer);
								PeriodInfo contractPeriod = reqInfo
										.getPeriod();
								if (contractPeriod != null
										&& contractPeriod.getNumber() > currentPeriod
												.getNumber()) {
									if (bookedDate
											.before(contractPeriod
													.getBeginDate())) {
										bookedDate = contractPeriod
												.getBeginDate();
									} else if (bookedDate
											.after(contractPeriod
													.getEndDate())) {
										bookedDate = contractPeriod
												.getEndDate();
									}
									builder = new FDCSQLBuilder(ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
									builder.addParam(contractPeriod
											.getId().toString());
									builder.addParam(FDCDateHelper
											.getSqlDate(bookedDate));
									builder.addParam(splitInfo.getId()
											.toString());
									builder.executeUpdate();

									builder = new FDCSQLBuilder(ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
									builder.addParam(contractPeriod
											.getId().toString());
									builder.addParam(splitInfo.getId()
											.toString());
									builder.addParam(splitInfo
											.getPaymentBill().getId()
											.toString());
									builder.executeUpdate();
								} else if (currentPeriod != null) {
									if (bookedDate.before(currentPeriod
											.getBeginDate())) {
										bookedDate = currentPeriod
												.getBeginDate();
									} else if (bookedDate
											.after(currentPeriod
													.getEndDate())) {
										bookedDate = currentPeriod
												.getEndDate();
									}
									builder = new FDCSQLBuilder(ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
									builder.addParam(currentPeriod
											.getId().toString());
									builder.addParam(FDCDateHelper
											.getSqlDate(bookedDate));
									builder.addParam(splitInfo.getId()
											.toString());
									builder.executeUpdate();

									builder = new FDCSQLBuilder(ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
									builder.addParam(currentPeriod
											.getId().toString());
									builder.addParam(splitInfo.getId()
											.toString());
									builder.addParam(splitInfo
											.getPaymentBill().getId()
											.toString());
									builder.executeUpdate();
								}
							}
							splitInfo.setFivouchered(false);
							paymentSplit.updatePartial(splitInfo,
									selector);
						}
					}
				}
			}
			if (idList.size() > 0) {
				IObjectPK pk = voucher.reverseSaveBatch(idList);
				PaySplitUtilFacadeFactory.getLocalInstance(ctx)
						.traceReverseVoucher(pk);
				paymentSplit.generateVoucher(pks);
			}
		}
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.curProject.id", info
						.getId().toString()));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isConWithoutText", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.fdcPayType.payType.id",
						idSet, CompareType.INCLUDE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("Fivouchered", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isRedVouchered", Boolean.TRUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("state",
						FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("completePrjAmt", null,
						CompareType.NOTEQUALS));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("id");
		viewSplit.getSelector().add("state");
		viewSplit.getSelector().add("paymentBill.id");
		viewSplit.getSelector().add("hisVoucher.id");
		viewSplit.getSelector().add("isRedVouchered");
		viewSplit.getSelector().add("Fivouchered");
		PaymentSplitCollection splitColl = paymentSplit
				.getPaymentSplitCollection(viewSplit);
		int splitSize = splitColl.size();
		IObjectPK[] pks = new IObjectPK[splitSize];
		PaymentSplitInfo splitInfo = new PaymentSplitInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("Fivouchered");
		if (splitSize > 0) {
			for (int i = 0; i < splitSize; i++) {
				splitInfo = splitColl.get(i);
				pks[i] = new ObjectUuidPK(splitInfo.getId());
				EntityViewInfo mapping = new EntityViewInfo();
				FilterInfo mappingFilter = new FilterInfo();
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcEntityID", splitInfo
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("destEntityID", newVoucher
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcObjectID", splitInfo
								.getId().toString()));
				mapping.setFilter(mappingFilter);
				mapping.getSelector().add("id");
				mapping.getSelector().add("destObjectID");
				mapping.getSorter().add(new SorterItemInfo("date"));
				BOTRelationCollection relationColl = BOTRelationFactory
						.getLocalInstance(ctx).getCollection(mapping);
				if (relationColl.size() > 0) {
					BOTRelationInfo botInfo = relationColl
							.get(relationColl.size() - 1);
					SelectorItemCollection voucherSelector = new SelectorItemCollection();
					voucherSelector.add("id");
					voucherSelector.add("hasReversed");
					VoucherInfo oldInfo = (VoucherInfo) voucher
							.getValue(new ObjectUuidPK(BOSUuid
									.read(botInfo.getDestObjectID())),
									voucherSelector);
					if (!oldInfo.isHasReversed()) {
						List idList = new ArrayList();
						idList.add(splitInfo.getHisVoucher().getId()
								.toString());
						Date bookedDate = DateTimeUtils
								.truncateDate(splitInfo.getCreateTime());
						if (currentPeriod != null) {
							String payreqID = splitInfo
									.getPaymentBill().getFdcPayReqID();
							SelectorItemCollection reqPer = new SelectorItemCollection();
							reqPer.add("id");
							reqPer.add("period.number");
							reqPer.add("period.beginDate");
							reqPer.add("period.endDate");
							PayRequestBillInfo reqInfo = PayRequestBillFactory
									.getLocalInstance(ctx)
									.getPayRequestBillInfo(
											new ObjectUuidPK(BOSUuid
													.read(payreqID)),
											reqPer);
							PeriodInfo contractPeriod = reqInfo
									.getPeriod();
							if (contractPeriod != null
									&& contractPeriod.getNumber() > currentPeriod
											.getNumber()) {
								if (bookedDate.before(contractPeriod
										.getBeginDate())) {
									bookedDate = contractPeriod
											.getBeginDate();
								} else if (bookedDate
										.after(contractPeriod
												.getEndDate())) {
									bookedDate = contractPeriod
											.getEndDate();
								}
								builder = new FDCSQLBuilder(ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
								builder.addParam(contractPeriod.getId()
										.toString());
								builder.addParam(FDCDateHelper
										.getSqlDate(bookedDate));
								builder.addParam(splitInfo.getId()
										.toString());
								builder.executeUpdate();

								builder = new FDCSQLBuilder(ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
								builder.addParam(contractPeriod.getId()
										.toString());
								builder.addParam(splitInfo.getId()
										.toString());
								builder.addParam(splitInfo
										.getPaymentBill().getId()
										.toString());
								builder.executeUpdate();
							} else if (currentPeriod != null) {
								if (bookedDate.before(currentPeriod
										.getBeginDate())) {
									bookedDate = currentPeriod
											.getBeginDate();
								} else if (bookedDate
										.after(currentPeriod
												.getEndDate())) {
									bookedDate = currentPeriod
											.getEndDate();
								}
								builder = new FDCSQLBuilder(ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
								builder.addParam(currentPeriod.getId()
										.toString());
								builder.addParam(FDCDateHelper
										.getSqlDate(bookedDate));
								builder.addParam(splitInfo.getId()
										.toString());
								builder.executeUpdate();

								builder = new FDCSQLBuilder(ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
								builder.addParam(currentPeriod.getId()
										.toString());
								builder.addParam(splitInfo.getId()
										.toString());
								builder.addParam(splitInfo
										.getPaymentBill().getId()
										.toString());
								builder.executeUpdate();
							}
						}
						splitInfo.setFivouchered(false);
						paymentSplit.updatePartial(splitInfo, selector);
						IObjectPK pk = voucher.reverseSaveBatch(idList);
						PaySplitUtilFacadeFactory.getLocalInstance(ctx)
								.traceReverseVoucher(pk);
						paymentSplit.generateVoucher(pks[i]);
					}
				}
			}
		}
	}

	private CostCenterOrgUnitInfo getCostOrg(Context ctx, CurProjectInfo info)
			throws BOSException, EASBizException {
		IProjectWithCostCenterOU iProject = ProjectWithCostCenterOUFactory
				.getLocalInstance(ctx);
		String projectID = null;
		if (info.getParent() == null || info.getParent().getId() == null)
			projectID = info.getId().toString();
		else {
			String longNumber = info.getLongNumber();
			String pln = longNumber.substring(0, longNumber.indexOf("!"));
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems().add(new FilterItemInfo("longNumber", pln));
			view.setFilter(fi);
			CurProjectCollection coll = null;
			coll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);
			if (coll != null && coll.size() > 0) {
				CurProjectInfo pProjInfo = coll.get(0);
				projectID = pProjInfo.getId().toString();
			}
		}
		if (projectID != null) {
			EntityViewInfo viewProject = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", projectID));
			viewProject.setFilter(filter);
			viewProject.getSelector().add("costCenterOU.*");
			if (iProject.exists(filter)) {
				ProjectWithCostCenterOUCollection collPrjWithOU = iProject
						.getProjectWithCostCenterOUCollection(viewProject);
				if (collPrjWithOU.iterator().hasNext()) {
					ProjectWithCostCenterOUInfo infoPrjWithOU = (ProjectWithCostCenterOUInfo) collPrjWithOU
							.iterator().next();
					return infoPrjWithOU.getCostCenterOU();
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	protected void _traceVoucher4Get(Context ctx, IObjectPK projectPK)
			throws BOSException, EASBizException {

		CurProjectInfo info = super.getCurProjectInfo(ctx, projectPK);
		boolean isAdjust =isAdjustModel(ctx);
		if (info.getProjectStatus() != null
				&& info.getProjectStatus().getId().toString().equals(
						ProjectStatusInfo.notGetID) && info.isIsLeaf()) {
			info.setProjectStatus(ProjectStatusFactory.getLocalInstance(ctx)
					.getProjectStatusInfo(
							new ObjectUuidPK(BOSUuid
									.read(ProjectStatusInfo.notIntiID))));

			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("projectStatus"));
			sic.add(new SelectorItemInfo("costOrg"));
			super._updatePartial(ctx, info, sic);
			if(!isAdjust){
				traceVoucher4GetRedPattern(ctx, info);
			}else{
				traceVoucher4GetAdjustPattern(ctx, info);
			}
		} else {
			throw new FDCException(FDCException.PRJSTATEWRONG);
		}
	}
	private void traceVoucher4GetAdjustPattern(Context ctx, CurProjectInfo info) throws BOSException, EASBizException, FDCException {
		tractVoucherAdjustPattern(ctx, info,VoucherAdjustReasonEnum.STATUSGET);
		
	}

	/**
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws FDCException
	 */
	private void traceVoucher4GetRedPattern(Context ctx, CurProjectInfo info) throws BOSException, EASBizException, FDCException {
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, info.getId()
				.toString(), false);
		
		

		

		Set idSet = new HashSet();
		idSet.add(PaymentTypeInfo.progressID);
		idSet.add(PaymentTypeInfo.settlementID);

		// ƾ֤���� Ԥ���ʿ�ת�ɱ�
		EntityViewInfo viewContract = new EntityViewInfo();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems()
				.add(
						new FilterItemInfo("curProject.id", info.getId()
								.toString()));
		viewContract.setFilter(filterContract);
		ContractBillCollection contractColl = ContractBillFactory
				.getLocalInstance(ctx).getContractBillCollection(
						viewContract);
		IPaymentSplit paymentSplit = PaymentSplitFactory
				.getLocalInstance(ctx);
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		VoucherInfo newVoucher = new VoucherInfo();
		for (Iterator it = contractColl.iterator(); it.hasNext();) {
			ContractBillInfo contract = (ContractBillInfo) it.next();
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.contractBillId",
							contract.getId().toString()));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("paymentBill.fdcPayType.payType.id",
							idSet, CompareType.INCLUDE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("Fivouchered", Boolean.TRUE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("isRedVouchered", Boolean.TRUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("completePrjAmt", null,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			viewSplit.getSelector().add("state");
			viewSplit.getSelector().add("createTime");
			viewSplit.getSelector().add("paymentBill.id");
			viewSplit.getSelector().add("paymentBill.fdcPayReqID");
			viewSplit.getSelector().add("hisVoucher.id");
			viewSplit.getSelector().add("isRedVouchered");
			viewSplit.getSelector().add("Fivouchered");
			PaymentSplitCollection splitColl = paymentSplit
					.getPaymentSplitCollection(viewSplit);
			int splitSize = splitColl.size();
			IObjectPK[] pks = new IObjectPK[splitSize];
			List idList = new ArrayList();
			PaymentSplitInfo splitInfo = new PaymentSplitInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("Fivouchered");
			if (splitSize > 0) {
				for (int i = 0; i < splitSize; i++) {
					splitInfo = splitColl.get(i);
					pks[i] = new ObjectUuidPK(splitInfo.getId());
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", splitInfo
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", splitInfo
									.getId().toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					mapping.getSorter().add(new SorterItemInfo("date"));
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					if (relationColl.size() > 0) {
						BOTRelationInfo botInfo = relationColl
								.get(relationColl.size() - 1);
						SelectorItemCollection voucherSelector = new SelectorItemCollection();
						voucherSelector.add("id");
						voucherSelector.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher
								.getValue(new ObjectUuidPK(BOSUuid
										.read(botInfo.getDestObjectID())),
										voucherSelector);
						if (!oldInfo.isHasReversed()) {
							idList.add(botInfo.getDestObjectID());
							splitInfo.setFivouchered(false);
							Date bookedDate = DateTimeUtils
									.truncateDate(splitInfo.getCreateTime());
							if (currentPeriod != null) {
								String payreqID = splitInfo
										.getPaymentBill().getFdcPayReqID();
								SelectorItemCollection reqPer = new SelectorItemCollection();
								reqPer.add("id");
								reqPer.add("period.number");
								reqPer.add("period.beginDate");
								reqPer.add("period.endDate");
								PayRequestBillInfo reqInfo = PayRequestBillFactory
										.getLocalInstance(ctx)
										.getPayRequestBillInfo(
												new ObjectUuidPK(BOSUuid
														.read(payreqID)),
												reqPer);
								PeriodInfo contractPeriod = reqInfo
										.getPeriod();
								if (contractPeriod != null
										&& contractPeriod.getNumber() > currentPeriod
												.getNumber()) {
									if (bookedDate.before(contractPeriod
											.getBeginDate())) {
										bookedDate = contractPeriod
												.getBeginDate();
									} else if (bookedDate
											.after(contractPeriod
													.getEndDate())) {
										bookedDate = contractPeriod
												.getEndDate();
									}
									FDCSQLBuilder builder = new FDCSQLBuilder(
											ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
									builder.addParam(contractPeriod.getId()
											.toString());
									builder.addParam(FDCDateHelper
											.getSqlDate(bookedDate));
									builder.addParam(splitInfo.getId()
											.toString());
									builder.executeUpdate();

									builder = new FDCSQLBuilder(ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
									builder.addParam(contractPeriod.getId()
											.toString());
									builder.addParam(splitInfo.getId()
											.toString());
									builder.addParam(splitInfo
											.getPaymentBill().getId()
											.toString());
									builder.executeUpdate();
								} else if (currentPeriod != null) {
									if (bookedDate.before(currentPeriod
											.getBeginDate())) {
										bookedDate = currentPeriod
												.getBeginDate();
									} else if (bookedDate
											.after(currentPeriod
													.getEndDate())) {
										bookedDate = currentPeriod
												.getEndDate();
									}
									FDCSQLBuilder builder = new FDCSQLBuilder(
											ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
									builder.addParam(currentPeriod.getId()
											.toString());
									builder.addParam(FDCDateHelper
											.getSqlDate(bookedDate));
									builder.addParam(splitInfo.getId()
											.toString());
									builder.executeUpdate();

									builder = new FDCSQLBuilder(ctx);
									builder.clear();
									builder
											.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
									builder.addParam(currentPeriod.getId()
											.toString());
									builder.addParam(splitInfo.getId()
											.toString());
									builder.addParam(splitInfo
											.getPaymentBill().getId()
											.toString());
									builder.executeUpdate();
								}
							}
							paymentSplit.updatePartial(splitInfo, selector);
						}
					}
				}
			}
			if (idList.size() > 0) {
				IObjectPK pk = voucher.reverseSaveBatch(idList);
				PaySplitUtilFacadeFactory.getLocalInstance(ctx)
						.traceReverseVoucher(pk);
				paymentSplit.generateVoucher(pks);
			}
		}
		EntityViewInfo viewSplit = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.curProject.id", info
						.getId().toString()));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isConWithoutText", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("paymentBill.fdcPayType.payType.id",
						idSet, CompareType.INCLUDE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("Fivouchered", Boolean.TRUE));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("isRedVouchered", Boolean.TRUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("completePrjAmt", null,
						CompareType.NOTEQUALS));
		viewSplit.setFilter(filterSplit);
		viewSplit.getSelector().add("id");
		viewSplit.getSelector().add("state");
		viewSplit.getSelector().add("paymentBill.id");
		viewSplit.getSelector().add("hisVoucher.id");
		viewSplit.getSelector().add("isRedVouchered");
		viewSplit.getSelector().add("Fivouchered");
		PaymentSplitCollection splitColl = paymentSplit
				.getPaymentSplitCollection(viewSplit);
		int splitSize = splitColl.size();
		IObjectPK[] pks = new IObjectPK[splitSize];
		PaymentSplitInfo splitInfo = new PaymentSplitInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("Fivouchered");
		if (splitSize > 0) {
			for (int i = 0; i < splitSize; i++) {
				splitInfo = splitColl.get(i);
				pks[i] = new ObjectUuidPK(splitInfo.getId());
				EntityViewInfo mapping = new EntityViewInfo();
				FilterInfo mappingFilter = new FilterInfo();
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcEntityID", splitInfo
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("destEntityID", newVoucher
								.getBOSType()));
				mappingFilter.getFilterItems().add(
						new FilterItemInfo("srcObjectID", splitInfo.getId()
								.toString()));
				mapping.setFilter(mappingFilter);
				mapping.getSelector().add("id");
				mapping.getSelector().add("destObjectID");
				mapping.getSorter().add(new SorterItemInfo("date"));
				BOTRelationCollection relationColl = BOTRelationFactory
						.getLocalInstance(ctx).getCollection(mapping);
				if (relationColl.size() > 0) {
					BOTRelationInfo botInfo = relationColl.get(relationColl
							.size() - 1);
					SelectorItemCollection voucherSelector = new SelectorItemCollection();
					voucherSelector.add("id");
					voucherSelector.add("hasReversed");
					VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
							new ObjectUuidPK(BOSUuid.read(botInfo
									.getDestObjectID())), voucherSelector);
					if (!oldInfo.isHasReversed()) {
						List idList = new ArrayList();
						idList.add(splitInfo.getHisVoucher().getId()
								.toString());
						splitInfo.setFivouchered(false);
						Date bookedDate = DateTimeUtils
								.truncateDate(splitInfo.getCreateTime());
						if (currentPeriod != null) {
							String payreqID = splitInfo.getPaymentBill()
									.getFdcPayReqID();
							SelectorItemCollection reqPer = new SelectorItemCollection();
							reqPer.add("id");
							reqPer.add("period.number");
							reqPer.add("period.beginDate");
							reqPer.add("period.endDate");
							PayRequestBillInfo reqInfo = PayRequestBillFactory
									.getLocalInstance(ctx)
									.getPayRequestBillInfo(
											new ObjectUuidPK(BOSUuid
													.read(payreqID)),
											reqPer);
							PeriodInfo contractPeriod = reqInfo.getPeriod();
							if (contractPeriod != null
									&& contractPeriod.getNumber() > currentPeriod
											.getNumber()) {
								if (bookedDate.before(contractPeriod
										.getBeginDate())) {
									bookedDate = contractPeriod
											.getBeginDate();
								} else if (bookedDate.after(contractPeriod
										.getEndDate())) {
									bookedDate = contractPeriod
											.getEndDate();
								}
								FDCSQLBuilder builder = new FDCSQLBuilder(
										ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
								builder.addParam(contractPeriod.getId()
										.toString());
								builder.addParam(FDCDateHelper
										.getSqlDate(bookedDate));
								builder.addParam(splitInfo.getId()
										.toString());
								builder.executeUpdate();

								builder = new FDCSQLBuilder(ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
								builder.addParam(contractPeriod.getId()
										.toString());
								builder.addParam(splitInfo.getId()
										.toString());
								builder.addParam(splitInfo.getPaymentBill()
										.getId().toString());
								builder.executeUpdate();
							} else if (currentPeriod != null) {
								if (bookedDate.before(currentPeriod
										.getBeginDate())) {
									bookedDate = currentPeriod
											.getBeginDate();
								} else if (bookedDate.after(currentPeriod
										.getEndDate())) {
									bookedDate = currentPeriod.getEndDate();
								}
								FDCSQLBuilder builder = new FDCSQLBuilder(
										ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FPeriodId=?, FBookedDate=? where FID=?");
								builder.addParam(currentPeriod.getId()
										.toString());
								builder.addParam(FDCDateHelper
										.getSqlDate(bookedDate));
								builder.addParam(splitInfo.getId()
										.toString());
								builder.executeUpdate();

								builder = new FDCSQLBuilder(ctx);
								builder.clear();
								builder
										.appendSql("update T_FNC_PaymentSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fpaymentbillid=?");
								builder.addParam(currentPeriod.getId()
										.toString());
								builder.addParam(splitInfo.getId()
										.toString());
								builder.addParam(splitInfo.getPaymentBill()
										.getId().toString());
								builder.executeUpdate();
							}
						}
						paymentSplit.updatePartial(splitInfo, selector);
						IObjectPK pk = voucher.reverseSaveBatch(idList);
						PaySplitUtilFacadeFactory.getLocalInstance(ctx)
								.traceReverseVoucher(pk);
						paymentSplit.generateVoucher(pks[i]);
					}
				}
			}
		}
	
	}

	protected void _changeStatus(Context ctx, String projectId,
			String changeCase) throws BOSException, EASBizException {
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(projectId));
		CurProjectInfo info = super.getCurProjectInfo(ctx, pk);
		if (changeCase.equals(ANTIGETTED)) {
			if (info.getProjectStatus() != null
					&& info.getProjectStatus().getId().toString().equals(
							ProjectStatusInfo.notIntiID) && info.isIsLeaf()) {
				info.setProjectStatus(ProjectStatusFactory
						.getLocalInstance(ctx).getProjectStatusInfo(
								new ObjectUuidPK(BOSUuid
										.read(ProjectStatusInfo.notGetID))));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("projectStatus"));
				super.updatePartial(ctx, info, sic);
				/**
				 * ����ȡ--������ƾ֤����
				 */
				tractVoucherAdjustPattern(ctx,info,VoucherAdjustReasonEnum.STATUSANTIGET);
				
			} else {
				throw new FDCException(FDCException.PRJSTATEWRONG);
			}
		} else if (changeCase.equals(ANTIFLOW)) {
			if (info.getProjectStatus() != null
					&& info.getProjectStatus().getId().toString().equals(
							ProjectStatusInfo.flowID) && info.isIsLeaf()) {
				info.setProjectStatus(ProjectStatusFactory
						.getLocalInstance(ctx).getProjectStatusInfo(
								new ObjectUuidPK(BOSUuid
										.read(ProjectStatusInfo.notGetID))));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("projectStatus"));
				super.updatePartial(ctx, info, sic);
				
				/**
				 * ����ʧ--������ƾ֤����
				 */
				tractVoucherAdjustPattern(ctx,info,VoucherAdjustReasonEnum.STATUSANTIFLOW);
			} else {
				throw new FDCException(FDCException.PRJSTATEWRONG);
			}
		} else if (changeCase.equals(CLOSE)) {
			if (info.getProjectStatus() != null
					&& info.getProjectStatus().getId().toString().equals(
							ProjectStatusInfo.settleID) && info.isIsLeaf()) {
				// checkCurProject(ctx, info);
				checkContract(ctx, info);
				info.setProjectStatus(ProjectStatusFactory
						.getLocalInstance(ctx).getProjectStatusInfo(
								new ObjectUuidPK(BOSUuid
										.read(ProjectStatusInfo.closeID))));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("projectStatus"));
				super.updatePartial(ctx, info, sic);
				
				//�رղ���ɱ��½�״̬
				ProjectPeriodStatusUtil._close(ctx,pk,info,true);
			} else {
				throw new FDCException(FDCException.PRJSTATEWRONG);
			}
		} else if (changeCase.equals(ANTICLOSE)) {
			if (info.getProjectStatus() != null
					&& info.getProjectStatus().getId().toString().equals(
							ProjectStatusInfo.closeID) && info.isIsLeaf()) {
				info.setProjectStatus(ProjectStatusFactory
						.getLocalInstance(ctx).getProjectStatusInfo(
								new ObjectUuidPK(BOSUuid
										.read(ProjectStatusInfo.settleID))));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("projectStatus"));
				super.updatePartial(ctx, info, sic);
				
				//���رղ���ɱ��½�״̬
				ProjectPeriodStatusUtil._close(ctx,pk,info,false);
			} else {
				throw new FDCException(FDCException.PRJSTATEWRONG);
			}
		} else if (changeCase.equals(TRANSFER)) {
			if (info.getProjectStatus() != null					 
					&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.flowID)//��ʧ
					&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.closeID)//�ر�
					&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.settleID)//��������
					&& !info.getProjectStatus().getId().toString().equals(ProjectStatusInfo.transferID)//����ת
					&& info.isIsLeaf()) {
				info.setProjectStatus(ProjectStatusFactory
						.getLocalInstance(ctx).getProjectStatusInfo(
								new ObjectUuidPK(BOSUuid
										.read(ProjectStatusInfo.transferID))));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("projectStatus"));
				super.updatePartial(ctx, info, sic);
			} else {
				throw new FDCException(FDCException.PRJSTATEWRONG);
			}
		}
	}

	private void checkCurProject(Context ctx, CurProjectInfo info)
			throws BOSException, EASBizException {
		String objectId = info.getId().toString();
		AimCostSplitDataGetter aimGetter;
		DyCostSplitDataGetter dyGetter;
		HappenDataGetter happenGetter;
		aimGetter = new AimCostSplitDataGetter(ctx, objectId);
		happenGetter = new HappenDataGetter(ctx, objectId, false, false,true);
		dyGetter = new DyCostSplitDataGetter(ctx, objectId, aimGetter,
				happenGetter);
		BigDecimal dyna = FDCHelper.ZERO;
		BigDecimal happen = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", info.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("id");
		CostAccountCollection coll = CostAccountFactory.getLocalInstance(ctx)
				.getCostAccountCollection(view);
		for (Iterator it = coll.iterator(); it.hasNext();) {
			CostAccountInfo account = (CostAccountInfo) it.next();
			String accId = account.getId().toString();
			DynamicCostInfo dynamicCostInfo = dyGetter.getDynamicInfo(accId);
			if (dynamicCostInfo != null) {
				AdjustRecordEntryCollection adjusts = dynamicCostInfo
						.getAdjustEntrys();
				BigDecimal aimCost = aimGetter.getAimCost(accId);
				BigDecimal temp = aimCost.add(FDCHelper.ZERO);
				for (int i = 0; i < adjusts.size(); i++) {
					AdjustRecordEntryInfo adjust = adjusts.get(i);
					BigDecimal costAmount = adjust.getCostAmount();
					if (costAmount != null) {
						temp = temp.add(costAmount);
					}
				}
				dyna = dyna.add(temp);
			}

			HappenDataInfo happenInfo = happenGetter.getHappenInfo(accId);
			BigDecimal hasHappen = FDCHelper.ZERO;
			if (happenInfo != null) {
				hasHappen = happenInfo.getAmount();
			}
			happen = happen.add(hasHappen);
		}
		if (!(dyna.compareTo(happen) == 0)) {
			throw new FDCException(FDCException.NOCLOSEFORNOTZERO);
		}
	}

	private void checkContract(Context ctx, CurProjectInfo info)
			throws BOSException, EASBizException {
		String objectId = info.getId().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.id", objectId));
		filter.getFilterItems().add(
				new FilterItemInfo("hasSettled", Boolean.TRUE,
						CompareType.NOTEQUALS));
		filter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
		if (ContractBillFactory.getLocalInstance(ctx).exists(filter)) {
			throw new FDCException(FDCException.NOCLOSEFORNOTSETT);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterSplit = new FilterInfo();
		filterSplit.getFilterItems().add(
				new FilterItemInfo("costAccount.curProject.id", objectId));
		filterSplit.getFilterItems().add(
				new FilterItemInfo("parent.contractBill.hasSettled",
						Boolean.TRUE, CompareType.NOTEQUALS));
		view.setFilter(filterSplit);
		ContractCostSplitEntryCollection coll = ContractCostSplitEntryFactory
				.getLocalInstance(ctx)
				.getContractCostSplitEntryCollection(view);
		if (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists(
				filterSplit)) {
			throw new FDCException(FDCException.NOCLOSEFORNOTSETT);
		}

		FilterInfo filterChangeSplit = new FilterInfo();
		filterChangeSplit.getFilterItems().add(
				new FilterItemInfo("costAccount.curProject.id", objectId));
		filterChangeSplit.getFilterItems().add(
				new FilterItemInfo(
						"parent.contractChange.contractBill.hasSettled",
						Boolean.TRUE, CompareType.NOTEQUALS));
		if (ConChangeSplitEntryFactory.getLocalInstance(ctx).exists(
				filterChangeSplit)) {
			throw new FDCException(FDCException.NOCLOSEFORNOTSETT);
		}
	}
	/*
	 * private void refreshGatherData(Context ctx, CurProjectInfo cpi) throws
	 * EASBizException, BOSException { //ָ����� if (cpi != null) {
	 * SelectorItemCollection selector = new SelectorItemCollection();
	 * selector.add(new SelectorItemInfo("id")); selector.add(new
	 * SelectorItemInfo("curProjCostEntries.*")); selector.add(new
	 * SelectorItemInfo("curProjCostEntries.apportionType.*")); selector.add(new
	 * SelectorItemInfo("curProjCostEntries.value")); selector.add(new
	 * SelectorItemInfo("parent.id")); CurProjectInfo parent =
	 * this.getCurProjectInfo(ctx, new ObjectUuidPK(cpi.getId().toString()),
	 * selector); CurProjectInfo parentsParent = this.getCurProjectInfo(ctx, new
	 * ObjectUuidPK(cpi.getId().toString()), selector).getParent();
	 * CurProjCostEntriesCollection parentCpcec =
	 * parent.getCurProjCostEntries(); String sql = "update
	 * T_FDC_CurProjCostEntries set FValue = ? where FID = ? "; Object[] params;
	 * EntityViewInfo evi = new EntityViewInfo(); FilterInfo filter = new
	 * FilterInfo(); filter.getFilterItems().add(new FilterItemInfo("parent.id",
	 * parent.getId().toString())); evi.setFilter(filter);
	 * evi.getSelector().add(new SelectorItemInfo("curProjCostEntries.id"));
	 * evi.getSelector().add(new
	 * SelectorItemInfo("curProjCostEntries.apportionType.*"));
	 * evi.getSelector().add(new SelectorItemInfo("curProjCostEntries.value"));
	 * evi.getSelector().add(new SelectorItemInfo("curProject.*"));
	 * CurProjectCollection cpc = this.getCurProjectCollection(ctx, evi);
	 * CurProjCostEntriesCollection xxx = new CurProjCostEntriesCollection(); if
	 * (cpc.size() != 0) { for (int i = 0; i < cpc.size(); i++) {
	 * xxx.addCollection(cpc.get(i).getCurProjCostEntries()); } }
	 * ApportionTypeCollection atc =
	 * ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeCollection("select *
	 * where isEnabled = 1 "); HashMap hm = new HashMap(); for (int i = 0; i <
	 * atc.size(); i++) { hm.put(atc.get(i).getId().toString(), new
	 * BigDecimal(0)); } if (xxx.size() != 0) { for (int i = 0; i < xxx.size();
	 * i++) { if (xxx.get(i).getApportionType() != null) { String id =
	 * xxx.get(i).getApportionType().getId().toString(); BigDecimal oldData =
	 * (BigDecimal) hm.get(id); BigDecimal newData = xxx.get(i).getValue(); if
	 * (newData != null) { if (oldData != null) { newData =
	 * newData.add(oldData); } } else { newData = oldData; } hm.put(id,
	 * newData); } } } for (int j = 0; j < parentCpcec.size(); j++) { if
	 * (parentCpcec.get(j).getApportionType() != null) { String key =
	 * parentCpcec.get(j).getApportionType().getId().toString(); params = new
	 * Object[] { hm.get(key), parentCpcec.get(j).getId().toString() };
	 * DbUtil.execute(ctx, sql, params); hm.remove(key); } } //��Ҫ��ӵ� if
	 * (hm.size() != 0) { Iterator iterator = hm.keySet().iterator();
	 * ICurProjCostEntries iCurProjCostEntries =
	 * CurProjCostEntriesFactory.getLocalInstance(ctx); CurProjCostEntriesInfo
	 * add; ApportionTypeInfo ati;
	 * 
	 * if (iterator.hasNext()) { ati = new ApportionTypeInfo(); String id =
	 * (String) iterator.next(); ati.setId(BOSUuid.read(id)); add = new
	 * CurProjCostEntriesInfo(); add.setApportionType(ati);
	 * add.setValue((BigDecimal) hm.get(id)); add.setCurProject(parent);
	 * iCurProjCostEntries.addnew(add); } } refreshGatherData(ctx,
	 * parentsParent);//�ݹ� } }
	 */
	// private void gatherTargetData(Context ctx, CurProjectInfo cpi) throws
	// EASBizException, BOSException {
	// //ָ�����
	// if (cpi.getParent() != null) {
	// CurProjCostEntriesCollection cpcec = cpi.getCurProjCostEntries();
	// CurProjCostEntriesCollection forAdd = new CurProjCostEntriesCollection();
	// if (cpcec.size() != 0) {
	// SelectorItemCollection selector = new SelectorItemCollection();
	// selector.add(new SelectorItemInfo("id"));
	// selector.add(new SelectorItemInfo("curProjCostEntries.*"));
	// selector.add(new SelectorItemInfo("curProjCostEntries.apportionType.*"));
	// selector.add(new SelectorItemInfo("curProjCostEntries.value"));
	// selector.add(new SelectorItemInfo("parent.id"));
	// CurProjectInfo parent = this.getCurProjectInfo(ctx, new
	// ObjectUuidPK(cpi.getParent().getId().toString()), selector);
	// CurProjCostEntriesCollection parentCpcec =
	// parent.getCurProjCostEntries();
	// String sql = "update T_FDC_CurProjCostEntries set FValue = ? where FID =
	// ? ";
	// Object[] params;
	// for (int i = 0; i < cpcec.size(); i++) {
	// boolean oldHave = false;
	// for (int j = 0; j < parentCpcec.size(); j++) {
	// if
	// (cpcec.get(i).getApportionType().getId().toString().equals(parentCpcec.get(j).getApportionType().getId().toString()))
	// {
	// //��ǰ��
	// oldHave = true;
	// //����
	// BigDecimal oldData = parentCpcec.get(j).getValue();
	// BigDecimal newData = cpcec.get(i).getValue().add(oldData);
	// params = new Object[] { newData, parentCpcec.get(j).getId().toString() };
	// DbUtil.execute(ctx, sql, params);
	// }
	// }
	// if (!oldHave) {//��ǰû��
	// //���
	// forAdd.add(cpcec.get(i));
	// }
	// }
	// //��Ҫ��ӵ�
	// if (forAdd.size() != 0) {
	// ICurProjCostEntries iCurProjCostEntries =
	// CurProjCostEntriesFactory.getLocalInstance(ctx);
	// CurProjCostEntriesInfo add;
	// for (int i = 0; i < forAdd.size(); i++) {
	// add = new CurProjCostEntriesInfo();
	// add.setApportionType(forAdd.get(i).getApportionType());
	// add.setValue(forAdd.get(i).getValue());
	// add.setCurProject(parentCpcec.get(0).getCurProject());
	// iCurProjCostEntries.addnew(add);
	// }
	// }
	// gatherTargetData(ctx, parent);//�ݹ�
	// }
	// }
	// }
	
	//���¹�����Ŀ 
	protected void updateProject(Context ctx,CurProjectInfo project) throws BOSException{
		
//		String sql = " update t_fdc_curproject  set fcostcenterId = ( "+
//		//������Ŀ��ӽű�����Ӧ�ĳɱ�����
//		 
//		 "       select fcostcenterouid from			"+ 
//		 "       (										"+
//		 "        select distinct a.fcostcenterouid,ch.fid from t_fdc_projectwithcostcenterou a				"+
//		 "        inner join t_fdc_curproject pa on pa.fid=a.fcurprojectId									"+
//		 "        inner join t_fdc_curproject ch on charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1	"+
//		 "       )  temp  where temp.fid=t_fdc_curproject.fid     											  "+
//		 ") where fid=? ";
		//2008-11-22 ����ű����� BT308809 Caused exception message is: ORA-01427: single-row subquery returns more than one row
		//����Ϊ�������
//		String sql = " update t_fdc_curproject  set fcostcenterId = ( "+
//		 "        select distinct a.fcostcenterouid from t_fdc_projectwithcostcenterou a				"+
//		 "        inner join t_fdc_curproject pa on pa.fid=a.fcurprojectId									"+
//		 "        inner join t_fdc_curproject ch on charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1	"+
//		 "	where fcurprojectid = ?  )" +
//		 " where fid = ?";
		//����ű����� BT314615 Caused exception message is: ORA-01427: single-row subquery returns more than one row
		String sql = "select fcostcenterouid,pal from  " +
				"(select distinct a.fcostcenterouid,ch.fid,pa.flongnumber as pal " +
				"from t_fdc_projectwithcostcenterou a inner join t_fdc_curproject pa on pa.fid=a.fcurprojectId " +
				"inner join t_fdc_curproject ch on charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1 where ch.fid=?)  temp " +
				" order by pal desc"; 
		IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{project.getId().toString()});
		try {
			while(rs.next()){
				//������Լ��Ļ������ã������������ϼ���costcenter  by sxhong 2008-12-23 20:00:16
				if(!project.getLongNumber().equals(rs.getString("pal"))){
					String updateSql = "update t_fdc_curproject  " +
							"set fcostcenterId = '"+rs.getString("fcostcenterouid")+"' where fid = ?";
					DbUtil.execute(ctx,updateSql,new Object[]{project.getId().toString()});
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
//		project.getParent().getId()
		//������Ŀ��ӽű���
		String sqlLongNumber=" update t_fdc_curproject  set FCodingNumber= replace(FLongnumber,'!','.')";

		DbUtil.execute(ctx,sqlLongNumber);
		
		if(!project.isIsLeaf()){
			try {
				updateDisplayName(ctx,project.getId().toString(),project.getDisplayName(),project.getBOSType());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
	}
	
	//�����¼��ĳ�����,�Ѿ��������
	public void updateDisplayName(Context ctx,String parentid,String parentDisplayName,BOSObjectType type) throws BOSException, SQLException{
		String upsql = "update t_fdc_curproject set fdisplayname_l2=? where fid=?";
		String selSql = "select fid,fisleaf,fdisplayname_l2,fname_l2 from t_fdc_curproject where fparentid=? ";
		//����displayname
		
		IRowSet rs = DbUtil.executeQuery(ctx,selSql,new Object[]{parentid});
		while(rs.next()){
				String id = rs.getString("fid");
				boolean isLeaf = rs.getBoolean("fisleaf");
				String name = rs.getString("fname_l2");
				String disname = rs.getString("fdisplayname_l2");
				
				if(isLeaf){
					DbUtil.execute(ctx,upsql,new Object[]{parentDisplayName+"_"+name,id});	
					AssistUtil.updateAssist(ctx,id,type);
				}else{
					updateDisplayName(ctx,id,disname,type);
				}
		}
		
	}

	//���ù�����Ŀϵ��
	protected boolean _setProjectTpe(Context ctx, Map projectTypeMap) throws BOSException, EASBizException {

		if(projectTypeMap==null){
			return false;
		}
		String sql = " update t_fdc_curproject  set fprojecttypeid = ? where fid in ( 						\r\n"+
		 "        select ch.fid from t_fdc_curproject pa 																\r\n"+
		 "        inner join t_fdc_curproject ch on charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1	\r\n"+
		 "	  	  where pa.fid=?																			\r\n"+
		 ")  ";
		String sql2 = " update t_fdc_curproject  set fprojecttypeid = null where fid in ( 						\r\n"+
		 "        select ch.fid  from t_fdc_curproject pa 																\r\n"+
		 "        inner join t_fdc_curproject ch on charindex(pa.flongnumber||'!',ch.flongnumber||'!')=1	\r\n"+
		 "	  	  where pa.fid=?																			\r\n"+
		 ")  ";

		Set set = projectTypeMap.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			ProjectTypeInfo info  = (ProjectTypeInfo)projectTypeMap.get(id);
			if(info!=null){
				DbUtil.execute(ctx,sql,new Object[]{info.getId().toString(),id});	
			}else{
				DbUtil.execute(ctx,sql2,new Object[]{id});
			}
		}		
		
		return true;
	}
	protected String _synchronousProjects(Context ctx, Map projectMap)
			throws BOSException, EASBizException {
		//TODO ��ֻͬ���������Ժ��������Ƶ�ʵ��
		return null;
	}

	protected void _updateSortNo(Context ctx, String cuId) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("update T_FDC_CurProject set fsortno=0 where fisleaf=0 and fcontrolunitid=?");
		sqlBuilder.addParam(cuId);
		sqlBuilder.executeUpdate(ctx);		
	}
	/**
	 * �Ƿ���һ�廯����ƾ֤ģʽ
	 * @param ctx
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isAdjustModel(Context ctx) throws EASBizException, BOSException{
		String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		return FDCUtils.isAdjustVourcherModel(ctx, companyId);
	}

	/**
	 * 
	 * �����������Ƿ񿪷���Ŀ,��Ҫ�������¼���ϵ
	 * 
	 * @author hpw date:2009-08-22 18:23:23
	 * 
	 * @param ctx
	 * @param pk
	 * @param isDevPrj
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected void _setIsDevPrj(Context ctx, IObjectPK pk, boolean isDevPrj)
			throws BOSException, EASBizException {
		Map childInfos = ProjectHelper.getCurProjChildInfos(ctx, pk.toString());
		Set idSet = new HashSet(childInfos.keySet());
		idSet.add(pk.toString());
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_fdc_curproject set fisdevprj = ? ");
		builder.addParam(Boolean.valueOf(isDevPrj));
		builder.appendSql("where ");
		builder.appendParam("t_fdc_curproject.fid", idSet.toArray());
		builder.executeUpdate(ctx);
	}
	/***
	 * ͨ��Ŀ��ɱ����ѯ����ѡ�Ĺ�����Ŀ�У��Ƿ���Ŀ��ɱ�
	 * û������TreeBaseInfo��put("hasAimCost",Boolean.False);
	 * ������TreeBaseInfo��put("hasAimCost",Boolean.True);
	 * 
	 * ���ص��ͻ���
	 * �ͻ�����ʹ�����Info ��ʱ�򣬸��ݴ�key��ֵ���ж��Ƿ���Ŀ��ɱ�
	 */
	public TreeBaseCollection getTreeBaseCollection(Context ctx,
			EntityViewInfo view) throws BOSException {
		TreeBaseCollection colls = super.getTreeBaseCollection(ctx, view);
		Set projectIds = new HashSet();
		for(Iterator it = colls.iterator();it.hasNext();){
			projectIds.add(((TreeBaseInfo)it.next()).getId().toString());
		}
		EntityViewInfo v=new EntityViewInfo();
		v.getSelector().add("orgOrProId");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId",projectIds,CompareType.INCLUDE));
		v.setFilter(filter);
		AimCostCollection acColl=AimCostFactory.getLocalInstance(ctx).getAimCostCollection(v);
		Map projectHasAimCost = new HashMap();
		for(Iterator it = acColl.iterator();it.hasNext();){
			//projectHasAimCost.put(((AimCostInfo)it.next()).getOrgOrProId().toString(),null);
			projectHasAimCost.put(((AimCostInfo)it.next()).getOrgOrProId(),null);
		}
		for(Iterator it = colls.iterator();it.hasNext();){
			TreeBaseInfo info = (TreeBaseInfo)it.next();
			if(projectHasAimCost.containsKey(info.getId().toString()))
				info.put("hasAimCost", Boolean.TRUE);
			else
				info.put("hasAimCost", Boolean.FALSE);
		}
		return colls;
	}	
}
