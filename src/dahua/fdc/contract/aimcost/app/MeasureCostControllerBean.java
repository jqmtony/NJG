package com.kingdee.eas.fdc.aimcost.app;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostException;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryFactory;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeFactory;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogFactory;
import com.kingdee.eas.fdc.aimcost.TempConstrPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.TempConstrPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.TemplateCustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplateCustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.util.AccountStageHelper;
import com.kingdee.eas.fdc.basedata.util.CostAccountHelper;
import com.kingdee.eas.fdc.basedata.util.IncomeAccountHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class MeasureCostControllerBean extends AbstractMeasureCostControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.MeasureCostControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		_updatePartial(ctx, billInfo, selector);
	}

	protected boolean isUseName() {

		return false;
	}

	protected boolean isUseNumber() {
		return false;
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		MeasureCostInfo info = (MeasureCostInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
		PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");
		super._save(ctx, pk, model);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		planIndex.setHeadID(pk.toString());
//		PlanIndexFactory.getLocalInstance(ctx).submit(planIndex); by Cassiel_peng  2009-8-18
		PlanIndexFactory.getLocalInstance(ctx).save(planIndex);
		
		//�ɱ���������������Ƿ�����,��--ɾ��ʱ��Ҳɾ���������
		boolean isIncomeJoinCost=FDCUtils.getDefaultFDCParamByKey(ctx, 
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
		if(isIncomeJoinCost){
			this._costSycMeasureIncome(ctx, pk.toString());
		}
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		MeasureCostInfo info = (MeasureCostInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
		if(info.getVersionNumber()!=null&&info.getVersionNumber().indexOf('!')>-1){
			int index=info.getVersionNumber().indexOf('!');
			info.setMainVerNo(Integer.parseInt(info.getVersionNumber().substring(0,index)));
			info.setSubVerNo(Integer.parseInt(info.getVersionNumber().substring(index+1)));
		}
		PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");

		IObjectPK pk = super._save(ctx, model);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		planIndex.setHeadID(pk.toString());
//		PlanIndexFactory.getLocalInstance(ctx).submit(planIndex);  by Cassiel_peng  2009-8-18
		PlanIndexFactory.getLocalInstance(ctx).save(planIndex);
		
		//�ɱ���������������Ƿ�����,��--ɾ��ʱ��Ҳɾ���������
		boolean isIncomeJoinCost=FDCUtils.getDefaultFDCParamByKey(ctx, 
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
		if(isIncomeJoinCost){
			this._costSycMeasureIncome(ctx, pk.toString());
		}
		
		return pk;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		MeasureCostInfo info = (MeasureCostInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
		if(info.getVersionNumber()!=null&&info.getVersionNumber().indexOf('!')>-1){
			int index=info.getVersionNumber().indexOf('!');
			info.setMainVerNo(Integer.parseInt(info.getVersionNumber().substring(0,index)));
			info.setSubVerNo(Integer.parseInt(info.getVersionNumber().substring(index+1)));
		}
		PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");
//		IObjectPK pk = super._save(ctx, model);    by Cassiel_peng  2009-8-18
		IObjectPK pk = super._submit(ctx, model);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		if(planIndex==null){
			return pk;
		}
		planIndex.setHeadID(pk.toString());
		for (Iterator iter = planIndex.getEntrys().iterator(); iter.hasNext();) {
			PlanIndexEntryInfo entry = (PlanIndexEntryInfo) iter.next();
			// ��null����ת��
			entry.setIsSplit(entry.isIsSplit());
		}
		PlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
		
		//�ɱ���������������Ƿ�����,��--ɾ��ʱ��Ҳɾ���������
		boolean isIncomeJoinCost=FDCUtils.getDefaultFDCParamByKey(ctx, 
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
		if(isIncomeJoinCost){
			this._costSycMeasureIncome(ctx, pk.toString());
		}
		
		return pk;
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		MeasureCostInfo info = (MeasureCostInfo) model;
		info.setVersionNumber(info.getVersionNumber().replaceAll("\\.", "!"));
//		info.setState(FDCBillStateEnum.SAVED);
		info.setState(FDCBillStateEnum.SUBMITTED);
		PlanIndexInfo planIndex = (PlanIndexInfo) info.get("PlanIndex");
		super._submit(ctx, pk, model);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		planIndex.setHeadID(pk.toString());
		PlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
		
		//�ɱ���������������Ƿ�����,��--ɾ��ʱ��Ҳɾ���������
		boolean isIncomeJoinCost=FDCUtils.getDefaultFDCParamByKey(ctx, 
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
		if(isIncomeJoinCost){
			this._costSycMeasureIncome(ctx, pk.toString());
		}
		
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		
		//�ɱ���������������Ƿ�����,��--ɾ��ʱ��Ҳɾ���������
		boolean isIncomeJoinCost=FDCUtils.getDefaultFDCParamByKey(ctx, 
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
		if(isIncomeJoinCost){
			FilterInfo filterIncome = new FilterInfo();
			filterIncome.appendFilterItem("srcMeasureCostId", pk.toString());
			MeasureIncomeFactory.getLocalInstance(ctx).delete(filterIncome);
		}
		
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		Set set = new HashSet();
		for (int i = 0; i < arrayPK.length; i++) {
			set.add(arrayPK[i].toString());
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("headID", set, CompareType.INCLUDE));
		PlanIndexFactory.getLocalInstance(ctx).delete(filter);
		
		//�ɱ���������������Ƿ�����,��--ɾ��ʱ��Ҳɾ���������
		boolean isIncomeJoinCost=FDCUtils.getDefaultFDCParamByKey(ctx, 
				ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
		if(isIncomeJoinCost){
			FilterInfo filterIncome = new FilterInfo();
			filterIncome.getFilterItems().add(new FilterItemInfo("srcMeasureCostId",set,CompareType.INCLUDE));			
			MeasureIncomeFactory.getLocalInstance(ctx).delete(filterIncome);
		}
		
		super._delete(ctx, arrayPK);
	}

	protected void _storeToTmplate(Context ctx, String id, boolean isTemplate) throws BOSException, EASBizException {
		_storeToTemplate(ctx, id, isTemplate);

	}

	protected void _storeToTemplate(Context ctx, String measureCostId, boolean isTemplate) throws BOSException, EASBizException {
		if (measureCostId == null || measureCostId.trim().length() < 1) {
			throw new EASBizException(EASBizException.CHECKNAMEBLANK);
		}

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("costEntry.costAccount.longNumber");
		selector.add("costEntry.*");
		selector.add("constrEntrys.*");
		selector.add("constrEntrys.productType.*");
		
		MeasureCostInfo measure = MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureCostId)), selector);
		PlanIndexInfo planIndex = getPlanIndex(ctx, measureCostId,false);

		TemplateMeasureCostInfo templateMeasureInfo = new TemplateMeasureCostInfo();
		TemplatePlanIndexInfo templatePlanIndexInfo = new TemplatePlanIndexInfo();
		templateMeasureInfo.putAll(measure);
		templatePlanIndexInfo.putAll(planIndex);
		templateMeasureInfo.setId(null);
		templatePlanIndexInfo.setId(null);
		templateMeasureInfo.put("costEntry", new TemplateMeasureEntryCollection());
		// templateMeasureInfo.getCostEntry().clear();
		for (int i = 0; i < measure.getCostEntry().size(); i++) {
			TemplateMeasureEntryInfo entry = new TemplateMeasureEntryInfo();
			entry.putAll(measure.getCostEntry().get(i));
			entry.setId(null);
			templateMeasureInfo.getCostEntry().add(entry);
		}
		templateMeasureInfo.put("constrEntrys", new TempConstrPlanIndexEntryCollection());
		for (int i = 0; i < measure.getConstrEntrys().size(); i++) {
			TempConstrPlanIndexEntryInfo entry = new TempConstrPlanIndexEntryInfo();
			entry.putAll(measure.getConstrEntrys().get(i));
			entry.setId(BOSUuid.create(entry.getBOSType()));
			entry.setParent(templateMeasureInfo);
			templateMeasureInfo.getConstrEntrys().add(entry);
		}
		templatePlanIndexInfo.put("entrys", new TemplatePlanIndexEntryCollection());
		// templatePlanIndexInfo.getEntrys().clear();
		for (int i = 0; i < planIndex.getEntrys().size(); i++) {
			TemplatePlanIndexEntryInfo entry = new TemplatePlanIndexEntryInfo();
			entry.putAll(planIndex.getEntrys().get(i));
			entry.setId(null);
			templatePlanIndexInfo.getEntrys().add(entry);
		}
		templatePlanIndexInfo.put("customEntrys", new TemplateCustomPlanIndexEntryCollection());
		for (int i = 0; i < planIndex.getCustomEntrys().size(); i++) {
			TemplateCustomPlanIndexEntryInfo entry = new TemplateCustomPlanIndexEntryInfo();
			entry.putAll(planIndex.getCustomEntrys().get(i));
			entry.setId(null);
			templatePlanIndexInfo.getCustomEntrys().add(entry);
		}
		
		templateMeasureInfo.put("PlanIndex", templatePlanIndexInfo);
		templateMeasureInfo.setState(FDCBillStateEnum.SAVED);
		templateMeasureInfo.setSourceBillId(measureCostId);
		// if(isTemplate!=null&&isTemplate.equals("1")){
		// templateMeasureInfo.setHasEffected(true);//is template
		// }else{
		// templateMeasureInfo.setHasEffected(false);
		// }
		templateMeasureInfo.setHasEffected(isTemplate);
		templateMeasureInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		templateMeasureInfo.setCreator(null);
		TemplateMeasureCostFactory.getLocalInstance(ctx).save(templateMeasureInfo);

	}

	private PlanIndexInfo getPlanIndex(Context ctx, String headId, boolean isExportIndex) throws BOSException, EASBizException {
		if (headId == null)
			return null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("headID", headId);

		SelectorItemCollection selector = view.getSelector();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("entrys.product.*");
		selector.add("customEntrys.*");
		selector.add("customEntrys.productType.id");
		selector.add("customEntrys.productType.number");
		selector.add("customEntrys.productType.name");
		selector.add("customEntrys.apportType.id");
		selector.add("customEntrys.apportType.number");
		selector.add("customEntrys.apportType.name");
		
		view.getSorter().add(new SorterItemInfo("entrys.type"));
		view.getSorter().add(new SorterItemInfo("entrys.index"));
		PlanIndexCollection planIndexCollection = PlanIndexFactory.getLocalInstance(ctx).getPlanIndexCollection(view);
		if (planIndexCollection.size() == 1) {
			PlanIndexInfo planIndexInfo = planIndexCollection.get(0);
			boolean isBuildPartLogic = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_BUILDPARTLOGIC);
			//����ָ��ʱ���滮ָ����ϵ��Ƿ��̯Ϊ�ǵĲ�Ʒ�������֮�͵�����Ϊ������Ŀ�Ľ������
			if(isBuildPartLogic&&isExportIndex){
				BigDecimal amount = FDCHelper.ZERO;
				for(int i=0;i<planIndexInfo.getEntrys().size();i++){
					if(planIndexInfo.getEntrys().get(i).isIsProduct()&&planIndexInfo.getEntrys().get(i).isIsSplit()){
						amount=amount.add(FDCHelper.toBigDecimal(planIndexInfo.getEntrys().get(i).getBuildArea()));
					}
				}
				planIndexInfo.setTotalBuildArea(amount);
			}
			return planIndexInfo;
		} else {
			return null;
		}
	}

	/** 
	 * @deprecated �ѷ���
	 * @see com.kingdee.eas.fdc.aimcost.app.AbstractMeasureCostControllerBean#_storeFromTemplate(com.kingdee.bos.Context, java.lang.String, java.lang.String)
	 * 
	 */
	protected String _storeFromTemplate(Context ctx, String id, String orgId) throws BOSException, EASBizException {

		
		if (id == null || orgId == null || id.trim().length() < 1) {
			throw new EASBizException(EASBizException.CHECKNAMEBLANK);
		}

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("costEntry.costAccount.longNumber");
		selector.add("costEntry.*");
		TemplateMeasureCostInfo templateMeasure = TemplateMeasureCostFactory.getLocalInstance(ctx).getTemplateMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(id)), selector);
		TemplatePlanIndexInfo templatePlanIndex = getTemplatePlanIndex(ctx, id);

		MeasureCostInfo measureInfo = new MeasureCostInfo();
		PlanIndexInfo planIndexInfo = new PlanIndexInfo();
		measureInfo.putAll(templateMeasure);
		planIndexInfo.putAll(templatePlanIndex);
		measureInfo.setId(null);
		planIndexInfo.setId(null);
		measureInfo.put("costEntry", new MeasureEntryCollection());
		for (int i = 0; i < templateMeasure.getCostEntry().size(); i++) {
			MeasureEntryInfo entry = new MeasureEntryInfo();
			entry.putAll(templateMeasure.getCostEntry().get(i));
			entry.setCostAccount(getOrgCostAccount(ctx, orgId, entry.getCostAccount().getLongNumber()));
			entry.setId(null);
			measureInfo.getCostEntry().add(entry);
		}
		planIndexInfo.put("entrys", new PlanIndexEntryCollection());
		for (int i = 0; i < templatePlanIndex.getEntrys().size(); i++) {
			PlanIndexEntryInfo entry = new PlanIndexEntryInfo();
			entry.putAll(templatePlanIndex.getEntrys().get(i));
			entry.setId(null);
			planIndexInfo.getEntrys().add(entry);
		}
		planIndexInfo.put("customEntrys", new CustomPlanIndexEntryCollection());
		for (int i = 0; i < templatePlanIndex.getCustomEntrys().size(); i++) {
			CustomPlanIndexEntryInfo entry = new CustomPlanIndexEntryInfo();
			entry.putAll(templatePlanIndex.getCustomEntrys().get(i));
			entry.setId(BOSUuid.create(entry.getBOSType()));
			planIndexInfo.getCustomEntrys().add(entry);
		}
		measureInfo.setSourceBillId(null);
		measureInfo.put("PlanIndex", planIndexInfo);
		// save state
		measureInfo.setState(FDCBillStateEnum.SAVED);
		FullOrgUnitInfo org = new FullOrgUnitInfo();
		org.setId(BOSUuid.read(orgId));
		measureInfo.setOrgUnit(org);
		measureInfo.setIsLastVersion(false);
		IObjectPK pk = save(ctx, measureInfo);
		return pk.toString();
	}

	/* 
	 * ֧�ֹ�����Ŀ����֮֯���ģ������
	 * @see com.kingdee.eas.fdc.aimcost.app.AbstractMeasureCostControllerBean#_getMeasureFromTemplate(com.kingdee.bos.Context, java.lang.String, java.lang.String)
	 */
	protected IObjectValue _getMeasureFromTemplate(Context ctx, String id, String objectId) throws BOSException, EASBizException {

		if (id == null || objectId == null || id.trim().length() < 1) {
			throw new EASBizException(EASBizException.CHECKNAMEBLANK);
		}

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("costEntry.costAccount.longNumber");
		selector.add("costEntry.*");
		selector.add("costEntry.unit.id");
		selector.add("costEntry.unit.name");
		selector.add("constrEntrys.*");
		selector.add("constrEntrys.productType.*");
		TemplateMeasureCostInfo templateMeasure = TemplateMeasureCostFactory.getLocalInstance(ctx).getTemplateMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(id)), selector);
		TemplatePlanIndexInfo templatePlanIndex = getTemplatePlanIndex(ctx, id);
		
		MeasureCostInfo measureInfo = new MeasureCostInfo();
		PlanIndexInfo planIndexInfo = new PlanIndexInfo();
		measureInfo.putAll(templateMeasure);
		planIndexInfo.putAll(templatePlanIndex);
		measureInfo.setId(null);
		planIndexInfo.setId(null);
		measureInfo.put("costEntry", new MeasureEntryCollection());
		
		Map acctMap=getAcctMap(ctx, objectId);
		
		for (int i = 0; i < templateMeasure.getCostEntry().size(); i++) {
			MeasureEntryInfo entry = new MeasureEntryInfo();
			entry.putAll(templateMeasure.getCostEntry().get(i));
//			final CostAccountInfo orgCostAccount = getOrgCostAccount(ctx, objectId, entry.getCostAccount().getLongNumber());
			final CostAccountInfo acct = getMapCostAccount(acctMap, entry.getCostAccount().getLongNumber());
			if(acct==null){
				continue;
			}
			entry.setCostAccount(acct);
			entry.setId(null);
			measureInfo.getCostEntry().add(entry);
		}
		measureInfo.put("constrEntrys", new ConstructPlanIndexEntryCollection());
		for (int i = 0; i < templateMeasure.getConstrEntrys().size(); i++) {
			ConstructPlanIndexEntryInfo entry = new ConstructPlanIndexEntryInfo();
			entry.putAll(templateMeasure.getConstrEntrys().get(i));
			entry.setId(BOSUuid.create(entry.getBOSType()));
			measureInfo.getConstrEntrys().add(entry);
		}

		planIndexInfo.put("entrys", new PlanIndexEntryCollection());
		for (int i = 0; i < templatePlanIndex.getEntrys().size(); i++) {
			PlanIndexEntryInfo entry = new PlanIndexEntryInfo();
			entry.putAll(templatePlanIndex.getEntrys().get(i));
			entry.setId(null);
			planIndexInfo.getEntrys().add(entry);
		}
		
		planIndexInfo.put("customEntrys", new CustomPlanIndexEntryCollection());
		for (int i = 0; i < templatePlanIndex.getCustomEntrys().size(); i++) {
			CustomPlanIndexEntryInfo entry = new CustomPlanIndexEntryInfo();
			entry.putAll(templatePlanIndex.getCustomEntrys().get(i));
			entry.setId(BOSUuid.create(entry.getBOSType()));
			planIndexInfo.getCustomEntrys().add(entry);
		}
		
		measureInfo.setSourceBillId(null);
		measureInfo.setSrcMeasureCostId(templateMeasure.getSourceBillId());
		measureInfo.put("PlanIndex", planIndexInfo);
		// save state
		measureInfo.setState(FDCBillStateEnum.SAVED);
		FullOrgUnitInfo org = new FullOrgUnitInfo();
		org.setId(BOSUuid.read(objectId));
		measureInfo.setOrgUnit(org);
		measureInfo.setIsLastVersion(false);
		return measureInfo;
	}

	private Map getAcctStageMap(Context ctx, String objectId, String stageId) throws BOSException {
		Map acctStageMap = AccountStageHelper.initAccoutStageMap(ctx, objectId, stageId);
		Map map = new TreeMap();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())){
			view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		}else{
			view.getFilter().getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", objectId));
		}
