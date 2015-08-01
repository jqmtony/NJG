/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.LandDeveloperCollection;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusCollection;
import com.kingdee.eas.fdc.basedata.ProjectStatusFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.DbUtil;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		朱俊
 * @version		EAS7.0		
 * @createDate	2011-6-15	 
 * @see						
 */
public class ProjectTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	/** 用于接收保存一个完整的工程项目长编码，方便后续调用修改该导入的工程项目长编码 */
	private String str_LongNumber = "";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return CurProjectFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		CurProjectInfo info = new CurProjectInfo();
		ProjectStatusInfo projectStatusInfo = null;
		ProjectTypeInfo projectTypeInfo = null;
		LandDeveloperInfo landDeveloperInfo = null;
		ProductTypeInfo productTypeInfo = null;
		FullOrgUnitInfo fullOrgUnit = null; 
		FullOrgUnitInfo baseUnitInfo = null;

		//组织编码
		String fCostOrgLongNumber=(String) ((DataToken) hsData.get("FCostOrg_longNumber")).data;
		//工程项目长编码
		String fLongNumber=(String) ((DataToken) hsData.get("FLongNumber")).data;
		//项目状态
		String fProjectStatusName=(String) ((DataToken) hsData.get("FProjectStatus_name_l2")).data;
		//项目系列
		String fProjectTypeName=(String) ((DataToken) hsData.get("FProjectType_name_l2")).data;
		//名称
		String fName=(String) ((DataToken) hsData.get("FName_l2")).data;
		//工程简码
		String fCodingNumber=(String) ((DataToken) hsData.get("FCodingNumber")).data;
		//开发项目
		String fIsDevPrj=(String) ((DataToken) hsData.get("FIsDevPrj")).data;
		//甲方
		String fLandDeveloperName=(String) ((DataToken) hsData.get("FLandDeveloper_name_l2")).data;
		//开始日期
		String fStartDate=(String) ((DataToken) hsData.get("FStartDate")).data;
		//开发顺序
		String fSortNo=(String) ((DataToken) hsData.get("FSortNo")).data;
		//描述
		String fDescription=(String) ((DataToken) hsData.get("FDescription_l2")).data;
		//项目地址
		String fProjectAddress=(String) ((DataToken) hsData.get("FProjectAddress")).data;
		//启用
		String fIsEnabled=(String) ((DataToken) hsData.get("FIsEnabled")).data;
		//产品类型/是否核算对象
		String fProductTypeIsAccObj=(String) ((DataToken) hsData.get("FProductType&isAccObj")).data;
		
		
		if (StringUtils.isEmpty(fCostOrgLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_CostOrgLongNumberIsNull",resource));
		}
		if (StringUtils.isEmpty(fLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsNull",resource));
		}
		if (fLongNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_LongNumberIsTooLong",resource));
		}
		if (StringUtils.isEmpty(fProjectStatusName)) {
			throw new TaskExternalException(getResource(ctx, "Import_ProjectStatusNameIsNull",resource));
		}
		if (StringUtils.isEmpty(fProjectTypeName)) {
			throw new TaskExternalException(getResource(ctx, "Import_ProjectTypeNameIsNull",resource));
		}
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsNull",resource));
		}
		if (!StringUtils.isEmpty(fName) && fName.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsTooLong",resource));
		}
		
		//工程简码
		info.setCodingNumber(fCodingNumber);
		
		//开发项目
		if (fIsDevPrj.trim().equals(getResource(ctx, "no",resourceCommon))) {
			info.setIsDevPrj(false);
		} else {
			info.setIsDevPrj(true);
		}
		
		//开始日期
		if (StringUtils.isEmpty(fStartDate)) {
			throw new TaskExternalException(getResource(ctx, "Import_StartDateIsNull",resource));
		}
		info.setStartDate(FDCTransmissionHelper.checkDateFormat(fStartDate, getResource(ctx,"DateFormatIsWrong",resource)));
		
		//开发顺序
		if(StringUtils.isEmpty(fSortNo)) {
			info.setSortNo(0);
		}else if(fSortNo.matches("^\\d*|\\d*.\\d*$")) {
			int a;
			double b = Double.parseDouble(fSortNo);
			a = (int)b;
			info.setSortNo(a);
		}else {
			throw new TaskExternalException(getResource(ctx, "Import_IsNotNumber",resource));
		}
		//描述
		if (!StringUtils.isEmpty(fDescription) && fDescription.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "Import_DescriptionTooLength",resource));
		}
		info.setDescription(fDescription);
		//项目地址
		if (!StringUtils.isEmpty(fProjectAddress) && fProjectAddress.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_AddressTooLang",resource));
		}
		info.setProjectAddress(fProjectAddress);
		
		//启用
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsEnabled(true);
		}else {
			info.setIsEnabled(false);
		}

		try {
			
			//名称
			FilterInfo curProjectNameFilter = new FilterInfo();
			EntityViewInfo curProjectNameView = new EntityViewInfo();
			curProjectNameFilter.getFilterItems().add(new FilterItemInfo("name", fName));
			curProjectNameView.setFilter(curProjectNameFilter);
			CurProjectCollection curProjectNameColl = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectNameView);
			if(curProjectNameColl.size()>0){
				throw new TaskExternalException(getResource(ctx, "Import_NameIsExist",resource));
			}else{
				info.setName(fName);
			}
			
			// 工程项目长编码
			FilterInfo curFilter = new FilterInfo();
			EntityViewInfo curView = new EntityViewInfo();
			curFilter.getFilterItems().add(new FilterItemInfo("LongNumber", fLongNumber.replace('.', '!')));
			curView.setFilter(curFilter);
			CurProjectCollection curcoll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curView);
			if(curcoll.size()>0){
				throw new TaskExternalException(getResource(ctx, "Import_IsExist",resource));
			}else{
				info.setLongNumber(fLongNumber.replace('.', '!'));
			}
			//给编码设值
			String[] numbers = fLongNumber.replace('.', '!').split("!");
			if(numbers.length>1)
			{
				info.setNumber(numbers[numbers.length-1]);
			}else if(numbers.length == 1)
			{
				info.setNumber(fLongNumber.replace('.', '!'));
			}
			
			//组织编码		
			FilterInfo filterfullOrgUnit = new FilterInfo();
			filterfullOrgUnit.getFilterItems().add(new FilterItemInfo("longnumber", fCostOrgLongNumber.replace('.', '!')));
			EntityViewInfo viewfullOrgUnit = new EntityViewInfo();
			viewfullOrgUnit.setFilter(filterfullOrgUnit);
			FullOrgUnitCollection fullOrgUnitColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(viewfullOrgUnit);
			//如果是组织编码
			if (fullOrgUnitColl.size() > 0){
				fullOrgUnit = fullOrgUnitColl.get(0);
				//是否是实体财务组织
				if(fullOrgUnit.isIsCompanyOrgUnit()){
					//在财务组织中查是否存在
					FilterInfo companyOrgUnitFilter = new FilterInfo();
					companyOrgUnitFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCostOrgLongNumber.replace('.', '!')));
					EntityViewInfo companyOrgUnitView = new EntityViewInfo();
					companyOrgUnitView.setFilter(companyOrgUnitFilter);
					CompanyOrgUnitCollection companyOrgUnitColl = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(companyOrgUnitView);
					if(companyOrgUnitColl.size() > 0) {
						info.setFullOrgUnit(fullOrgUnit);
					}
				}
				//是否是成本中心
				else if(fullOrgUnit.isIsCostOrgUnit()){
					//在成本中心中查是否存在
					FilterInfo costCenterOrgUnitFilter = new FilterInfo();
					costCenterOrgUnitFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCostOrgLongNumber.replace('.', '!')));
					EntityViewInfo costCenterOrgUnitView = new EntityViewInfo();
					costCenterOrgUnitView.setFilter(costCenterOrgUnitFilter);
					CostCenterOrgUnitCollection costCenterOrgUnitColl = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(costCenterOrgUnitView);
					if(costCenterOrgUnitColl.size() > 0) {
						costCenterOrgUnitColl.get(0);
						//得到上级组织编码
//						String baseUnitNumber = costCenterOrgUnitInfo.getParent().getNumber();//baseUnitNumber为空啊
						String[] baseUnitNumbers = fCostOrgLongNumber.replace('.', '!').split("!");
						String newBaseUnitNumber = null;
						for(int i=0; i<baseUnitNumbers.length-1; i++) {
							newBaseUnitNumber += baseUnitNumbers[i] + "!";
						}
						newBaseUnitNumber = newBaseUnitNumber.substring(4, newBaseUnitNumber.length()-1);
						FilterInfo baseUnitFilter = new FilterInfo();
						baseUnitFilter.getFilterItems().add(new FilterItemInfo("longnumber", newBaseUnitNumber));
						EntityViewInfo baseUnitView = new EntityViewInfo();
						baseUnitView.setFilter(baseUnitFilter);
						FullOrgUnitCollection baseUnitColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(baseUnitView);
						//如果是组织编码
						if (baseUnitColl.size() > 0){
							baseUnitInfo = baseUnitColl.get(0);
							info.setFullOrgUnit(baseUnitInfo);
//							info.setCostOrg(costCenterOrgUnitInfo);
						}
					}
				}
			}else {
				throw new TaskExternalException(getResource(ctx, "OrgUnitNumberNonexist",resource));
			}
			
			//项目状态
			FilterInfo projectStatusFilter = new FilterInfo();
			projectStatusFilter.getFilterItems().add(new FilterItemInfo("name", fProjectStatusName));
			EntityViewInfo projectStatusView = new EntityViewInfo();
			projectStatusView.setFilter(projectStatusFilter);
			ProjectStatusCollection projectStatusCollection = ProjectStatusFactory.getLocalInstance(ctx).getProjectStatusCollection(projectStatusView);
			if (projectStatusCollection.size() > 0){
				projectStatusInfo = projectStatusCollection.get(0);
				info.setProjectStatus(projectStatusInfo);
			}else{
				throw new TaskExternalException(getResource(ctx, "ProjectStatus",resource)+getResource(ctx, "Import_IsNotExist",resource));
			}
		
			//项目系列
			FilterInfo projectTypeFilter = new FilterInfo();
			projectTypeFilter.getFilterItems().add(new FilterItemInfo("name", fProjectTypeName));
			EntityViewInfo projectTypeView = new EntityViewInfo();
			projectTypeView.setFilter(projectTypeFilter);
			ProjectTypeCollection projectTypeCollection = ProjectTypeFactory.getLocalInstance(ctx).getProjectTypeCollection(projectTypeView);
			if (projectTypeCollection.size() > 0){
				projectTypeInfo = projectTypeCollection.get(0);
				info.setProjectType(projectTypeInfo);
			}else{
				throw new TaskExternalException(getResource(ctx, "ProjectType",resource)+getResource(ctx, "Import_IsNotExist",resource));
			}
		
			//甲方
			FilterInfo landDeveloperFilter = new FilterInfo();
			landDeveloperFilter.getFilterItems().add(new FilterItemInfo("name", fLandDeveloperName));
			EntityViewInfo landDeveloperView = new EntityViewInfo();
			landDeveloperView.setFilter(landDeveloperFilter);
			LandDeveloperCollection landDeveloperCollection = LandDeveloperFactory.getLocalInstance(ctx).getLandDeveloperCollection(landDeveloperView);
			if (landDeveloperCollection.size() > 0){
				landDeveloperInfo = landDeveloperCollection.get(0);
				info.setLandDeveloper(landDeveloperInfo);
			}
			
			
			// 产品类型/是否核算对象 
