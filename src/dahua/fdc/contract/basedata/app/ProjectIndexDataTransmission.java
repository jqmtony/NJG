/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.master.util.StringUtil;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		�ű�
 * @version		EAS7.0		
 * @createDate	2011-7-5	 
 * @see						
 */
public class ProjectIndexDataTransmission extends AbstractDataTransmission{

	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ProjectIndexDataFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}
	
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		// ��֯����
		String orgUnitNumber = (String)((DataToken)hsData.get("FOrgUnit_number")).data;		
		// ������Ŀ������
		String projectLongNumber = (String)((DataToken)hsData.get("FProject_longNumber")).data;	
		// ������Ŀ����
		String projectName = (String)((DataToken)hsData.get("FProject_name")).data;	
		// ��Ʒ���ͳ�����
		String productTypeNumber = (String)((DataToken)hsData.get("FProductType_number")).data;				
		// ��̬-Ԥ�۲���_�������
		String asIndex_buildArea = (String)((DataToken)hsData.get("asIndex_buildArea")).data;		
		// ��̬-Ԥ�۲���_�������
		String asIndex_sellArea = (String)((DataToken)hsData.get("asIndex_sellArea")).data;		
		// ��̬-Ԥ�۲���_ռ�����
		String asIndex_placeArea = (String)((DataToken)hsData.get("asIndex_placeArea")).data;		
		// ��̬-��������_�������
		String acIndex_buildArea = (String)((DataToken)hsData.get("acIndex_buildArea")).data;		
		// ��̬-��������_������� 
		String acIndex_sellArea = (String)((DataToken)hsData.get("acIndex_sellArea")).data;	
		// ��̬-��������_ռ�����
		String acIndex_placeArea = (String)((DataToken)hsData.get("acIndex_placeArea")).data;
		
		ProjectIndexDataInfo projectIndexData = new ProjectIndexDataInfo();
		
		if(StringUtil.isEmpty(projectLongNumber)){
			throw new TaskExternalException(getResource(ctx, "ProjectNumberIsNull"));	
		}
		if(StringUtil.isEmpty(productTypeNumber)){
			throw new TaskExternalException(getResource(ctx, "ProductTypeNumberIsNull"));	
		}
        
        // ��ǰ������Ŀ
        CurProjectInfo projectInfo = null;
		try{
			/** ������Ŀ�����ѯ���� */
			FilterInfo curProjectInfo = new FilterInfo();
			curProjectInfo.getFilterItems().add(new FilterItemInfo("longNumber", projectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectInfoView = new EntityViewInfo();
			curProjectInfoView.setFilter(curProjectInfo);
			CurProjectCollection curProjectInfoColl = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectInfoView);
			if (curProjectInfoColl != null && curProjectInfoColl.size() > 0) {
				projectInfo = curProjectInfoColl.get(0);
				projectIndexData.setProjOrOrgID(projectInfo.getId());
				projectName = projectInfo.getName();
			} else {
				throw new TaskExternalException(getResource(ctx, "ProjectNonexist"));
			}
			
			/** ��֯�����ѯ���� */
			FilterInfo orgUnitNumberInfo = new FilterInfo();
			EntityViewInfo orgUnitNumberInfoView = new EntityViewInfo();		
			if(!StringUtils.isEmpty(orgUnitNumber)){
				orgUnitNumberInfo.getFilterItems().add(new FilterItemInfo("longNumber", orgUnitNumber.replace('.', '!')));							
			}else{
				// ��֯����Ϊ��ʱ�����ӹ�����Ŀ��Ӧ�ĳɱ����Ļ�ȡ
				orgUnitNumberInfo.getFilterItems().add(new FilterItemInfo("id",projectInfo.getCostCenter().getId()));	
			}
			orgUnitNumberInfoView.setFilter(orgUnitNumberInfo);			
			FullOrgUnitCollection orgUnitNumberColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(orgUnitNumberInfoView);
			if (orgUnitNumberColl != null && orgUnitNumberColl.size() > 0) {
				projectIndexData.setOrgUnit(orgUnitNumberColl.get(0));
			} else {
				throw new TaskExternalException(getResource(ctx, "OrgUnitNonexist"));
			}
			
			/** ��Ʒ���ͱ����ѯ���� */
			FilterInfo productTypeInfo = new FilterInfo();
			productTypeInfo.getFilterItems().add(new FilterItemInfo("number", productTypeNumber));
			EntityViewInfo productTypeInfoView = new EntityViewInfo();
			productTypeInfoView.setFilter(productTypeInfo);
			ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeInfoView);
			if(productTypeColl != null && productTypeColl.size() > 0){
				projectIndexData.setProductType(productTypeColl.get(0));
			}else{
				throw new TaskExternalException(getResource(ctx, "ProductTypeNonexist"));
			}
			
			FilterInfo targetTypeInfo = new FilterInfo();
			// ��¼ָ����������Ϊ"��Ʒ����ָ��"
			targetTypeInfo.getFilterItems().add(new FilterItemInfo("id","WhiXXQEOEADgAAFiwKgTu0gq1N0="));
			EntityViewInfo targetTypeInfoView = new EntityViewInfo();
			targetTypeInfoView.setFilter(targetTypeInfo);
			CoreBaseCollection targetTypeColl = TargetTypeFactory.getLocalInstance(ctx).getCollection(targetTypeInfoView);
			TargetTypeInfo typeInfo = null;
			if(targetTypeColl != null && targetTypeColl.size() > 0){
				typeInfo = (TargetTypeInfo)targetTypeColl.get(0);
			}
			
			projectIndexData.setProjectStage(ProjectStageEnum.DYNCOST);
			// ���ó�ʼ�汾��
			projectIndexData.setVerNo("V1.0");
			projectIndexData.setIsLatestVer(true);
			
			// ��̬-Ԥ�۲��ɵ���ͷ
			ProjectIndexDataInfo presellareaIndexData = new ProjectIndexDataInfo();
			presellareaIndexData.setProjOrOrgID(projectIndexData.getProjOrOrgID());
			presellareaIndexData.setOrgUnit(projectIndexData.getOrgUnit());
			presellareaIndexData.setProductType(projectIndexData.getProductType());
			presellareaIndexData.setProjectStage(projectIndexData.getProjectStage());
			presellareaIndexData.setVerNo(projectIndexData.getVerNo());
			presellareaIndexData.setIsLatestVer(projectIndexData.isIsLatestVer());
			presellareaIndexData.setVerName(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE);
			
			/** ��̬-Ԥ�۲���_������� ָ���¼���� **/	
			if(!StringUtil.isEmpty(asIndex_buildArea)){
				ProjectIndexDataEntryInfo projectIndexDataEntryInfo = new ProjectIndexDataEntryInfo();
				projectIndexDataEntryInfo.setParent(presellareaIndexData);			
				projectIndexDataEntryInfo.setTargetType(typeInfo);			
				projectIndexDataEntryInfo.setApportionType(this.getApportionType(ctx, "qHQt0wEMEADgAAaBwKgTuzW0boA="));
				projectIndexDataEntryInfo.setIndexValue(new BigDecimal(asIndex_buildArea));
				presellareaIndexData.getEntries().add(projectIndexDataEntryInfo);
			}
			
			 /** ��̬-Ԥ�۲���_������� ָ���¼����**/						
			if(!StringUtil.isEmpty(asIndex_sellArea)){
				ProjectIndexDataEntryInfo projectIndexDataEntryInfo = new ProjectIndexDataEntryInfo();
				projectIndexDataEntryInfo.setParent(presellareaIndexData);			
				projectIndexDataEntryInfo.setTargetType(typeInfo);		
				projectIndexDataEntryInfo.setApportionType(this.getApportionType(ctx, "qHQt0wEMEADgAAaHwKgTuzW0boA="));
				projectIndexDataEntryInfo.setIndexValue(new BigDecimal(asIndex_sellArea));
				presellareaIndexData.getEntries().add(projectIndexDataEntryInfo);
			}
			
			/** ��̬-Ԥ�۲���_ռ����� ָ���¼����**/	
			if(!StringUtil.isEmpty(asIndex_placeArea)){
				ProjectIndexDataEntryInfo projectIndexDataEntryInfo = new ProjectIndexDataEntryInfo();
				projectIndexDataEntryInfo.setParent(presellareaIndexData);			
				projectIndexDataEntryInfo.setTargetType(typeInfo);		
				projectIndexDataEntryInfo.setApportionType(this.getApportionType(ctx, "qHQt0wEMEADgAAaMwKgTuzW0boA="));
				projectIndexDataEntryInfo.setIndexValue(new BigDecimal(asIndex_placeArea));
				presellareaIndexData.getEntries().add(projectIndexDataEntryInfo);
			}
			
			// ��̬-�������ɵ���ͷ
			ProjectIndexDataInfo completeareaIndexData = new ProjectIndexDataInfo();
			completeareaIndexData.setProjOrOrgID(projectIndexData.getProjOrOrgID());
			completeareaIndexData.setOrgUnit(projectIndexData.getOrgUnit());
			completeareaIndexData.setProductType(projectIndexData.getProductType());
			completeareaIndexData.setProjectStage(projectIndexData.getProjectStage());
			completeareaIndexData.setVerNo(projectIndexData.getVerNo());
			completeareaIndexData.setIsLatestVer(projectIndexData.isIsLatestVer());
			completeareaIndexData.setVerName(ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE);
			
			/** ��̬-��������_������� ָ���¼����**/	
			if(!StringUtil.isEmpty(acIndex_buildArea)){
				ProjectIndexDataEntryInfo projectIndexDataEntryInfo = new ProjectIndexDataEntryInfo();
				projectIndexDataEntryInfo.setParent(completeareaIndexData);			
				projectIndexDataEntryInfo.setTargetType(typeInfo);			
				projectIndexDataEntryInfo.setApportionType(this.getApportionType(ctx, "qHQt0wEMEADgAAaBwKgTuzW0boA="));
				projectIndexDataEntryInfo.setIndexValue(new BigDecimal(acIndex_buildArea));
				completeareaIndexData.getEntries().add(projectIndexDataEntryInfo);
			}
			
			/** ��̬-��������_������� ָ���¼����**/	
			if(!StringUtil.isEmpty(acIndex_sellArea)){
				ProjectIndexDataEntryInfo projectIndexDataEntryInfo = new ProjectIndexDataEntryInfo();
				projectIndexDataEntryInfo.setParent(completeareaIndexData);			
				projectIndexDataEntryInfo.setTargetType(typeInfo);		
				projectIndexDataEntryInfo.setApportionType(this.getApportionType(ctx, "qHQt0wEMEADgAAaHwKgTuzW0boA="));
				projectIndexDataEntryInfo.setIndexValue(new BigDecimal(acIndex_sellArea));
				completeareaIndexData.getEntries().add(projectIndexDataEntryInfo);
			}
			
			/** ��̬-��������_ռ����� ָ���¼����**/	
			if(!StringUtil.isEmpty(acIndex_placeArea)){
				ProjectIndexDataEntryInfo projectIndexDataEntryInfo = new ProjectIndexDataEntryInfo();
				projectIndexDataEntryInfo.setParent(completeareaIndexData);			
				projectIndexDataEntryInfo.setTargetType(typeInfo);		
				projectIndexDataEntryInfo.setApportionType(this.getApportionType(ctx, "qHQt0wEMEADgAAaMwKgTuzW0boA="));
				projectIndexDataEntryInfo.setIndexValue(new BigDecimal(acIndex_placeArea));
				completeareaIndexData.getEntries().add(projectIndexDataEntryInfo);
			}
				
			/** ��Ŀ��Ʒ��¼���� **/
			FilterInfo curProjProductEntriesInfo = new FilterInfo();
			curProjProductEntriesInfo.getFilterItems().add(new FilterItemInfo("curProject.id",projectIndexData.getProjOrOrgID().toString()));
			curProjProductEntriesInfo.getFilterItems().add(new FilterItemInfo("productType.id",projectIndexData.getProductType().getId().toString()));
			EntityViewInfo curProjProductEntriesInfoView = new EntityViewInfo();
			curProjProductEntriesInfoView.setFilter(curProjProductEntriesInfo);
			CurProjProductEntriesCollection curProjProductEntriesColl = CurProjProductEntriesFactory.getLocalInstance(ctx).getCurProjProductEntriesCollection(curProjProductEntriesInfoView);
			if(curProjProductEntriesColl == null || curProjProductEntriesColl.size() == 0){
				// û����Ŀ��Ʒ��¼ʱ������һ��
				CurProjProductEntriesInfo curProjProductEntries = new CurProjProductEntriesInfo();
				curProjProductEntries.setCurProject(projectInfo);
				curProjProductEntries.setProductType(projectIndexData.getProductType());
				CurProjProductEntriesFactory.getLocalInstance(ctx).addnew(curProjProductEntries);
			}	
			// ������̬-Ԥ�۲��ɵ���ͷ
			ProjectIndexDataFactory.getLocalInstance(ctx).addnew(presellareaIndexData);
			// ������̬-�������ɵ���ͷ
			ProjectIndexDataFactory.getLocalInstance(ctx).addnew(completeareaIndexData);
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * �����������ֵ��ȡ���������̯����
	 * @description		
	 * @author			�ű�
	 * @createDate		2011-7-6
	 * @param ctx
	 * @param fID
	 * @return
	 * @throws BOSException ApportionTypeInfo
	 * @version			EAS1.0
	 * @see
	 */
	public ApportionTypeInfo getApportionType(Context ctx,String fID) throws BOSException{
		FilterInfo apportionTypeInfo = new FilterInfo();
		apportionTypeInfo.getFilterItems().add(new FilterItemInfo("id",fID));
		EntityViewInfo apportionTypeInfoView = new EntityViewInfo();
		apportionTypeInfoView.setFilter(apportionTypeInfo);
		CoreBaseCollection apportionTypeColl = ApportionTypeFactory.getLocalInstance(ctx).getCollection(apportionTypeInfoView);
		ApportionTypeInfo apportionType = null;
		if(apportionTypeColl != null && apportionTypeColl.size() > 0){
			apportionType = (ApportionTypeInfo) apportionTypeColl.get(0);
		}
		return apportionType;
	}
	
	/**
	 * 
	 * @description		��ȡ��Դ�ļ���Ϣ
	 * @author			�ű�
	 * @createDate		2011-7-5
	 * @param ctx
	 * @param key
	 * @return String
	 * @version			EAS1.0
	 * @see
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