//		view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
		// view.getFilter().getFilterItems().add(new FilterItemInfo("isEnabled",
		// Boolean.TRUE));
		//�ɱ���Ŀ�༶����
//		view.getFilter().getFilterItems().add(new FilterItemInfo("stageEntrys.value", Boolean.TRUE));
//		view.getFilter().getFilterItems().add(new FilterItemInfo("stageEntrys.measureStage.FMeasureStageID", stageId));
		CostAccountCollection c = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			CostAccountInfo info = (CostAccountInfo) iter.next();
			if(acctStageMap.containsKey(info.getId().toString())){
				map.put(info.getLongNumber(), info);
			}
		}
		return map;
	}
	
	private Map getAcctMap(Context ctx, String objectId) throws BOSException {
		//create Acct Map
		Map map = new TreeMap();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())){
			view.getFilter().appendFilterItem("curProject.id", objectId);
		}else{
			view.getFilter().appendFilterItem("fullOrgUnit.id", objectId);
		}
//		view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		CostAccountCollection c = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			CostAccountInfo info = (CostAccountInfo) iter.next();
			map.put(info.getLongNumber(), info);
		}
		return map;
	}
	
	private Map getIncomeAcctMap(Context ctx, String objectId) throws BOSException {
		Map map = new TreeMap();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())){
			view.getFilter().appendFilterItem("curProject.id", objectId);
		}else{
			view.getFilter().appendFilterItem("fullOrgUnit.id", objectId);
		}
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		IncomeAccountCollection c = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(view);
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			IncomeAccountInfo info = (IncomeAccountInfo) iter.next();
			map.put(info.getLongNumber(), info);
		}
		return map;
	}
	
	private TemplatePlanIndexInfo getTemplatePlanIndex(Context ctx, String headId) throws BOSException {
		if (headId == null)
			return null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("headID", headId);

		SelectorItemCollection selector = view.getSelector();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("entrys.product.*");
		selector.add("customEntrys.*");
		selector.add("customEntrys.productType.id");
		selector.add("customEntrys.productType.number");
		selector.add("customEntrys.productType.name");
		selector.add("customEntrys.apportType.id");
		selector.add("customEntrys.apportType.number");
		selector.add("customEntrys.apportType.name");
		
		view.getSorter().add(new SorterItemInfo("entrys.type"));
		view.getSorter().add(new SorterItemInfo("entrys.index"));
		TemplatePlanIndexCollection planIndexCollection = TemplatePlanIndexFactory.getLocalInstance(ctx).getTemplatePlanIndexCollection(view);
		if (planIndexCollection.size() == 1) {
			return planIndexCollection.get(0);
		} else {
			return null;
		}
	}

	/**
	 * ����������ָ��
	 * 
	 * �޸ģ�
	 * ����:�Զ���ָ��֧��
	 * @author pengwei_hou by 2010.11.24
	 */
	protected void _exportIndex(Context ctx, BOSUuid id) throws BOSException, EASBizException {
		if (id == null)
			return;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("project.id");
		selector.add("project.projectStatus.id");
		
		MeasureCostInfo measure = MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(id), selector);
		
		if (!measure.isIsLastVersion() || measure.getState() != FDCBillStateEnum.AUDITTED) {
			throw new EASBizException(new NumericExceptionSubItem("120", "�����������հ汾���ܵ���ָ��"));
		}
		if(measure.getProject()!=null&&measure.getProject().getProjectStatus()!=null&&measure.getProject().getProjectStatus().getId()!=null){
			final String projectStatusId = measure.getProject().getProjectStatus().getId().toString();
			if(projectStatusId.equals(ProjectStatusInfo.settleID)||projectStatusId.equals(ProjectStatusInfo.closeID)){
				throw new EASBizException(new NumericExceptionSubItem("121", "�ѿ������ѹرյĹ�����Ŀ��������ָ��"));
			}
		}

		PlanIndexInfo planIndex = getPlanIndex(ctx, id.toString(),true);

		ProjectIndexDataInfo info = new ProjectIndexDataInfo();
		info.setProjOrOrgID(measure.getProject().getId());
		info.setName("���㵼��");
		info.setVerName("���㵼��");
		
		SelectorItemCollection appSelector=new SelectorItemCollection();
		appSelector.add("id");
		appSelector.add("targetType.id");
		ProjectIndexDataEntryInfo buildEntry = new ProjectIndexDataEntryInfo();
		buildEntry.setParent(info);
		ApportionTypeInfo apport = new ApportionTypeInfo();
		apport.setId(BOSUuid.read(ApportionTypeInfo.buildAreaType));
		apport=ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
		buildEntry.setApportionType(apport);
		buildEntry.setParent(info);
		buildEntry.setTargetType(apport.getTargetType());
		
		ProjectIndexDataEntryInfo containEntry = new ProjectIndexDataEntryInfo();
		apport = new ApportionTypeInfo();
		apport.setId(BOSUuid.read(ApportionTypeInfo.placeAreaType));
		apport=ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
		containEntry.setApportionType(apport);
		containEntry.setParent(info);
		containEntry.setTargetType(apport.getTargetType());

		ProjectIndexDataEntryInfo sellEntry = new ProjectIndexDataEntryInfo();
		sellEntry.setParent(info);
		apport = new ApportionTypeInfo();
		apport.setId(BOSUuid.read(ApportionTypeInfo.sellAreaType));
		apport=ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
		sellEntry.setApportionType(apport);
		sellEntry.setParent(info);
		sellEntry.setTargetType(apport.getTargetType());
		
		info.getEntries().add(buildEntry);
		info.getEntries().add(containEntry);
		info.getEntries().add(sellEntry);

		Set customIds = new HashSet();
		if(planIndex!=null&&planIndex.getCustomEntrys()!=null){
			for(Iterator iter=planIndex.getCustomEntrys().iterator();iter.hasNext();){
				CustomPlanIndexEntryInfo entry = (CustomPlanIndexEntryInfo)iter.next();
				if(entry!=null&&entry.getApportType()!=null&&entry.getApportType().getId()!=null){
					customIds.add(entry.getApportType().getId().toString());
				}
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("id");
		view.getSelector().add("targetType.id");
		view.setFilter(new FilterInfo());
		if(customIds.size()>0){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",customIds,CompareType.INCLUDE));
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		//�ɱ���̯����
		view.getFilter().getFilterItems().add(new FilterItemInfo("forCostApportion",Boolean.TRUE));
		
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",ApportionTypeInfo.buildAreaType,CompareType.NOTEQUALS));
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",ApportionTypeInfo.placeAreaType,CompareType.NOTEQUALS));
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",ApportionTypeInfo.sellAreaType,CompareType.NOTEQUALS));
		//TODO �����Ż�һ��ȡ����ָ��
		//ȡ������ָ��
		ApportionTypeCollection colls= ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeCollection(view);
		//����ÿ��ָ������ֵ�ϼ�
		Map otherApportIndex = new HashMap();
		//ָ��
		Map otherApport = new HashMap();
		//ָ�� ����Ʒ
		Map otherProdApport = new HashMap();
		for(Iterator iter = colls.iterator();iter.hasNext();){
			ApportionTypeInfo apportInfo = (ApportionTypeInfo)iter.next();
			ProjectIndexDataEntryInfo indexInfo = new ProjectIndexDataEntryInfo();
			indexInfo.setApportionType(apportInfo);
			indexInfo.setParent(info);
			indexInfo.setTargetType(apportInfo.getTargetType());
			info.getEntries().add(indexInfo);
			otherApport.put(apportInfo.getId().toString(), indexInfo);
			otherProdApport.put(apportInfo.getId().toString(), new HashSet());
			otherApportIndex.put(apportInfo.getId().toString(), FDCHelper.ZERO);
		}
	
		
		// BigDecimal buildArea=FDCHelper.ZERO;//
		BigDecimal buildArea = FDCHelper.toBigDecimal(planIndex.getTotalBuildArea(), 2);
		BigDecimal containArea = FDCHelper.toBigDecimal(planIndex.getTotalContainArea(), 2);
		BigDecimal sellArea = FDCHelper.ZERO;
		BigDecimal sumBuildArea=FDCHelper.ZERO;
		List params=new ArrayList();//sql ���²���
		for (Iterator iter = planIndex.getEntrys().iterator(); iter.hasNext();) {
			PlanIndexEntryInfo entry = (PlanIndexEntryInfo) iter.next();
			// buildArea=buildArea.add(FDCHelper.toBigDecimal(entry.getBuildArea(),2));
			if (entry.getProduct() != null) {
				info.setProductType(entry.getProduct());
				
				//�Զ���ָ��ֵ:һ��ָ������ж����Ʒ
				for (Iterator iter2 = planIndex.getCustomEntrys().iterator(); iter2.hasNext();) {
					CustomPlanIndexEntryInfo entry2 = (CustomPlanIndexEntryInfo) iter2.next();
					if (entry2.getProductType() != null && entry2.getApportType() != null) {
						String apportId = entry2.getApportType().getId().toString();
						String productId = entry2.getProductType().getId().toString();
						//��ͬ��Ʒʱ����ָ��ֵ
						if(entry.getProduct().getId().toString().equals(productId)){
							if (otherApport.containsKey(apportId)) {
								Set prodApport = (Set)otherProdApport.get(apportId);
								if(!prodApport.contains(apportId+productId)){
									ProjectIndexDataEntryInfo indexInfo = (ProjectIndexDataEntryInfo) otherApport
									.get(apportId);
									indexInfo.setIndexValue(entry2.getValue());
									prodApport.add(productId);
								}else{
									ProjectIndexDataEntryInfo indexInfo = (ProjectIndexDataEntryInfo) otherApport
									.get(apportId);
									ProjectIndexDataEntryInfo newIndexInfo = new ProjectIndexDataEntryInfo();
									newIndexInfo.setApportionType(entry2.getApportType());
									newIndexInfo.setParent(info);
									newIndexInfo.setTargetType(indexInfo.getTargetType());
									newIndexInfo.setIndexValue(entry2.getValue());
									info.getEntries().add(newIndexInfo);
									
									
								}

								otherApportIndex.put(apportId, FDCHelper.add(
										otherApportIndex.get(apportId), entry2.getValue()));
							}

						}else{
							//��Ҫ�� ��ͬ��Ʒʱ����֮ǰ���õ�ָ��ֵ�ÿ�
							ProjectIndexDataEntryInfo indexInfo = (ProjectIndexDataEntryInfo) otherApport
							.get(apportId+productId);
							if(indexInfo!=null)
							indexInfo.setIndexValue(null);
						}
					}
				}
				
				// ��Ŀ�Ŀ�������ǲ�Ʒ�������֮��
				// �Ҳ�����ͣ�� by sxhong 2007-12-28
/*				if (entry.getType() == PlanIndexTypeEnum.parking) {
					continue;
				}*/
				if(entry.isIsSplit()){
					sellArea = sellArea.add(FDCHelper.toBigDecimal(entry.getSellArea(), 2));
				}
				sumBuildArea=sumBuildArea.add(FDCHelper.toBigDecimal(entry.getBuildArea(),2));
				buildEntry.setIndexValue(entry.getBuildArea());
				containEntry.setIndexValue(entry.getContainArea());
				sellEntry.setIndexValue(entry.getSellArea());
				if (!measure.isIsAimMeasure()) {
					// ����ָ��
					saveIndex(ctx, info, ProjectStageEnum.RESEARCH);
				} else {
					// Ŀ��ָ�ꡢ��ָ̬��
					saveIndex(ctx, info, ProjectStageEnum.AIMCOST);
					saveIndex(ctx, info, ProjectStageEnum.DYNCOST);
				}
				
				for (int i = 0; i < info.getEntries().size(); i++) {
					info.getEntries().get(i).setIndexValue(null);
				}
				//���¹�����Ŀ�϶�Ӧ�Ĳ�Ʒ���ֶ�
				params.add(Arrays.asList(new Object[] { Boolean.valueOf(entry.isIsSplit()), info.getProjOrOrgID().toString(), entry.getProduct().getId().toString()}));
			}
		}

		info.setProductType(null);
		buildEntry.setIndexValue(buildArea);
		containEntry.setIndexValue(containArea);
		sellEntry.setIndexValue(sellArea);
		buildEntry.setDifValue(buildArea.subtract(sumBuildArea));//����Ĳ�ֵ
		//ָ������ָ����ֵ
		for(Iterator iter = info.getEntries().iterator();iter.hasNext();){
			ProjectIndexDataEntryInfo indexEntry = (ProjectIndexDataEntryInfo)iter.next();
			if(indexEntry!=null){
				if(otherApportIndex.containsKey(indexEntry.getApportionType().getId().toString())){
					indexEntry.setIndexValue(FDCHelper.toBigDecimal(otherApportIndex.get(indexEntry.getApportionType().getId().toString())));
				}
			}
			
		}
		
		// ��Ŀ
		if (!measure.isIsAimMeasure()) {
			// ����ָ��
			saveIndex(ctx, info, ProjectStageEnum.RESEARCH);
		} else {
			// Ŀ��ָ�ꡢ��ָ̬��
			saveIndex(ctx, info, ProjectStageEnum.AIMCOST);
			saveIndex(ctx, info, ProjectStageEnum.DYNCOST);
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		String sql="update T_FDC_CurProjProductEntries set FIsSplit=? where FCurProject=? and FProductType=?";
		builder.executeBatch(sql, params);
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		/**
		 * ֮ǰ"���гɱ�����༭����"��û���ṩ"�ύ"����,����"����"״̬�ĵ��ݿ�ֱ��"����".������֮��Ҳ�ͽ�����״̬����Ϊ��"����"
		 * ���ṩ"�ύ"���ܣ��ͱ�����Ʒ�����֮�󵥾�״̬Ϊ"�ύ"����"����"   by Cassiel_peng 2009-8-18
		 */
//		billInfo.setState(FDCBillStateEnum.SAVED);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		_updatePartial(ctx, billInfo, selector);
	}

	private void saveIndex(Context ctx, ProjectIndexDataInfo info, ProjectStageEnum stage) throws BOSException, EASBizException {
		info.setVerNo(null);
		ProjectIndexDataInfo clone = (ProjectIndexDataInfo) info.clone();
		if (clone.getVerNo() == null) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select fid,fVerNo from t_fdc_projectindexdata where FIsLatestVer=1 and FProjOrOrgID =? and FProjectStage =?  and FProductTypeID ");
			builder.addParam(clone.getProjOrOrgID().toString());
			builder.addParam(stage.getName());
			if (clone.getProductType() != null) {
				builder.appendSql(" = ");
				builder.appendParam(clone.getProductType().getId().toString());
			} else {
				builder.appendSql(" is null ");
			}
			
			
			Set set = new HashSet();
			if (clone.getEntries() != null) {
				for (int i = 0; i < clone.getEntries().size(); i++) {
					if (clone.getEntries().get(i).getApportionType() != null
							&& clone.getEntries().get(i).getApportionType().getId() != null) {
						set.add(clone.getEntries().get(i).getApportionType().getId().toString());
					}
				}
			}
			
			IRowSet rowSet = builder.executeQuery();
			try {
				if (rowSet.size() == 1) {
					// exist get next version No.
					rowSet.next();
					String verNo = rowSet.getString("fVerNo");
//					String fid = rowSet.getString("fid");
					String v = verNo.substring(1, verNo.indexOf("."));
					int i = Integer.parseInt(v);
					verNo = "V" + (++i) + ".0";
					clone.setVerNo(verNo);
					
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					ProjectIndexDataEntryCollection oldInfo = ProjectIndexDataEntryFactory.getLocalInstance(ctx)
							.getProjectIndexDataEntryCollection("select * where parent.id = '" + rowSet.getString("fid") + "'");
					if (oldInfo != null) {
						for (int j = 0; j < oldInfo.size(); j++) {
							ProjectIndexDataEntryInfo entryInfo = oldInfo.get(j);
							if (entryInfo.getApportionType() != null && !set.contains(entryInfo.getApportionType().getId().toString())) {
								entryInfo.setId(null);
								clone.getEntries().add(entryInfo);
							}
						}
					}
				} else {
					// not exist
					clone.setVerNo("V1.0");
				}
				// update db
				builder.clear();
				//ͬʱ��֮ǰ�����°汾�����µ����²��ɽ��и���
				builder.appendSql("update t_fdc_projectindexdata set FIsLatestVer=0,FIsLatestSubVer=0 where (FIsLatestVer=1 or FIsLatestSubVer=1) and FProjOrOrgID =? and FProjectStage =?  and FProductTypeID ");
				builder.addParam(clone.getProjOrOrgID().toString());
				builder.addParam(stage.getName());
				if (clone.getProductType() != null) {
					builder.appendSql(" = ");
					builder.appendParam(clone.getProductType().getId().toString());
				} else {
					builder.appendSql(" is null ");
				}
				builder.execute();
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		clone.setProjectStage(stage);
//		if(stage==ProjectStageEnum.DYNCOST){
//			info.setIsLatestSubVer(true);
//			//Ԥ�۲���
//			info.setVerName(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
//		}else{
//			info.setIsLatestSubVer(false);
//			info.setVerName("���㵼��");
//		}
		
		if(stage==ProjectStageEnum.AIMCOST){
			clone.setVerName(ProjectIndexVerTypeEnum.AIMCOSTAREA_VALUE);
		}else if (stage==ProjectStageEnum.RESEARCH){
			clone.setVerName("���㵼��");
		}else{
			clone.setVerName("���㵼��");
		}
		
		clone.setId(null);
		clone.setIsLatestVer(true);
		for (Iterator iter = clone.getEntries().iterator(); iter.hasNext();) {
			((ProjectIndexDataEntryInfo) iter.next()).setId(null);
		}
		clone.setCreateTime(new Timestamp(System.currentTimeMillis()));
		IObjectPK pk = ProjectIndexDataFactory.getLocalInstance(ctx).submit(clone);
		ProjectIndexDataFactory.getLocalInstance(ctx).audit(BOSUuid.read(pk.toString()));
		

	}	
	
	protected void _costSycMeasureIncome(Context ctx, String measureId) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		MeasureCostInfo measure = MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureId)),sels);
		if(measure.getProject()==null){
			//�������ֻ֧����Ŀ�� by hpw 2010.09.24
			return;
		}
		CurProjectInfo prj = new CurProjectInfo();
		prj.setId(BOSUuid.read(measure.getProject().getId().toString()));
		measure.setProject(prj);
		createMeasureIncome(ctx,measure);
	}
	
	/*
	 * �ɱ����� ���桢�ύ ����ʱ���� ͬ�� �������:��srcMeasureCostId �ж���������Ƿ���ڼ�¼�������޸ģ�����������
	 * �޸�ʱͬ��������׶Ρ���Ŀϵ�С��汾����
	 * ����ʱͬ��������׶Ρ���Ŀϵ�С��汾���ơ�sourceBillId���汾�š�������Ŀ��srcMeasureCostId
	 */
	private void createMeasureIncome(Context ctx, MeasureCostInfo measure) throws BOSException, EASBizException
	{
		MeasureIncomeInfo income =null;
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("srcMeasureCostId", measure.getId().toString());
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("measureStage.id");
		view.getSelector().add("projectType.id");
		view.getSelector().add("versionNumber");
		view.getSelector().add("versionName");
		view.getSelector().add("project.id");
		view.getSelector().add("srcMeasureCostId");
		view.getSelector().add("state");
		SorterItemInfo sort = new SorterItemInfo("versionNumber");
		sort.setSortType(SortType.DESCEND);
		view.getSorter().add(sort);
		
		MeasureIncomeCollection incomeColl = MeasureIncomeFactory.getLocalInstance(ctx).getMeasureIncomeCollection(view);
		if (incomeColl.size() > 0){
			income = incomeColl.get(0);
			income.setMeasureStage(measure.getMeasureStage());
			income.setProjectType(measure.getProjectType());
			income.setVersionName(measure.getVersionName());			
			income.setOrgUnit(measure.getOrgUnit());	// modified by zhaoqin for R130913-0020 on 2013/9/29
		} else{
			income = new MeasureIncomeInfo();
			income.setMeasureStage(measure.getMeasureStage());
			income.setProjectType(measure.getProjectType());
			income.setVersionName(measure.getVersionName());
			income.setVersionNumber(measure.getVersionNumber());
			income.setSourceBillId(measure.getSourceBillId());
			income.setProject(measure.getProject());
			income.setSrcMeasureCostId(measure.getId().toString());
			income.setOrgUnit(measure.getOrgUnit());	// modified by zhaoqin for R130913-0020 on 2013/9/29
			//ͬ����һ�汾������ by hpw
			view = new EntityViewInfo();
			filter=new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("head.srcMeasureCostId",measure.getSrcMeasureCostId()));
			view.getSelector().add("*");
			MeasureIncomeEntryCollection entrys = MeasureIncomeEntryFactory.getLocalInstance(ctx).getMeasureIncomeEntryCollection(view);
			for(Iterator iter=entrys.iterator();iter.hasNext();){
	    		((MeasureIncomeEntryInfo)iter.next()).setId(null);
	    	}
			income.getIncomeEntry().addCollection(entrys);
		}
		
		IObjectPK pk = MeasureIncomeFactory.getLocalInstance(ctx).save(income);
		reverseWriteMeasureInComeProject(ctx, pk.toString(), income.getProject().getId().toString());
		
	}

	protected void _exportAimCost(Context ctx, String measureId, String projectId) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("costEntry.costAccount.longNumber");
		sels.add("costEntry.unit.name");
		sels.add("costEntry.*");
		MeasureCostInfo measure = MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureId)), sels);
		CurProjectInfo prj = new CurProjectInfo();
		prj.setId(BOSUuid.read(projectId));
		measure.setProject(prj);
		createAimCost(ctx, measure);
		Set prjSet=new HashSet();
		prjSet.add(projectId);
		ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
	}
	//ͬ����Ŀ��Ʒ
	protected void _sysProduct(Context ctx, Map param) throws EASBizException, BOSException{
		boolean isSysProd = ((Boolean)param.get("isSysProd")).booleanValue();
		String measureId =(String)param.get("measureId");
		String projectId =(String)param.get("projectId");
		if(isSysProd==false || measureId==null ||projectId==null){
			return;
		}
		PlanIndexInfo planIndexInfo=getPlanIndex(ctx,measureId,false);//����дһ�·���
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("curProjProductEntries.productType.id");
		selector.add("curProjProductEntries.productType.name");
		CurProjectInfo prj=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId), selector);
		CurProjProductEntriesCollection curProjProductEntries = prj.getCurProjProductEntries();
		
		Map planIndexProducts=new HashMap();
		Set addIds = new HashSet();//����
		Set deleteIds = new HashSet();//����
		
		for(int i=0;i<planIndexInfo.getEntrys().size();i++){
			PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
			if(entry.isIsProduct()&&entry.getProduct()!=null){
				entry.getProduct().setBoolean("isSplit", entry.isIsSplit());
				planIndexProducts.put(entry.getProduct().getId().toString(), entry.getProduct());
			}
		}
		addIds=new HashSet(planIndexProducts.keySet());
		for (int i = 0; i < curProjProductEntries.size(); i++) {
			ProductTypeInfo productType = curProjProductEntries.get(i).getProductType();
			String id = productType.getId().toString();
			if (addIds.contains(id)) {
				addIds.remove(id);
			}else{
				deleteIds.add(id);
			}
			
		}
		CoreBaseCollection collection=new CoreBaseCollection();
		for(Iterator iter=addIds.iterator();iter.hasNext();){
			String id = (String)iter.next();
			CurProjProductEntriesInfo info = new CurProjProductEntriesInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			info.setCurProject(prj);
			ProductTypeInfo type = (ProductTypeInfo)planIndexProducts.get(id);
			info.setProductType(type);
			info.setIsSplit(type.getBoolean("isSplit"));
			info.setIsAccObj(true);
			collection.add(info);
		}
		if(collection.size()>0){
			CurProjProductEntriesFactory.getLocalInstance(ctx).addnew(collection);
		}
		if (deleteIds.size() > 0) {

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectId));
			filter.getFilterItems().add(new FilterItemInfo("productType.id", deleteIds, CompareType.INCLUDE));
			CurProjProductEntriesFactory.getLocalInstance(ctx).delete(filter);
		}
		
	}
	
	/**
	 * Ŀ��ɱ����㵼������Ŀ��ɱ�
	 * @param measure �ɱ�����
	 */
	private void createAimCost(Context ctx, MeasureCostInfo measure) throws BOSException, EASBizException {
		AimCostInfo aim = new AimCostInfo();
		aim.setMeasureStage(measure.getMeasureStage());
		aim.setOrgOrProId(measure.getProject().getId().toString());
		aim.setVersionName(measure.getVersionName() + "_���㵼��");
		aim.setIsLastVersion(true);
		int aimCostVersion = getAimCostColsByCurProject(ctx, measure);
		if (aimCostVersion > 1) { // �������Ŀ��ɱ����Ѱ汾�Ŵ������������ɵİ汾��Ҫ��1
//			AimCostInfo aimCostInfo = aimCostCollection.get(0);
//			String versionNumber = aimCostInfo.getVersionNumber();
//			if (versionNumber != null && versionNumber.indexOf('.') > -1) {
//				int index = versionNumber.indexOf('.');
//				versionNumber = (Integer.parseInt(versionNumber.substring(0, index)) + 1) + versionNumber.substring(index);
//			}
			aim.setVersionNumber(aimCostVersion +".0");
			//aim.setVersionNumber(versionNumber);
			
			// ��֮ǰ������Ŀ��ɱ��汾��ʶȥ��
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_AIM_AimCost set fisLastVersion=0 where forgOrProId=? and fisLastVersion=1");
			builder.addParam(measure.getProject().getId().toString());
			builder.execute();
		} else {
			aim.setVersionNumber("1.0");
		}
		
		//����Ŀ��ɱ�Ϊ����������Ϊ������������ܵ��������������ι�����
		aim.setState(measure.getState());
		aim.setAuditor(measure.getAuditor());
		aim.setAuditDate(measure.getAuditTime());
		aim.getCostEntry().clear();
		Map acctMap = getAcctStageMap(ctx, measure.getProject().getId().toString(), measure.getMeasureStage().getId().toString());

		for (int i = 0; i < measure.getCostEntry().size(); i++) {
			MeasureEntryInfo measureEntry = measure.getCostEntry().get(i);
			CostEntryInfo costEntry = new CostEntryInfo();
			BOSUuid entryid = BOSUuid.create(costEntry.getBOSType());
			costEntry.setId(entryid);
			measureEntry.put("entryid", entryid.toString());// ��������
			costEntry.setEntryName(measureEntry.getEntryName());
//			CostAccountInfo costAccount = getPrjCostAccount(ctx, measure.getProject().getId().toString(), measureEntry.getCostAccount().getLongNumber());
			//�ѿ�Ŀӳ�䵽���ʵĶ�Ӧ��Ŀ  by sxhong 2009-08-25 20:12:37
			CostAccountInfo costAccount =getMapCostAccount(acctMap, measureEntry.getCostAccount().getLongNumber());
			if(costAccount==null){
				continue;
			}
			costEntry.setCostAccount(costAccount);
			costEntry.setWorkload(measureEntry.getWorkload());
			costEntry.setUnit(measureEntry.getUnit()==null?measureEntry.getName():measureEntry.getUnit().getName());
			costEntry.setCostAmount(measureEntry.getAmount());
//			costEntry.setPrice(measureEntry.getPrice());
			//����˵���ϵ����Ĵ����߼�
			costEntry.setPrice(FDCNumberHelper.divide(costEntry.getCostAmount(), costEntry.getWorkload()));
			costEntry.setProduct(measureEntry.getProduct());
			costEntry.setProgram(measureEntry.getProgram());
			costEntry.setDesc(measureEntry.getDesc());
			costEntry.setChangeReason(measureEntry.getChangeReason());
			costEntry.setDescription(measureEntry.getDescription());
			costEntry.setMeasureEntryID(measureEntry.getId().toString());
			costEntry.setHead(aim);
			aim.getCostEntry().add(costEntry);
		}

		aim.setIsLastVersion(true);
		IObjectPK pk = AimCostFactory.getLocalInstance(ctx).submit(aim);
		AimCostFactory.getLocalInstance(ctx).audit(BOSUuid.read(pk.toString()));
		setProductSplitType(ctx, measure);
	
	}

	/**
	 * 
	 * ���ݹ�����Ŀ��ø���Ŀ�µ�����Ŀ��ɱ�AimCost�������汾�Ž������򣬼����°汾��ǰ
	 * @Author��owen_wen
	 * @CreateTime��2012-3-30
	 */
	private int getAimCostColsByCurProject(Context ctx, MeasureCostInfo measure) throws BOSException{
		/***********
		 * ���汾��ȡ���һ���汾����ȡ���°�(lastVersion)   -by haiou_li,2014-07-14
		 * 
		
		 * **********/
		int lastVersionNumber = 0;
		try{
			String sql = "select top 1 FVersionNumber from T_Aim_AimCost " +
					"	where FOrgOrProId='" + measure.getProject().getId().toString() + "' and TO_NUMBER(fversionnumber)=("+
					"	select  max(TO_NUMBER(fversionnumber)) from t_aim_aimcost " +
					"	where forgOrProId='"+measure.getProject().getId().toString()+"')";
			IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql).executeSQL();
			
			while (rs.next()) {
				String versionNumber = rs.getString("FVersionNumber");
				int k = versionNumber.indexOf(".");
				String number = versionNumber.substring(0, k);
				lastVersionNumber = Integer.parseInt(number);
	
			}
		} catch(Exception e){
			throw new BOSException(e.getCause());
		}
		return (lastVersionNumber + 1);
	}

	private void setProductSplitType(Context ctx, MeasureCostInfo measure) throws BOSException, EASBizException {
		String productAimSplitInsert = "insert into T_AIM_AimCostProductSplitEntry "
				+ "(fid,FCostEntryId,FProductID,FApportionTypeID,FDirectProportion,FAppointAmount,FDescription_l2) "
				+ "values(?,?,?,?,?,?,'isChoose')";
		String aimproductAimSplitInsert = "insert into T_AIM_AIMAimCostProdSplitEntry "
				+ "(fid,FCostEntryId,FProductID,FApportionTypeID,FDirectProportion,FAppointAmount,FDescription_l2) "
				+ "values(?,?,?,?,?,?,'isChoose')";
		
		List aimSplitList = new ArrayList();
		List aimAimSplitList = new ArrayList();
		List productList = new ArrayList();
		BOSObjectType aimCostType = new AimCostProductSplitEntryInfo().getBOSType();
		BOSObjectType aimAimCostType = new AIMAimCostProductSplitEntryInfo().getBOSType();
		PlanIndexInfo planIndex = getPlanIndex(ctx, measure.getId().toString(),false);
		for (Iterator iter = planIndex.getEntrys().iterator(); iter.hasNext();) {
			PlanIndexEntryInfo entry = (PlanIndexEntryInfo) iter.next();
			if (entry.getProduct() != null && entry.isIsSplit()) {
				productList.add(entry.getProduct().getId().toString());
			}
		}
		for (Iterator iter = measure.getCostEntry().iterator(); iter.hasNext();) {
			MeasureEntryInfo entry = (MeasureEntryInfo) iter.next();
			String splitType = entry.getNumber();
			String costEntryid = entry.getString("entryid");
			if (splitType != null && entry.getProduct() == null && costEntryid != null) {
				String id = null;
				String productid = null;
				String apportionType = null;
				BigDecimal directAmt = FDCHelper.ZERO;
				
				/*******�޸�֧��ָ����̯****/
				if(splitType.equals("containArea")||splitType.equals("buildArea")||splitType.equals("sellArea")){
					if (splitType.equals("containArea")) {
						apportionType = ApportionTypeInfo.placeAreaType;
					} else if (splitType.equals("buildArea")) {
						apportionType = ApportionTypeInfo.buildAreaType;
					} else if (splitType.equals("sellArea")) {
						apportionType = ApportionTypeInfo.sellAreaType;
					}
					for (int i = 0; i < productList.size(); i++) {
						id = BOSUuid.create(aimCostType).toString();
						productid = (String) productList.get(i);
						aimSplitList.add(Arrays.asList(new Object[] { id, costEntryid, productid, apportionType, directAmt, null }));
						id = BOSUuid.create(aimAimCostType).toString();
						aimAimSplitList.add(Arrays.asList(new Object[] { id, costEntryid, productid, apportionType, directAmt, null }));
					}
				}else{
					apportionType = ApportionTypeInfo.appointType;
					 if (splitType.startsWith("man")) {
							String[] splitProduct = splitType.split("\\[\\]");
							if(splitProduct.length>0){
								
								BigDecimal sumAmount = FDCHelper.ZERO;
							for (int i = 0; i < splitProduct.length; i++) {
								if (splitProduct[i].startsWith("man"))
									continue;
								String[] s = splitProduct[i].split("\\|");
								if (s.length != 2)
									continue;
								if (!productList.contains(s[0]))
									continue;
								sumAmount = FDCHelper.add(sumAmount, new BigDecimal(s[1]));
							}
								for(int i=0;i<splitProduct.length;i++){
									if(splitProduct[i].startsWith("man"))
										continue;
									String[] s = splitProduct[i].split("\\|");
									if(s.length!=2)
										continue;
									if(!productList.contains(s[0]))
										continue;
								//									aimSplitList.add(
								//											Arrays.asList(new Object[] { BOSUuid.create(aimCostType).toString(), costEntryid, s[0], 
								//													apportionType,new BigDecimal(s[1])}));
								//									aimAimSplitList.add(
								//											Arrays.asList(new Object[] { BOSUuid.create(aimAimCostType).toString(), costEntryid, s[0], 
								//													apportionType,new BigDecimal(s[1])}));
									BigDecimal divide = FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), new BigDecimal(s[1])),
										sumAmount, 2, BigDecimal.ROUND_HALF_UP);
									
									aimSplitList.add(
											Arrays.asList(new Object[] { BOSUuid.create(aimCostType).toString(), costEntryid, s[0], 
													apportionType, divide, new BigDecimal(s[1]) }));
									aimAimSplitList.add(
											Arrays.asList(new Object[] { BOSUuid.create(aimAimCostType).toString(), costEntryid, s[0], 
													apportionType, divide, new BigDecimal(s[1]) }));
								}
							}
					}else{
							
						otherSplit(aimSplitList, aimAimSplitList,productList, aimCostType, aimAimCostType, splitType, costEntryid, 
								apportionType,planIndex,(MeasureCostInfo)measure.clone());
					}
				}
				/*******�޸�֧��ָ����̯****/
			}
		}

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.executeBatch(productAimSplitInsert, aimSplitList);
		builder.executeBatch(aimproductAimSplitInsert, aimAimSplitList);
		//����Ʒ��̬�ɱ���
		setDynProductAimSplit(ctx,measure.getProject().getId().toString(),productList);
		// ������������
		if(false){
			//����Ӱ������,��ʱȥ��
			List gcList = new ArrayList();
			gcList.add("delete from T_AIM_AimCostProductSplitEntry where fcostentryid not in (select fid from T_AIM_CostEntry)");
			gcList.add("delete from T_AIM_AIMAimCostProdSplitEntry where fcostentryid not in (select fid from T_AIM_CostEntry)");
			gcList.add("delete from T_AIM_DynCostProduSplitEntry��where FDynamicCostId not in (select  fid from T_AIM_DynamicCost)");
			builder.executeBatch(gcList);
		}
		// String aimProductAimSplitInsert="insert into
		// T_AIM_AIMAimCostProdSplitEntry
		// (fid,FCostEntryId,FProductID,FApportionTypeID,FDescription_l2,directAmount)
		// values(?,(select fid from T_AIM_CostEntry where
		// fcostaccountid=?),?,?,'isChoose',?)";
		// String fullDynProdcutSplitInsert="insert into
		// T_AIM_DynCostProduSplitEntry
		// (fid,FDynamicCostId,FProductID,FApportionTypeID,FDescription_l2)
		// values(?,(select fid T_AIM_DynamicCost where
		// FAccountID=?),?,'isChoose')";
	}

	
	/**
	 * Ŀ��ɱ��������
	 * @param aimSplitList
	 * @param aimAimSplitList
	 * @param aimCostType
	 * @param aimAimCostType
	 * @param splitType
	 * @param costEntryid
	 * @param apportionType
	 * @param planIndex
	 */
	private void otherSplit(List aimSplitList, List aimAimSplitList,List productList, BOSObjectType aimCostType,	BOSObjectType aimAimCostType, 
			String splitType, String costEntryid,	String apportionType, PlanIndexInfo planIndex,MeasureCostInfo measure){
		BigDecimal indexSum = FDCHelper.ZERO;			//ָ����ֵ
		BigDecimal amtSum = FDCHelper.ZERO;				//�ϼ���ֵ
		BigDecimal tempSum = FDCHelper.ZERO;			//���������������һ��ָ��Ľ��
		BigDecimal temp = FDCHelper.ZERO;
		HashMap productIndexmap = new HashMap();				//��ƷID-ָ���Ӧֵ
//		HashMap productAmtmap = new HashMap();					//��ƷID-����Ӧֵ
		String aimCostTypeId = null;
		String aimAimCostTypeId = null;
		String productId = null;
		/**
		 * ��ָ����ֵ��������ƷID����Ӧ��ָ�����productIndexmap
		 */
		for(Iterator ite = planIndex.getEntrys().iterator();ite.hasNext();temp = FDCHelper.ZERO){
			PlanIndexEntryInfo planIndexEntry = (PlanIndexEntryInfo)ite.next();
			if(planIndexEntry.getProduct() != null &&
					planIndexEntry.getProduct().getId()!=null && 
					productList.contains(planIndexEntry.getProduct().getId().toString())) {
				temp = FDCHelper.toBigDecimal(planIndexEntry.get(splitType));
				productIndexmap.put(planIndexEntry.getProduct().getId().toString(),temp);
				indexSum = indexSum.add(temp);
			}
		}
		/**
		 * ��ϼ���ֵ��������ƷID����Ӧ�ĺϼ۴���productAmtmap
		 */
		for(Iterator ite = measure.getCostEntry().iterator();ite.hasNext();temp = FDCHelper.ZERO){
			MeasureEntryInfo measureEntry = (MeasureEntryInfo)ite.next();
			if(measureEntry.getProduct() ==null &&
					splitType.equals(measureEntry.getNumber())){
				temp = FDCHelper.toBigDecimal(measureEntry.getAmount());
//				productAmtmap.put(measureEntry.getProduct().getId().toString(),temp);
				amtSum = amtSum.add(temp);
			}
		}
		for(int i=0;i<productList.size();i++){
			temp = FDCHelper.ZERO;
			aimCostTypeId = BOSUuid.create(aimCostType).toString();
			aimAimCostTypeId = BOSUuid.create(aimAimCostType).toString();
			productId = (String)productList.get(i);
			if(i<productList.size()-1) { 
				temp = FDCHelper.toBigDecimal(
						FDCHelper.divide(amtSum.multiply(FDCHelper.toBigDecimal(productIndexmap.get(productId))),
						indexSum, 2, BigDecimal.ROUND_HALF_UP));
				tempSum = tempSum.add(temp);
			}
			else
				temp = amtSum.subtract(tempSum);
			aimSplitList.add(
					Arrays.asList(new Object[] { aimCostTypeId, costEntryid, productId, apportionType, temp, null }));
			aimAimSplitList.add(
					Arrays.asList(new Object[] { aimAimCostTypeId, costEntryid, productId, apportionType, temp, null }));
		}
		
	}
	
	
	private void setDynProductAimSplit(Context ctx, String prjId,List productList) throws BOSException, EASBizException {
		//����Ʒ��̬�ɱ���
		String mapKey="prjAcct"+prjId;
		if (ctx.get(mapKey) instanceof Map) {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			String dynInset="insert into T_AIM_DynamicCost (fid,FAccountID) values (?,?)"; 
			String dynSplitInsert = "insert into T_AIM_DynCostProduSplitEntry " + 
			"(fid,FDynamicCostId,FProductID,FApportionTypeID,FDescription_l2) " + 
			"values(?,?,?,?,'isChoose')";
			List dynList=new ArrayList();
			List dynSplitList=new ArrayList();
			
			BOSObjectType dynType = new DynamicCostInfo().getBOSType();
			BOSObjectType dynSplitType = new DynamicCostProductSplitEntryInfo().getBOSType();
			Map map = (Map) ctx.get(mapKey);
			Set set=new HashSet();
			try {
				builder.clear();
				builder.appendSql("select b.fid from T_AIM_DynamicCost a inner join T_FDC_CostAccount b on a.faccountid=b.fid where b.fcurproject=?");
				builder.addParam(prjId);
				IRowSet rowSet = builder.executeQuery();
				while (rowSet.next()) {
					set.add(rowSet.getString("fid"));
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
				SysUtil.abort();
			}
			
			for(Iterator iter=map.values().iterator();iter.hasNext();	){
				CostAccountInfo info=(CostAccountInfo)iter.next();
				if(set.contains(info.getId().toString())){
					//���з�̯�����Ĳ�����
					continue;
				}
				
				String dynId = BOSUuid.create(dynType).toString();
				dynList.add(Arrays.asList(new Object[]{
						dynId,info.getId().toString()
				}));
				
				for (int i = 0; i < productList.size(); i++) {
					String id = BOSUuid.create(dynSplitType).toString();
					String productid = (String) productList.get(i);
					dynSplitList.add(Arrays.asList(new Object[] { id, dynId, productid, ApportionTypeInfo.aimCostType }));

				}
				
				
			}
			
			builder.executeBatch(dynInset, dynList);
			builder.executeBatch(dynSplitInsert, dynSplitList);
		}

	}

	private CostAccountInfo getOrgCostAccount(Context ctx, String orgId, String longNumber) throws BOSException {
		if (orgId == null || longNumber == null) {
			throw new BOSException("��ѡ������Ŀ�µĳɱ���Ŀ������");
		}
		String mapKey="orgAcct"+orgId;
		longNumber = longNumber.replaceAll("\\.", "!");
		Map map = null;
		if (ctx.get(mapKey) instanceof Map) {
			map = (Map) ctx.get(mapKey);
		} else {
			map = new TreeMap();
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().appendFilterItem("fullOrgUnit.id", orgId);
			view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
			CostAccountCollection c = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
			for (Iterator iter = c.iterator(); iter.hasNext();) {
				CostAccountInfo info = (CostAccountInfo) iter.next();
				map.put(orgId + info.getLongNumber(), info);
			}
			ctx.put(mapKey, map);
		}
		String myKey = orgId + longNumber;
		if (map.get(myKey) != null) {
			return (CostAccountInfo) map.get(myKey);
		} else {
			// �����¼�
			Set keys = map.keySet();
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (key.startsWith(myKey + "!")) {
					return (CostAccountInfo) map.get(key);
				}
			}
			
			//��˾����Ŀû�еĿ�Ŀ��ֱ�ӹ鵽�ϼ��ϼ���Ŀ
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (myKey.startsWith(key+"!")) {
					return (CostAccountInfo) map.get(key);
				}
			}
		}

		return null;

	}
	private CostAccountInfo getPrjCostAccount(Context ctx, String prjId, String longNumber) throws BOSException {
		if (prjId == null || longNumber == null) {
			throw new BOSException("��ѡ������Ŀ�µĳɱ���Ŀ������");
		}
		longNumber = longNumber.replaceAll("\\.", "!");
		Map map = null;
		String mapKey="prjAcct"+prjId;
		if (ctx.get(mapKey) instanceof Map) {
			map = (Map) ctx.get(mapKey);
		} else {
			map = new TreeMap();
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().appendFilterItem("curProject.id", prjId);
			view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
			CostAccountCollection c = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
			for (Iterator iter = c.iterator(); iter.hasNext();) {
				CostAccountInfo info = (CostAccountInfo) iter.next();
				map.put(prjId + info.getLongNumber(), info);
			}
			ctx.put(mapKey, map);
		}
		String myKey = prjId + longNumber;
		if (map.get(myKey) != null) {
			return (CostAccountInfo) map.get(myKey);
		} else {
			// �����¼�
			Set keys = map.keySet();
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (key.startsWith(myKey + "!")) {
					return (CostAccountInfo) map.get(key);
				}
			}
			
			//��˾����Ŀû�еĿ�Ŀ��ֱ�ӹ鵽�ϼ��ϼ���Ŀ
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (myKey.startsWith(key+"!")) {
					return (CostAccountInfo) map.get(key);
				}
			}
		}

		return null;

	}
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		return new HashMap();
	}

	protected Map _getImportData(Context ctx, Map params) throws BOSException, EASBizException {
		if(params==null||params.size()<=0){
			return new HashMap();
		}
		Map dataMap=new HashMap();
		String projectTypeNumber=(String)params.get("ProjectTypeNumber");
		String projectNumber=(String)params.get("ProjectNumber");
		String orgId=(String)params.get("OrgId");
		Set productSet=(Set)params.get("ProductNumberSet");
		if(projectTypeNumber!=null){
			EntityViewInfo view=new  EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("number", projectTypeNumber);
			view.getSelector().add("number");
			view.getSelector().add("id");
			view.getSelector().add("name");
			ProjectTypeCollection projectTypeCol=ProjectTypeFactory.getLocalInstance(ctx).getProjectTypeCollection(view);
			if(projectTypeCol.size()>0){
				dataMap.put("projectType", projectTypeCol.get(0));
			}
		}
		if(projectNumber!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new  FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("longNumber", projectNumber.replace('.','!'));
			view.getSelector().add("id");
			view.getSelector().add("longNumber");
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("projectType.id");
			CurProjectCollection prjs=CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);
			if(prjs.size()>0){
				dataMap.put("project", prjs.get(0));
			}
		}
		if(orgId!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new  FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("fullOrgUnit.id", orgId);
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("id");
			view.getSelector().add("isLeaf");
			view.getSelector().add("longNumber");
			CostAccountCollection accts=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
			if(accts.size()>0){
				HashMap acctMap=new HashMap();
				for(Iterator iter=accts.iterator();iter.hasNext();){
					CostAccountInfo info=(CostAccountInfo)iter.next();
					acctMap.put(info.getLongNumber().replace('!', '.'), info);
				}
				dataMap.put("acctMap", acctMap);
			}
			
		}
		if(productSet!=null&&productSet.size()>0){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new  FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("number",productSet,CompareType.INCLUDE));
			ProductTypeCollection productCol=ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(view);
			if(productCol.size()>0){
				Map productMap=new HashMap();
				for(Iterator iter=productCol.iterator();iter.hasNext();){
					ProductTypeInfo info=(ProductTypeInfo)iter.next();
					productMap.put(info.getNumber(), info);
				}
				dataMap.put("productMap", productMap);
			}
		}
		return dataMap;
	}

	/**
	 * �������ӷ������˶�ȡģ������<p>
	 * @return Object
	 */
	protected Object _getTemplateDataStream(Context ctx) throws BOSException, EASBizException {
		logger.info("hpwURL: " +"ready to export data ...");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Object object = null;
		InputStream is = null;
		try {
			is=Class.forName("com.kingdee.eas.fdc.aimcost.app.MeasureCostControllerBean").getResourceAsStream("MeasureImporterTemplate.xls");
			logger.info("hpwURL: is= "+is);
			byte[] inData  = new byte[is.available()];
			while(is!=null&&is.available()>0){
				is.read(inData);
				os.write(inData);
			}
			logger.info("hpwURL: " + "export complete !");
			object = os.toByteArray();
		} catch (Exception e){
			throw new AimCostException(AimCostException.FILEREADENRROR);
		} finally{
			try{
				os.close();
				if(is != null)
				{
					is.close();
				}
				logger.info("hpwURL: " + "server close stream!");
			} catch( Exception e){
				throw new AimCostException(AimCostException.FILEREADENRROR);
			}
		}
		
		return object;
	}
	
	private CostAccountInfo getMapCostAccount(Map acctMap,String longNumber){
		//�༶�ɱ��ܿ�
//		return CostAccountHelper.getCostAccount(acctMap, longNumber);
		return CostAccountHelper.getCostAccount1(acctMap, longNumber);
	}

	private IncomeAccountInfo getMapIncomeAccount(Map incomeAcctMap,String longNumber){
		return IncomeAccountHelper.getIncomeAccount(incomeAcctMap, longNumber);
	}
	
	/**
	 * ����ע�ͣ�Ŀ��ɱ�����A����Ŀ��ɱ���B��Ŀʱ����¼�п�Ŀ��A��Ŀ�ģ���Ҫ���� by hpw
	 */
	protected void _reverseWriteProject(Context ctx, String measureId, String targetPrjId) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("project.id");
		sels.add("costEntry.id");
		sels.add("costEntry.costAccount.id");
		sels.add("costEntry.costAccount.longNumber");
		sels.add("costEntry.costAccount.curProject.id");
		MeasureCostInfo measure = MeasureCostFactory.getLocalInstance(ctx).getMeasureCostInfo(new ObjectUuidPK(BOSUuid.read(measureId)), sels);
		
		sels=new SelectorItemCollection();
		sels.add("project.id");
		MeasureCostFactory.getLocalInstance(ctx).updatePartial(measure, sels);
		String projectId=targetPrjId;
		//��������Ӧ�Ŀ�Ŀ�Ĺ�����Ŀ��������Ŀ��ͬ��Ҫ���²����ĿΪ������Ŀ�Ŀ�Ŀ
		boolean mustSyn=true;
		if(measure.getCostEntry().size()>0){
			if(measure.getCostEntry().get(0).getCostAccount().getCurProject()!=null){
				String acctPrjId=measure.getCostEntry().get(0).getCostAccount().getCurProject().getId().toString();
				if(acctPrjId.equals(projectId)){
					mustSyn=false;
				}
			}
		}
		
		if(mustSyn){
			//ͬ��
			Map acctMap=getAcctMap(ctx, projectId);
			List list=new ArrayList();
			String insertSql="update T_Aim_MeasureEntry set fcostaccountid=? where fid=?";
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			for(Iterator iter=measure.getCostEntry().iterator();iter.hasNext();){
				MeasureEntryInfo entry=(MeasureEntryInfo)iter.next();
				CostAccountInfo acct=getMapCostAccount(acctMap, entry.getCostAccount().getLongNumber());
				if(acct==null){
					continue;
				}
				list.add(Arrays.asList(new String[]{acct.getId().toString(),entry.getId().toString()}));
				if(list.size()>1000){
					builder.executeBatch(insertSql, list);
					list.clear();
				}
			}
			
			if(list.size()>0){
				builder.executeBatch(insertSql, list);
			}
		}
		
	}
	
	/**
	 * ����ע�ͣ�����������A������B��Ŀʱ����¼�п�Ŀ��A��Ŀ�ģ���Ҫ���� by hpw
	 * @param income
	 * @param targetPrjId
	 */
	private void reverseWriteMeasureInComeProject(Context ctx, String measureId, String targetPrjId) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("project.id");
		sels.add("incomeEntry.id");
		sels.add("incomeEntry.IncomeAccount.id");
		sels.add("incomeEntry.IncomeAccount.longNumber");
		sels.add("incomeEntry.IncomeAccount.curProject.id");
		MeasureIncomeInfo income = MeasureIncomeFactory.getLocalInstance(ctx).getMeasureIncomeInfo(new ObjectUuidPK(BOSUuid.read(measureId)), sels);
		
		
		String projectId=targetPrjId;
		//��������Ӧ�Ŀ�Ŀ�Ĺ�����Ŀ��������Ŀ��ͬ��Ҫ���²����ĿΪ������Ŀ�Ŀ�Ŀ
		boolean mustSyn=true;
		if(income.getIncomeEntry().size()>0){
			if(income.getIncomeEntry().get(0).getIncomeAccount().getCurProject()!=null){
				String acctPrjId=income.getIncomeEntry().get(0).getIncomeAccount().getCurProject().getId().toString();
				if(acctPrjId.equals(projectId)){
					mustSyn=false;
				}
			}
		}
		
		if(mustSyn){
			//ͬ��
			Map acctMap=getIncomeAcctMap(ctx, projectId);
			List list=new ArrayList();
			String insertSql="update T_AIM_MeasureIncomeEntry set FIncomeAccountID=? where fid=?";
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			for(Iterator iter=income.getIncomeEntry().iterator();iter.hasNext();){
				MeasureIncomeEntryInfo entry=(MeasureIncomeEntryInfo)iter.next();
				IncomeAccountInfo acct=getMapIncomeAccount(acctMap, entry.getIncomeAccount().getLongNumber());
				if(acct==null){
					continue;
				}
				list.add(Arrays.asList(new String[]{acct.getId().toString(),entry.getId().toString()}));
				if(list.size()>1000){
					builder.executeBatch(insertSql, list);
					list.clear();
				}
			}
			
			if(list.size()>0){
				builder.executeBatch(insertSql, list);
			}
		}
		
	}
	

	/**
	 * ��Ŀ���ڿ��в������
	 */
	protected Map _getMeasureRptData(Context ctx, Map params)
			throws BOSException, EASBizException {
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("----------start----------");
		Map retValue = new HashMap();
		CurProjectInfo curProjectInfo = (CurProjectInfo)params.get("curProjectInfo");
		String curProjectID = curProjectInfo.getId().toString();
		//������ȷ������������Ŀ�����¼�,���¼����¼�
		Map childInfos = ProjectHelper.getCurProjChildInfos(ctx, curProjectID);
		Set idSet = new HashSet(childInfos.keySet());
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("project.*");
		view.getSelector().add("costEntry.*");
		view.getSelector().add("costEntry.costAccount.id");
		view.getSelector().add("costEntry.costAccount.name");
		view.getSelector().add("costEntry.costAccount.longNumber");
		view.getSelector().add("costEntry.costAccount.type");
		view.getSelector().add("costEntry.product.id");
		view.getSelector().add("costEntry.product.name");
		view.getSelector().add("costEntry.product.number");
		view.getSelector().add("costEntry.unit.name");
		filter.getFilterItems().add(new FilterItemInfo("project.id",idSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("project.longNumber"));
		MeasureCostCollection measureCosts = getMeasureCostCollection(ctx, view);
		Set headIDSet = new HashSet();
		Set prjIDSet = new HashSet();
		Map measureCostsMap = new HashMap();
		Map costEntrysMap = new HashMap();
		Map acctMap = getAcctMap(ctx, curProjectID);
		for(int i=0;i<measureCosts.size();i++){
			MeasureCostInfo measureCost = measureCosts.get(i);
			String prjId = measureCost.getProject().getId().toString();
			for(int j=0;j<measureCost.getCostEntry().size();j++){
				MeasureEntryInfo measureEntry = measureCost.getCostEntry().get(j);
				CostAccountTypeEnum type = measureCost.getCostEntry().get(j)
						.getCostAccount().getType();
				if (measureEntry == null) {
					continue;
				}
				String key = getMapCostAccount(acctMap,measureEntry.getCostAccount().getLongNumber()).getLongNumber();
				if(measureEntry.getProduct()!=null){
					key += prjId+measureEntry.getProduct().getId().toString();;
				}
				
				if(costEntrysMap.containsKey(key)){
					MeasureEntryCollection coll = (MeasureEntryCollection)costEntrysMap.get(key);
					MeasureEntryInfo fuseInfo = coll.get(0);
					if(CostAccountTypeEnum.SIX.equals(type)){
						fuseInfo.setCostAccount(getMapCostAccount(acctMap,measureEntry.getCostAccount().getLongNumber()));
						fuseInfo.setIndexValue(FDCHelper.add(fuseInfo.getIndexValue(), measureEntry.getIndexValue()));
						fuseInfo.setWorkload(FDCHelper.add(fuseInfo.getWorkload(), measureEntry.getWorkload()));
						fuseInfo.setAmount(FDCHelper.add(fuseInfo.getAmount(), measureEntry.getAmount()));
					}else{
						coll.add(measureEntry);
					}
				}else{
					MeasureEntryCollection newColl = new MeasureEntryCollection();
					measureEntry.setCostAccount(getMapCostAccount(acctMap,measureEntry.getCostAccount().getLongNumber()));
					newColl.add(measureEntry);
					costEntrysMap.put(key, newColl);
				}
			}
			String headID = measureCost.getId().toString();
			headIDSet.add(headID);
			prjIDSet.add(prjId);
			measureCostsMap.put(headID, measureCost);
		}
		if(headIDSet.size()==0){
			throw new EASBizException(new NumericExceptionSubItem("100","������Ŀ����û�����հ汾����!"));
		}
		TimeTools.getInstance().msValuePrintln("------------ after load measureCosts ----------");
		
		
		view = new EntityViewInfo();
		filter=new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("entrys.*");
		view.getSelector().add("entrys.product.*");
		filter.getFilterItems().add(new FilterItemInfo("headID", headIDSet, CompareType.INCLUDE));
		view.getSorter().add(new SorterItemInfo("entrys.type"));
		view.getSorter().add(new SorterItemInfo("entrys.index"));
		PlanIndexCollection planIndexs = PlanIndexFactory.getLocalInstance(ctx).getPlanIndexCollection(view);
		Map planIndexsMap = new HashMap();
		int entrysSize = 0;
		BigDecimal allSellArea = FDCHelper.ZERO;
		BigDecimal allBuildArea = FDCHelper.ZERO;
		boolean isBuildPartLogic = false;
		boolean isPlanIndexLogic = false;
		Map hmAllParam = getParams(ctx);
		isBuildPartLogic = isBuildPartLogic(hmAllParam);
		isPlanIndexLogic  = isPlanIndexLogic(hmAllParam);
		retValue.put("isUseAdjustCoefficient", Boolean.valueOf(isUseAdjustCoefficient(hmAllParam)));
		retValue.put("isUseQuality", Boolean.valueOf(isUseQuality(hmAllParam)));
		retValue.put("isUseCustomIndex", Boolean.valueOf(isUseCustomIndex(hmAllParam)));
		retValue.put("isBuildPartLogic", Boolean.valueOf(isBuildPartLogic));
		retValue.put("isPlanIndexLogic", Boolean.valueOf(isPlanIndexLogic));
		PlanIndexInfo fixInfo = new PlanIndexInfo();
		for(int i=0;i<planIndexs.size();i++){
			PlanIndexInfo planIndex = planIndexs.get(i);
			for(int j=0;j<planIndex.getEntrys().size();j++){
				if(planIndex.getEntrys().get(j)==null||planIndex.getEntrys().get(j).getProduct()==null){
					continue;
				}
				if(planIndex.getEntrys().get(j).isIsProduct()&&planIndex.getEntrys().get(j).isIsSplit()){
					allSellArea=allSellArea.add(FDCHelper.toBigDecimal(planIndex.getEntrys().get(j).getSellArea()));
					if(isBuildPartLogic){
						allBuildArea=allBuildArea.add(FDCHelper.toBigDecimal(planIndex.getEntrys().get(j).getBuildArea()));
					}
				}
				entrysSize ++;
			}
			if(!isBuildPartLogic){
				allBuildArea = allBuildArea.add(FDCHelper.toBigDecimal(planIndex.getTotalBuildArea()));
			}
			planIndexsMap.put(planIndexs.get(i).getHeadID().toString(), planIndexs.get(i));
			
			fixInfo.setTotalContainArea(FDCHelper.add(fixInfo.getTotalContainArea(), planIndex.getTotalContainArea()));
			fixInfo.setBuildArea(FDCHelper.add(fixInfo.getBuildArea(), planIndex.getBuildArea()));
			fixInfo.setTotalBuildArea(FDCHelper.add(fixInfo.getTotalBuildArea(), planIndex.getTotalBuildArea()));
			fixInfo.setBuildContainArea(FDCHelper.add(fixInfo.getBuildContainArea(), planIndex.getBuildContainArea()));
			fixInfo.setTotalRoadArea(FDCHelper.add(fixInfo.getTotalRoadArea(), planIndex.getTotalRoadArea()));
			fixInfo.setTotalGreenArea(FDCHelper.add(fixInfo.getTotalGreenArea(), planIndex.getTotalGreenArea()));
			fixInfo.setCubageRateArea(FDCHelper.add(fixInfo.getCubageRateArea(), planIndex.getCubageRateArea()));
			fixInfo.setPitchRoad(FDCHelper.add(fixInfo.getPitchRoad(), planIndex.getPitchRoad()));
			fixInfo.setConcreteRoad(FDCHelper.add(fixInfo.getConcreteRoad(), planIndex.getConcreteRoad()));
			fixInfo.setHardRoad(FDCHelper.add(fixInfo.getHardRoad(), planIndex.getHardRoad()));
			
			fixInfo.setHardSquare(FDCHelper.add(fixInfo.getHardSquare(), planIndex.getHardSquare()));
			fixInfo.setHardManRoad(FDCHelper.add(fixInfo.getHardManRoad(), planIndex.getHardManRoad()));
			
			fixInfo.setImportPubGreenArea(FDCHelper.add(fixInfo.getImportPubGreenArea(), planIndex.getImportPubGreenArea()));
			fixInfo.setHouseGreenArea(FDCHelper.add(fixInfo.getHouseGreenArea(), planIndex.getHouseGreenArea()));
			fixInfo.setPrivateGarden(FDCHelper.add(fixInfo.getPrivateGarden(), planIndex.getPrivateGarden()));
			fixInfo.setWarterViewArea(FDCHelper.add(fixInfo.getWarterViewArea(), planIndex.getWarterViewArea()));
		}
		BigDecimal amt=FDCHelper.ZERO;
		amt=FDCHelper.add(amt,fixInfo.getPitchRoad());
		amt=FDCHelper.add(amt,fixInfo.getConcreteRoad());
		amt=FDCHelper.add(amt, fixInfo.getHardRoad());
		if(!isPlanIndexLogic(hmAllParam)){
			amt=FDCHelper.add(amt, fixInfo.getHardSquare());
			amt=FDCHelper.add(amt, fixInfo.getHardManRoad());
		}
		fixInfo.setTotalRoadArea(amt);
		amt=FDCHelper.ZERO;
		amt=FDCHelper.add(amt,fixInfo.getImportPubGreenArea());
		amt=FDCHelper.add(amt,fixInfo.getHouseGreenArea());
		amt=FDCHelper.add(amt, fixInfo.getPrivateGarden());
		amt=FDCHelper.add(amt, fixInfo.getWarterViewArea());
		if(isPlanIndexLogic(hmAllParam)){
			amt=FDCHelper.add(amt, fixInfo.getHardSquare());
			amt=FDCHelper.add(amt, fixInfo.getHardManRoad());
		}
		fixInfo.setTotalGreenArea(amt);
		
		fixInfo.setBuildDensity(FDCHelper.divide(fixInfo.getBuildContainArea(), fixInfo.getBuildArea(), 4, BigDecimal.ROUND_HALF_UP));
		fixInfo.setCubageRate(FDCHelper.divide(fixInfo.getCubageRateArea(), fixInfo.getBuildArea(), 2, BigDecimal.ROUND_HALF_UP));
		fixInfo.setGreenAreaRate(FDCHelper.divide(fixInfo.getTotalGreenArea(), fixInfo.getTotalContainArea(), 4, BigDecimal.ROUND_HALF_UP));
		
		retValue.put("allSellArea", allSellArea);
		retValue.put("allBuildArea", allBuildArea);
		retValue.put("fixInfo", fixInfo);
		
		//Ϊ�˱������򣬴�����
		retValue.put("measureCosts", measureCosts);
		retValue.put("measureCostsMap", measureCostsMap);
		retValue.put("costEntrysMap", costEntrysMap);
		retValue.put("planIndexsMap", planIndexsMap);
		retValue.put("entrysSize", new Integer(entrysSize));
		TimeTools.getInstance().msValuePrintln("------------ end ----------");
		return retValue;
	}
	
	/**
	 * ��˽������params(HashMap),�ɲ�ȡ���·�ʽȡֵ
	 * <pre>if (params==null) {
			try {
				params = FDCUtils.getDefaultFDCParam(null, null);
			} catch (Exception e) {
				handUIException(e);
			}
		}
        Object theValue = params.get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	 * @param ctx 
	 */
	private HashMap getParams(Context ctx) {
	        HashMap hmParamIn = new HashMap();
	        HashMap hmAllParam= new HashMap();
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREADJUST, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREQUALITY, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTOMINDEX, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_PLANINDEXLOGIC, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_BUILDPARTLOGIC, null);
	        try{
	        	hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
	        }catch(Exception e){
	        	e.printStackTrace();
	        	SysUtil.abort();
	        }
        return hmAllParam;
	}
	private boolean isUseAdjustCoefficient(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_MEASUREADJUST);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * ����Ʒ��������
	 * @return
	 */
	private boolean isUseQuality(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * �����Զ���ָ��
	 * @return
	 */
	protected boolean isUseCustomIndex(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_USECOSTOMINDEX);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * ���гɱ����㼰Ŀ��ɱ�����ʱ���滮ָ����ϣ����е����㳡�����ڡ��̻��õغϼơ���
	 * @return
	 */
	protected boolean isPlanIndexLogic(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_PLANINDEXLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * ���гɱ����㡢Ŀ��ɱ���������Ŀ�����������㣬Ŀ��ɱ����㵼����Ŀ�������ָ�궼ʹ�ò����̯�Ĳ�Ʒ�������֮�ͣ��������ܽ������
	 * @return
	 */
	protected boolean isBuildPartLogic(Map hmAllParam) {
		Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_BUILDPARTLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}

	/**
	 * ��������������ڹ�������Ҫչʾ ʵʩ�ɱ��ܶ� �ɱ���Ŀ��������
	 * 
	 * �ύ��������ʱ�������޶��汾����Ҫ����ĳ��Ŀ��汾1.0ʱ�ܼƵ������ʣ�eg:
	 * �ڶ����޶����Ƿ�Ŀ��ɱ��������ٷ���=��1.2ʵʩ�ɱ��ܶ�-1.0ʵʩ�ɱ��ܶ��1.0ʵʩ�ɱ��ܶ
	 * @author owen_wen 2010-10-11
	 * @param ctx
	 * @param billid �����������ĵ��ݵ�ID
	 * @param costaccountid �ɱ���ĿID
	 * @param projectid  ������ĿID
	 * @return ������
	 * @throws BOSException
	 */
	protected double _getIncreaseRate(Context ctx, String billid, String costaccountLongNumber, String projectid) throws BOSException {
		
		FDCSQLBuilder sqlBuilder1 = new FDCSQLBuilder(ctx);
		sqlBuilder1.appendSql("select sum(entry.famount) from t_aim_measureentry entry ");
		sqlBuilder1.appendSql(" inner join T_AIM_MeasureCost mc on mc.fid = entry.fheadid ");
		sqlBuilder1.appendSql(" inner join T_FDC_CostAccount ca on ca.fid = entry.fcostaccountid ");
		sqlBuilder1.appendSql(" where mc.fid =? and ca.flongnumber like ? ");
		sqlBuilder1.addParam(billid);
		sqlBuilder1.addParam(costaccountLongNumber + "%");
		logger.debug(sqlBuilder1.getTestSql());
		IRowSet rs1 = sqlBuilder1.executeQuery();
		
		double currentAmount = 0;
		try {
			if (rs1.next()) 
				currentAmount = rs1.getDouble(1);
		} catch (SQLException e1) {
			logger.debug(e1.getMessage(), e1);
			e1.printStackTrace();
			SysUtil.abort();
		}
		
		FDCSQLBuilder sqlBuilder2 = new FDCSQLBuilder(ctx);
		sqlBuilder2.appendSql("select FMainVerNo,FMeasureStageID from T_AIM_MeasureCost mc where mc.fid = ? ");
		sqlBuilder2.addParam(billid);
		logger.debug(sqlBuilder2.getTestSql());
		IRowSet rs2 = sqlBuilder2.executeQuery();
		
		String mainVerNo = null ; // ���汾�ţ�Ҫ�жϵ�ǰ�Ƕ��ĸ����汾���ݽ����޶�
		String measureStageID = null; // ����׶�
		try {
			if (rs2.next()){ 
				mainVerNo = rs2.getString(1);
				measureStageID = rs2.getString(2);
			}
		} catch (SQLException e1) {
			logger.debug(e1.getMessage(), e1);
			e1.printStackTrace();
			SysUtil.abort();
		}
		
		FDCSQLBuilder sqlBuilder3 = new FDCSQLBuilder(ctx);
		sqlBuilder3.appendSql("select sum(entry.famount) from t_aim_measureentry entry");
		sqlBuilder3.appendSql(" inner join T_AIM_MeasureCost mc on mc.Fid = entry.fheadid ");
		sqlBuilder3.appendSql(" inner join T_FDC_CostAccount ca on ca.Fid = entry.fcostaccountid ");
		sqlBuilder3.appendSql(" where ca.flongnumber like ? " );
		sqlBuilder3.appendSql(" and mc.Fprojectid =? " );
		sqlBuilder3.appendSql(" and mc.FMeasureStageID =? " );
		sqlBuilder3.appendSql(" and mc.Fversionnumber = ?  and  mc.fisAimMeasure = 1");		
		sqlBuilder3.addParam(costaccountLongNumber + "%");
		sqlBuilder3.addParam(projectid);
		sqlBuilder3.addParam(measureStageID);
		sqlBuilder3.addParam(mainVerNo + "!0");
		
		logger.debug(sqlBuilder3.getTestSql());
		IRowSet rs3 = sqlBuilder3.executeQuery();
		
		double initAmount = 0; 
		try {
			if (rs3.next()) 
				initAmount = rs3.getDouble(1);
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
			SysUtil.abort();
		}
		
		if (initAmount == 0) // �п��ܻ���ַ�ĸΪ0������������֣�ֱ�ӷ���0
			return 0;
		else
			return (currentAmount - initAmount)/initAmount;
	}

}
