package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.rpc.RPCException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.jdbc.rowset.IRowSet;
import common.Logger;

/**
 * ������Ŀ�����࣬�����ṩ�ӿ�
 * @author liupd
 *
 */
public class ProjectHelper {
	
	public static final String INDEX_VALUE = "indexValue";
	
	public static final String APPORTION_TYPE_ID = "apportionTypeID";
	
	private static Logger logger = Logger.getLogger(ProjectHelper.class);

	/**
	 * ���ݹ�����ĿID����Ʒ����ID��ָ��ID��ȡָ��ֵ
	 * @param ctx
	 * @param projectId
	 * @param productTypeId
	 * @param apportionTypeId
	 * @return
	 * @throws BOSException
	 */
	public static BigDecimal getIndexValueByProjProdIdx(Context ctx, String projectId, String productTypeId, String apportionTypeId, ProjectStageEnum projectStage) throws BOSException {
		BigDecimal indexValue = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.projOrOrgId", projectId));
		filter.getFilterItems().add(new FilterItemInfo("parent.productType.id", productTypeId));
		filter.getFilterItems().add(new FilterItemInfo("parent.projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("apportionType.id", apportionTypeId));
		view.setFilter(filter);
		view.getSelector().add(INDEX_VALUE);
		IProjectIndexDataEntry iProjectIndexDataEntry = null;
		if(ctx != null) {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getRemoteInstance();
		}
		ProjectIndexDataEntryCollection projectIndexDataEntryCollection = iProjectIndexDataEntry.getProjectIndexDataEntryCollection(view);
		
		if(projectIndexDataEntryCollection == null || projectIndexDataEntryCollection.size() == 0) return indexValue;
		
		indexValue = projectIndexDataEntryCollection.get(0).getIndexValue();
	
		return indexValue;
	}
	
	/**
	 * ���ݹ�����ĿID��ָ��ID��ȡָ��ֵ,���صĽ��������Ŀ����Ʒ��ָ��
	 * @param ctx
	 * @param projectId
	 * @param productTypeId
	 * @param apportionTypeId
	 * @return
	 * @throws BOSException
	 */
	public static Map getIndexValueByProjProd(Context ctx, String projectId,String apportionTypeId, ProjectStageEnum projectStage) throws BOSException {
		Map indexValues = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.projOrOrgId", projectId));
		filter.getFilterItems().add(new FilterItemInfo("parent.projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("apportionType.id", apportionTypeId));
		view.setFilter(filter);
		view.getSelector().add(INDEX_VALUE);
		view.getSelector().add("apportionType.id");
		view.getSelector().add("parent.projOrOrgID");
		view.getSelector().add("parent.productType.id");
		IProjectIndexDataEntry iProjectIndexDataEntry = null;
		if(ctx != null) {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getRemoteInstance();
		}
		ProjectIndexDataEntryCollection projectIndexDataEntryCollection = iProjectIndexDataEntry.getProjectIndexDataEntryCollection(view);

		
		if(projectIndexDataEntryCollection == null || projectIndexDataEntryCollection.size() == 0){ 
			return indexValues;
		}
			
		for (Iterator iter = projectIndexDataEntryCollection.iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
			String key=element.getParent().getProjOrOrgID().toString();
			ProductTypeInfo prod=element.getParent().getProductType();
			if(prod!=null){
				key += " " + prod.getId().toString();
			}
			key += " " + element.getApportionType().getId().toString();
			indexValues.put(key, element.getIndexValue());
		}
		
		return indexValues;
	}
	
	/**
	 * ���ݹ�����ĿID��ָ��ID��ȡָ��ֵ,���صĽ��������Ŀ����Ʒ��ָ��
	 * @param ctx
	 * @param projectId
	 * @param productTypeId
	 * @param apportionTypeId
	 * @return
	 * @throws BOSException
	 */
	public static Map getIndexValue(Context ctx, String projectId,String[] apportionTypeIds, ProjectStageEnum projectStage,boolean includeProduct) throws BOSException {
		if(projectId==null||apportionTypeIds==null||apportionTypeIds.length==0||projectStage==null){
			throw new IllegalArgumentException("argument is null,please check");
		}
		Map indexValues = new HashMap();
		Set apportionTypeSet=new HashSet(Arrays.asList(apportionTypeIds));
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.projOrOrgId", projectId));
		filter.getFilterItems().add(new FilterItemInfo("parent.projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("apportionType.id", apportionTypeSet,CompareType.INCLUDE));
		if(!includeProduct){
			filter.getFilterItems().add(new FilterItemInfo("parent.productType.id", null,CompareType.EQUALS));
		}
		view.setFilter(filter);
		view.getSelector().add(INDEX_VALUE);
		view.getSelector().add("apportionType.id");
		view.getSelector().add("parent.projOrOrgID");
		view.getSelector().add("parent.productType.id");
		IProjectIndexDataEntry iProjectIndexDataEntry = null;
		if(ctx != null) {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getRemoteInstance();
		}
		ProjectIndexDataEntryCollection projectIndexDataEntryCollection = iProjectIndexDataEntry.getProjectIndexDataEntryCollection(view);

		
		if(projectIndexDataEntryCollection == null || projectIndexDataEntryCollection.size() == 0){ 
			return indexValues;
		}
			
		for (Iterator iter = projectIndexDataEntryCollection.iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
			String key=element.getParent().getProjOrOrgID().toString();
			ProductTypeInfo prod=element.getParent().getProductType();
			if(prod!=null){
				key += " " + prod.getId().toString();
			}
			key += " " + element.getApportionType().getId().toString();
			indexValues.put(key, element.getIndexValue());
		}
		
		return indexValues;
	}
	/**
	 * ���ݹ�����ĿID����Ʒ����ID��ȡָ��ֵ
	 * @param ctx �ͻ��˵��ô�null������˵��ô�Context
	 * @param projectId ������ĿID
	 * @param projectStage ��Ŀ����ö�٣����У�Ŀ�꣬��̬��
	 * @param productTypeId ��Ʒ����ID
	 * @return Map{} Key=projectId+[" "+prodId+]" "+appId
	 * @throws BOSException
	 */
	public static Map getIndexValueByProjProd(Context ctx, String projectId, ProjectStageEnum projectStage) throws BOSException {
		Map indexValues = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(!FDCHelper.isEmpty(projectId)) {
			filter.getFilterItems().add(new FilterItemInfo("projOrOrgId", projectId));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("projOrOrgID");
		view.getSelector().add("productType.id");
		view.getSelector().add("entries.apportionType.id");
		view.getSelector().add("entries."+INDEX_VALUE);
		IProjectIndexData iProjectIndexData = null;
		if(ctx != null) {
			iProjectIndexData = ProjectIndexDataFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexData = ProjectIndexDataFactory.getRemoteInstance();
		}
		
		ProjectIndexDataCollection projectIndexDataCollection = iProjectIndexData.getProjectIndexDataCollection(view);
		
		if(projectIndexDataCollection == null || projectIndexDataCollection.size() == 0) return indexValues;
		
			
		String productTypeId = null;
		String key = null;
		for (Iterator iter = projectIndexDataCollection.iterator(); iter.hasNext();) {
			
			ProjectIndexDataInfo info = (ProjectIndexDataInfo) iter.next();
			
			if(info.getProductType() != null) {
				productTypeId = info.getProductType().getId().toString();
			}else{
				productTypeId=null;
			}
			
			ProjectIndexDataEntryCollection entries = info.getEntries();
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iterator.next();
				key = info.getProjOrOrgID().toString();
				if(productTypeId != null) {
					key += " " + productTypeId;
				}
				key += " " + element.getApportionType().getId().toString();
				indexValues.put(key, element.getIndexValue());
			}
			
		}
		
		return indexValues;
	}
	
	/**
	 * ���ݹ�����ĿID����Ʒ����ID��ȡָ��ֵ
	 * @param ctx �ͻ��˵��ô�null������˵��ô�Context
	 * @param projectId ������ĿID
	 * @param projectStage ��Ŀ����ö�٣����У�Ŀ�꣬��̬��
	 * @param productTypeId ��Ʒ����ID
	 * @return Map{}
	 * @throws BOSException
	 */
	public static Map getIndexValueByProjProd(Context ctx, String[] projectIds, ProjectStageEnum projectStage) throws BOSException {
		Map indexValues = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(!FDCHelper.isEmpty(projectIds)) {
			filter.getFilterItems().add(new FilterItemInfo("projOrOrgId", FDCHelper.getSetByArray(projectIds), CompareType.INCLUDE));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("projOrOrgID");
		view.getSelector().add("productType.id");
		view.getSelector().add("entries.apportionType.id");
		view.getSelector().add("entries."+INDEX_VALUE);
		IProjectIndexData iProjectIndexData = null;
		if(ctx != null) {
			iProjectIndexData = ProjectIndexDataFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexData = ProjectIndexDataFactory.getRemoteInstance();
		}
		
		ProjectIndexDataCollection projectIndexDataCollection = iProjectIndexData.getProjectIndexDataCollection(view);
		
		if(projectIndexDataCollection == null || projectIndexDataCollection.size() == 0) return indexValues;
		
			
		String productTypeId = null;
		String key = null;
		for (Iterator iter = projectIndexDataCollection.iterator(); iter.hasNext();) {
			
			ProjectIndexDataInfo info = (ProjectIndexDataInfo) iter.next();
			
			if(info.getProductType() != null) {
				productTypeId = info.getProductType().getId().toString();
			}
			else {
				productTypeId = null;
			}
			
			ProjectIndexDataEntryCollection entries = info.getEntries();
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				key = info.getProjOrOrgID().toString();
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iterator.next();
				if(productTypeId != null) {
					key += "_" + productTypeId;
				}
				key += "_" + element.getApportionType().getId().toString();
				indexValues.put(key, element.getIndexValue());
			}
			
		}
		
		return indexValues;
	}
	
	/**
	 * ���ݹ�����ĿID��ָ��ID��ȡָ��ֵ,���صĽ��������Ŀ����Ʒ��ָ��
	 * @param ctx
	 * @param projectId
	 * @param productTypeId
	 * @param apportionTypeId
	 * @return
	 * @throws BOSException
	 */
	public static Map getIndexValueByProjProd(Context ctx, String projectId,String apportionTypeId, ProjectStageEnum projectStage,PeriodInfo periodInfo) throws BOSException {
		Map indexValues = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.projOrOrgId", projectId));
		filter.getFilterItems().add(new FilterItemInfo("parent.projectStage", projectStage.getValue()));
		//filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("apportionType.id", apportionTypeId));
		final BOSObjectType type = new ProjectIndexDataInfo().getBOSType();
		SettledMonthlyHelper.addPeriodFilter(filter, "parent.id", type, periodInfo.getId().toString(),projectId,null);
		view.setFilter(filter);
		view.getSelector().add(INDEX_VALUE);
		view.getSelector().add("apportionType.id");
		view.getSelector().add("parent.projOrOrgID");
		view.getSelector().add("parent.productType.id");
		IProjectIndexDataEntry iProjectIndexDataEntry = null;
		if(ctx != null) {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getRemoteInstance();
		}
		ProjectIndexDataEntryCollection projectIndexDataEntryCollection = iProjectIndexDataEntry.getProjectIndexDataEntryCollection(view);

		
		if(projectIndexDataEntryCollection == null || projectIndexDataEntryCollection.size() == 0){ 
			return indexValues;
		}
			
		for (Iterator iter = projectIndexDataEntryCollection.iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
			String key=element.getParent().getProjOrOrgID().toString();
			ProductTypeInfo prod=element.getParent().getProductType();
			if(prod!=null){
				key += " " + prod.getId().toString();
			}
			key += " " + element.getApportionType().getId().toString();
			indexValues.put(key, element.getIndexValue());
		}
		
		return indexValues;
	}
	
	/**
	 * ���ݹ�����ĿID��ָ��ID��ȡָ��ֵ,���صĽ��������Ŀ����Ʒ��ָ�꣨�����½��м��ȡָ���ڼ����ݣ�
	 * @param ctx
	 * @param projectId
	 * @param productTypeIds 
	 * @param apportionTypeId
	 * @return
	 * @throws BOSException
	 */
	public static Map getIndexValueByProjProd(Context ctx, String projectId,Set apportionTypeIds, ProjectStageEnum projectStage,PeriodInfo periodInfo) throws BOSException {
		Map indexValues = new HashMap();
		if(apportionTypeIds == null || apportionTypeIds.size() <= 0)
			return indexValues;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.projOrOrgId", projectId));
		filter.getFilterItems().add(new FilterItemInfo("parent.projectStage", projectStage.getValue()));
		//�����½��м���ڼ�ȡָ��ʱ����Ӧ��ȡ���°汾���ݣ����°汾����ʼ�����������
//		filter.getFilterItems().add(new FilterItemInfo("parent.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("apportionType.id", apportionTypeIds,CompareType.INCLUDE));
		final BOSObjectType type = new ProjectIndexDataInfo().getBOSType();
		SettledMonthlyHelper.addPeriodFilter(filter, "parent.id", type, periodInfo.getId().toString(),projectId,null);
		view.setFilter(filter);
		view.getSelector().add(INDEX_VALUE);
		view.getSelector().add("apportionType.id");
		view.getSelector().add("parent.projOrOrgID");
		view.getSelector().add("parent.productType.id");
		IProjectIndexDataEntry iProjectIndexDataEntry = null;
		if(ctx != null) {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexDataEntry = ProjectIndexDataEntryFactory.getRemoteInstance();
		}
		ProjectIndexDataEntryCollection projectIndexDataEntryCollection = iProjectIndexDataEntry.getProjectIndexDataEntryCollection(view);

		
		if(projectIndexDataEntryCollection == null || projectIndexDataEntryCollection.size() == 0){ 
			return indexValues;
		}
			
		for (Iterator iter = projectIndexDataEntryCollection.iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
			String key=element.getParent().getProjOrOrgID().toString();
			ProductTypeInfo prod=element.getParent().getProductType();
			if(prod!=null){
				key += " " + prod.getId().toString();
			}
			key += " " + element.getApportionType().getId().toString();
			indexValues.put(key, element.getIndexValue());
		}
		
		return indexValues;
	}
	
	/**
	 * ���ݹ�����ĿID����Ʒ����ID��ȡָ��ֵ
	 * @param ctx �ͻ��˵��ô�null������˵��ô�Context
	 * @param projectId ������ĿID
	 * @param projectStage ��Ŀ����ö�٣����У�Ŀ�꣬��̬��
	 * @param productTypeId ��Ʒ����ID
	 * @return Map{} Key=projectId+[" "+prodId+]" "+appId
	 * @throws BOSException
	 */
	public static Map getIndexValueByProjProd(Context ctx, String projectId, ProjectStageEnum projectStage,String periodId) throws BOSException {
		if(periodId==null){
			return getIndexValueByProjProd(ctx, projectId, projectStage);
		}
		Map indexValues = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(!FDCHelper.isEmpty(projectId)) {
			filter.getFilterItems().add(new FilterItemInfo("projOrOrgId", projectId));
		}
		filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		final BOSObjectType type = new ProjectIndexDataInfo().getBOSType();
		SettledMonthlyHelper.addPeriodFilter(filter, "id", type, periodId,projectId,null);
		view.setFilter(filter);
		view.getSelector().add("projOrOrgID");
		view.getSelector().add("productType.id");
		view.getSelector().add("entries.apportionType.id");
		view.getSelector().add("entries."+INDEX_VALUE);
		IProjectIndexData iProjectIndexData = null;
		if(ctx != null) {
			iProjectIndexData = ProjectIndexDataFactory.getLocalInstance(ctx);
		}
		else {
			iProjectIndexData = ProjectIndexDataFactory.getRemoteInstance();
		}
		
		ProjectIndexDataCollection projectIndexDataCollection = iProjectIndexData.getProjectIndexDataCollection(view);
		
		if(projectIndexDataCollection == null || projectIndexDataCollection.size() == 0) return indexValues;
		
			
		String productTypeId = null;
		String key = null;
		for (Iterator iter = projectIndexDataCollection.iterator(); iter.hasNext();) {
			
			ProjectIndexDataInfo info = (ProjectIndexDataInfo) iter.next();
			
			if(info.getProductType() != null) {
				productTypeId = info.getProductType().getId().toString();
			}else{
				productTypeId=null;
			}
			
			ProjectIndexDataEntryCollection entries = info.getEntries();
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iterator.next();
				key = info.getProjOrOrgID().toString();
				if(productTypeId != null) {
					key += " " + productTypeId;
				}
				key += " " + element.getApportionType().getId().toString();
				indexValues.put(key, element.getIndexValue());
			}
			
		}
		
		return indexValues;
	}
	
	/**
	 * ���ݹ�����ĿID��ȡָ��ֵ
	 * @param ctx �ͻ��˵��ô�null������˵��ô�Context
	 * @param projectId ������ĿID
	 * @return Map{} Key=ָ������ + ��Ŀ����ö�٣����У�Ŀ�꣬��̬��+ ��Ʒ����
	 * @throws BOSException
	 */
	public static Map getIndexValueByProjectId(Context ctx, String projectId)
			throws BOSException, EASBizException {
		Map indexValues = new HashMap();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("projOrOrgId", projectId));
		filter.getFilterItems().add(
				new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("projOrOrgID");
		view.getSelector().add("projectStage");
		view.getSelector().add("productType.id");
		view.getSelector().add("entries.apportionType.id");
		view.getSelector().add("entries." + INDEX_VALUE);
		IProjectIndexData iProjectIndexData = null;
		if (ctx != null) {
			iProjectIndexData = ProjectIndexDataFactory.getLocalInstance(ctx);
		} else {
			iProjectIndexData = ProjectIndexDataFactory.getRemoteInstance();
		}
		ProjectIndexDataCollection projectIndexDataCollection = iProjectIndexData
				.getProjectIndexDataCollection(view);
		if (projectIndexDataCollection == null
				|| projectIndexDataCollection.size() == 0) {
			return indexValues;
		}
		String productTypeId = null;
		String key = null;
		for (Iterator iter = projectIndexDataCollection.iterator(); iter
				.hasNext();) {
			ProjectIndexDataInfo info = (ProjectIndexDataInfo) iter.next();
			if (info.getProductType() != null) {
				productTypeId = info.getProductType().getId().toString();
			} else {
				productTypeId = null;
			}
			ProjectIndexDataEntryCollection entries = info.getEntries();
			for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iterator
						.next();
				key = element.getApportionType().getId().toString() + info.getProjectStage().getValue();
				indexValues.put(key, element.getIndexValue());
				if (productTypeId != null) {
					key += productTypeId;
					// ֧�ֲ�Ʒָ��
					indexValues.put(key, element.getIndexValue());
				}
			}
		}
		return indexValues;
	}
	
	
    /**
     * ����ԭ���Ľӿڣ���ָ�����ȡ��
     * @return
     */
    public static CurProjCostEntriesCollection getCurProjCostEntries(String prjId) {
    	CurProjCostEntriesCollection coll = new CurProjCostEntriesCollection();
    	
    	if(prjId == null) return coll;
    	
    	String id = prjId;
    	
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", id));
		filter.getFilterItems().add(new FilterItemInfo("productType", null));
		filter.getFilterItems().add(new FilterItemInfo("projectStage", ProjectStageEnum.DYNCOST_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("entries.apportionType.*");
		view.getSelector().add("entries.indexValue");
		
		ProjectIndexDataCollection projectIndexDataCollection = null;
		
		try {
			projectIndexDataCollection = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		}catch (RPCException ex){ //������ڷ���˵��ã���Ҫ�״��쳣�������쳣�����÷������˵ķ���
			// @AbortException
			Context context = ContextUtils.getContextFromSession();
			try {
				projectIndexDataCollection = ProjectIndexDataFactory.getLocalInstance(context).getProjectIndexDataCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} catch (BOSException e) {
			throw new RuntimeException(e);
		}
    	
    	if(projectIndexDataCollection == null || projectIndexDataCollection.size() == 0) return coll;
    	
    	ProjectIndexDataInfo idxInfo = projectIndexDataCollection.get(0);
    	ProjectIndexDataEntryCollection entries = idxInfo.getEntries();
    	
    	CurProjCostEntriesInfo costInfo = null;
    	for (Iterator iter = entries.iterator(); iter.hasNext();) {
    		ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
    		
    		costInfo = new CurProjCostEntriesInfo();
    		
    		costInfo.setApportionType(element.getApportionType());
    		costInfo.setValue(element.getIndexValue());
    		
    		coll.add(costInfo);
		}
    	
    	return coll;
    }
    
    /**
     * ����ԭ���Ľӿڣ���ָ�����ȡ��
     * @return
     */
    public static Map getCurProjCostEntries(Set prjSet) {
    	
    	if(prjSet == null )
    		return new HashMap();
    	if(prjSet.size() <= 0)
    		return new HashMap();
    	
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", prjSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("productType", null));
		filter.getFilterItems().add(new FilterItemInfo("projectStage", ProjectStageEnum.DYNCOST_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("entries.apportionType.*");
		view.getSelector().add("entries.indexValue");
		view.getSelector().add("projOrOrgID");
		
		ProjectIndexDataCollection projectIndexDataCollection = null;
		
		try {
			projectIndexDataCollection = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		}catch (RPCException ex){ //������ڷ���˵��ã���Ҫ�״��쳣�������쳣�����÷������˵ķ���
			// @AbortException
			Context context = ContextUtils.getContextFromSession();
			try {
				projectIndexDataCollection = ProjectIndexDataFactory.getLocalInstance(context).getProjectIndexDataCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} catch (BOSException e) {
			throw new RuntimeException(e);
		}
    	
    	if(projectIndexDataCollection == null || projectIndexDataCollection.size() == 0) return new HashMap();
    	HashMap map=new HashMap();
    	for (int i = 0; i < projectIndexDataCollection.size(); i++) {
			ProjectIndexDataInfo idxInfo = projectIndexDataCollection.get(i);
			ProjectIndexDataEntryCollection entries = idxInfo.getEntries();

			CurProjCostEntriesInfo costInfo = null;
			CurProjCostEntriesCollection coll = new CurProjCostEntriesCollection();
			for (Iterator iter = entries.iterator(); iter.hasNext();) {
				ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();

				costInfo = new CurProjCostEntriesInfo();

				costInfo.setApportionType(element.getApportionType());
				costInfo.setValue(element.getIndexValue());

				coll.add(costInfo);
			}
			map.put(idxInfo.getProjOrOrgID().toString(), coll);
		}
    	return map;
    }
    /**
     * ��Ŀ��Ʒָ�꣺��Ŀ��ɱ����㵼��
     * @param projId
     * @param projectStage
     * @return
     */
    public static Map getCurProjProEntrApporData(String projId,ProjectStageEnum projectStage) {
    	if(projId==null){
    		return new HashMap();
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", projId));
		filter.getFilterItems().add(new FilterItemInfo("productType.id", null,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("projectStage", projectStage.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("entries.apportionType.*");
		view.getSelector().add("entries.indexValue");
		view.getSelector().add("productType.id");
		
    	ProjectIndexDataCollection projectIndexDataCollection = null;
		try {
			projectIndexDataCollection = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		}catch (RPCException ex){	//������ڷ���˵��ã���Ҫ�״��쳣�������쳣�����÷������˵ķ���
			// @AbortException
			Context context = ContextUtils.getContextFromSession();
			try {
				projectIndexDataCollection = ProjectIndexDataFactory.getLocalInstance(context).getProjectIndexDataCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} catch (BOSException e) {
			throw new RuntimeException(e);
		}
    	
    	if(projectIndexDataCollection == null || projectIndexDataCollection.size() == 0) return new HashMap();
    	Map map=new HashMap();
    	for(int i=0;i<projectIndexDataCollection.size();i++){
	    	ProjectIndexDataInfo idxInfo = projectIndexDataCollection.get(i);
	    	ProjectIndexDataEntryCollection entries = idxInfo.getEntries();
	    	CurProjProEntrApporDataCollection coll = new CurProjProEntrApporDataCollection();
	    	CurProjProEntrApporDataInfo adInfo = null;
	    	for (Iterator iter = entries.iterator(); iter.hasNext();) {
	    		ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
	    		
	    		adInfo = new CurProjProEntrApporDataInfo();
	    		
	    		adInfo.setApportionType(element.getApportionType());
	    		adInfo.setValue(element.getIndexValue());
	    		
	    		coll.add(adInfo);
			}
	    	map.put(idxInfo.getProductType().getId().toString(), coll);
    	}
    	return map;
    }
    
    /**
     * ȡ�����¼�������Ŀ
     * @author hpw date:2009-8-26
     * @param ctx
     * @param curProjectID
     * @return
     * @throws BOSException
     */
    public static Map getCurProjChildInfos(Context ctx, String curProjectID) throws BOSException{
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select child.fid from t_fdc_curproject parent \n");
		builder.appendSql("inner join t_fdc_curproject child on charindex(parent.flongnumber||'!',child.flongnumber||'!')=1 \n");
		builder.appendSql("where parent.fid = ? \n");
		builder.addParam(curProjectID);
		IRowSet rs = null;
		Map childInfos = new HashMap();
		if(ctx==null){
			rs = builder.executeQuery();
		}else{
			rs = builder.executeQuery(ctx);
		}
		try {
			while(rs.next()){
				String key = rs.getString("fid");
				childInfos.put(key, null); //����Ҫ,�ٸ�info��ֵ
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		childInfos.remove(curProjectID);//ȥ������
		return childInfos;
    }
    
    public static boolean checkBeforeModifyIsDevPrj(CurProjectInfo info) throws EASBizException, BOSException{
    	return ProjectFacadeFactory.getRemoteInstance().checkBeforeModifyIsDevPrj(info);
    }
    
    /**
     * ȡ������֯�µĹ�����Ŀ
     * @author pengwei_hou
     */
    public static Set getCompanyPrjs(String comId) throws BOSException{
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select fid from t_fdc_curproject where ffullorgunit=? ");
    	builder.addParam(comId);
    	IRowSet rs = builder.executeQuery();
    	Set prjs = new HashSet();
    	if(rs==null||rs.size()==0){
    		return prjs;
    	}
    	try {
			while(rs.next()){
				prjs.add(rs.getString("fid"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	return prjs;
    }
    
    /**
     * ����ȡ������֯�µĹ�����Ŀ
     * @param comIds
     * @return
     * @throws BOSException
     * @author pengwei_hou 2011.11.18
     */
    public static Set getCompanysPrjs(Set comIds) throws BOSException{
    	Set prjs = new HashSet();
		if (comIds.size() == 0) {
			//�Դ���Ĳ������д�������sizeΪ0ʱ��builder.executeQuery()���׳��쳣��Added by Owen_wen 2013-9-3
			return prjs;
		}
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select fid from t_fdc_curproject where ");
    	builder.appendParam("ffullorgunit",comIds.toArray(),"VARCHAR");
    	IRowSet rs = builder.executeQuery();
    	try {
			while(rs.next()){
				prjs.add(rs.getString("fid"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
    	return prjs;
    }
}