//			if(StringUtils.isEmpty(fProductTypeIsAccObj)) {
//				throw new TaskExternalException(getResource(ctx, "Import_FormatIsNotRight"));
//			}
			if(!StringUtils.isEmpty(fProductTypeIsAccObj))
			{
				String [] fProductTypeIsAccObjs = fProductTypeIsAccObj.split(",");
	//			if(fProductTypeIsAccObjs.length == 1) {
	//				throw new TaskExternalException(getResource(ctx, "Import_FormatIsNotRight"));
	//			}
				String fProductType = fProductTypeIsAccObjs[0];
				String fIsAccObjs = "";
				if(fProductTypeIsAccObjs.length>=2 ){//
					fIsAccObjs = fProductTypeIsAccObjs[1];
				}
				if(StringUtils.isEmpty(fProductType)) {
					throw new TaskExternalException(getResource(ctx, "Import_ProductTypeIsNull",resource));
				}
				CurProjProductEntriesInfo curProjProductEntriesInfo = new CurProjProductEntriesInfo();
				
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("name", fProductType));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				ProductTypeCollection productTypeCollection = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if (productTypeCollection.size() > 0){
					productTypeInfo = productTypeCollection.get(0);
					curProjProductEntriesInfo.setProductType(productTypeInfo);
				}else{
					throw new TaskExternalException(getResource(ctx, "Import_ProductTypeIsNotExsit",resource));
				}
				if (fIsAccObjs.trim().equals(getResource(ctx, "no",resourceCommon))) {
					curProjProductEntriesInfo.setIsAccObj(false);
				}else {
					curProjProductEntriesInfo.setIsAccObj(true);
				}
				curProjProductEntriesInfo.setCurProject(info);
				info.getCurProjProductEntries().add(curProjProductEntriesInfo);
			}

			// 因为直接返回info之后数据库中保存的LongNumber会自动更改为与number相同。因此在返回之前将LongNumber赋给全局变量str_LongNumber方便后续修改LongNumber
			str_LongNumber = info.getLongNumber();
			
			return info;			
		}catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
	
	/**
	 * @description		重新submit方法，用于修改导入的工程项目的长编码！
	 * @author				雍定文		
	 * @createDate			2011-7-21
	 * @param				coreBaseInfo
	 * @param				context
	 * @return				void	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#submit(com.kingdee.eas.framework.CoreBaseInfo, com.kingdee.bos.Context)					
	 */
	public void submit(CoreBaseInfo coreBaseInfo, Context context) throws TaskExternalException {
		
		// 在保存该导入的工程项目之前需要判断是否有工程项目
		// 得到全局变量str_LongNumber，如果得到的工程项目为空则抛出消息提示！
		String longNumber = str_LongNumber.trim();
		if (StringUtils.isEmpty(longNumber)) {
			throw new TaskExternalException("导入工程项目失败，原因：没有获取到工程项目长编码！请尝试重新导入。");
		}
		
		/** 如果得到的工程项目不为空，则执行submit方法保存该导入的工程项目否则不执行<方法一开始已经判断> */
		super.submit(coreBaseInfo, context);
		
		/**	
		 * 修改工程项目长编码问题描述：
		 * 		因为不管在什么情况下<除了以下的直接SQL>去执行修改时，都会关联到CurProjectControllerBean.java类中的方法saveBaseProject，
		 * 		一但执行到该方法后，将会经过一系列的源处理，在处理之后的工程项目长编码与工程项目编码任然不会改变。
		 *  	因此采用了直接的SQL执行Update语句进行修改该工程项目长编码为所需的项目长编码！
		 *  可执行源SQL的理由： 因为工程项目的名称在数据库中没有可重复数据，因此可以判断唯一性。
		 */
		try {
			String sql_UpdateLongNumber = "update T_FDC_CurProject set  FLongNumber = ? where FName_l2 = ?";
			Object[] obj_params = new Object[] {longNumber, ((CurProjectInfo) coreBaseInfo).getName()};
			DbUtil.execute(context, sql_UpdateLongNumber, obj_params);
		} catch (BOSException e) {
			// @AbortException 如果SQL执行失败则提示并将该导入的工程项目记录执行Delete删除
			String sql_DeleteCurProjectInfo = "delete from T_FDC_CurProject where FName_l2 = ?";
			Object[] obj_param = new Object[] {((CurProjectInfo) coreBaseInfo).getName()};
			try {
				DbUtil.execute(context, sql_DeleteCurProjectInfo, obj_param);
			} catch (BOSException e1) {
				// 如果删除失败时：
				throw new TaskExternalException("你导入的工程项目已经成为数据库中的脏数据，建议清理数据库！温馨提示：在导入工程项目时请保证数据的正确性，以免出现错误！！");
			}

		}
		
//		//标注不可用，原因以上已经注明
//		/** 在改工程项目成功保存之后进行修改该工程项目的长编码<原因为该工程项目在之前保存时编码与长编码相同> */
//		try {
//			if (!(coreBaseInfo instanceof CurProjectInfo)) {
//				return;
//			}
//			CurProjectInfo curProject = (CurProjectInfo) coreBaseInfo;
//			// 查找工程项目编码为null的数据修改其编码。
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("number", curProject.getNumber()));
//			EntityViewInfo view = new EntityViewInfo();
//			view.setFilter(filter);
//			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view);
//			if (curProjectColl.size() > 0) {
//				CurProjectInfo curProjectInfo = curProjectColl.get(0);
//				//给长编码设值
//				curProjectInfo.setLongNumber(longNumber);
//				CurProjectFactory.getLocalInstance(context).update(new ObjectUuidPK(String.valueOf(curProjectInfo.getId())), curProjectInfo);
//			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		} catch (EASBizException e) {
//			e.printStackTrace();
//		}
		
	}
	
	/**
	 * 得到资源文件
	 * @author 朱俊
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
	 
	
}
